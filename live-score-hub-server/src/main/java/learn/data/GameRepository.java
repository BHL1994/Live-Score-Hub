package learn.data;

import learn.models.Game;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface GameRepository {
    Game findById(int id);

    List<Game> findByDate(LocalDateTime date);

    Game findByDateAndTeams(LocalDateTime date, String league, String homeName, String awayName);

    List<Game> findByTeam(String city, String team);

    List<Game> findGamesByLeagueAndDate(String league, LocalDate date);

    Game add(Game game);

    boolean update(Game game);

    boolean delete(int id);
}
