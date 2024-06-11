package me.devjian.onetwopm.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Entity
@Table(name = "\"USER\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> projects = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus status = UserStatus.PENDING;

    @Column(name = "invitation_token")
    private String invitationToken;

    public void completeRegistration(String encryptedPassword) {
        this.setPassword(encryptedPassword);
        this.setInvitationToken(null);
        this.setStatus(UserStatus.ENABLED);
    }

    /**
     * 추가할 사용자의 이메일 정보로 user를 대기 상태로 생성합니다.
     * @param email 추가할 사용자의 이메일
     * @return 생성된 user
     */
    public static User createUser(String email) {
        log.info("Create Pending Status User Entity with email & token");
        User user = new User();
        user.setUsername(email);
        user.setInvitationToken(UUID.randomUUID().toString());
        user.setStatus(UserStatus.PENDING);
        return user;
    }
}
