package learn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Team {
    @JsonProperty("team_id")
    private int id;
    @JsonProperty("name")
    private String name;
    private String city;
    private String team;
    private League league;
    private String abbreviation;
    @JsonProperty("logo_url")
    private String logoUrl;

    public Team() {}

    public Team(int id, String name, String city, String team, League league, String abbreviation, String logoUrl) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.team = team;
        this.league = league;
        this.abbreviation = abbreviation;
        this.logoUrl = logoUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team1 = (Team) o;
        return id == team1.id && Objects.equals(name, team1.name) && Objects.equals(city, team1.city) && Objects.equals(team, team1.team) && league == team1.league && Objects.equals(abbreviation, team1.abbreviation) && Objects.equals(logoUrl, team1.logoUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, city, team, league, abbreviation, logoUrl);
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", team='" + team + '\'' +
                ", league=" + league +
                ", abbreviation='" + abbreviation + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                '}';
    }
}