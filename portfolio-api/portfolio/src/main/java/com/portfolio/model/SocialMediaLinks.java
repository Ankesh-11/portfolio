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
    private String linkedIn;
    private String github;
    private String instagram;
    private String leetcode;
    private String gfg;
    private String portfolioDriveLink;

}