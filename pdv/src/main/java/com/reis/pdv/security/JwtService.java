package com.reis.pdv.security;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Value("${security.jwt.expiration}")
	private int expiration;
	
	@Value("${security.jwt.key}")
	private String key;
	
	public String generateToken(String username) {
		Calendar currentTimeNow = Calendar.getInstance();
		currentTimeNow.add(Calendar.MINUTE, expiration);
		Date expirationDate = currentTimeNow.getTime();
				
		SecretKey secretKey = getSecretKey();
		
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(expirationDate)
				.signWith(secretKey)
				.compact();
	}

	private SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
	}

	private Claims getClaims(String token) {
		SecretKey secretKey = getSecretKey();
		
		return Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}
	
	public String getUserName(String token) {
		return getClaims(token).getSubject();
	}
}
