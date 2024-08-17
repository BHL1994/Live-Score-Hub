package learn.data;

import learn.models.Game;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class GameJdbcTemplateRepository implements GameRepository{
    private final JdbcTemplate jdbcTemplate;


    public GameJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Game findById(int id) {
        final String sql = """
                select
                    g.game_id,
                    g.game_date,
                    g.home_score,
                    g.away_score,
                    h.team_id,
                    h.name,
                    h.city,
                    h.team,
                    h.league,
                    h.abbreviation,
                    h.logo_url,
                    a.team_id,
                    a.name,
                    a.city,
                    a.team,
                    a.league,
                    a.abbreviation,
                    a.logo_url
                from game g
                inner join team h on g.home_id = h.team_id
                inner join team a on g.home_id = a.team_id
                where g.game_id = ?;
                """;

        return jdbcTemplate.query(sql, new GameMapper(), id).stream().findFirst().orElse(null);
    }

    @Override
    public List<Game> findByDate(LocalDateTime date) {
        final String sql = """
                select
                    g.game_id,
                    g.game_date,
                    g.home_score,
                    g.away_score,
                    h.team_id,
                    h.name,
                    h.city,
                    h.team,
                    h.league,
                    h.abbreviation,
                    h.logo_url,
                    a.team_id,
                    a.name,
                    a.city,
                    a.team,
                    a.league,
                    a.abbreviation,
                    a.logo_url
                from game g
                inner join team h on g.home_id = h.team_id
                inner join team a on g.away_id = a.team_id
                where DATE(g.game_date) = ?;
                """;

        return jdbcTemplate.query(sql, new GameMapper(), date.toLocalDate());
    }

    @Override
    public Game findByDateAndTeams(LocalDateTime date, String homeName, String awayName) {
        final String sql = """
                select
                    g.game_id,
                    g.game_date,
                    g.home_score,
                    g.away_score,
                    h.team_id,
                    h.name,
                    h.city,
                    h.team,
                    h.league,
                    h.abbreviation,
                    h.logo_url,
                    a.team_id,
                    a.name,
                    a.city,
                    a.team,
                    a.league,
                    a.abbreviation,
                    a.logo_url
                from game g
                inner join team h on g.home_id = h.team_id
                inner join team a on g.away_id = a.team_id
                where h.`name` = ? and a.`name` = ?
                and DATE(g.game_date) = ?;
                """;
        return jdbcTemplate.query(sql, new GameMapper(), homeName, awayName, date.toLocalDate()).stream().findFirst().orElse(null);
    }

    @Override
    public List<Game> findByTeam(String city, String team) {
        final String sql = """
                select
                    g.game_id,
                    g.game_date,
                    g.home_score,
                    g.away_score,
                    h.team_id,
                    h.name,
                    h.city,
                    h.team,
                    h.league,
                    h.abbreviation,
                    h.logo_url,
                    a.team_id,
                    a.name,
                    a.city,
                    a.team,
                    a.league,
                    a.abbreviation,
                    a.logo_url
                from game g
                inner join team h on g.home_id = h.team_id
                inner join team a on g.away_id = a.team_id
                where h.city = ? and h.team = ? or a.city = ? and a.team = ?;
                """;
        return jdbcTemplate.query(sql, new GameMapper(), city, team, city, team);

    }

    @Override
    public Game add(Game game) {
        final String sql = """
                insert into game (home_id, away_id, game_date, home_score, away_score)
                values (?, ?, ?, ?, ?);
                """;

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, game.getHome().getId());
            ps.setInt(2, game.getAway().getId());
            ps.setTimestamp(3, Timestamp.valueOf(game.getGameDate()));
            ps.setInt(4, game.getHomeScore());
            ps.setInt(5, game.getAwayScore());
            return ps;
        }, keyHolder);

        if(rowsAffected > 0) {
            game.setId(keyHolder.getKey().intValue());
            return game;
        }

        return null;
    }

    @Override
    public boolean update(Game game) {
        final String sql = """
                update game set
                    home_id = ?,
                    away_id = ?,
                    game_date = ?,
                    home_score = ?,
                    away_score = ?
                where game_id = ?;
                """;

        return jdbcTemplate.update(sql,
                game.getHome().getId(),
                game.getAway().getId(),
                game.getGameDate(),
                game.getHomeScore(),
                game.getAwayScore(),
                game.getId()) > 0;
    }

    @Override
    public boolean delete(int id) {
        final String sql = "delete from game where game_id = ?";

        return jdbcTemplate.update(sql, id) > 0;
    }
}
