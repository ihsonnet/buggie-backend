package com.testingtool.buggie.services;

import com.testingtool.buggie.dto.ApiResponse;
import com.testingtool.buggie.dto.request.AddProjectRequest;
import com.testingtool.buggie.dto.request.AssignProjectRequest;
import com.testingtool.buggie.dto.response.ProjectInfoResponse;
import com.testingtool.buggie.jwt.model.Role;
import com.testingtool.buggie.jwt.model.RoleName;
import com.testingtool.buggie.jwt.model.User;
import com.testingtool.buggie.jwt.repository.RoleRepository;
import com.testingtool.buggie.jwt.repository.UserRepository;
import com.testingtool.buggie.model.Bug;
import com.testingtool.buggie.model.Project;
import com.testingtool.buggie.repository.BugRepository;
import com.testingtool.buggie.repository.ProjectRepository;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.*;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BugRepository bugRepository;

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
//        User user = userRepository.getById(addProjectRequest.getCreated_by());
        User user = userRepository.findByUsername(addProjectRequest.getCreated_by()).get();
        List<Project> projectList = user.getProjects();
        projectList.add(project);
        user.setProjects(projectList);
        userRepository.save(user);
        System.out.println(user);

        List<User> projectUser = new ArrayList<>();
        projectUser.add(user);
        project.setMembers(projectUser);
        projectRepository.save(project);
        return new ResponseEntity<>(new ApiResponse<>(200,"Created Successfully!",null),HttpStatus.CREATED);
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
            user.setRoles(getRolesFromStringToRole(assignProjectRequest.getUserRole()));

            List<User> projectUser = project.getMembers();
            projectUser.add(user);
            project.setMembers(projectUser);
            projectRepository.save(project);
            return new ResponseEntity<>(new ApiResponse<>(200,"Assigned Successfully!",null),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new ApiResponse<>(200,"User Not Found!",null),HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<User>>> getProjectMembers(String id) {
        Project project = projectRepository.findById(id).get();
        List<User> users = project.getMembers();

        return new ResponseEntity<>(new ApiResponse<>(200,"Data Found",users),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<ProjectInfoResponse>> getProjectInfo(String id) {
        Project project = projectRepository.getById(id);
        List<Bug> bugs = bugRepository.findByProjectId(id);

        ProjectInfoResponse projectInfoResponse = new ProjectInfoResponse();

        projectInfoResponse.setId(project.getId());
        projectInfoResponse.setName(project.getName());
        projectInfoResponse.setDescription(project.getDescription());
        projectInfoResponse.setCreated_by(project.getCreated_by());
        projectInfoResponse.setMembers((project.getMembers()));
        projectInfoResponse.setBugs((bugs));

        return new ResponseEntity<>(new ApiResponse<>(200,"Data Found",projectInfoResponse),HttpStatus.OK);
    }

    public Set<Role> getRolesFromStringToRole(Set<String> roles2) {
        Set<Role> roles = new HashSet<>();
        for (String role : roles2) {
            System.out.println(role);
            Optional<Role> roleOptional = roleRepository.findByName(RoleName.valueOf(role));
//            System.out.println(roleOptional.get());

            if (!roleOptional.isPresent()) {
                throw new ValidationException("Role '" + role + "' does not exist.");
            }
            roles.add(roleOptional.get());
        }
        return roles;
    }
}

