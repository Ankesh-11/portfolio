package com.portfolio.controller;

import com.portfolio.dto.SocialMediaLinksDto;
import com.portfolio.service.SocialMediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/social-media")
@CrossOrigin
public class SocialMediaController {

    private final SocialMediaService service;

    public SocialMediaController(SocialMediaService service) {
        this.service = service;
    }

    @PutMapping("/{id}")
    public SocialMediaLinksDto update(@PathVariable Long id, @RequestBody SocialMediaLinksDto dto) {
        return service.updateLinks(id, dto);
    }
}
