package com.testingtool.buggie.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddProjectRequest {
    private String name;
    private String description;
    private String created_by;
}
