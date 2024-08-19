package learn.data;

import learn.models.Notification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class NotificationJdbcTemplateRepository implements NotificationRepository {
    private final JdbcTemplate jdbcTemplate;

    public NotificationJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Notification findById(int notificationId) {
        final String sql = """
                select *
                from notification
                where notification_id = ?;
                """;

        return jdbcTemplate.query(sql, new NotificationMapper(), notificationId).stream().findFirst().orElse(null);
    }

    @Override
    public List<Notification> findByUserId(int userId) {
        final String sql = """
                select *
                from notification
                where user_id = ?;
                """;

        return jdbcTemplate.query(sql, new NotificationMapper(), userId);
    }

    @Override
    public List<Notification> findByGameId(int gameId) {
        final String sql = """
                select *
                from notification
                where game_id = ?;
                """;

        return jdbcTemplate.query(sql, new NotificationMapper(), gameId);
    }

    @Override
    public Notification add(Notification notification) {
        final String sql = """
                insert into notification (user_id, game_id, notification_type, notification_time)
                values (?, ?, ?, ?);
                """;

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, notification.getUserId());
            ps.setInt(2, notification.getGame().getId());
            ps.setString(3, notification.getNotificationType().getName());
            ps.setTimestamp(4, Timestamp.valueOf(notification.getNotificationTime()));
            return ps;
        }, keyHolder);

        notification.setId(keyHolder.getKey().intValue());
        return notification;
    }

    @Override
    public boolean update(Notification notification) {
        final String sql = """
                update notification set
                    user_id = ?,
                    game_id = ?,
                    notification_type = ?,
                    notification_time = ?
                where notification_id = ?;
                """;

        return jdbcTemplate.update(sql,
                notification.getUserId(),
                notification.getGame().getId(),
                notification.getNotificationType().getName(),
                notification.getNotificationTime(),
                notification.getId()) > 0;
    }

    @Override
    public boolean delete(int notificationId) {
        final String sql = "delete from notification where notification_id = ?";
        return jdbcTemplate.update(sql, notificationId) > 0;
    }
}
