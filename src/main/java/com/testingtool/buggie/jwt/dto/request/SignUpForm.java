package com.testingtool.buggie.jwt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class SignUpForm {
//    String dob;
//    String nid;

    @NotBlank
    @Size(max = 60)
    @Email
    private String email;

    @NotBlank
    @Size(min = 11, max = 11)
    String phoneNo;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    String address;
    @NotBlank
    @Size(min = 3, max = 50)
    private String firstName;
    @NotBlank
    @Size(min = 3, max = 50)
    private String lastName;

    private String createdBy;
    private String createdOn;

}