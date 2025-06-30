package com.portfolio.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.portfolio.dto.PersonalInfoDto;
import com.portfolio.exception.UserNotFoundException;
import com.portfolio.service.PersonalInfoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personal-info")
@CrossOrigin(origins = "http://localhost:8080")
public class PersonalInfoController {

private final PersonalInfoService service;

    public PersonalInfoController(PersonalInfoService service) {
        this.service = service;
    }

    @GetMapping
    public PersonalInfoDto getPersonalInfo() throws UserNotFoundException {
        return service.getInfo();
    }

    @PutMapping
    public PersonalInfoDto updatePersonalInfo(@Valid @RequestBody PersonalInfoDto dto) throws JsonMappingException, UserNotFoundException {
        return service.updateInfo(dto);
    }

    @DeleteMapping("delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam Long id) throws UserNotFoundException {
        service.deleteUser(id);
        return new ResponseEntity<>("user deleted", HttpStatus.OK);
    }
}