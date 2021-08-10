package com.testingtool.buggie.controller;

import com.testingtool.buggie.dto.ApiResponse;
import com.testingtool.buggie.dto.request.AddBugRequest;
import com.testingtool.buggie.model.Bug;
import com.testingtool.buggie.services.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bug")
public class BugController {
    @Autowired
    private BugService bugService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse<Bug>> addBug(@RequestBody AddBugRequest addBugRequest){
        return bugService.addBug(addBugRequest);
    }

}
