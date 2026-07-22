package com.structify.controller;

import com.structify.dto.CreateProjectRequest;
import com.structify.dto.ProjectResponse;
import com.structify.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.structify.dto.UpdateProjectRequest;
import org.springframework.http.HttpStatus;
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ProjectResponse createProject(
            @Valid @RequestBody CreateProjectRequest request,
            Authentication authentication
    ) {

        return projectService.createProject(
                request,
                authentication.getName()
        );
    }
    @GetMapping
    public List<ProjectResponse> getMyProjects(
            Authentication authentication
    ) {

        return projectService.getMyProjects(
                authentication.getName()
        );
    }
    @GetMapping("/{id}")
    public ProjectResponse getProjectById(
            @PathVariable Long id,
            Authentication authentication
    ) {

        return projectService.getProjectById(
                id,
                authentication.getName()
        );
    }
    @PutMapping("/{id}")
    public ProjectResponse updateProject(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProjectRequest request,
            Authentication authentication
    ) {

        return projectService.updateProject(
                id,
                request,
                authentication.getName()
        );
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(
            @PathVariable Long id,
            Authentication authentication
    ) {

        projectService.deleteProject(
                id,
                authentication.getName()
        );
    }
}