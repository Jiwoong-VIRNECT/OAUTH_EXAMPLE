package com.auth.oauth.global.config;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.oauth.dto.entity.UserDetailsImpl;

public interface ChatbotUserRepository extends JpaRepository<UserDetailsImpl, Long> {

	public UserDetailsImpl findByUsername(String username);
}
