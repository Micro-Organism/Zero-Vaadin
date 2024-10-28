package com.zero.vaddin.common.repository;

import com.zero.vaddin.domain.entity.SystemUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemUserRepository extends JpaRepository<SystemUserEntity, Long> {

    List<SystemUserEntity> findByLastNameStartsWithIgnoreCase(String lastName);
}
