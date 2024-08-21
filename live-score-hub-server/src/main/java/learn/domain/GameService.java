package learn.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.data.GameRepository;
import learn.models.Game;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GameService {
    private final GameRepository repository;
    private final GameUpdateService gameUpdateService;
    private final ObjectMapper objectMapper;


    public GameService(GameRepository repository, GameUpdateService gameUpdateService, ObjectMapper objectMapper) {
        this.repository = repository;
        this.gameUpdateService = gameUpdateService;
        this.objectMapper = objectMapper;
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

    public List<Game> findGamesByLeagueAndDate(String league, LocalDate date) {
        return repository.findGamesByLeagueAndDate(league, date);
    }

    public Game add(Game game) {
        Game existingGame = repository.findById(game.getId());

        if (existingGame == null) {
            if (repository.add(game) != null) {
                return game;
            }
        } else if (hasGameChanged(existingGame, game)) {
            existingGame.setHomeScore(game.getHomeScore());
            existingGame.setAwayScore(game.getAwayScore());
            existingGame.setStatus(game.getStatus());
            existingGame.setPeriod(game.getPeriod());
            existingGame.setTimeRemaining(game.getTimeRemaining());

            if (update(existingGame)) {
                return existingGame;
            }
        }
        return null;
    }

    public boolean update(Game game) {
        boolean success = repository.update(game);
        if (success) {
            try {
                String gameUpdateJson = objectMapper.writeValueAsString(game);
                gameUpdateService.sendGameUpdate(gameUpdateJson);
            } catch (Exception e) {
                System.err.println("Error sending game update: " + e.getMessage());
            }
            return true;
        }
        return false;
    }

    public boolean delete(int id) {
        return repository.delete(id);
    }

    private boolean hasGameChanged(Game existingGame, Game newGame) {
        return existingGame.getHomeScore() != newGame.getHomeScore()
                || existingGame.getAwayScore() != newGame.getAwayScore()
                || !existingGame.getStatus().equals(newGame.getStatus())
                || existingGame.getPeriod() != newGame.getPeriod()
                || (existingGame.getTimeRemaining() != null && !existingGame.getTimeRemaining().equals(newGame.getTimeRemaining()));
    }
}
