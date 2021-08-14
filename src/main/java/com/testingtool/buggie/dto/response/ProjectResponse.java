package com.testingtool.buggie.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectResponse {

    private String id;
    private String name;
    private String description;
    private String created_by;
    private String created_on;

}
