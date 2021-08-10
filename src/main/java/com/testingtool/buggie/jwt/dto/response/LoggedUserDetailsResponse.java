package com.testingtool.buggie.jwt.dto.response;

import com.testingtool.buggie.model.Project;
import lombok.*;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoggedUserDetailsResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private List<Project> projects;
    private List<String> userRole;
    private Boolean isAuthenticated;
}