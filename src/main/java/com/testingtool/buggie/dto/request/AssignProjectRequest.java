package com.testingtool.buggie.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AssignProjectRequest {
    private String projectId;
    private String userEmail;
}
