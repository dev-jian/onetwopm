package me.devjian.onetwopm.repository;

import me.devjian.onetwopm.model.User;
import me.devjian.onetwopm.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndStatus(String username, UserStatus status);

    Optional<User> findByInvitationTokenAndStatus(String username, UserStatus status);

    Optional<User> findByUsernameAndInvitationTokenAndStatus(String username,
                                                             String invitationToken,
                                                             UserStatus status);
}
