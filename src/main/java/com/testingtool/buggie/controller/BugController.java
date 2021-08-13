package com.testingtool.buggie.controller;

import com.testingtool.buggie.dto.ApiResponse;
import com.testingtool.buggie.dto.request.AddBugRequest;
import com.testingtool.buggie.dto.request.ChangeStatusRequest;
import com.testingtool.buggie.model.Bug;
import com.testingtool.buggie.services.BugService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
@RestController
@RequestMapping("/bug")
public class BugController {
    @Autowired
    private BugService bugService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse<Bug>> addBug(@RequestBody AddBugRequest addBugRequest){
        return bugService.addBug(addBugRequest);
    }

//    @GetMapping
//    public ResponseEntity<ApiResponse<List<Bug>>> getBugs(@RequestParam(required = false) String bugTitle , String createdBy, String assignTo, String projectId,
//    @RequestHeader(name = "Authorization", required = false) String token){
//        return bugService.getBugs(bugTitle,createdBy,assignTo,projectId,token);
//    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Bug>>> getAllBugs(){
        return bugService.getAllBugs();
    }

    @GetMapping("/by-team/{id}")
    public ResponseEntity<ApiResponse<List<Bug>>> getBugByTeam(@PathVariable String id){
        return bugService.getBugByTeam(id);
    }

    @GetMapping("/by-creator/{id}")
    public ResponseEntity<ApiResponse<List<Bug>>> getBugByCreator(@PathVariable String id){
        return bugService.getBugByCreator(id);
    }

    @GetMapping("/assigned-to/{id}")
    public ResponseEntity<ApiResponse<List<Bug>>> getBugByAssigned(@PathVariable String id){
        return bugService.getBugByAssigned(id);
    }

    @PutMapping("/change-status")
    public ResponseEntity<ApiResponse<Bug>> changeBugStatus(@RequestBody ChangeStatusRequest changeStatusRequest){
        return bugService.changeBugStatus(changeStatusRequest);
    }

    @PutMapping("/change-approve-status")
    public ResponseEntity<ApiResponse<Bug>> changeApproveStatus(@RequestBody ChangeStatusRequest changeStatusRequest){
        return bugService.changeApproveStatus(changeStatusRequest);
    }
}
