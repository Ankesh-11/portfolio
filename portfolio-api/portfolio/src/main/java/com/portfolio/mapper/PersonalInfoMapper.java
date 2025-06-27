package com.portfolio.mapper;

import com.portfolio.dto.PersonalInfoDto;
import com.portfolio.dto.ProjectDto;
import com.portfolio.dto.SocialMediaLinksDto;
import com.portfolio.model.PersonalInfo;
import com.portfolio.model.Project;
import com.portfolio.model.SocialMediaLinks;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonalInfoMapper {
    public PersonalInfoDto toDto(PersonalInfo entity) {
        if (entity == null) return null;

        PersonalInfoDto dto = new PersonalInfoDto();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setTitle(entity.getTitle());
        dto.setAboutMe(entity.getAboutMe());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setProfileImageUrl(entity.getProfileImageUrl());
        dto.setResumeUrl(entity.getResumeUrl());
        dto.setBio(entity.getBio());
        dto.setLocation(entity.getLocation());
        dto.setSocialMediaLinks(mapSocialMediaLinksToDto(entity.getSocialMediaLinks()));

        if (entity.getProjects() != null) {
            List<ProjectDto> projectDtos = entity.getProjects().stream()
                    .map(this::mapProjectToDto)
                    .collect(Collectors.toList());
            dto.setProjects(projectDtos);
        }

        return dto;
    }

    public PersonalInfo toEntity(PersonalInfoDto dto) {
        if (dto == null) return null;

        PersonalInfo entity = new PersonalInfo();
        entity.setId(dto.getId());
        entity.setFullName(dto.getFullName());
        entity.setTitle(dto.getTitle());
        entity.setAboutMe(dto.getAboutMe());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setProfileImageUrl(dto.getProfileImageUrl());
        entity.setResumeUrl(dto.getResumeUrl());
        entity.setBio(dto.getBio());
        entity.setLocation(dto.getLocation());
        entity.setSocialMediaLinks(mapSocialMediaDtoToEntity(dto.getSocialMediaLinks()));
        if (entity.getProjects() != null) {
            List<Project> projects = dto.getProjects().stream()
                    .map(this::mapProjectDtoToProject)
                    .collect(Collectors.toList());
            entity.setProjects(projects);
        }
        return entity;
    }

    private SocialMediaLinksDto mapSocialMediaLinksToDto(SocialMediaLinks sm) {
        if (sm == null) return null;

        SocialMediaLinksDto dto = new SocialMediaLinksDto();
        dto.setId(sm.getId());
        dto.setLinkedIn(sm.getLinkedIn());
        dto.setGithub(sm.getGithub());
        dto.setInstagram(sm.getInstagram());
        dto.setLeetcode(sm.getLeetcode());
        dto.setGfg(sm.getGfg());
        dto.setPortfolioDriveLink(sm.getPortfolioDriveLink());
        return dto;
    }

    private SocialMediaLinks mapSocialMediaDtoToEntity(SocialMediaLinksDto dto) {
        if (dto == null) return null;

        SocialMediaLinks sm = new SocialMediaLinks();
        sm.setId(dto.getId());
        sm.setLinkedIn(dto.getLinkedIn());
        sm.setGithub(dto.getGithub());
        sm.setInstagram(dto.getInstagram());
        sm.setLeetcode(dto.getLeetcode());
        sm.setGfg(dto.getGfg());
        sm.setPortfolioDriveLink(dto.getPortfolioDriveLink());
        return sm;
    }

    private ProjectDto mapProjectToDto(Project p) {
        if (p == null) return null;

        ProjectDto dto = new ProjectDto();
        dto.setId(p.getId());
        dto.setTitle(p.getTitle());
        dto.setDescription(p.getDescription());
        dto.setTechStack(p.getTechStack());
        dto.setProjectUrl(p.getProjectUrl());
        dto.setImageUrl(p.getImageUrl());
        return dto;
    }

    private Project mapProjectDtoToProject(ProjectDto projectDto) {
        if (projectDto == null) return null;

        Project project = new Project();
        project.setId(projectDto.getId());
        project.setTitle(projectDto.getTitle());
        project.setDescription(projectDto.getDescription());
        project.setTechStack(projectDto.getTechStack());
        project.setProjectUrl(projectDto.getProjectUrl());
        project.setImageUrl(projectDto.getImageUrl());
        return project;
    }
}