package me.devjian.onetwopm.dto.controller;

import lombok.Data;

@Data
public class UserRegisterRequest {

    private String username;

    private String invitationToken;

    private String password;
}
