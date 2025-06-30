package com.portfolio.mapper;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
public class Mapper {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public PersonalInfoDto toDto(PersonalInfo entity) {
        if (entity == null) return null;

        PersonalInfoDto dto = mapper.convertValue(entity, PersonalInfoDto.class);

        if(entity.getSkills()!=null)
        {
            dto.setSkills(entity.getSkills());
        }
        if (entity.getSocialMediaLinks() != null) {
            dto.setSocialMediaLinks(toSocialDto(entity.getSocialMediaLinks()));
        }

        if (entity.getProjects() != null) {
            List<ProjectDto> projectDtos = entity.getProjects().stream()
                    .map(this::toProjectDto)
                    .collect(Collectors.toList());
            dto.setProjects(projectDtos);
        }

        return dto;
    }

    public SocialMediaLinksDto toSocialDto(SocialMediaLinks sm) {
        if (sm == null) return null;
        return mapper.convertValue(sm, SocialMediaLinksDto.class);
    }

    public ProjectDto toProjectDto(Project project) {
        if (project == null) return null;
        return mapper.convertValue(project, ProjectDto.class);
    }

    public PersonalInfo toNewPersonalInfoEntity(PersonalInfoDto dto) {

        if (dto == null) return null;
        PersonalInfo entity = mapper.convertValue(dto, PersonalInfo.class);
        if(dto.getSkills()!=null)
        {
            entity.setSkills(
                    dto.getSkills());
        }
        if (dto.getSocialMediaLinks() != null) {
            entity.setSocialMediaLinks(toNewSocialMediaLinksEntity(dto.getSocialMediaLinks()));
        }
        if (dto.getProjects() != null) {
            List<Project> projects = dto.getProjects().stream()
                    .map(this::toNewProjectEntity)
                    .collect(Collectors.toList());
            entity.setProjects(projects);
        }
        return entity;
    }

    public SocialMediaLinks toNewSocialMediaLinksEntity(SocialMediaLinksDto dto) {
        if (dto == null) return null;
        return mapper.convertValue(dto, SocialMediaLinks.class);
    }

    public Project toNewProjectEntity(ProjectDto dto) {
        if (dto == null) return null;
        return mapper.convertValue(dto, Project.class);
    }

    public void updatePersonalInfoFromDto(PersonalInfoDto dto, PersonalInfo entity) throws JsonMappingException {
        if (dto == null || entity == null) return;
        mapper.updateValue(entity, dto);
        if(dto.getSkills()!=null)
        {
            entity.setSkills(dto.getSkills());
        }else {
            entity.setSkills(null);
        }

        if (dto.getSocialMediaLinks() != null) {
            if (entity.getSocialMediaLinks() == null) {
                entity.setSocialMediaLinks(toNewSocialMediaLinksEntity(dto.getSocialMediaLinks()));
            } else {
                    updateSocialMediaLinksFromDto(dto.getSocialMediaLinks(), entity.getSocialMediaLinks());
            }
        } else {
            entity.setSocialMediaLinks(null);
        }

        if (dto.getProjects() != null) {
            List<Project> updatedProjects = dto.getProjects().stream()
                    .map(projectDto -> {
                        if (projectDto.getId() != null && entity.getProjects() != null) {
                            Project existingProject = entity.getProjects().stream()
                                    .filter(p -> p.getId() != null && p.getId().equals(projectDto.getId()))
                                    .findFirst()
                                    .orElse(null);
                            if (existingProject != null) {
                                try {
                                    updateProjectFromDto(projectDto, existingProject);
                                } catch (JsonMappingException e) {
                                    throw new RuntimeException(e);
                                }
                                return existingProject;
                            }
                        }
                        return toNewProjectEntity(projectDto);
                    })
                    .collect(Collectors.toList());
            entity.setProjects(updatedProjects);
        } else {
            entity.setProjects(null);
        }
    }

    public void updateSocialMediaLinksFromDto(SocialMediaLinksDto dto, SocialMediaLinks entity) throws JsonMappingException {
        if (dto == null || entity == null) return;
        mapper.updateValue(entity, dto);
    }

    public void updateProjectFromDto(ProjectDto dto, Project entity) throws JsonMappingException {
        if (dto == null || entity == null) return;
        mapper.updateValue(entity, dto);
    }
}