package com.mountain_journey.mj_auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    private String userFirstName;
    private String userLastName;
    private String userPhone;

    @Email
    @NotBlank
    private String userEmail;

    @NotBlank
    private String userPassword;
}

