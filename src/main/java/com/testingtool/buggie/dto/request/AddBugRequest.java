package com.testingtool.buggie.dto.request;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private String createdBy;
    private String assignedTo;
    private String updatedBy;
}
