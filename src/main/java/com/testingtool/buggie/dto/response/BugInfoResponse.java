package com.testingtool.buggie.dto.response;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BugInfoResponse {
        private String title;
//        private String type;
        @Column(columnDefinition="TEXT")
        private String description;
//        private String status;
//        private String comment;
//        private String projectId;
//        private String teamId;
//        private String createdBy;
//        private String createdOn;
//        private String assignedTo;
//        private String updatedBy;
//        private String updatedOn;
//        private String approveStatus;
//        private String approveComment;
}
