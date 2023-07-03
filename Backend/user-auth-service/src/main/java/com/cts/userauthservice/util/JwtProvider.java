package com.cts.userauthservice.util;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtProvider {
//	@Autowired
//	private JwtEncoder jwtEncoder;
//	@Autowired
//	private JwtDecoder jwtDecoder;

//	public String generateToken(Authentication authentication) {
//		User user = (User) authentication.getPrincipal();
//		GrantedAuthority authority = user.getAuthorities().iterator().next();
//		String role = "ROLE_" + authority.getAuthority();
//		return generateTokenWithUsername(user.getUsername(), role);
//	}
//
//	public String extractSubject(String token) {
//		Jwt jwt = jwtDecoder.decode(token);
//		return jwt.getSubject();
//	}
//
//	private String generateTokenWithUsername(String username, String role) {
//		JwtClaimsSet claims = JwtClaimsSet.builder().issuer("self").issuedAt(Instant.now()).subject(username)
//				.claim("scope", role).build();
//		return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
//	}
	
	private String secret = "secret";

	public String extractSubject(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}

	
	private String createToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	
	public boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	
	public boolean validateToken(String token, User userDetails) {
		final String username = extractSubject(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
