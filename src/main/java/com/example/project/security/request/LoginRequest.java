package com.example.project.security.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank
    private String emailId;

    @NotBlank
    private String password;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId( String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
