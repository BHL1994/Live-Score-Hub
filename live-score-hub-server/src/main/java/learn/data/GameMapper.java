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
        home.setId(rs.getInt("team_id"));
        home.setName(rs.getString("name"));
        home.setCity(rs.getString("city"));
        home.setTeam(rs.getString("team"));
        home.setLeague(League.valueOf(rs.getString("league")));
        home.setAbbreviation(rs.getString("abbreviation"));
        home.setLogoUrl(rs.getString("logo_url"));

        Team away = new Team();
        away.setId(rs.getInt("team_id"));
        away.setName(rs.getString("name"));
        away.setCity(rs.getString("city"));
        away.setTeam(rs.getString("team"));
        away.setLeague(League.valueOf(rs.getString("league")));
        away.setAbbreviation(rs.getString("abbreviation"));
        away.setLogoUrl(rs.getString("logo_url"));

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
