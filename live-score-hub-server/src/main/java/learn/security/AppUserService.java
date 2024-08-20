package learn.security;

import learn.App;
import learn.data.AppUserRepository;
import learn.domain.ActionStatus;
import learn.models.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import learn.domain.Result;

import java.util.List;

@Service
public class AppUserService implements UserDetailsService {
    private final AppUserRepository repository;
    private final PasswordEncoder encoder;

    public AppUserService(AppUserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = repository.findByUsername(username);

        if (appUser == null || !appUser.isEnabled()) {
            throw new UsernameNotFoundException(username + " not found");
        }

        return appUser;
    }

    public UserDetails loadUserByEmail(String email) throws Exception {
        AppUser appUser = repository.findByEmail(email);

        if(appUser == null || !appUser.isEnabled()) {
            throw new Exception(email + " not found.");
        }

        return appUser;
    }

    public Result<AppUser> create(String username, String email, String password) {
        Result<AppUser> result = validate(username, email, password);
        if(!result.isSuccess()) {
            return result;
        }

        if(repository.findByUsername(username) != null) {
            result.addMessage(ActionStatus.INVALID, "The provided username already exists.");
            return result;
        }

        if(repository.findByEmail(email) != null) {
            result.addMessage(ActionStatus.INVALID, "The provided email address already exists.");
            return result;
        }

        password = encoder.encode(password);

        AppUser appUser = new AppUser(0, username, email,  password, true, List.of("USER"));

        appUser = repository.create(appUser);
        result.setPayload(appUser);
        return result;
    }

    private Result<AppUser> validate(String username, String email, String password) {
        Result<AppUser> result = new Result<>();

        if(username == null || username.isBlank()) {
            result.addMessage(ActionStatus.INVALID, "Username is required");
            return result;
        }

        if (email == null || email.isBlank()) {
            result.addMessage(ActionStatus.INVALID, "Email is required");
            return result;
        }

        if (password == null) {
            result.addMessage(ActionStatus.INVALID, "Password is required");
            return result;
        }

        if (username.length() > 50) {
            result.addMessage(ActionStatus.INVALID, "Username must be less than 50 characters");
        }

        if(!isValidEmailAddress(email)) {
            result.addMessage(ActionStatus.INVALID, "Invalid email format.");
        }

        if (!isValidPassword(password)) {
            result.addMessage(ActionStatus.INVALID,
                    "Password must be at least 8 character and contain a digit," +
                            " a letter, and a non-digit/non-letter");
        }

        return result;
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        int digits = 0;
        int letters = 0;
        int others = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isLetter(c)) {
                letters++;
            } else {
                others++;
            }
        }

        return digits > 0 && letters > 0 && others > 0;
    }

    public static boolean isValidEmailAddress(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(regex);

    }
}
