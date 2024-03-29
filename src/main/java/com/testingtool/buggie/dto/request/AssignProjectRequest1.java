package com.testingtool.buggie.dto.request;

import lombok.*;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AssignProjectRequest1 {
    private String projectId;
    private String userEmail;
    private Set<String> userRole;
}
