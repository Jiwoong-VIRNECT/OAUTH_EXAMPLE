package com.auth.oauth.global.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

// 인증서버 임을 알리는 어노테이션
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	// 인증 서버는 클라이언트 애플리케이션에게 액세스토큰,리프레시 토큰 등을 발급해주는 역할을 하는 서버이다

	@Autowired
	private DataSource dataSource;
	@Autowired
	private ClientDetailsService clientDetailsService;
	@Autowired
	private UserDetailsService userDetailsService;


	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
	}

	/*
	 * Client에 대한 인증 처리를 위한 설정
	 * 1) In-Memory 설정 - 기본 구현체 InMemoryClientDetailsService(Map에 클라이언트를 저장)
	 * 2) JDBC 설정 - 기본 구현체 JdbcClientDetailsService(JdbcTemplate를 이용한 DB이용)
	 * 3) CleintDetailsService 설정
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		/*
		 * Client를 DB에서 관리하기 위하여 DataSource 주입.
		 * UserDetailsService와 동일한 역할을 하는 객체이다.
		 */
		clients.withClientDetails(clientDetailsService);
	}


	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		// Authorize, Token발급, Token Check 할때의 설정이 포함됨
		
		// userDetailService : Refresh Token을 통해 Access Token을 재발급 받을 때 사용
		// .authorizationCodeServices : Resource Owner의 인증을 통해 Client가 얻는 인증 코드를 다루는 클래스 설정(현재 jdbc 사용)
		// .approvalStore : Resource owner의 리소스의 사용을 허락한다는 내용 (현재 DB 에서 관리함)
		// .tokenStore : 어떤 Token을 사용할지 설정하는 메소드, 현재 jwt token 사용 설정됨
		
		// TODO Auto-generated method stub
		endpoints
			.userDetailsService(userDetailsService) //refresh token 발급을 위해서는 UserDetailsService(AuthenticationManager authenticate()에서 사용)필요
			.authorizationCodeServices(new JdbcAuthorizationCodeServices(dataSource)) //authorization code를 DB로 관리 코드 테이블의 authentication은 blob데이터타입으로..
			.approvalStore(approvalStore()) //리소스 소유자의 승인을 추가, 검색, 취소하기 위한 메소드를 정의
			.tokenStore(tokenStore()) //토큰과 관련된 인증 데이터를 저장, 검색, 제거, 읽기를 정의
			.accessTokenConverter(accessTokenConverter())
		;
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JdbcApprovalStore approvalStore() {
		return new JdbcApprovalStore(dataSource);
	}
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("non-prod-signature");

		return converter;
	}

	/*
	 * 새로운 클라이언트 등록을 위한 빈
	 */
	@Bean
	public ClientRegistrationService clientRegistrationService() {
		return new JdbcClientDetailsService(dataSource);
	}

}