package com.testingtool.buggie.services;

import com.testingtool.buggie.dto.ApiResponse;
import com.testingtool.buggie.dto.request.AddBugRequest;
import com.testingtool.buggie.dto.request.ChangeStatusRequest;
import com.testingtool.buggie.jwt.model.User;
import com.testingtool.buggie.jwt.repository.UserRepository;
import com.testingtool.buggie.model.Bug;
import com.testingtool.buggie.jwt.security.jwt.JwtProvider;
import com.testingtool.buggie.repository.BugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BugServiceImpl implements BugService{

    @Autowired
    private BugRepository bugRepository;
    @Autowired
    private UserRepository userRepository;

    private JwtProvider jwtProvider;

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
        bug.setTeamId(addBugRequest.getTeamId());
        bug.setAssignedTo(addBugRequest.getAssignedTo());
        bug.setCreatedBy(addBugRequest.getCreatedBy());
        bug.setUpdatedBy(addBugRequest.getCreatedOn());
        bug.setApproveStatus("No Action");

//        long timestamp = System.currentTimeMillis() / 1000;
//        bug.setCreatedOn(Long.toString(timestamp));

        bugRepository.save(bug);
        return new ResponseEntity<>(new ApiResponse<>(201,"Bug Created",bug), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiResponse<List<Bug>>> getBugs(String bugTitle, String createdBy, String assignTo, String projectId, String token) {

        String userId = getUserId(token);
        System.out.println(userId);

        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<List<Bug>>> getBugByCreator(String id) {
        List<Bug> bugList = bugRepository.findByCreatedBy(id);
        if (!bugList.isEmpty()){

            return new ResponseEntity<>(new ApiResponse<>(200,"Data Found",bugList),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new ApiResponse<>(200,"Data Found",null),HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<Bug>>> getBugByAssigned(String id) {
        List<Bug> bugList = bugRepository.findByAssignedTo(id);
        if (!bugList.isEmpty()){

            return new ResponseEntity<>(new ApiResponse<>(200,"Data Found",bugList),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new ApiResponse<>(200,"Data Not Found",null),HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<Bug>>> getAllBugs() {
        List<Bug> bugList = bugRepository.findAll();
        if (!bugList.isEmpty()){

            return new ResponseEntity<>(new ApiResponse<>(200,"Data Found",bugList),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new ApiResponse<>(200,"Data Not Found",null),HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Bug>> changeBugStatus(ChangeStatusRequest changeStatusRequest) {
        Bug bug = bugRepository.findById(changeStatusRequest.getBugId()).get();
        bug.setStatus(changeStatusRequest.getStatus());
        bug.setUpdatedBy(changeStatusRequest.getUserId());
        bug.setUpdatedOn(changeStatusRequest.getUpdateTime());

        Optional<Bug> bugOptional = bugRepository.findById(changeStatusRequest.getBugId());
        if (bugOptional.isPresent()){
            bugRepository.save(bug);
            return new ResponseEntity<>(new ApiResponse<>(200,"Status Changed",bug),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new ApiResponse<>(200,"Bug Not Found",null),HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Bug>> changeApproveStatus(ChangeStatusRequest changeStatusRequest) {
        Bug bug = bugRepository.findById(changeStatusRequest.getBugId()).get();
        bug.setApproveStatus(changeStatusRequest.getStatus());
        bug.setUpdatedBy(changeStatusRequest.getUserId());
        bug.setUpdatedOn(changeStatusRequest.getUpdateTime());

        Optional<Bug> bugOptional = bugRepository.findById(changeStatusRequest.getBugId());
        if (bugOptional.isPresent()){
            bugRepository.save(bug);
            return new ResponseEntity<>(new ApiResponse<>(200,"Approve Status Changed",bug),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new ApiResponse<>(200,"Bug Not Found",null),HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<Bug>>> getBugByTeam(String id) {
        List<Bug> bugList = bugRepository.findByTeamId(id);
        if (!bugList.isEmpty()){

            return new ResponseEntity<>(new ApiResponse<>(200,"Data Found",bugList),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new ApiResponse<>(200,"Data Found",null),HttpStatus.OK);
        }
    }


    private String getUserId(String token) {
        String userId = null;
        if (token != null && !token.isEmpty()) {
            String username = jwtProvider.getUserNameFromJwt(token);
            Optional<User> profileModelOptional = userRepository.findByUsername(username);
            if (profileModelOptional.isPresent()) {
                userId = profileModelOptional.get().getId();
            }
        }

        return userId;
    }
}
