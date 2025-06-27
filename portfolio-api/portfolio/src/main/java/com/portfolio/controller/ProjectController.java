package com.portfolio.controller;

import com.portfolio.dto.ProjectDto;
import com.portfolio.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin
public class ProjectController {

    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping("/{personalInfoId}")
    public List<ProjectDto> getProjects(@PathVariable Long personalInfoId) {
        return service.getAllProjects(personalInfoId);
    }

    @GetMapping("/project/{id}")
    public ProjectDto getProject(@PathVariable Long id) {
        return service.getProjectById(id);
    }

    @PostMapping("/{personalInfoId}")
    public ProjectDto createProject(@PathVariable Long personalInfoId, @RequestBody ProjectDto dto) {
        return service.createProject(personalInfoId, dto);
    }

    @PutMapping("/{id}")
    public ProjectDto updateProject(@PathVariable Long id, @RequestBody ProjectDto dto) {
        return service.updateProject(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        service.deleteProject(id);
    }
}
