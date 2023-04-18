package com.www.bank.config.jwt;

/*
* 모든 주소에서 동작함 (토큰검증)
 */

import com.www.bank.config.auth.LoginUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {


    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        if(isHeaderVerify(request,response)) {
            // 토큰이 존재함
            String token = request.getHeader(jwtVo.HEADER).replace(jwtVo.TOKEN_PREFIX,"");
            LoginUser loginUser = JwtProcess.verify(token);

            // 임시 세션 ( UserDetails 타입 or username )
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
            chain.doFilter(request,response);

    }

    private boolean isHeaderVerify(HttpServletRequest request,HttpServletResponse response) {

        String header = request.getHeader(jwtVo.HEADER);
        if(header == null || !header.startsWith(jwtVo.TOKEN_PREFIX)) {
            return false;
        } else {
            return true;
        }
    }
}
