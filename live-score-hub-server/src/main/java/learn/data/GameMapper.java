package learn.data;

import learn.models.Game;
import learn.models.League;
import learn.models.Team;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameMapper implements RowMapper<Game> {

    @Override
    public Game mapRow(ResultSet rs, int rowNum) throws SQLException {
        Game game = new Game();
        game.setId(rs.getInt("game_id"));

        Team home = new Team();
        home.setId(rs.getInt("home_team_id"));
        home.setName(rs.getString("home_name"));
        home.setCity(rs.getString("home_city"));
        home.setTeam(rs.getString("home_team"));
        home.setLeague(League.valueOf(rs.getString("home_league")));
        home.setAbbreviation(rs.getString("home_abbreviation"));
        home.setLogoUrl(rs.getString("home_logo_url"));

        Team away = new Team();
        away.setId(rs.getInt("away_team_id"));
        away.setName(rs.getString("away_name"));
        away.setCity(rs.getString("away_city"));
        away.setTeam(rs.getString("away_team"));
        away.setLeague(League.valueOf(rs.getString("away_league")));
        away.setAbbreviation(rs.getString("away_abbreviation"));
        away.setLogoUrl(rs.getString("away_logo_url"));

        game.setHome(home);
        game.setAway(away);
        game.setStatus(rs.getString("game_status"));
        game.setPeriod(rs.getInt("period"));
        game.setLeague(League.valueOf(rs.getString("league")));
        game.setTimeRemaining(rs.getString("time_remaining"));
        game.setGameDate(rs.getTimestamp("game_date").toLocalDateTime());
        game.setHomeScore(rs.getInt("home_score"));
        game.setAwayScore(rs.getInt("away_score"));

        return game;
    }
}
