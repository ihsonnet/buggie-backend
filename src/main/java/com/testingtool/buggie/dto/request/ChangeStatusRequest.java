package com.testingtool.buggie.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChangeStatusRequest {
    private String bugId;
    private String status;
    private String comment;
    private String userId;
    private String updateTime;
    private String updateBy;
}
