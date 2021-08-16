package com.testingtool.buggie.dto.request;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetDeveloperRequest {
    private String name;
    private String username;
}
