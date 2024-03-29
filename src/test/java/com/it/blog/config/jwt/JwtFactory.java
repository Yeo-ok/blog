package com.it.blog.config.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Getter
public class JwtFactory {
    //모킹(Mocking)용 객체
    //테스트 작성할 때 환경 구축이나 시간이 걸리거나 순간에 의존적인 경우에
    private String subject = "test@email.com";
    private Date issuedAt = new Date();
    private Date expiration = new Date(new Date().getTime() + Duration.ofDays(14).toMillis());

    private Map<String, Object> claims = Collections.emptyMap();

    //@Builder 패턴을 사용해서 필요한 데이터만 선택 설정

    @Builder
    private JwtFactory(String subject, java.sql.Date issuedAt, Date expiration, Map<String, Object> claims){
        this.subject = subject != null ? subject: this.subject;
        this.issuedAt = issuedAt != null ? issuedAt: this.issuedAt;
        this.expiration = expiration != null ? expiration : this.expiration;
        this.claims = claims !=null ? claims : this.claims;
    }

    public static JwtFactory withDefaultValues(){
        return JwtFactory.builder().build();
    }

    public String createToken(JwtProperties jwtProperties){
        return Jwts.builder()
                .setSubject(subject)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();

    }
}
