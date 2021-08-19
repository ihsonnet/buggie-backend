package com.testingtool.buggie.services;

import com.testingtool.buggie.dto.ApiResponse;
import com.testingtool.buggie.dto.request.AddBugRequest;
import com.testingtool.buggie.dto.request.ChangeStatusRequest;
import com.testingtool.buggie.dto.response.BugInfoResponse;
import com.testingtool.buggie.model.Bug;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BugService {
    ResponseEntity<ApiResponse<Bug>> addBug(AddBugRequest addBugRequest);

    ResponseEntity<ApiResponse<List<Bug>>> getBugs(String bugTitle, String createdBy, String assignTo, String projectId, String token);

    ResponseEntity<ApiResponse<List<Bug>>> getBugByCreator(String id);

    ResponseEntity<ApiResponse<List<Bug>>> getBugByAssigned(String id);

    ResponseEntity<ApiResponse<List<Bug>>> getAllBugs();

    ResponseEntity<ApiResponse<Bug>> changeBugStatus(ChangeStatusRequest changeStatusRequest);

    ResponseEntity<ApiResponse<Bug>> changeApproveStatus(ChangeStatusRequest changeStatusRequest);

    ResponseEntity<ApiResponse<List<Bug>>> getBugByTeam(String id);

    ResponseEntity<ApiResponse<BugInfoResponse>> getBugInfo(String id);
}
