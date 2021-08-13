package com.testingtool.buggie.jwt.controller;

import com.testingtool.buggie.dto.ApiResponse;
import com.testingtool.buggie.jwt.dto.request.*;
import com.testingtool.buggie.jwt.dto.response.LoggedUserDetailsResponse;
import com.testingtool.buggie.jwt.dto.response.UserResponse;
import com.testingtool.buggie.jwt.model.User;
import com.testingtool.buggie.jwt.services.SignUpAndSignInService;
import javassist.bytecode.DuplicateMemberException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private SignUpAndSignInService signUpAndSignInService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        return ResponseEntity.ok(signUpAndSignInService.signIn(loginRequest));
    }

    @PostMapping("/signup")
    public Object registerUser(@RequestBody SignUpForm signUpRequest) throws DuplicateMemberException {
        return signUpAndSignInService.signUp(signUpRequest);
    }

//    @GetMapping("/users")
//    public ResponseEntity<UserResponse> getLoggedAuthId() {
//        return signUpAndSignInService.getLoggedAuthUser();
//    }
//

    @GetMapping("/serverCheck")
    public String getServerStatStatus() {
        return "The Server is Running";
    }

//    @GetMapping("/sendMail")
//    public String sendMail()throws MessagingException, IOException {
//        return forgetPasswordService.sendMail();
//    }

    @GetMapping("/user-info")
    public LoggedUserDetailsResponse getDashboard(Authentication authentication) {

        return signUpAndSignInService.getLoggedUserDetails(authentication);
    }

    @GetMapping("user/createdBy/{id}")
    public ResponseEntity<ApiResponse<List<User>>> getTeamMembers(@RequestParam String id){
        return signUpAndSignInService.getTeamMembers(id);
    }

}
