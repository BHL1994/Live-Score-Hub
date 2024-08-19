package learn.data;

import learn.models.Notification;
import learn.models.NotificationType;
import learn.models.Game;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationMapper implements RowMapper<Notification> {

    @Override
    public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
        Notification notification = new Notification();
        notification.setId(rs.getInt("notification_id"));
        notification.setUserId(rs.getInt("user_id"));

        Game game = new Game();
        game.setId(rs.getInt("game_id"));
        notification.setGame(game);

        NotificationType notificationType = NotificationType.valueOf(rs.getString("notification_type"));
        notification.setNotificationType(notificationType);

        notification.setNotificationTime(rs.getTimestamp("notification_time").toLocalDateTime());

        return notification;
    }
}
