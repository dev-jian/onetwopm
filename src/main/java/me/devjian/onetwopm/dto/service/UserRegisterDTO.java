package me.devjian.onetwopm.dto.service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterDTO {

    String username;

    String password;

    String invitationToken;
}
