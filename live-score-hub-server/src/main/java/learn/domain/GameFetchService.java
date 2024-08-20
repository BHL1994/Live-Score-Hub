package learn.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.data.GameRepository;
import learn.models.Game;
import learn.websockets.SocketHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GameFetchService {

    private final SportspageFeedService sportspageFeedService;
    private final GameRepository gameRepository;
    private final GameUpdateService gameUpdateService;
    private final ObjectMapper objectMapper;

    public GameFetchService(SportspageFeedService sportspageFeedService, GameRepository gameRepository, GameUpdateService gameUpdateService, ObjectMapper objectMapper) {
        this.sportspageFeedService = sportspageFeedService;
        this.gameRepository = gameRepository;
        this.gameUpdateService = gameUpdateService;
        this.objectMapper = objectMapper;
    }

//    @Scheduled(cron = "*/15 * * * * *")
//    public void fetchGamesForDateRange() {
//        LocalDate startDate = LocalDate.of(2024, 5, 1);
//        LocalDate endDate = LocalDate.of(2024, 9, 18);
//
//        while (!startDate.isAfter(endDate)) {
//            LocalDate weekEndDate = startDate.plusDays(6);
//            if (weekEndDate.isAfter(endDate)) {
//                weekEndDate = endDate;
//            }
//            sportspageFeedService.fetchAndSaveGamesForDateRange(startDate, weekEndDate);
//
//            startDate = startDate.plusDays(7);
//            System.out.println("done");
//        }
//    }

//    @Scheduled(cron = "*/60 * * * * *")
//    public void fetchGamesFromAPI() throws Exception {
//        sportspageFeedService.fetchAndSaveGamesForToday();
//
//        notifyClientsForLiveGames();
//    }

    private void notifyClientsForLiveGames() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        List<Game> liveGames = gameRepository.findByDate(now);

        for (Game game : liveGames) {
            // Convert the Game object to JSON string before sending
            String gameUpdateJson = objectMapper.writeValueAsString(game);
            gameUpdateService.sendGameUpdate(gameUpdateJson);
        }
    }
}
