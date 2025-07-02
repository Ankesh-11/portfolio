package com.portfolio.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.dto.PersonalInfoDto;
import com.portfolio.exception.UserFoundException;
import com.portfolio.exception.UserNotFoundException;
import com.portfolio.model.PersonalInfo;
import com.portfolio.model.SocialMediaLinks;
import com.portfolio.model.User;
import com.portfolio.repository.PersonalInfoRepository;
import com.portfolio.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PersonalInfoRepository personalInfoRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public PersonalInfoDto getMyPortfolio(String username) {
        PersonalInfo info = personalInfoRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
        return objectMapper.convertValue(info, PersonalInfoDto.class);
    }

    public PersonalInfoDto getMyPortfolioAuto(String username) throws UserFoundException, UserNotFoundException {
        if (username == null || username.isBlank()) {
            List<User> allUsers = userRepository.findAll();
            if (allUsers.isEmpty()) {
                throw new UserNotFoundException("No user found in system.");
            } else if (allUsers.size() > 1) {
                throw new UserFoundException("Multiple users found. Please provide username.");
            }
            username = allUsers.get(0).getUsername();
        }
        String finalUsername = username;
        PersonalInfo info = personalInfoRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Portfolio not found for username: " + finalUsername));
        return objectMapper.convertValue(info, PersonalInfoDto.class);
    }

    @Transactional
    public PersonalInfoDto createOrUpdate(String username, PersonalInfoDto dto) throws UserNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Optional<PersonalInfo> existingOpt = personalInfoRepository.findByUserUsername(username);
        PersonalInfo entity;

        if (existingOpt.isPresent()) {
            entity = existingOpt.get();
            if (dto.getFullName() != null) entity.setFullName(dto.getFullName());
            if (dto.getTitle() != null) entity.setTitle(dto.getTitle());
            if (dto.getAboutMe() != null) entity.setAboutMe(dto.getAboutMe());
            if (dto.getEmail() != null) entity.setEmail(dto.getEmail());
            if (dto.getPhone() != null) entity.setPhone(dto.getPhone());
            if (dto.getLocation() != null) entity.setLocation(dto.getLocation());
            if (dto.getProfileImageUrl() != null) entity.setProfileImageUrl(dto.getProfileImageUrl());
            if (dto.getResumeUrl() != null) entity.setResumeUrl(dto.getResumeUrl());
            if (dto.getBio() != null) entity.setBio(dto.getBio());
            if (dto.getSkills() != null) entity.setSkills(dto.getSkills());
            if (dto.getSocialMediaLinks() != null) entity.setSocialMediaLinks(objectMapper.convertValue(dto.getSocialMediaLinks(), SocialMediaLinks.class));

        } else {
            entity = objectMapper.convertValue(dto, PersonalInfo.class);
            entity.setUser(user);
            if (entity.getProjects() != null) {
                entity.getProjects().forEach(p -> p.setPersonalInfo(entity));
            }
        }

        PersonalInfo saved = personalInfoRepository.save(entity);
        return objectMapper.convertValue(saved, PersonalInfoDto.class);
    }

    public PersonalInfoDto updateSkills(String username, PersonalInfoDto dto) {
        PersonalInfo existing = personalInfoRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
        if (dto.getSkills() != null) {
            existing.setSkills(dto.getSkills());
        }
        return objectMapper.convertValue(personalInfoRepository.save(existing), PersonalInfoDto.class);
    }

    public PersonalInfoDto updateAboutMe(String username, PersonalInfoDto dto) {
        PersonalInfo existing = personalInfoRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));
        if (dto.getAboutMe() != null) {
            existing.setAboutMe(dto.getAboutMe());
        }
        return objectMapper.convertValue(personalInfoRepository.save(existing), PersonalInfoDto.class);
    }
}
