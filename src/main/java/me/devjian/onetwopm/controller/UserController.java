package me.devjian.onetwopm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.devjian.onetwopm.converter.UserConverter;
import me.devjian.onetwopm.dto.controller.UserRegisterRequest;
import me.devjian.onetwopm.dto.service.UserRegisterDTO;
import me.devjian.onetwopm.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 사용자 등록 링크를 클릭하여 토큰으로 사용자 등록 폼을 조회합니다.
     *
     * @param invitationToken 초대 토큰
     * @return 사용자 등록 폼
     */
    @GetMapping("/open/users/{invitationToken}")
    public ResponseEntity<UserRegisterDTO> getRegisterFormByInvitationToken(@PathVariable("invitationToken") String invitationToken) {
        log.info("Receiving get Register Form Request");

        return ResponseEntity.ok(userService.getRegisterFormByInvitationToken(invitationToken));
    }

    /**
     * 작성된 사용자 등록 정보를 바탕으로 등록을 완료합니다.
     *
     * @param userRegisterRequest 사용자 등록 정보
     * @return 200 OK
     */
    @PostMapping("/open/users/{invitationToken}")
    public ResponseEntity<Void> completeRegistration(@RequestBody UserRegisterRequest userRegisterRequest) {
        log.info("Receiving complete registration Request");

        userService.completeRegistration(
                UserConverter.userRegisterRequestToUserRegisterDTO(userRegisterRequest)
        );

        return ResponseEntity.ok().build();
    }

    /**
     * 사용자를 서비스에 초대합니다. 입력받은 이메일 주소로 링크를 발송합니다.
     *
     * @param email 초대할 사용자의 이메일 주소
     * @return 200 OK
     */
    @PostMapping("/users")
    public ResponseEntity<Void> inviteUser(@RequestBody String email) {
        log.info("Receiving invite user Request");

        userService.createUser(email);

        return ResponseEntity.ok().build();
    }
}
