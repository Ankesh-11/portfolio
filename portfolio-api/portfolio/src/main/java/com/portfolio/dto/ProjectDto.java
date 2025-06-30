package com.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDto {
    private Long id;

    private String title;
    private String description;
    private String projectUrl;
    private String githubRepoUrl;
    private String imageUrl;
    private String techStack;

}