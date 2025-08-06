package com.profect.delivery.common.repository;

import com.profect.delivery.global.entity.ErrorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ErrorLogRepository extends JpaRepository<ErrorEntity, UUID> {
}