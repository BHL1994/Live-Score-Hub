package learn.domain;

import learn.data.TeamRepository;
import learn.models.League;
import learn.models.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class TeamServiceTest {
    @MockBean
    TeamRepository repository;

    @Autowired
    TeamService service;

    @Test
    void shouldFindWhiteSoxById() {
        Team expected = new Team(1,"Chicago White Sox", "Chicago", "White Sox", League.MLB, "CWS", "https://a.espncdn.com/i/teamlogos/mlb/500/chw.png");

        when(repository.findById(1)).thenReturn(expected);

        Team actual = service.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindTeamsByCityAndName() {
        List<Team> expected = List.of(
                new Team(1,"Chicago White Sox", "Chicago", "White Sox", League.MLB, "CWS", "https://a.espncdn" +
                        ".com/i/teamlogos/mlb/500/chw.png"),
                new Team(2,"Detroit Tigers", "Detroit", "Tigers", League.MLB, "DET", "https://a.espncdn.com/i/teamlogos/mlb/500/det.png")
        );

        when(repository.findTeamsByCityAndName(anyString(), anyString(), anyString(), anyString())).thenReturn(expected);

        List<Team> actual = service.findTeamsByCityAndName("Chicago", "White Sox", "Detroit", "Tigers");

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindTeamByName() {
        Team expected = new Team(1,"Chicago White Sox", "Chicago", "White Sox", League.MLB, "CWS", "https://a.espncdn.com/i/teamlogos/mlb/500/chw.png");

        when(repository.findByName("Chicago White Sox")).thenReturn(expected);

        Team actual = service.findByName("Chicago White Sox");
        assertEquals(expected, actual);
    }
}