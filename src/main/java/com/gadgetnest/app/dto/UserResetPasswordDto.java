package com.gadgetnest.app.dto;

import lombok.Data;

@Data
public class UserResetPasswordDto {
    private String email;
    private String answer;
    private String newPassword;
}
