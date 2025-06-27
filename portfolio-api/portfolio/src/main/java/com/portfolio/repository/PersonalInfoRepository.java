package com.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.portfolio.model.PersonalInfo;

public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long> {
}
