package com.portfolio.service;

import com.portfolio.dto.PersonalInfoDto;

public interface PersonalInfoService {
PersonalInfoDto getInfo();
PersonalInfoDto updateInfo(PersonalInfoDto dto);
}