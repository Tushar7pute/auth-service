package com.projects.auth.controllers;


import com.projects.auth.db.entities.UserEntity;
import com.projects.auth.dtos.requests.UserCreationRequestBody;
import com.projects.auth.dtos.requests.UserLoginRequestBody;
import com.projects.auth.dtos.responses.UserResponseBody;
import com.projects.auth.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<UserResponseBody> createUser(@RequestBody UserCreationRequestBody userCreationRequestBody) {

        UserEntity createdUser = userService.createUser(
                userCreationRequestBody.getEmail(),
                userCreationRequestBody.getUsername(),
                userCreationRequestBody.getPassword()
        );

        UserResponseBody userResponseBody = UserResponseBody.builder()
                .id(createdUser.getId())
                .email(createdUser.getEmail())
                .username(createdUser.getUsername())
                .build();

        return new ResponseEntity<>(userResponseBody, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<UserResponseBody> loginUser(@RequestBody UserLoginRequestBody userLoginRequestBody) {
        UserEntity existingUser = userService.verifyUser(userLoginRequestBody.getUsername(), userLoginRequestBody.getPassword());

        UserResponseBody userResponseBody = UserResponseBody.builder()
                .id(existingUser.getId())
                .email(existingUser.getEmail())
                .username(existingUser.getUsername())
                .build();

        HttpHeaders h = new HttpHeaders();
        h.set("Token", "XXX");

        return new ResponseEntity<>(userResponseBody,h,HttpStatus.OK);
    }

}
