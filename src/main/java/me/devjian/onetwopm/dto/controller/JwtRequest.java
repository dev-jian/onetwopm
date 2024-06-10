package me.devjian.onetwopm.dto.controller;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;

    private String password;
}
