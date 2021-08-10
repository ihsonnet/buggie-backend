package com.testingtool.buggie.services;

import com.testingtool.buggie.dto.ApiResponse;
import com.testingtool.buggie.dto.request.AddBugRequest;
import com.testingtool.buggie.model.Bug;
import org.springframework.http.ResponseEntity;

public interface BugService {
    ResponseEntity<ApiResponse<Bug>> addBug(AddBugRequest addBugRequest);
}
