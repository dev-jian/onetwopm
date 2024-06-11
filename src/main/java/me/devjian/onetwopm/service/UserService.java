package me.devjian.onetwopm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.devjian.onetwopm.converter.UserConverter;
import me.devjian.onetwopm.dto.service.SimpleEmailDTO;
import me.devjian.onetwopm.dto.service.UserRegisterDTO;
import me.devjian.onetwopm.exception.ResourceNotFoundException;
import me.devjian.onetwopm.messaging.EmailProducer;
import me.devjian.onetwopm.model.User;
import me.devjian.onetwopm.model.UserStatus;
import me.devjian.onetwopm.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final EmailProducer emailProducer;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserRegisterDTO getRegisterFormByInvitationToken(String invitationToken) {
        log.info("Entering getRegisterFormByInvitationToken method");

        User targetUser = userRepository.findByInvitationTokenAndStatus(invitationToken, UserStatus.PENDING)
                .orElseThrow(() -> {
                    log.error("해당 토큰으로 초대된 회원정보를 찾을 수 없습니다. 토큰: {}", invitationToken);
                    return new ResourceNotFoundException("해당 토큰으로 초대된 회원정보를 찾을 수 없습니다.");
                });

        return UserConverter.userEntityToUserRegisterDTO(targetUser);
    }

    /**
     * 초대 토큰과 사용자 등록에 필요한 정보를 이용해 가입 대기 상태인 사용자의 등록을 완료합니다.
     *
     * @param userRegisterDTO 사용자 등록에 필요한 추가 정보
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void completeRegistration(UserRegisterDTO userRegisterDTO) {
        log.info("Entering completeRegistration method");

        // 초대 토큰으로 회원가입 상태인 회원을 조회합니다.
        String username = userRegisterDTO.getUsername();
        String invitationToken = userRegisterDTO.getInvitationToken();
        User targetUser = userRepository.findByUsernameAndInvitationTokenAndStatus(
                username,
                invitationToken,
                UserStatus.PENDING
        ).orElseThrow(() -> {
            log.error("해당 토큰과 사용자 명으로 초대된 회원정보를 찾을 수 없습니다. 토큰: {}, 사용자 명: {}", invitationToken, username);
            return new ResourceNotFoundException("해당 토큰과 사용자 명으로 초대된 회원정보를 찾을 수 없습니다.");
        });

        // 등록 비밀번호를 암호화 한 후 등록을 진행합니다.
        String password = userRegisterDTO.getPassword();
        targetUser.completeRegistration(passwordEncoder.encode(password));
    }

    /**
     * 사용자 가입 대기 상태의 유저를 생성하고, 가입을 진행시키기 위한 메일을 발송 큐 메세지를 생성합니다.
     *
     * @param email 대상 사용자의 이메일 주소
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void createUser(String email) {
        log.info("Entering createUser method");

        // 회원가입 대기 상태 유저 엔티티를 생성합니다.
        User newUser = User.createUser(email);
        userRepository.save(newUser);

        // 회원가입을 진행시키기 위한 메일을 발송합니다.
        sendInvitationEmail(newUser);
    }

    private void sendInvitationEmail(User newUser) {
        log.info("Entering sendInvitationEmail method");

        // 사용자 등록 안내 URL을 생성합니다.
        String confirmationUrl = "/users/confirm?token=" + newUser.getInvitationToken();
        log.debug("Created URL: {}", confirmationUrl);

        // 안내 메일 정보를 생성합니다.
        String to = newUser.getUsername();
        String subject = "원투피엠 사용자 등록 안내 메일";
        String body = "사용자 등록을 완료하려면 아래의 링크를 클릭하여 진행해주세요: \n"
                + "http://localhost:8081" + confirmationUrl;

        // 이메일 발송 큐에 메세지를 등록합니다.
        SimpleEmailDTO simpleEmailDTO = new SimpleEmailDTO(to, subject, body);
        emailProducer.sendEmail(simpleEmailDTO);
    }
}
