package com.structify.dto;

import com.structify.entity.BlueprintStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BlueprintResponse {

    private Long id;
    private Long projectId;
    private Integer versionNumber;
    private BlueprintStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}