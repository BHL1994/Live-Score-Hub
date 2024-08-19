package learn.domain;

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
            String gameUpdate = String.format("Game %s updated: %s vs %s - %d:%d",
                    game.getId(), game.getHome().getName(), game.getAway().getName(), game.getHomeScore(),
                    game.getAwayScore());
            gameUpdateService.sendGameUpdate(gameUpdate);


            //Game notification logic
//            Notification notification = new Notification();
//            notification.setAppUser(game.getId(););
//            notification.setGame(game);
//            notification.setNotificationType(NotificationType.GAME_END);
//            notification.setNotificationTime(LocalDateTime.now());
//            notificationService.add(notification);
//            notificationService.sendGameNotification(notification);

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
