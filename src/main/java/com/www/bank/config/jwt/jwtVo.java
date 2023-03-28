package com.www.bank.config.jwt;

/**
 * SECERT 는 노출되면 안된다. ( 환경변수,클라우드AWS 꺼내서 써야함 )
 * 리플래시 토큰 (X)
 */
public interface jwtVo {
    public static final String SECRET = "민우코딩"; // HS256 (대칭키)
    public static final int EXPIRATTION_TIME = 1000 * 60 * 60 * 24 * 7; // 일주일
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
}
