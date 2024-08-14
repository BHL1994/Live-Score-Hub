package learn.data;

import learn.models.AppUser;
import org.springframework.transaction.annotation.Transactional;

public interface AppUserRepository {
    @Transactional
    AppUser findByUsername(String username);

    @Transactional
    AppUser findByEmail(String email);

    @Transactional
    AppUser create(AppUser user);

    @Transactional
    boolean update(AppUser user);

}
