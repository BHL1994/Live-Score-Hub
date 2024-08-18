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

    public Game findByDateAndTeams(LocalDateTime date, String league, String homeName, String awayName) {
        return repository.findByDateAndTeams(date, league, homeName, awayName);
    }

    public List<Game> findByTeam(String city, String team) {
        return repository.findByTeam(city, team);
    }

    public Game add(Game game) {
        System.out.println("In game Service add.");
        System.out.println("Fetched game Home Team: " + (game != null ? game.getHome().getName() :
                "null"));
        System.out.println("Fetched game Away Team: " + (game != null ? game.getAway().getName() :
                "null"));
        Game existingGame = repository.findByDateAndTeams(game.getGameDate(), game.getHome().getLeague().getName(),
                game.getHome().getName(),
                game.getAway().getName());

        System.out.println("In game Service add.");
        System.out.println("Fetched existing Home Team: " + (existingGame != null ? existingGame.getHome().getName() :
                "null"));
        System.out.println("Fetched existing Away Team: " + (existingGame != null ? existingGame.getAway().getName() :
                "null"));

        if(existingGame == null) {
            if(repository.add(game) != null) {
                return game;
            }
        } else if (existingGame.getHomeScore() != game.getHomeScore() || existingGame.getAwayScore() != game.getAwayScore()) {
            existingGame.setHomeScore(game.getHomeScore());
            existingGame.setAwayScore(game.getAwayScore());
            if(update(existingGame)){
                return existingGame;
            }
        }
        return null;
    }

    public boolean update(Game game) {
        System.out.println("In game Service before .");
        System.out.println("Fetched Home Team: " + (game != null ? game.getHome().getName() : "null"));
        System.out.println("Fetched Away Team: " + (game != null ? game.getAway().getName() : "null"));
        boolean success = repository.update(game);
        System.out.println("In game Service after update .");
        System.out.println("Fetched Home Team: " + (game != null ? game.getHome().getName() : "null"));
        System.out.println("Fetched Away Team: " + (game != null ? game.getAway().getName() : "null"));
        if (success) {
            String gameUpdate = String.format("Game %s updated: %s vs %s - %d:%d",
                    game.getId(), game.getHome().getName(), game.getAway().getName(), game.getHomeScore(),
                    game.getAwayScore());
            gameUpdateService.sendGameUpdate(gameUpdate);
            return true;
        }
        return false;
    }

    public boolean delete(int id) {
        return repository.delete(id);
    }
}
