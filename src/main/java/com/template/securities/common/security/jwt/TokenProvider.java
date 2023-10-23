package com.template.securities.common.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY= "auth";

    @Value("${jwt.secret}")
    private String accessSecret;
    @Value("${jwt.token-validity-in-seconds}")
    private long accessTokenValidityInMilliSeconds;

    @Value("${jwt.refresh.secret}")
    private String refreshSecret;
    @Value("${jwt.refresh.token-validity-in-seconds}")
    private long refreshTokenValidityInMilliSeconds;

    private Key key;
    private Key refreshKey;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(accessSecret);
        this.key = Keys.hmacShaKeyFor(keyBytes);

        byte[] secretKeyBytes = Decoders.BASE64.decode(refreshSecret);
        this.refreshKey = Keys.hmacShaKeyFor(secretKeyBytes);
    }

    public String createAccessToken(Authentication authentication){
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now+this.accessTokenValidityInMilliSeconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY,authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public String createRefreshToken(Authentication authentication){
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now+this.refreshTokenValidityInMilliSeconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY,authorities)
                .signWith(refreshKey, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();

    }

    public Authentication getAuthentication(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        System.out.println(claims);
        List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal,token,authorities);
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (SecurityException| MalformedJwtException e){
            log.info("잘못된 jwt 서명입니다.");
        }catch (ExpiredJwtException e){
            log.info("만료된 jwt 서명입니다.");
        }catch (UnsupportedJwtException e){
            log.info("지원되지 않는 jwt 토큰입니다.");
        }catch (IllegalArgumentException e){
            log.info("Jwt 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
