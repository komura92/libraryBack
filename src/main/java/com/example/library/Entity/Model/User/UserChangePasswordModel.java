package com.example.library.Entity.Model.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserChangePasswordModel {
    private String login;
    private String oldPassword;
    private String newPassword;
}
