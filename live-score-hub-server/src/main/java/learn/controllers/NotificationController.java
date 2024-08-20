package learn.controllers;

import learn.domain.NotificationService;
import learn.models.Notification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUserId(@PathVariable int userId) {
        List<Notification> notifications = notificationService.findByUserId(userId);

        if (notifications.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/user/{userId}/game/{gameId}")
    public ResponseEntity<Notification> getNotificationByUserIdAndGameId(@PathVariable int userId, @PathVariable int gameId) {
        Notification notification = notificationService.findByUserIdAndGameId(userId, gameId);

        if (notification == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(notification);
    }

    @PostMapping
    public ResponseEntity<?> addNotification(@RequestBody Notification notification) {
        Notification result = notificationService.add(notification);

        if (result == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable int notificationId) {
        boolean isDeleted = notificationService.delete(notificationId);

        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<List<Notification>> getNotificationsByGameId(@PathVariable int gameId) {
        List<Notification> notifications = notificationService.findByGameId(gameId);

        if (notifications.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(notifications);
    }
}
