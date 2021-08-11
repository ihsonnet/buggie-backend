package com.testingtool.buggie.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddBugRequest {
    private String title;
    private String type;
    private String description;
    private String projectId;
    private String teamId;
    private String assignedTo;
    private String createdBy;
    private String CreatedOn;
}
