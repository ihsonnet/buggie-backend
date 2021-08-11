package com.testingtool.buggie.controller;

import com.testingtool.buggie.dto.ApiResponse;
import com.testingtool.buggie.dto.request.AddProjectRequest;
import com.testingtool.buggie.dto.request.AssignProjectRequest;
import com.testingtool.buggie.jwt.model.User;
import com.testingtool.buggie.model.Project;
import com.testingtool.buggie.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Project>>> getProjectList(){
        return projectService.getProjectList();
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<Project>> AddNewProject(@RequestBody AddProjectRequest addProjectRequest){
        return projectService.AddNewProject(addProjectRequest);
    }

    @PostMapping("/assign")
    public ResponseEntity<ApiResponse<User>> AssignProject(@RequestBody AssignProjectRequest assignProjectRequest){
        return projectService.AssignProject(assignProjectRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<List<User>>> getProjectMembers(@RequestParam String id){
        return projectService.getProjectMembers(id);
    }
}
