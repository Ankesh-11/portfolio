package com.portfolio.controller;

import com.portfolio.dto.ProjectDto;
import com.portfolio.exception.ProjectNotFoundException;
import com.portfolio.exception.UserNotFoundException;
import com.portfolio.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ProjectDto getProject(@PathVariable Long id) throws ProjectNotFoundException {
        return service.getProjectById(id);
    }

    @PostMapping("/{personalInfoId}")
    public ProjectDto createProject(@PathVariable Long personalInfoId, @RequestBody ProjectDto dto) throws UserNotFoundException {
        return service.createProject(personalInfoId, dto);
    }

    @PutMapping("/{id}")
    public ProjectDto updateProject(@PathVariable Long id, @RequestBody ProjectDto dto) throws ProjectNotFoundException {
        return service.updateProject(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id) throws ProjectNotFoundException {
        service.deleteProject(id);
        return new ResponseEntity<>("Project successfully deleted with id : "+id, HttpStatus.OK);
    }
}
