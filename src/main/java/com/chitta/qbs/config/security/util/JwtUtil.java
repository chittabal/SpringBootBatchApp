package com.chitta.qbs.config.security.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	private final String SECRET_KEY = "fce1f26584d2f199ce35e0f91d2a3766f074e9aa59fd4ce599dd1984698a8db1";

	private final long JWT_EXPIRATION = 3600000L;

	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public boolean validateToken(String token, String username) {
		return username.equals(extractUsername(token)) && !isTokenExpired(token);
	}

	public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}

	private Claims extractClaims(String token) {
		System.out.println("Token : - " + token);
		return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Date extractExpiration(String token) {
		return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody().getExpiration();
	}
}
