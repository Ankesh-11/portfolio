package com.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Includes @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialMediaLinksDto {
    private Long id; // Will be null for new creations, populated for updates/retrievals
    private String linkedIn;
    private String github;
    private String instagram;
    private String leetcode;
    private String gfg;
    private String portfolioDriveLink;

    // Removed redundant getters and setters, as @Data handles them.
    // public Long getId() { return id; }
    // public void setId(Long id) { this.id = id; }
    // ... and so on for all fields
}