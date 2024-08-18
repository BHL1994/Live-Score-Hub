package learn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {
    @JsonProperty("game_id")
    private int id;
    @JsonProperty("home_id")
    private Team home;
    @JsonProperty("away_id")
    private Team away;
    @JsonProperty("game_date")
    private LocalDateTime gameDate;
    @JsonProperty("home_score")
    private int homeScore;
    @JsonProperty("away_score")
    private int awayScore;

    public Game() {}

    public Game(int id, Team home, Team away, LocalDateTime gameDate, int homeScore, int awayScore) {
        this.id = id;
        this.home = home;
        this.away = away;
        this.gameDate = gameDate;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Team getHome() {
        return home;
    }

    public void setHome(Team home) {
        this.home = home;
    }

    public Team getAway() {
        return away;
    }

    public void setAway(Team away) {
        this.away = away;
    }

    public LocalDateTime getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDateTime gameDate) {
        this.gameDate = gameDate;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id && home == game.home && away == game.away && homeScore == game.homeScore && awayScore == game.awayScore && Objects.equals(gameDate, game.gameDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, home, away, gameDate, homeScore, awayScore);
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", home=" + home.getName() +
                ", away=" + away.getName() +
                ", gameDate=" + gameDate +
                ", homeScore=" + homeScore +
                ", awayScore=" + awayScore +
                '}';
    }

}