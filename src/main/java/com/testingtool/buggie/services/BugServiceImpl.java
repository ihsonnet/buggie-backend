package com.testingtool.buggie.services;

import com.testingtool.buggie.dto.ApiResponse;
import com.testingtool.buggie.dto.request.AddBugRequest;
import com.testingtool.buggie.model.Bug;
import com.testingtool.buggie.repository.BugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BugServiceImpl implements BugService{

    @Autowired
    private BugRepository bugRepository;
    @Override
    public ResponseEntity<ApiResponse<Bug>> addBug(AddBugRequest addBugRequest) {
        Bug bug = new Bug();
        UUID id = UUID.randomUUID();
        String uuid = id.toString();
        bug.setId(uuid);
        bug.setTitle(addBugRequest.getTitle());
        bug.setType(addBugRequest.getType());
        bug.setDescription(addBugRequest.getDescription());
        bug.setStatus("New");
        bug.setProjectId(addBugRequest.getProjectId());
        bug.setAssignedTo(addBugRequest.getAssignedTo());
        bug.setCreatedBy(addBugRequest.getCreatedBy());
        bug.setUpdatedBy(addBugRequest.getUpdatedBy());
        bug.setApproveStatus("No Action");

        bugRepository.save(bug);
        return new ResponseEntity<>(new ApiResponse<>(201,"Bug Created",bug), HttpStatus.CREATED);
    }
}
