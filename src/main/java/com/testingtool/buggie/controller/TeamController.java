package com.testingtool.buggie.controller;

import com.testingtool.buggie.model.Team;
import com.testingtool.buggie.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping("/byOwner/{id}")
    public Team getTeamInfo(@RequestParam String id){
        return teamService.getTeamInfo(id);
    }

}
