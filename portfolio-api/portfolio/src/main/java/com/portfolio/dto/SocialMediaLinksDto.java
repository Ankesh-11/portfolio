package com.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialMediaLinksDto {
    private Long id;
    private String linkedIn;
    private String github;
    private String instagram;
    private String leetcode;
    private String gfg;
    private String portfolioDriveLink;
}