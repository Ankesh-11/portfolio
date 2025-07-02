package com.portfolio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "personal_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private int version;

    @NotBlank
    private String fullName;

    @NotBlank
    private String title;

    @Lob
    private String aboutMe;

    @Email
    private String email;

    private String phone;
    private Integer totalExperience;
    private String profileImageUrl;
    private String resumeUrl;

    @Lob
    private String bio;

    private String location;

    @ElementCollection
    private List<String> skills;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "social_media_links_id")
    private SocialMediaLinks socialMediaLinks;

    @OneToMany(mappedBy = "personalInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Project> projects;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    @JsonIgnore
    private User user;
}