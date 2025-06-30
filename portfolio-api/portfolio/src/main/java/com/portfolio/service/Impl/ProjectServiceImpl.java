package com.portfolio.service.Impl;

import com.portfolio.dto.ProjectDto;
import com.portfolio.exception.ProjectNotFoundException;
import com.portfolio.exception.UserNotFoundException;
import com.portfolio.model.PersonalInfo;
import com.portfolio.model.Project;
import com.portfolio.repository.PersonalInfoRepository;
import com.portfolio.repository.ProjectRepository;
import com.portfolio.service.ProjectService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepo;
    private final PersonalInfoRepository personalRepo;

    public ProjectServiceImpl(ProjectRepository projectRepo, PersonalInfoRepository personalRepo) {
        this.projectRepo = projectRepo;
        this.personalRepo = personalRepo;
    }

    private ProjectDto mapToDto(Project project) {
        ProjectDto dto = new ProjectDto();
        dto.setId(project.getId());
        dto.setTitle(project.getTitle());
        dto.setDescription(project.getDescription());
        dto.setTechStack(project.getTechStack());
        dto.setGithubRepoUrl(project.getGithubRepoUrl());
        dto.setProjectUrl(project.getProjectUrl());
        dto.setImageUrl(project.getImageUrl());
        return dto;

    }

    private Project mapToEntity(ProjectDto dto) {
        Project project = new Project();
        project.setId(dto.getId());
        project.setGithubRepoUrl(dto.getGithubRepoUrl());
        project.setTitle(dto.getTitle());
        project.setDescription(dto.getDescription());
        project.setTechStack(dto.getTechStack());
        project.setProjectUrl(dto.getProjectUrl());
        project.setImageUrl(dto.getImageUrl());
        return project;

    }

    @Override
    public List<ProjectDto> getAllProjects(Long personalInfoId) {
        return projectRepo.findByPersonalInfoId(personalInfoId)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ProjectDto getProjectById(Long id) throws ProjectNotFoundException {
        return projectRepo.findById(id).map(this::mapToDto)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id :"+id));
    }

    @Override
    public ProjectDto createProject(Long personalInfoId, ProjectDto dto) throws UserNotFoundException {
        PersonalInfo personalInfo = personalRepo.findById(personalInfoId)
                .orElseThrow(() -> new UserNotFoundException("Personal information not found for id : "+personalInfoId));
        Project project = mapToEntity(dto);
        project.setPersonalInfo(personalInfo);
        return mapToDto(projectRepo.save(project));
    }

    @Override
    public ProjectDto updateProject(Long id, ProjectDto dto) throws ProjectNotFoundException {
        Project existing = projectRepo.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id : "+id));
        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setTechStack(dto.getTechStack());
        existing.setProjectUrl(dto.getProjectUrl());
        existing.setImageUrl(dto.getImageUrl());
        return mapToDto(projectRepo.save(existing));
    }

    @Override
    public boolean deleteProject(Long id) throws ProjectNotFoundException {
        if (!projectRepo.existsById(id)) {
            throw new ProjectNotFoundException("Project not found with ID: " + id);
        }
        projectRepo.deleteById(id);
        return true;
    }
}
