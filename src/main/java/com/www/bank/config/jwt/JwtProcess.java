package com.www.bank.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.www.bank.config.auth.LoginUser;
import com.www.bank.domain.user.User;
import com.www.bank.domain.user.UserEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class JwtProcess {
    private final Logger logger = LoggerFactory.getLogger(JwtProcess.class);


    /**
     * 토큰생성
     */
    public static String create(LoginUser loginUser) {
        String jwtToken = JWT.create()
                .withSubject("bank")  /// TODO : 토큰의 제목
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtVo.EXPIRATTION_TIME)) /// TODO 현재시간 + 토큰 1주일
                .withClaim("id",loginUser.getUser().getId())
                .withClaim("role",loginUser.getUser().getRole()+"")
                .sign(Algorithm.HMAC512(jwtVo.SECRET)); /// TODO : 제일중요한 시크릿 키
        return jwtVo.TOKEN_PREFIX+jwtToken;
    }

    /**
     * 토큰 검증
     * ( return 되는 LoginUser 객체를 강제로 시큐리티 세션에 직접 주입할 예정 )
     */
    public static LoginUser verify(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(jwtVo.SECRET)).build().verify(token);
        Long id = decodedJWT.getClaim("id").asLong();
        String role = decodedJWT.getClaim("role").asString();
        User user = User.builder().id(id).role(UserEnum.valueOf(role)).build(); /// TODO : "role" 이런식으로 들어옴
        LoginUser loginUser = new LoginUser(user);
        return loginUser;
    }
    
}
