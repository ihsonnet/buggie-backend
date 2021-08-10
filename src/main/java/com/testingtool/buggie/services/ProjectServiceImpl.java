package com.testingtool.buggie.services;

import com.testingtool.buggie.dto.ApiResponse;
import com.testingtool.buggie.dto.request.AddProjectRequest;
import com.testingtool.buggie.dto.request.AssignProjectRequest;
import com.testingtool.buggie.jwt.model.User;
import com.testingtool.buggie.jwt.repository.UserRepository;
import com.testingtool.buggie.model.Project;
import com.testingtool.buggie.repository.ProjectRepository;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<ApiResponse<List<Project>>> getProjectList() {
        List<Project> projectInfoResponses = projectRepository.findAll();

        return new ResponseEntity<>(new ApiResponse<>(200,"Data Found",projectInfoResponses),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<Project>> AddNewProject(AddProjectRequest addProjectRequest) {
        Project project = new Project();
        UUID id = UUID.randomUUID();
        String uuid = id.toString();
        project.setId(uuid);
        project.setName(addProjectRequest.getName());
        project.setDescription(addProjectRequest.getDescription());
        project.setCreated_by(addProjectRequest.getCreated_by());
        projectRepository.save(project);

        System.out.println(addProjectRequest.getCreated_by());
        User user = userRepository.getById(addProjectRequest.getCreated_by());
        List<Project> projectList = user.getProjects();
        projectList.add(project);
        user.setProjects(projectList);
        userRepository.save(user);
        return new ResponseEntity<>(new ApiResponse<>(200,"Created",project),HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponse<User>> AssignProject(AssignProjectRequest assignProjectRequest) {
        String projectId = assignProjectRequest.getProjectId();
        String userEmail = assignProjectRequest.getUserEmail();

        Project project = projectRepository.getById(projectId);
        Optional<User> userOptional = userRepository.findByEmail(userEmail);

        if (userOptional.isPresent()){
            User user = userRepository.getById(userOptional.get().getId());
            List<Project> projectList = user.getProjects();
            projectList.add(project);
            user.setProjects(projectList);
            userRepository.save(user);
            return new ResponseEntity<>(new ApiResponse<>(200,"Created",null),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new ApiResponse<>(200,"User Not Found",null),HttpStatus.OK);
        }
    }
}
