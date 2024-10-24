package com.inha.springbootapp.domain.user.repository;

import com.inha.springbootapp.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findBySocialLoginId(String socialLoginId);
    User findByEmail(String email);
}