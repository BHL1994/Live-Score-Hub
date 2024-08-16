package learn.data;

import learn.models.Game;

import java.time.LocalDateTime;
import java.util.List;

public interface GameRepository {
    Game findById(int id);

    List<Game> findByDate(LocalDateTime date);

    Game findByDateAndTeams(LocalDateTime date, String cityHome, String teamHome, String cityAway, String teamAway);

    List<Game> findByTeam(String city, String team);


    Game add(Game game);

    boolean update(Game game);

    boolean delete(int id);
}
