package learn.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.data.GameRepository;
import learn.data.TeamRepository;
import learn.models.Game;
import learn.models.Team;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SportspageFeedService {
    private final RestTemplate restTemplate;
    private final GameService gameService;
    private final TeamService teamService;
    private final ObjectMapper objectMapper;

    private final String baseUrl = System.getenv("API_URL");
    private final String apiKey = System.getenv("API_KEY");

    public SportspageFeedService(RestTemplate restTemplate, GameService gameService, TeamService teamService,
                                 ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.gameService = gameService;
        this.teamService = teamService;
        this.objectMapper = objectMapper;
    }

    public void fetchAndSaveGamesForDateRange(LocalDate startDate, LocalDate endDate) {
        String url = String.format("%s/games?date=%s,%s", baseUrl, startDate, endDate);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        Map<String, Object> body = response.getBody();
        if (body != null && body.containsKey("results")) {
            List<Map<String, Object>> gamesData = (List<Map<String, Object>>) body.get("results");
            List<Game> games = gamesData.stream()
                    .map(this::convertToGame)
                    .collect(Collectors.toList());

            for (Game game : games) {
                gameService.add(game);
            }
        }
    }

    public void fetchAndSaveGamesForToday() {
        String url = String.format("%s/games", baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        Map<String, Object> body = response.getBody();
        if (body != null && body.containsKey("results")) {
            List<Map<String, Object>> gamesData = (List<Map<String, Object>>) body.get("results");
            List<Game> games = gamesData.stream()
                    .map(this::convertToGame)
                    .collect(Collectors.toList());

            for (Game game : games) {
                gameService.add(game);
            }
        }
    }

    private Game convertToGame(Map<String, Object> gameData) {
        Game game = new Game();

        Map<String, Object> teams = (Map<String, Object>) gameData.get("teams");
        String homeName = (String) ((Map<String, Object>) teams.get("home")).get("team");
        String awayName = (String) ((Map<String, Object>) teams.get("away")).get("team");


        Map<String, Object> schedule = (Map<String, Object>) gameData.get("schedule");
        String dateString = (String) schedule.get("date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        LocalDateTime gameDate = LocalDateTime.parse(dateString, formatter);

        Team homeTeam = teamService.findByName(homeName);
        Team awayTeam = teamService.findByName(awayName);

        game.setHome(homeTeam);
        game.setAway(awayTeam);
        game.setGameDate(gameDate);

        System.out.println("In convert to game");
        System.out.println("Fetched Home Team: " + (homeTeam != null ? homeTeam.getName() : "null"));
        System.out.println("Fetched Away Team: " + (awayTeam != null ? awayTeam.getName() : "null"));


        Map<String, Object> scoreboard = (Map<String, Object>) gameData.get("scoreboard");

        if(scoreboard == null) {
            game.setHomeScore(0);
            game.setAwayScore(0);
        } else {
            Map<String, Object> score = (Map<String, Object>) scoreboard.get("score");
            game.setHomeScore((int) score.get("home"));
            game.setAwayScore((int) score.get("away"));
        }

        return game;
    }
}
