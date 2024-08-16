package learn.data;

import learn.models.League;
import learn.models.Team;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamMapper implements RowMapper<Team> {
    @Override
    public Team mapRow(ResultSet rs, int rowNum) throws SQLException {
        Team team = new Team();
        team.setId(rs.getInt("team_id"));
        team.setName(rs.getString("name"));
        team.setCity(rs.getString("city"));
        team.setTeam(rs.getString("team"));
        team.setLeague(League.valueOf(rs.getString("league")));
        team.setAbbreviation(rs.getString("abbreviation"));
        team.setLogoUrl(rs.getString("logo_url"));
        return team;
    }
}