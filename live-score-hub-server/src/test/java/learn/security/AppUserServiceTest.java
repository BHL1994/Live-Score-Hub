package learn.security;

import learn.App;
import learn.data.AppUserRepository;
import learn.domain.Result;
import learn.models.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class AppUserServiceTest {
    @MockBean
    AppUserRepository repository;

    @Autowired
    AppUserService service;

    @Test
    void shouldLoadUserByUsername() {
        AppUser expected = new AppUser(1, "JSmith", "john@smith.com", "Hashed-P@ssw0rd!", true, List.of("ADMIN"));

        when(repository.findByUsername("john@smith.com")).thenReturn(expected);

        UserDetails actual = service.loadUserByUsername("john@smith.com");

        assertEquals("JSmith", actual.getUsername());
        assertEquals("Hashed-P@ssw0rd!", actual.getPassword());
        assertEquals(1, actual.getAuthorities().size());
        assertTrue(actual.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")));
        assertTrue(actual.isEnabled());
    }

    @Test
    void shouldCreateValidAppUser() {
        String username = "BLowe";
        String password = "Val1dP@ssw0rd!";
        String email = "Blowe@gmail.com";

        AppUser expected = new AppUser(5, "BLowe", "Blowe@gmail.com", "HashedPassword", true, List.of("USER"));

        when(repository.create(any())).thenReturn(expected);

        Result<AppUser> result = service.create(username, email, password);

        assertTrue(result.isSuccess());
        assertEquals(5, result.getPayload().getAppUserId());
        assertEquals("BLowe", result.getPayload().getUsername());
        assertEquals("Blowe@gmail.com", result.getPayload().getEmail());
        assertEquals("HashedPassword", result.getPayload().getPassword());
        assertEquals(1, result.getPayload().getAuthorities().size());
        assertTrue(expected.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER")));
    }

    @Test
    void shouldNotCreateWithNullUsername() {
        Result<AppUser> result = service.create(null, "BLowe@gmail.com", "Val1dP@ssw0rd!");

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
        assertEquals("username is required", result.getMessages().get(0));
    }

    @Test
    void shouldNotCreateWithBlankUsername() {
        Result<AppUser> result = service.create("", "BLowe@gmail.com", "Val1dP@ssw0rd!");

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
        assertEquals("username is required", result.getMessages().get(0));
    }

    @Test
    void shouldNotCreateWithUsernameGreaterThan50Chars() {
        Result<AppUser> result = service.create("a".repeat(51), "Blowe@gmail.com", "Val1dP@ssw0rd!");

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
        assertEquals("username must be less than 50 characters", result.getMessages().get(0));
    }

    @Test
    void shouldNotCreateAppUserWithExistingUsername() {
        String username = "BLowe";
        String email = "BLowe@gmail.com";
        String password = "Val1dP@ssw0rd!";

        AppUser existingUser = new AppUser(1, username, email, "ExistingHashedPassword", true, List.of("USER"));

        when(repository.findByUsername(username)).thenReturn(existingUser);

        Result<AppUser> result = service.create(username, email, password);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
        assertEquals("The provided username already exists.", result.getMessages().get(0));
    }

    @Test
    void shouldNotCreateWithNullEmail() {
        Result<AppUser> result = service.create("Blowe", null, "Val1dP@ssw0rd!");

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
        assertEquals("email is required", result.getMessages().get(0));
    }

    @Test
    void shouldNotCreateWithBlankEmail() {
        Result<AppUser> result = service.create("Blowe", "", "Val1dP@ssw0rd!");

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
        assertEquals("email is required", result.getMessages().get(0));
    }

    @Test
    void shouldNotCreateWithInvalidEmailFormat() {
        Result<AppUser> result = service.create("Blowe", "email", "Val1dP@ssw0rd!");

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
        assertEquals("Invalid email format.", result.getMessages().get(0));

        result = service.create("Blowe", "email@", "Val1dP@ssw0rd!");
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
        assertEquals("Invalid email format.", result.getMessages().get(0));

        result = service.create("Blowe", "email@com", "Val1dP@ssw0rd!");
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
        assertEquals("Invalid email format.", result.getMessages().get(0));

        result = service.create("Blowe", "email@..com", "Val1dP@ssw0rd!");
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
        assertEquals("Invalid email format.", result.getMessages().get(0));
    }

    @Test
    void shouldNotCreateWithExistingEmail() {
        String username = "BLowe";
        String email = "BLowe@gmail.com";
        String password = "Val1dP@ssw0rd!";

        AppUser existingUser = new AppUser(1, username, email, "ExistingHashedPassword", true, List.of("USER"));

        when(repository.findByEmail(email)).thenReturn(existingUser);

        Result<AppUser> result = service.create(username, email, password);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
        assertEquals("The provided email address already exists.", result.getMessages().get(0));
    }

    @Test
    void shouldNotCreateWithNullPassword() {
        Result<AppUser> result = service.create("valid@username.com", "BLowe@gmail.com", null);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
        assertEquals("password is required", result.getMessages().get(0));
    }

    @Test
    void shouldNotCreatePasswordWithLessThan8Chars() {
        Result<AppUser> result = service.create("valid@username.com","BLowe@gmail.com", "invalid");

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
        assertEquals("password must be at least 8 character and contain a digit, a letter, and a non-digit/non-letter",
                result.getMessages().get(0));
    }

    @Test
    void shouldNotCreateWithoutNumberInPassword() {
        Result<AppUser> result = service.create("valid@username.com", "BLowe@gmail.com", "invalidp@ssword!");

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
        assertEquals("password must be at least 8 character and contain a digit, a letter, and a non-digit/non-letter",
                result.getMessages().get(0));
    }

    @Test
    void shouldNotCreateWithoutSpecialCharInPassword() {
        Result<AppUser> result = service.create("valid@username.com", "BLowe@gmail.com", "invalidp4ssword");

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals(1, result.getMessages().size());
        assertEquals("password must be at least 8 character and contain a digit, a letter, and a non-digit/non-letter",
                result.getMessages().get(0));
    }


}