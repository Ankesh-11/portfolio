package com.portfolio.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.portfolio.dto.PersonalInfoDto;
import com.portfolio.exception.UserNotFoundException;
import com.portfolio.model.PersonalInfo;

public interface PersonalInfoService {
    PersonalInfoDto getInfo() throws UserNotFoundException;
    PersonalInfo addAndUpdateInfo(PersonalInfoDto dto) throws JsonMappingException, UserNotFoundException;
    Boolean deleteUser(Long id) throws UserNotFoundException;
}