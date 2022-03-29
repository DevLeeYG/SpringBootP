package com.example.demo.security;

import com.example.demo.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service

public class TokenProvider {
    private  static final String SECRET_KEY = "124124124124";

    public String create(UserEntity userEntity){
        Date expiryDate = Date.from(Instant.now().plus(1,ChronoUnit.DAYS));//기한설정

        return Jwts.builder()//토큰생성
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)//헤더에 들어갈 내용 및 서명위한 키
                .setSubject(userEntity.getId())//setSub
                .setIssuer("demo app")//iss
                .setIssuedAt(new Date())//iat
                .setExpiration(expiryDate)//exp
                .compact();

    }
    //처음 토큰 인증받을때 서명한것과 비교를해서 토큰과 서명이 달라진게 없다면 패스 시크릿키는 서명

    public String validateAndGetUserId(String token){
        log.info(token);//토큰
        //parseClamimsJws가 base64 디코딩 파싱
        //헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이요해 서명한 후 token 서명과 비교
        //위조된 토큰이 아니라면 페이로드(Claims)리턴 ,위조라면 예외를 날림
        //그중 우리는 userId가 필요하므로 getBody를 부른다.
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        }catch(Exception e){
            return false;
        }
    }



}
