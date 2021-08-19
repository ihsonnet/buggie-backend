package com.testingtool.buggie.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Bug {

    @Id
    private String id;
    private String title;
    private String type;
    @JsonIgnore
    @Column(columnDefinition="TEXT")
    private String description;
    private String status;
    private String comment;
    private String projectId;
    private String teamId;
    private String createdBy;
    private String createdOn;
    private String assignedTo;
    private String updatedBy;
    private String updatedOn;
    private String approveStatus;
    private String approveComment;
}
