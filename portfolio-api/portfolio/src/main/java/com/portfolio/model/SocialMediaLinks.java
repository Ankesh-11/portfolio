package com.portfolio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "social_media_links")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialMediaLinks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // No @Version here is generally fine if it's always managed via PersonalInfo
    // If SocialMediaLinks could be updated independently, you might add @Version.

    private String linkedIn;
    private String github;
    private String instagram;
    private String leetcode;
    private String gfg;
    private String portfolioDriveLink;

}