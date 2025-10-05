package com.digi.auth.adapter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digi.auth.domain.model.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByUsername(String username);
}


