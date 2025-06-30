package com.portfolio.service;

import com.portfolio.dto.ProjectDto;
import com.portfolio.exception.ProjectNotFoundException;
import com.portfolio.exception.UserNotFoundException;

import java.util.List;

public interface ProjectService {
    List<ProjectDto> getAllProjects(Long personalInfoId);
    ProjectDto getProjectById(Long id) throws ProjectNotFoundException;
    ProjectDto createProject(Long personalInfoId, ProjectDto dto) throws UserNotFoundException;
    ProjectDto updateProject(Long id, ProjectDto dto) throws ProjectNotFoundException;
    boolean deleteProject(Long id) throws ProjectNotFoundException;
}
