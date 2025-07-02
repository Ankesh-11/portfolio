package com.portfolio.service.Impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.dto.PersonalInfoDto;
import com.portfolio.dto.ProjectDto;
import com.portfolio.exception.UserNotFoundException;
import com.portfolio.model.PersonalInfo;
import com.portfolio.model.Project;
import com.portfolio.repository.PersonalInfoRepository;
import com.portfolio.repository.ProjectRepository;
import com.portfolio.service.PersonalInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Slf4j
@Service
public class PersonalInfoServiceImpl implements PersonalInfoService {

    private final PersonalInfoRepository personalInfoRepository;
    private final ObjectMapper objectMapper;
    private final ProjectRepository projectRepository;

    public PersonalInfoServiceImpl(PersonalInfoRepository personalInfoRepository,
                                   ObjectMapper objectMapper,
                                   ProjectRepository projectRepository) {
        this.personalInfoRepository = personalInfoRepository;
        this.objectMapper = objectMapper;
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PersonalInfoDto getInfo() throws UserNotFoundException {
        log.debug("Fetching personal info from DB...");
        PersonalInfo info = personalInfoRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new UserNotFoundException("No personal info found in the database. Please create one."));
        log.info("Found personal info with ID: {}", info.getId());
        return objectMapper.convertValue(info,PersonalInfoDto.class);
    }

    @Override
    @Transactional
    public PersonalInfo addAndUpdateInfo(PersonalInfoDto dto) throws JsonMappingException, UserNotFoundException {
        log.debug("Starting addOrUpdate for PersonalInfoDto: {}", dto.getFullName());
        PersonalInfo existingPersonalInfo;

        if (dto.getId() != null) {
            log.debug("Updating existing personal info with ID: {}", dto.getId());
            existingPersonalInfo = personalInfoRepository.findById(dto.getId())
                    .orElseThrow(() -> new UserNotFoundException("Not found personal Info with ID " + dto.getId() + " not found."));
        } else {
            log.debug("Creating new personal info...");
            existingPersonalInfo = personalInfoRepository.findAll().stream().findFirst()
                    .orElse(new PersonalInfo());
        }

        log.debug("Mapping DTO to entity...");

        if (dto.getProjects() != null) {
            log.debug("Updating project list... count = {}", dto.getProjects().size());
            if (existingPersonalInfo.getProjects() == null) {
                existingPersonalInfo.setProjects(new ArrayList<>());
            } else {
                existingPersonalInfo.getProjects().clear();
            }

            for (ProjectDto projectDto : dto.getProjects()) {
                log.debug("Processing project: {}", projectDto.getTitle());
                Project projectEntity;
                if (projectDto.getId() != null) {
                    projectEntity = projectRepository.findById(projectDto.getId())
                            .orElse( objectMapper.convertValue(projectDto,Project.class));

                    if (projectEntity.getId() != null && projectEntity.getId().equals(projectDto.getId())) {
                        objectMapper.convertValue(projectDto,Project.class);
                        log.debug("Updated existing project: {}", projectEntity.getTitle());
                    }
                } else {
                    projectEntity = objectMapper.convertValue(projectDto,Project.class);
                    log.debug("Created new project entity: {}", projectEntity.getTitle());
                }
                projectEntity.setPersonalInfo(existingPersonalInfo);
                existingPersonalInfo.getProjects().add(projectEntity);
            }
        } else {
            log.debug("Clearing existing projects...");
            if (existingPersonalInfo.getProjects() != null) {
                existingPersonalInfo.getProjects().clear();
            }
        }

        log.debug("Saving personal info to DB...");
        PersonalInfo savedPersonalInfo = personalInfoRepository.save(existingPersonalInfo);
        log.info("Personal info saved successfully with ID: {}", savedPersonalInfo.getId());

        return savedPersonalInfo;
    }

    @Override
    public Boolean deleteUser(Long id) throws UserNotFoundException {
        log.debug("Attempting to delete personal info with ID: {}", id);
        if (!personalInfoRepository.existsById(id)) {
            log.warn("User not found with ID: {}", id);
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        personalInfoRepository.deleteById(id);
        log.info("Deleted personal info with ID: {}", id);
        return true;
    }
}
