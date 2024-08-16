package learn.data;

import learn.models.Team;

import java.util.List;

public interface TeamRepository {
    Team findById(int id);

    List<Team> findTeamsByCityAndName(String homeCity, String homeTeam, String awayCity, String awayTeam);
}
