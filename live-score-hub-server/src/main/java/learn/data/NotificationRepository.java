package learn.data;

import learn.models.Notification;

import java.util.List;

public interface NotificationRepository {
    Notification findById(int notificationId);

    List<Notification> findByUserId(int userId);

    List<Notification> findByGameId(int gameId);

    Notification add(Notification notification);

    boolean update(Notification notification);

    boolean delete(int notificationId);
}
