package me.devjian.onetwopm.converter;

import lombok.extern.slf4j.Slf4j;
import me.devjian.onetwopm.dto.controller.UserRegisterRequest;
import me.devjian.onetwopm.dto.service.UserRegisterDTO;
import me.devjian.onetwopm.model.User;

@Slf4j
public class UserConverter {

    public static UserRegisterDTO userRegisterRequestToUserRegisterDTO(UserRegisterRequest source) {
        log.debug("Converting UserRegisterRequest to UserRegisterDTO");

        return UserRegisterDTO.builder()
                .username(source.getUsername())
                .invitationToken(source.getInvitationToken())
                .password(source.getPassword())
                .build();
    }

    public static UserRegisterDTO userEntityToUserRegisterDTO(User source) {
        log.debug("Converting User Entity to UserRegisterDTO");

        return UserRegisterDTO.builder()
                .username(source.getUsername())
                .invitationToken(source.getInvitationToken())
                .password(null)
                .build();
    }
}
