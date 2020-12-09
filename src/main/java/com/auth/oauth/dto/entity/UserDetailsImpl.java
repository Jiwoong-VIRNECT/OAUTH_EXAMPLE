package com.auth.oauth.dto.entity;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.auth.oauth.global.config.UserRole;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = -4608347932140057654L;

	@Id
	private Long id;
	private String username;
	private String password;
	private UserRole role;

	@Column(length=2000)
	private String access_token;

	private LocalDateTime access_token_validity;

	@Column(length=2000)
	private String refresh_token;

	@Transient
	private Collection<? extends GrantedAuthority> authorities;
	@Transient
	private boolean accountNonExpired = true;
	@Transient
	private boolean accountNonLocked = true;
	@Transient
	private boolean credentialsNonExpired = true;
	@Transient
	private boolean enabled = true;

}