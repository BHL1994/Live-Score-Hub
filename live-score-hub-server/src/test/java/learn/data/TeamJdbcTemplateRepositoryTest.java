package learn.data;

import learn.models.League;
import learn.models.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeamJdbcTemplateRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TeamJdbcTemplateRepository repository;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("call set_known_good_state();");
    }

    @Test
    void shouldFindWhiteSoxById() {
        Team expected = new Team(1,"Chicago White Sox", "Chicago", "White Sox", League.MLB, "CWS", "https://a.espncdn.com/i/teamlogos/mlb/500/chw.png");
        Team actual = repository.findById(1);

        System.out.println(expected);
        System.out.println(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindWhiteSoxAndTigersByIds() {
        List<Team> expected = List.of(
                new Team(1,"Chicago White Sox", "Chicago", "White Sox", League.MLB, "CWS", "https://a.espncdn" +
                        ".com/i/teamlogos/mlb/500/chw.png"),
                new Team(2,"Detroit Tigers", "Detroit", "Tigers", League.MLB, "DET", "https://a.espncdn.com/i/teamlogos/mlb/500/det.png")
        );

        List<Team> actual = repository.findTeamsByCityAndName("Chicago", "White Sox", "Detroit", "Tigers");
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindWhiteSoxByName() {
        Team expected = new Team(1,"Chicago White Sox", "Chicago", "White Sox", League.MLB, "CWS", "https://a.espncdn.com/i/teamlogos/mlb/500/chw.png");
        Team actual = repository.findByName("Chicago White Sox");

        assertEquals(expected, actual);
    }
}