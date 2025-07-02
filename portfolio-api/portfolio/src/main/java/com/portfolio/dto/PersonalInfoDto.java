package com.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonalInfoDto {
    private Long id;
    private String fullName;
    private String title;
    private String aboutMe;
    private Integer totalExperience;
    private String email;
    private String phone;
    private String profileImageUrl;
    private String resumeUrl;
    private String bio;
    private String location;
    private List<String> skills;
    private SocialMediaLinksDto socialMediaLinks;
    private List<ProjectDto> projects;

}