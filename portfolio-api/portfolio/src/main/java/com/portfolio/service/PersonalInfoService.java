package com.portfolio.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.portfolio.dto.PersonalInfoDto;
import com.portfolio.exception.UserNotFoundException;

public interface PersonalInfoService {
    PersonalInfoDto getInfo() throws UserNotFoundException;
    PersonalInfoDto updateInfo(PersonalInfoDto dto) throws JsonMappingException, UserNotFoundException;
    Boolean deleteUser(Long id) throws UserNotFoundException;
}