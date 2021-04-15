package com.coursesystem.app.security.jwt;

import java.util.Date;

import com.coursesystem.app.security.services.UserDetailsImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {

	private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${bezkoder.app.jwtSecret}")
	// @Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${bezkoder.app.jwtExpirationMs}")
	// @Value("${jwt.expiration}")
	private int jwtExpiration;

	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl principalUser = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder().setSubject((principalUser.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpiration))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			log.error("Invalid token signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.error("Invalid token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("Token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("Token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("Jwt string is empty: {}", e.getMessage());
		}

		return false;
	}
}
