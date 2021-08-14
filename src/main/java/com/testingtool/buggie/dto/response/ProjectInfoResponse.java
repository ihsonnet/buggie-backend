package com.testingtool.buggie.dto.response;

import com.testingtool.buggie.jwt.model.User;
import com.testingtool.buggie.model.Bug;
import lombok.*;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectInfoResponse {
    private String id;
    private String name;
    private String description;
    private String created_by;
    private String created_on;
    private List<User> members;
    private List<Bug> bugs;
}
