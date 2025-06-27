package com.portfolio.service.Impl;

import com.portfolio.dto.ProjectDto;
import com.portfolio.model.PersonalInfo;
import com.portfolio.model.Project;
import com.portfolio.repository.PersonalInfoRepository;
import com.portfolio.repository.ProjectRepository;
import com.portfolio.service.ProjectService;
import lombok.RequiredArgsConstructor;
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
        dto.setProjectUrl(project.getProjectUrl());
        dto.setImageUrl(project.getImageUrl());
        return dto;

    }

    private Project mapToEntity(ProjectDto dto) {
        Project project = new Project();
        project.setId(dto.getId());
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
    public ProjectDto getProjectById(Long id) {
        return projectRepo.findById(id).map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Override
    public ProjectDto createProject(Long personalInfoId, ProjectDto dto) {
        PersonalInfo personalInfo = personalRepo.findById(personalInfoId)
                .orElseThrow(() -> new RuntimeException("PersonalInfo not found"));
        Project project = mapToEntity(dto);
        project.setPersonalInfo(personalInfo);
        return mapToDto(projectRepo.save(project));
    }

    @Override
    public ProjectDto updateProject(Long id, ProjectDto dto) {
        Project existing = projectRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setTechStack(dto.getTechStack());
        existing.setProjectUrl(dto.getProjectUrl());
        existing.setImageUrl(dto.getImageUrl());
        return mapToDto(projectRepo.save(existing));
    }

    @Override
    public void deleteProject(Long id) {
        projectRepo.deleteById(id);
    }
}
