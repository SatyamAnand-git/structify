package com.structify.service;

import com.structify.dto.BlueprintResponse;
import com.structify.entity.Blueprint;
import com.structify.entity.BlueprintStatus;
import com.structify.entity.Project;
import com.structify.entity.User;
import com.structify.exception.ProjectNotFoundException;
import com.structify.repository.BlueprintRepository;
import com.structify.repository.ProjectRepository;
import com.structify.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class BlueprintService {

    private final BlueprintRepository blueprintRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public BlueprintService(
            BlueprintRepository blueprintRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository
    ) {
        this.blueprintRepository = blueprintRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public BlueprintResponse generateBlueprint(
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

        // Also verifies project ownership
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

        Integer nextVersion = blueprintRepository
                .findTopByProjectIdOrderByVersionNumberDesc(projectId)
                .map(blueprint ->
                        blueprint.getVersionNumber() + 1
                )
                .orElse(1);

        Blueprint blueprint = new Blueprint();

        blueprint.setProject(project);
        blueprint.setVersionNumber(nextVersion);
        blueprint.setStatus(BlueprintStatus.GENERATING);

        Blueprint savedBlueprint =
                blueprintRepository.save(blueprint);

        return new BlueprintResponse(
                savedBlueprint.getId(),
                project.getId(),
                savedBlueprint.getVersionNumber(),
                savedBlueprint.getStatus(),
                savedBlueprint.getCreatedAt(),
                savedBlueprint.getUpdatedAt()
        );
    }
}