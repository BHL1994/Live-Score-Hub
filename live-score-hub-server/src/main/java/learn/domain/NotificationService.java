package learn.domain;

import learn.data.NotificationJdbcTemplateRepository;
import learn.models.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationJdbcTemplateRepository repository;

    public NotificationService(NotificationJdbcTemplateRepository repository) {
        this.repository = repository;
    }

    public Notification findById(int notificationId) {
        return repository.findById(notificationId);
    }

    public List<Notification> findByUserId(int userId) {
        return repository.findByUserId(userId);
    }

    public List<Notification> findByGameId(int gameId) {
        return repository.findByGameId(gameId);
    }

    public Notification findByUserIdAndGameId(int userId, int gameId) {
        return repository.findByUserIdAndGameId(userId, gameId);
    }

    public Notification add(Notification notification) {
        return repository.add(notification);
    }

    public boolean update(Notification notification) {
        return repository.update(notification);
    }

    public boolean delete(int notificationId) {
        return repository.delete(notificationId);
    }
}
