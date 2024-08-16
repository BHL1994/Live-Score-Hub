package learn.domain;

import learn.data.GameRepository;
import learn.models.Game;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GameService {
    private final GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public Game findById(int id) {
        return repository.findById(id);
    }

    public List<Game> findByDate(LocalDateTime date) {
        return repository.findByDate(date);
    }

    public Game findByDateAndTeams(LocalDateTime date, String cityHome, String teamHome, String cityAway, String teamAway) {
        return repository.findByDateAndTeams(date, cityHome, teamHome, cityAway, teamAway);
    }

    public List<Game> findByTeam(String city, String team) {
        return repository.findByTeam(city, team);
    }

    public Game add(Game game) {
        Game existingGame = repository.findByDateAndTeams(game.getGameDate(), game.getHome().getCity(), game.getHome().getTeam(), game.getAway().getCity(), game.getAway().getTeam());

        if(existingGame == null) {
            repository.add(game);
            return game;
        }
        return null;
    }

    public boolean update(Game game) {
        return repository.update(game);
    }

    public boolean delete(int id) {
        return repository.delete(id);
    }
}
