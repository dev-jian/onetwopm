package me.devjian.onetwopm.repository;

import me.devjian.onetwopm.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
