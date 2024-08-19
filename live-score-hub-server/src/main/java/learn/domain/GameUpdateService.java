package learn.domain;

import learn.websockets.SocketHandler;
import org.springframework.stereotype.Service;

@Service
public class GameUpdateService {

    private final SocketHandler socketHandler;

    public GameUpdateService(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    public void sendGameUpdate(String gameUpdate) {
        try {
            socketHandler.broadcastScoreUpdate(gameUpdate);
        } catch (Exception e) {
            System.err.println("Error sending game update: " + e.getMessage());
        }
    }
}
