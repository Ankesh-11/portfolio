package com.portfolio.controller;

import com.portfolio.dto.PersonalInfoDto;
import com.portfolio.exception.UserFoundException;
import com.portfolio.exception.UserNotFoundException;
import com.portfolio.service.Impl.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/me")
    public ResponseEntity<PersonalInfoDto> getPortfolio(@RequestParam(required = false) String username) throws UserNotFoundException, UserFoundException {
        return ResponseEntity.ok(portfolioService.getMyPortfolioAuto(username));
    }

    @PostMapping("/create")
    public ResponseEntity<PersonalInfoDto> saveOrUpdate(@RequestBody PersonalInfoDto dto,
                                                        Authentication authentication) throws UserNotFoundException {
        String username = authentication.getName();
        return ResponseEntity.ok(portfolioService.createOrUpdate(username, dto));
    }
}

