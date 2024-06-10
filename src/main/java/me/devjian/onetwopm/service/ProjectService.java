package me.devjian.onetwopm.service;

import lombok.RequiredArgsConstructor;
import me.devjian.onetwopm.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
}
