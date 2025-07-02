package com.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.portfolio.model.PersonalInfo;

import java.util.Optional;

public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long> {
    Optional<PersonalInfo> findByUserUsername(String username);
}
