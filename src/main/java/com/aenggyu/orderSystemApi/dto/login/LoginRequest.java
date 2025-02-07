package com.aenggyu.orderSystemApi.dto.login;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}
