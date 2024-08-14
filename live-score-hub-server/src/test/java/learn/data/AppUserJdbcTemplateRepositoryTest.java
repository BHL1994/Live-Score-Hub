package learn.data;

import learn.models.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppUserJdbcTemplateRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AppUserJdbcTemplateRepository repository;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("call set_known_good_state();");
    }

    @Test
    void shouldFindJohnSmithByUsername() {
        AppUser actual = repository.findByUsername("JSmith");
        assertTrue(actual.isEnabled());
        assertEquals(1, actual.getAuthorities().size());
        assertTrue(actual.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")));
    }

    @Test
    void shouldFindJohnSmithByEmail() {
        AppUser actual = repository.findByEmail("john@smith.com");
        assertTrue(actual.isEnabled());
        assertEquals(1, actual.getAuthorities().size());
        assertTrue(actual.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")));
    }

    @Test
    void shouldFindSallyJonesByUsername() {
        AppUser actual = repository.findByUsername("SJones");

        assertTrue(actual.isEnabled());
        assertEquals(1, actual.getAuthorities().size());
        assertTrue(actual.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER")));
    }

    @Test
    void shouldFindSallyJonesByEmail() {
        AppUser actual = repository.findByEmail("sally@jones.com");

        assertTrue(actual.isEnabled());
        assertEquals(1, actual.getAuthorities().size());
        assertTrue(actual.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER")));
    }

    @Test
    void shouldCreateNewUserNamedBrien() {
        AppUser appUser = new AppUser(0, "BLowe", "BLowe@gmail.com", "P@ssword1", true, List.of("ADMIN", "USER"));

        AppUser actual = repository.create(appUser);

        assertEquals(3, actual.getAppUserId());

        AppUser brien = repository.findByUsername("BLowe");

        assertTrue(brien.isEnabled());
        assertEquals("BLowe", brien.getUsername());
        assertEquals("BLowe@gmail.com", brien.getEmail());
        assertEquals("P@ssword1", brien.getPassword());
        assertEquals(2, brien.getAuthorities().size());
        assertTrue(brien.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")));
        assertTrue(brien.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER")));
    }

    @Test
    void shouldUpdateJohnSmith() {
        AppUser john = repository.findByUsername("JSmith");
        john.setEnabled(false);

        assertTrue(repository.update(john));
        assertFalse(john.isEnabled());
    }

}