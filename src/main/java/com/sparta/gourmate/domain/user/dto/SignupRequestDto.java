package com.sparta.gourmate.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank(message = "USERNAME_POLICY")
    @Size(min = 4, max = 10, message = "USERNAME_POLICY")
    @Pattern(regexp = "^[a-z0-9]+$", message = "USERNAME_POLICY")
    private String username;

    @NotBlank(message = "PASSWORD_POLICY")
    @Size(min = 8, max = 15, message = "PASSWORD_POLICY")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*)(+=._-])[a-zA-Z\\d!@#$%^&*)(+=._-]+$", message = "PASSWORD_POLICY")
    private String password;

    @NotBlank(message = "EMAIL_POLICY")
    @Email(message = "EMAIL_POLICY")
    private String email;

    private Boolean isOwner = false;

    private Boolean isAdmin = false;

    private String adminToken = "";
}
