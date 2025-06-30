package com.portfolio.service.Impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.portfolio.dto.PersonalInfoDto;
import com.portfolio.dto.ProjectDto;
import com.portfolio.dto.SocialMediaLinksDto;
import com.portfolio.exception.UserNotFoundException;
import com.portfolio.mapper.Mapper;
import com.portfolio.model.PersonalInfo;
import com.portfolio.model.Project;
import com.portfolio.model.SocialMediaLinks;
import com.portfolio.repository.PersonalInfoRepository;
import com.portfolio.repository.ProjectRepository;
import com.portfolio.repository.SocialMediaLinksRepository;
import com.portfolio.service.PersonalInfoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class PersonalInfoServiceImpl implements PersonalInfoService {

    private final PersonalInfoRepository personalInfoRepository;
    private final Mapper mapper;
    private final SocialMediaLinksRepository socialMediaLinksRepository;
    private final ProjectRepository projectRepository;

    public PersonalInfoServiceImpl(PersonalInfoRepository personalInfoRepository, Mapper mapper,
                                   SocialMediaLinksRepository socialMediaLinksRepository,
                                   ProjectRepository projectRepository) {
        this.personalInfoRepository = personalInfoRepository;
        this.mapper = mapper;
        this.socialMediaLinksRepository = socialMediaLinksRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PersonalInfoDto getInfo() throws UserNotFoundException {
        PersonalInfo info = personalInfoRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new UserNotFoundException("No personal info found in the database. Please create one."));
        return mapper.toDto(info);
    }

    @Override
    @Transactional
    public PersonalInfoDto updateInfo(PersonalInfoDto dto) throws JsonMappingException, UserNotFoundException {
        PersonalInfo existingPersonalInfo;

        if (dto.getId() != null) {
            existingPersonalInfo = personalInfoRepository.findById(dto.getId())
                    .orElseThrow(() -> new UserNotFoundException("Not found personal Info with ID " + dto.getId() + " not found."));
        } else {
            existingPersonalInfo = personalInfoRepository.findAll().stream().findFirst()
                    .orElse(new PersonalInfo());
        }

        mapper.updatePersonalInfoFromDto(dto, existingPersonalInfo);

        if (dto.getProjects() != null) {
            if (existingPersonalInfo.getProjects() == null) {
                existingPersonalInfo.setProjects(new ArrayList<>());
            } else {
                existingPersonalInfo.getProjects().clear();
            }

            for (ProjectDto projectDto : dto.getProjects()) {
                Project projectEntity;
                if (projectDto.getId() != null) {
                    projectEntity = projectRepository.findById(projectDto.getId())
                            .orElse(mapper.toNewProjectEntity(projectDto));

                    if (projectEntity.getId() != null && projectEntity.getId().equals(projectDto.getId())) {
                        mapper.updateProjectFromDto(projectDto, projectEntity);
                    }
                } else {
                    projectEntity = mapper.toNewProjectEntity(projectDto);
                }
                projectEntity.setPersonalInfo(existingPersonalInfo);
                existingPersonalInfo.getProjects().add(projectEntity);
            }
        } else {
            if (existingPersonalInfo.getProjects() != null) {
                existingPersonalInfo.getProjects().clear();
            }
        }

        PersonalInfo savedPersonalInfo = personalInfoRepository.save(existingPersonalInfo);

        return mapper.toDto(savedPersonalInfo);
    }

    @Override
    public Boolean deleteUser(Long id) throws UserNotFoundException {
        if (!personalInfoRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        personalInfoRepository.deleteById(id);
        return true;
    }
}