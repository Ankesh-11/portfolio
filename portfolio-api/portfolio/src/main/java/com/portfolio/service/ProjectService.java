package com.portfolio.service;

import com.portfolio.dto.ProjectDto;
import java.util.List;

public interface ProjectService {
    List<ProjectDto> getAllProjects(Long personalInfoId);
    ProjectDto getProjectById(Long id);
    ProjectDto createProject(Long personalInfoId, ProjectDto dto);
    ProjectDto updateProject(Long id, ProjectDto dto);
    void deleteProject(Long id);
}
