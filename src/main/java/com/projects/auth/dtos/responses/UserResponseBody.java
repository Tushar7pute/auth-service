package com.projects.auth.dtos.responses;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponseBody {

    private Long id;
    private String username;
    private String email;


}
