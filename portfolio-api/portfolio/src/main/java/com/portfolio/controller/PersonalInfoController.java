package com.portfolio.controller;

import com.portfolio.dto.PersonalInfoDto;
import com.portfolio.service.PersonalInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personal-info")
@CrossOrigin
public class PersonalInfoController {

private final PersonalInfoService service;

    public PersonalInfoController(PersonalInfoService service) {
        this.service = service;
    }

    @GetMapping
    public PersonalInfoDto getPersonalInfo() {
        return service.getInfo();
    }

    @PutMapping
    public PersonalInfoDto updatePersonalInfo(@Valid @RequestBody PersonalInfoDto dto) {
        return service.updateInfo(dto);
    }
}