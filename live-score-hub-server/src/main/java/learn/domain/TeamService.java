package learn.domain;

import learn.data.TeamRepository;
import learn.models.Team;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    private final TeamRepository repository;

    public TeamService(TeamRepository repository) {
        this.repository = repository;
    }

    public Team findById(int id){
        return repository.findById(id);
    }

    public List<Team> findTeamsByCityAndName(String homeCity, String homeTeam, String awayCity, String awayTeam) {
        return repository.findTeamsByCityAndName(homeCity, homeTeam, awayCity, awayTeam);
    }

    public Team findByName(String name) {
        return repository.findByName(name);
    }
}
