package com.testingtool.buggie.jwt.services;

import com.testingtool.buggie.jwt.dto.request.LoginForm;
import com.testingtool.buggie.jwt.dto.request.SignUpForm;
import com.testingtool.buggie.jwt.dto.response.JwtResponse;
import com.testingtool.buggie.jwt.dto.response.LoggedUserDetailsResponse;
import com.testingtool.buggie.jwt.dto.response.UserResponse;
import com.testingtool.buggie.jwt.model.Role;
import com.testingtool.buggie.jwt.model.RoleName;
import com.testingtool.buggie.jwt.model.User;
import com.testingtool.buggie.jwt.repository.RoleRepository;
import com.testingtool.buggie.jwt.repository.UserRepository;
import com.testingtool.buggie.jwt.security.jwt.JwtProvider;
import com.testingtool.buggie.model.Team;
import com.testingtool.buggie.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.*;

@AllArgsConstructor
@Service
public class SignUpAndSignInService {

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TeamRepository teamRepository;

//    private final AreaNameRepository areaNameRepository;

    public Object signUp(SignUpForm signUpRequest) {


        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            //return true;
            return new JwtResponse("Email Already Exists");
        }


        User user = new User();
        UUID id = UUID.randomUUID();
        String uuid = id.toString();
        user.setId(uuid);
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setUsername(signUpRequest.getEmail() + signUpRequest.getPhoneNo());
        user.setEmail(signUpRequest.getEmail());
        user.setPhoneNo(signUpRequest.getPhoneNo());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setRoles(getRolesFromStringToRole(signUpRequest.getRole()));
        user.setCreatedBy(signUpRequest.getCreatedBy());
        user.setCreatedOn(signUpRequest.getCreatedOn());
        userRepository.saveAndFlush(user);
        System.out.println(1);
        if (signUpRequest.getRole().contains("PROJECT_MANAGER")){
            Team team = new Team();
            UUID teamId = UUID.randomUUID();
            String teamUuid = teamId.toString();
            team.setId(teamUuid);
            team.setName(signUpRequest.getFirstName()+"'s Team");
            team.setCreatedBy(uuid);
            teamRepository.save(team);
        }
        System.out.println(2);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        signUpRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);

        return new JwtResponse("OK", jwt, signUpRequest.getRole());
    }


    public JwtResponse signIn(LoginForm loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        String userName;
        if (userOptional.isPresent()) {
            userName = userOptional.get().getUsername();
        } else {
            userName = "";
            //throw new ResponseStatusException(HttpStatus.valueOf(410),"User Not Exists");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userName,
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);

        return new JwtResponse("OK", jwt, getRolesStringFromRole(userOptional.get().getRoles()));
    }

    public ResponseEntity<UserResponse> getLoggedAuthUser() {

        Object authUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (authUser instanceof UserDetails) {
            String username = ((UserDetails) authUser).getUsername();

            Optional<User> userOptional = userRepository.findByUsername(username);

            if (userOptional.isPresent()) {
                User user = userOptional.get();

                UserResponse userResponse = new UserResponse(user.getUsername(), user.getEmail(), user.getFirstName(),
                        user.getLastName(), user.getPhoneNo(), getRolesStringFromRole(user.getRoles()), user.getCreatedBy(), user.getCreatedOn());

                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add("massage", "OK");
                return new ResponseEntity(userResponse, httpHeaders, HttpStatus.OK);


            } else {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add("massage", "No User Found");
                return new ResponseEntity(new UserResponse(), httpHeaders, HttpStatus.NO_CONTENT);
            }

        } else {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("massage", "Unauthenticated");
            return new ResponseEntity(new UserResponse(), httpHeaders, HttpStatus.UNAUTHORIZED);
        }

    }


    public Set<Role> getRolesFromStringToRole(Set<String> roles2) {
        Set<Role> roles = new HashSet<>();
        for (String role : roles2) {
            System.out.println(role);
            Optional<Role> roleOptional = roleRepository.findByName(RoleName.valueOf(role));
//            System.out.println(roleOptional.get());

            if (!roleOptional.isPresent()) {
                throw new ValidationException("Role '" + role + "' does not exist.");
            }
            roles.add(roleOptional.get());
        }
        return roles;
    }

    private Set<String> getRolesStringFromRole(Set<Role> roles2) {
        Set<String> roles = new HashSet<>();
        for (Role role : roles2) {

            roles.add(role.getName().toString());
        }
        return roles;
    }

//    public ResponseEntity changePass(PassChangeRequest passChangeRequest) {
//
//        Optional<User> userOptional = userRepository.findByUsername(getLoggedAuthUser().getBody().getUsername());
//
//        if(userOptional.isPresent()){
//            User user = userOptional.get();
//            if(encoder.matches(passChangeRequest.getOldPass(), user.getPassword())) {
//                user.setPassword(encoder.encode(passChangeRequest.getNewPass()));
//
//                userRepository.save(user);
//
//                MessageResponse messageResponse = new MessageResponse("Pass Changed Successful", 200);
//                return new ResponseEntity(messageResponse, HttpStatus.OK);
//            }
//            else {
//                MessageResponse messageResponse = new MessageResponse("Old Pass Not Matched", 400);
//                return new ResponseEntity(messageResponse, HttpStatus.BAD_REQUEST);
//            }
//        }
//        else {
//            MessageResponse messageResponse = new MessageResponse("No User Found", 204);
//            return new ResponseEntity(messageResponse, HttpStatus.NO_CONTENT);
//        }
//    }

//    public String deleteUser(String email) {
//
//        if (userRepository.findByEmail(email).isPresent()) {
//
//            userRepository.deleteById(userRepository.findByEmail(email).get().getId());
//            return "Deleted";
//        } else {
//            return "Not Found";
//        }
//
//    }
//
//    public String editProfile(EditProfile editProfile) {
//        String username = getLoggedAuthUserName();
//
//        if (!username.isEmpty()) {
//            //System.out.println(username);
//            Optional<User> userOptional = userRepository.findByUsername(username);
//
//            if (userOptional.isPresent()) {
//                User user = userOptional.get();
//                if (!editProfile.getName().isEmpty()) {
//                    user.setName(editProfile.getName());
//                }
//                if (!editProfile.getPhoneNo().isEmpty()) {
//                    user.setPhoneNo(editProfile.getPhoneNo());
//                }
//                if (!editProfile.getNewPassword().isEmpty() && !editProfile.getCurrentPassword().isEmpty()) {
//                    if (encoder.matches(editProfile.getCurrentPassword(), userOptional.get().getPassword())) {
//
//                        user.setPassword(encoder.encode(editProfile.getNewPassword()));
//                    } else {
//                        return "Wrong Current Password";
//                    }
//                }
//
//                userRepository.save(user);
//                return "Saved Successfully";
//            } else {
//                return "User Not Found";
//            }
//
//        } else {
//            return "Unsuccessful";
//        }
//
//
//    }
//
//    public String addAreaList(AreaNameRequestsResponse areaNameRequestsResponse) {
//        for (String names : areaNameRequestsResponse.getAreaNames()) {
//            AreaNames areaNames = new AreaNames(names);
//            areaNameRepository.save(areaNames);
//        }
//        return "Saved";
//    }
//
//    public AreaNameRequestsResponse getAreaList() {
//        List<AreaNames> areaNamesOptional = areaNameRepository.findAll();
//
//        AreaNameRequestsResponse areaNameRequestsResponse = new AreaNameRequestsResponse();
//        List<String> areaNamesList = new ArrayList<>();
//        for (AreaNames areaNames : areaNamesOptional) {
//            areaNamesList.add(areaNames.getAreaName());
//        }
//        areaNameRequestsResponse.setAreaNames(areaNamesList);
//        return areaNameRequestsResponse;
//    }

    public LoggedUserDetailsResponse getLoggedUserDetails(Authentication authentication) {

        System.out.println(authentication.toString());
        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        List<String> userRoleList = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : grantedAuthorities) {
            userRoleList.add(grantedAuthority.getAuthority());
        }
        LoggedUserDetailsResponse loggedUserDetailsResponse = new LoggedUserDetailsResponse();
        loggedUserDetailsResponse.setUserRole(userRoleList);
        loggedUserDetailsResponse.setIsAuthenticated(authentication.isAuthenticated());

        Optional<User> user = userRepository.findByUsername(authentication.getName());

        loggedUserDetailsResponse.setFirstName(user.get().getFirstName());
        loggedUserDetailsResponse.setLastName(user.get().getLastName());
        loggedUserDetailsResponse.setEmail(user.get().getEmail());
        loggedUserDetailsResponse.setPhoneNo(user.get().getPhoneNo());
        loggedUserDetailsResponse.setProjects(user.get().getProjects());
        return loggedUserDetailsResponse;
    }


}
