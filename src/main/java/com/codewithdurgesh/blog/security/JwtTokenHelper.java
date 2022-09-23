package com.codewithdurgesh.blog.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {
	
	public static final long JWT_TOKEN_VALIDITY = 5*60*60;
	
	private String secret = "jwtTokenKey";
	
	
	//retrive username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token,Claims::getSubject);
		
	}
	
	//retrieve expiratrion date from jwt token
	
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token,Claims::getExpiration);
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
		final Claims claims =  getAllClaimsFromToken(token);
		return claimResolver.apply(claims);
		
	}
	//for retrieving any information from token we willl need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		
	}
	//check if the token expired;
	private Boolean isTokenExpired(String token) {
		final Date expirationDate = getExpirationDateFromToken(token);
		return expirationDate.before(new Date());
	}
	
	/// generate token for user:
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claimsMap = new HashMap<>();
		return doGenerateToken(claimsMap,userDetails.getUsername());
		
	}
	
	//while creating the token--
	/***
	 * 1. defin claims ofr the token , like Issuer, Expiration ,subjct and the Id
	 * 2. Sign the Jwt useing the HS512 algo and secreat key
	 * 3. accorind to jws compact seialization 
	 * compactionaof jwt to a url safes string...
	 */
	private String doGenerateToken(Map<String, Object> claims,String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()+ JWT_TOKEN_VALIDITY*1000)) 
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) & !isTokenExpired(token));
	}
	
}
