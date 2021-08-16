package com.testingtool.buggie.services;

import com.testingtool.buggie.dto.ApiResponse;
import com.testingtool.buggie.dto.request.AddProjectRequest;
import com.testingtool.buggie.dto.request.AssignProjectRequest;
import com.testingtool.buggie.dto.request.GetDeveloperRequest;
import com.testingtool.buggie.dto.response.ProjectInfoResponse;
import com.testingtool.buggie.jwt.model.User;
import com.testingtool.buggie.model.Project;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProjectService {
    ResponseEntity<ApiResponse<List<Project>>> getProjectList();

    ResponseEntity<ApiResponse<Project>> AddNewProject(AddProjectRequest addProjectRequest);

    ResponseEntity<ApiResponse<User>> AssignProject(AssignProjectRequest assignProjectRequest);

    ResponseEntity<ApiResponse<List<User>>> getProjectMembers(String id);

    ResponseEntity<ApiResponse<ProjectInfoResponse>> getProjectInfo(String id);

    List<GetDeveloperRequest> getDeveloperList(String id);
}
