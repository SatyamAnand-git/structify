package com.structify.service;

import com.structify.dto.CreateProjectRequest;
import com.structify.dto.ProjectResponse;
import com.structify.entity.Project;
import com.structify.entity.User;
import com.structify.repository.ProjectRepository;
import com.structify.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import com.structify.exception.ProjectNotFoundException;
import com.structify.dto.UpdateProjectRequest;
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(
            ProjectRepository projectRepository,
            UserRepository userRepository
    ) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public ProjectResponse createProject(
            CreateProjectRequest request,
            String authenticatedEmail
    ) {

        User user = userRepository
                .findByEmail(authenticatedEmail)
                .orElseThrow(() ->
                        new RuntimeException("Authenticated user not found")
                );

        Project project = new Project();

        project.setName(request.getName().trim());
        project.setDescription(request.getDescription());
        project.setUser(user);

        Project savedProject = projectRepository.save(project);

        return new ProjectResponse(
                savedProject.getId(),
                savedProject.getName(),
                savedProject.getDescription(),
                savedProject.getCreatedAt(),
                savedProject.getUpdatedAt()
        );
    }
    public List<ProjectResponse> getMyProjects(
            String authenticatedEmail
    ) {

        User user = userRepository
                .findByEmail(authenticatedEmail)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Authenticated user not found"
                        )
                );

        return projectRepository
                .findAllByUserId(user.getId())
                .stream()
                .map(project ->
                        new ProjectResponse(
                                project.getId(),
                                project.getName(),
                                project.getDescription(),
                                project.getCreatedAt(),
                                project.getUpdatedAt()
                        )
                )
                .toList();
    }
    public ProjectResponse getProjectById(
            Long projectId,
            String authenticatedEmail
    ) {

        User user = userRepository
                .findByEmail(authenticatedEmail)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Authenticated user not found"
                        )
                );

        Project project = projectRepository
                .findByIdAndUserId(
                        projectId,
                        user.getId()
                )
                .orElseThrow(() ->
                        new ProjectNotFoundException(
                                "Project not found"
                        )
                );

        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
    public ProjectResponse updateProject(
            Long projectId,
            UpdateProjectRequest request,
            String authenticatedEmail
    ) {

        User user = userRepository
                .findByEmail(authenticatedEmail)
                .orElseThrow(() ->
                        new RuntimeException("Authenticated user not found")
                );

        Project project = projectRepository
                .findByIdAndUserId(projectId, user.getId())
                .orElseThrow(() ->
                        new ProjectNotFoundException("Project not found")
                );

        project.setName(request.getName().trim());
        project.setDescription(request.getDescription());

        Project updatedProject = projectRepository.save(project);

        return new ProjectResponse(
                updatedProject.getId(),
                updatedProject.getName(),
                updatedProject.getDescription(),
                updatedProject.getCreatedAt(),
                updatedProject.getUpdatedAt()
        );
    }
    public void deleteProject(
            Long projectId,
            String authenticatedEmail
    ) {

        User user = userRepository
                .findByEmail(authenticatedEmail)
                .orElseThrow(() ->
                        new RuntimeException("Authenticated user not found")
                );

        Project project = projectRepository
                .findByIdAndUserId(projectId, user.getId())
                .orElseThrow(() ->
                        new ProjectNotFoundException("Project not found")
                );

        projectRepository.delete(project);
    }
}