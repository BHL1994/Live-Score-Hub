package learn.controllers;

import learn.domain.GameService;
import learn.models.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/api/games")
    public ResponseEntity<List<Game>> getGamesByLeagueAndDate(@RequestParam String league, @RequestParam String date) {
        List<Game> games = gameService.findGamesByLeagueAndDate(league, LocalDate.parse(date));

        if (games.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(games);
    }
}
