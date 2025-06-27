package com.portfolio.service.Impl;

import com.portfolio.dto.SocialMediaLinksDto;
import com.portfolio.model.SocialMediaLinks;
import com.portfolio.repository.SocialMediaLinksRepository;
import com.portfolio.service.SocialMediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service

public class SocialMediaServiceImpl implements SocialMediaService {

    private final SocialMediaLinksRepository repo;

    public SocialMediaServiceImpl(SocialMediaLinksRepository repo) {
        this.repo = repo;
    }

    @Override
    public SocialMediaLinksDto updateLinks(Long id, SocialMediaLinksDto dto) {
        SocialMediaLinks sm = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Social links not found"));
        sm.setLinkedIn(dto.getLinkedIn());
        sm.setGithub(dto.getGithub());
        sm.setInstagram(dto.getInstagram());
        sm.setLeetcode(dto.getLeetcode());
        sm.setGfg(dto.getGfg());
        sm.setPortfolioDriveLink(dto.getPortfolioDriveLink());
        repo.save(sm);
        return dto;
    }
}
