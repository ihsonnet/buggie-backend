package com.testingtool.buggie.jwt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class GenerateOTPRequest2 {
    String email;
    int otp;
    String newPassword;
}
