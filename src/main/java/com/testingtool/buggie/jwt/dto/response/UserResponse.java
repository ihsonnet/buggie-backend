package com.testingtool.buggie.jwt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    String username;
    String email;
    String firstName;
    String lastName;
    String phoneNo;
    Set<String> role;
    String createdBy;
    String createdOn;

}
