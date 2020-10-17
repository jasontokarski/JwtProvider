package com.jwt.provider.repository;

import java.util.Optional;

import com.jwt.provider.model.AuthEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository("jwtRepository")
public interface JwtRepository extends JpaRepository<AuthEntity, Long> {
    Optional <AuthEntity> findByUser(String user);

	boolean existsAuthEntityByUser(String string);
}
