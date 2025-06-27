package com.portfolio.service.Impl;

import com.portfolio.dto.PersonalInfoDto;
import com.portfolio.mapper.PersonalInfoMapper;
import com.portfolio.model.PersonalInfo;
import com.portfolio.model.Project;
import com.portfolio.repository.PersonalInfoRepository;
import com.portfolio.repository.ProjectRepository;
import com.portfolio.repository.SocialMediaLinksRepository;
import com.portfolio.service.PersonalInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalInfoServiceImpl implements PersonalInfoService {

private final PersonalInfoRepository personalInfoRepository;
private final PersonalInfoMapper mapper;
private final SocialMediaLinksRepository socialMediaLinksRepository;
private final ProjectRepository projectRepository;

    public PersonalInfoServiceImpl(PersonalInfoRepository personalInfoRepository, PersonalInfoMapper mapper, SocialMediaLinksRepository socialMediaLinksRepository, ProjectRepository projectRepository) {
        this.personalInfoRepository = personalInfoRepository;
        this.mapper = mapper;
        this.socialMediaLinksRepository = socialMediaLinksRepository;
        this.projectRepository = projectRepository;
    }


    @Override
public PersonalInfoDto getInfo() {
    PersonalInfo info = personalInfoRepository.findAll().stream().findFirst()
            .orElseThrow(() -> new RuntimeException("No personal info found"));
    return mapper.toDto(info);
}

@Override
public PersonalInfoDto updateInfo(PersonalInfoDto dto) {
    PersonalInfo entity = mapper.toEntity(dto);
    if(entity.getSocialMediaLinks()!= null){
        socialMediaLinksRepository.save(entity.getSocialMediaLinks());
    }
    List<Project> projects =entity.getProjects();
    if (projects !=null){
        projectRepository.saveAll(projects);
    }
    return mapper.toDto(personalInfoRepository.save(entity));
}
}