package learn.data;

import learn.models.Notification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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

    public Notification findById(int notificationId) {
        final String sql = """
                select
                    notification_id,
                    user_id,
                    game_id,
                    notification_type,
                    notification_time
                from notification
                where notification_id = ?;
                """;

        return jdbcTemplate.query(sql, new NotificationMapper(), notificationId).stream().findFirst().orElse(null);
    }

    public List<Notification> findByUserId(int userId) {
        final String sql = """
                select
                    n.notification_id,
                    n.user_id,
                    n.notification_type,
                    n.notification_time,
                    g.game_id,
                    g.game_date,
                    g.game_status,
                    g.period,
                    g.league,
                    g.time_remaining,
                    g.home_score,
                    g.away_score,
                    g.home_period_scores,
                    g.away_period_scores,
                    h.team_id as home_team_id,
                    h.team as home_team,
                    h.name as home_name,
                    h.city as home_city,
                    h.abbreviation as home_abbreviation,
                    h.league as home_league,
                    h.logo_url as home_logo_url,
                    a.team_id as away_team_id,
                    a.name as away_name,
                    a.city as away_city,
                    a.team as away_team,
                    a.abbreviation as away_abbreviation,
                    a.league as away_league,
                    a.logo_url as away_logo_url
                from notification n
                inner join game g on n.game_id = g.game_id
                inner join team h on g.home_id = h.team_id
                inner join team a on g.away_id = a.team_id
                where n.user_id = ?;
                """;

        return jdbcTemplate.query(sql, new NotificationMapper(), userId);
    }

    public List<Notification> findByGameId(int gameId) {
        final String sql = """
                select
                    n.notification_id,
                    n.user_id,
                    n.notification_type,
                    n.notification_time,
                    g.game_id,
                    g.game_date,
                    g.game_status,
                    g.period,
                    g.league,
                    g.time_remaining,
                    g.home_score,
                    g.away_score,
                    g.home_period_scores,
                    g.away_period_scores,
                    h.team_id as home_team_id,
                    h.team as home_team,
                    h.name as home_name,
                    h.city as home_city,
                    h.abbreviation as home_abbreviation,
                    h.league as home_league,
                    h.logo_url as home_logo_url,
                    a.team_id as away_team_id,
                    a.name as away_name,
                    a.team as away_team,
                    a.city as away_city,
                    a.abbreviation as away_abbreviation,
                    a.league as away_league,
                    a.logo_url as away_logo_url
                from notification n
                inner join game g on n.game_id = g.game_id
                inner join team h on g.home_id = h.team_id
                inner join team a on g.away_id = a.team_id
                where n.game_id = ?;
                """;

        return jdbcTemplate.query(sql, new NotificationMapper(), gameId);
    }

    @Override
    public Notification findByUserIdAndGameId(int userId, int gameId) {
        final String sql = """
                select
                    n.notification_id,
                    n.user_id,
                    n.notification_type,
                    n.notification_time,
                    g.game_id,
                    g.game_date,
                    g.game_status,
                    g.period,
                    g.league,
                    g.time_remaining,
                    g.home_score,
                    g.away_score,
                    g.home_period_scores,
                    g.away_period_scores,
                    h.team_id as home_team_id,
                    h.team as home_team,
                    h.name as home_name,
                    h.city as home_city,
                    h.abbreviation as home_abbreviation,
                    h.league as home_league,
                    h.logo_url as home_logo_url,
                    a.team_id as away_team_id,
                    a.team as away_team,
                    a.name as away_name,
                    a.city as away_city,
                    a.abbreviation as away_abbreviation,
                    a.league as away_league,
                    a.logo_url as away_logo_url
                from notification n
                inner join game g on n.game_id = g.game_id
                inner join team h on g.home_id = h.team_id
                inner join team a on g.away_id = a.team_id
                where n.user_id = ? and n.game_id = ?;
                """;

        return jdbcTemplate.query(sql, new NotificationMapper(), userId, gameId).stream().findFirst().orElse(null);
    }

    @Override
    public Notification add(Notification notification) {
        final String sql = """
            insert into notification (user_id, game_id, notification_type, notification_time)
                values (?, ?, ?, ?);
            """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, notification.getUserId());
            ps.setInt(2, notification.getGame().getId());
            ps.setString(3, notification.getNotificationType().name());
            ps.setTimestamp(4, Timestamp.valueOf(notification.getNotificationTime()));
            return ps;
        }, keyHolder);

        if (rowsAffected > 0) {
            notification.setId(keyHolder.getKey().intValue());
            return notification;
        }
        return null;
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
                notification.getNotificationType().name(),
                java.sql.Timestamp.valueOf(notification.getNotificationTime()),
                notification.getId()) > 0;
    }

    public boolean delete(int notificationId) {
        final String sql = "delete from notification where notification_id = ?";

        return jdbcTemplate.update(sql, notificationId) > 0;
    }
}

