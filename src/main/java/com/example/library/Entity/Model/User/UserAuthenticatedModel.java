package com.example.library.Entity.Model.User;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAuthenticatedModel {
    private String login;
    private String JWT;
    private String role;
}
