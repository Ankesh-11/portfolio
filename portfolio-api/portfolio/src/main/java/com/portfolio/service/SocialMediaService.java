package com.portfolio.service;

import com.portfolio.dto.SocialMediaLinksDto;

public interface SocialMediaService {
    SocialMediaLinksDto updateLinks(Long id, SocialMediaLinksDto dto);
}
