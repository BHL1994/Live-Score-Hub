package learn.domain;

import learn.data.GameRepository;
import learn.models.Game;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GameService {
    private final GameRepository repository;
    private final GameUpdateService gameUpdateService;

    public GameService(GameRepository repository, GameUpdateService gameUpdateService) {
        this.repository = repository;
        this.gameUpdateService = gameUpdateService;
    }

    public Game findById(int id) {
        return repository.findById(id);
    }

    public List<Game> findByDate(LocalDateTime date) {
        return repository.findByDate(date);
    }

    public Game findByDateAndTeams(LocalDateTime date, String homeName, String awayName) {
        return repository.findByDateAndTeams(date, homeName, awayName);
    }

    public List<Game> findByTeam(String city, String team) {
        return repository.findByTeam(city, team);
    }

    public Game add(Game game) {
        Game existingGame = repository.findByDateAndTeams(game.getGameDate(), game.getHome().getName(),
                game.getAway().getName());


        if(existingGame == null) {
            repository.add(game);
            return game;
        } else if (existingGame.getHomeScore() != game.getHomeScore() || existingGame.getAwayScore() != game.getAwayScore()) {
            existingGame.setHomeScore(game.getHomeScore());
            existingGame.setAwayScore(game.getAwayScore());
            update(existingGame);
            return existingGame;
        }
        return null;
    }

    public boolean update(Game game) {
        boolean success = repository.update(game);
        if (success) {
            String gameUpdate = String.format("Game updated: %s vs %s - %d:%d",
                    game.getHome().getName(), game.getAway().getName(), game.getHomeScore(), game.getAwayScore());
            gameUpdateService.sendGameUpdate(gameUpdate);
            return true;
        }
        return false;
    }

    public boolean delete(int id) {
        return repository.delete(id);
    }
}
