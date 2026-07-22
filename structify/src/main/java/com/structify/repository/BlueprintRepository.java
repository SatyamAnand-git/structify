package com.structify.repository;

import com.structify.entity.Blueprint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlueprintRepository
        extends JpaRepository<Blueprint, Long> {

    Optional<Blueprint> findTopByProjectIdOrderByVersionNumberDesc(
            Long projectId
    );
}