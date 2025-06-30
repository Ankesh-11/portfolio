package com.portfolio.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "personal_info") // Added for clarity, defaults to class name
@Data // Includes @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
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

    @NotBlank
    @Column(length = 10000)
    private String aboutMe;

    @Email
    private String email;

    private String phone;

    private String profileImageUrl;

    private String resumeUrl;

    @NotBlank
    @Column(length = 100000)
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

}