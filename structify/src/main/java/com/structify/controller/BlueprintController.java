package com.structify.controller;

import com.structify.dto.BlueprintResponse;
import com.structify.service.BlueprintService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/{projectId}/blueprints")
public class BlueprintController {

    private final BlueprintService blueprintService;

    public BlueprintController(
            BlueprintService blueprintService
    ) {
        this.blueprintService = blueprintService;
    }

    @PostMapping
    public BlueprintResponse generateBlueprint(
            @PathVariable Long projectId,
            Authentication authentication
    ) {

        return blueprintService.generateBlueprint(
                projectId,
                authentication.getName()
        );
    }
}