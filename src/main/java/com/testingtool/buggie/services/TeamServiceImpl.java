package com.testingtool.buggie.services;

import com.testingtool.buggie.model.Team;
import com.testingtool.buggie.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService{
    @Autowired
    private TeamRepository teamRepository;

    @Override
    public Team getTeamInfo(String id) {
        Team team = teamRepository.findByCreatedBy(id).get();
        return team;
    }
}
