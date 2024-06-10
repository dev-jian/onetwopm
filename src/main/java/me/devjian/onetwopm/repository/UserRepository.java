package me.devjian.onetwopm.repository;

import me.devjian.onetwopm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
