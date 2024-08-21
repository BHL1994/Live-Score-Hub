package learn.data;

import learn.models.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationMapper implements RowMapper<Notification> {
    @Override
    public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
        Notification notification = new Notification();

        notification.setId(rs.getInt("notification_id"));
        notification.setUserId(rs.getInt("user_id"));
        notification.setNotificationType(NotificationType.valueOf(rs.getString("notification_type")));
        notification.setNotificationTime(rs.getTimestamp("notification_time").toLocalDateTime());

        Game game = new Game();
        game.setId(rs.getInt("game_id"));
        game.setGameDate(rs.getTimestamp("game_date").toLocalDateTime());
        game.setStatus(rs.getString("game_status"));
        game.setPeriod(rs.getInt("period"));
        game.setLeague(League.valueOf(rs.getString("league")));
        game.setTimeRemaining(rs.getString("time_remaining"));
        game.setHomeScore(rs.getInt("home_score"));
        game.setAwayScore(rs.getInt("away_score"));
        game.setHomePeriodScores(rs.getString("home_period_scores"));
        game.setAwayPeriodScores(rs.getString("away_period_scores"));

        Team homeTeam = new Team();
        homeTeam.setId(rs.getInt("home_team_id"));
        homeTeam.setName(rs.getString("home_name"));
        homeTeam.setCity(rs.getString("home_city"));
        homeTeam.setAbbreviation(rs.getString("home_abbreviation"));
        homeTeam.setTeam(rs.getString("home_team"));
        homeTeam.setLeague(League.valueOf(rs.getString("home_league")));
        homeTeam.setLogoUrl(rs.getString("home_logo_url"));


        Team awayTeam = new Team();
        awayTeam.setId(rs.getInt("away_team_id"));
        awayTeam.setName(rs.getString("away_name"));
        awayTeam.setCity(rs.getString("away_city"));
        awayTeam.setAbbreviation(rs.getString("away_abbreviation"));
        awayTeam.setTeam(rs.getString("away_team"));
        awayTeam.setLeague(League.valueOf(rs.getString("away_league")));
        awayTeam.setLogoUrl(rs.getString("away_logo_url"));

        game.setAway(awayTeam);
        game.setHome(homeTeam);

        notification.setGame(game);

        return notification;
    }
}
