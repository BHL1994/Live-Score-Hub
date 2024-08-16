package learn.data;

import learn.models.Team;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeamJdbcTemplateRepository implements TeamRepository {
    private final JdbcTemplate jdbcTemplate;

    public TeamJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Team findById(int id) {
        final String sql = """
                select
                    team_id,
                    `name`,
                    city,
                    team,
                    league,
                    abbreviation,
                    logo_url
                from team
                where team_id = ?;
                """;
        return jdbcTemplate.query(sql, new TeamMapper(), id).stream().findFirst().orElse(null);
    }

    @Override
    public List<Team> findTeamsByCityAndName(String homeCity, String homeTeam, String awayCity, String awayTeam) {
        final String sql = """
                	select
                	team_id,
                    `name`,
                    city,
                    team,
                    league,
                    abbreviation,
                    logo_url
                from team
                where city = ? and team = ? or city = ? and team = ?;
                """;
        return jdbcTemplate.query(sql, new TeamMapper(), homeCity, homeTeam, awayCity, awayTeam);
    }
}
