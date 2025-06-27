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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getLeetcode() {
        return leetcode;
    }

    public void setLeetcode(String leetcode) {
        this.leetcode = leetcode;
    }

    public String getGfg() {
        return gfg;
    }

    public void setGfg(String gfg) {
        this.gfg = gfg;
    }

    public String getPortfolioDriveLink() {
        return portfolioDriveLink;
    }

    public void setPortfolioDriveLink(String portfolioDriveLink) {
        this.portfolioDriveLink = portfolioDriveLink;
    }
}