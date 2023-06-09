package com.www.bank.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.www.bank.config.auth.LoginUser;
import com.www.bank.dto.user.JoinReqDto;
import com.www.bank.dto.user.JoinResDto;
import com.www.bank.util.CustomResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final AuthenticationManager authenticationManager;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        setFilterProcessesUrl("/api/login");
        this.authenticationManager = authenticationManager;
    }

    /// TODO : POST : /login 동작 인데  -- >? /api/login 으로 변경해야함
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        log.debug("디버그 : attemptAuthentication 호출됨");
        try {
            /// TODO : 1. USERNAME 이랑 PASSWORD 를 받음
            ObjectMapper om = new ObjectMapper();
            JoinReqDto.LoginReqDto loginReqDto = om.readValue(request.getInputStream(), JoinReqDto.LoginReqDto.class);

            /// TODO: 강제로그인 ( 2. 인증관련 토큰 )
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginReqDto.getUsername(),loginReqDto.getPassword()
            );

            //TODO: UserDetailService, loadUserByUsername 호출 ***** ( 3. loginByuserId 호출 )
            // jwt 쓴다 하더라도 , 컨트롤러 진입을 하면 시큐리티 권한체크 , 인증체크 도움을 받을수 있게 세션을 만든다
            // 이 세션의 유효기간은 request 하고 ,response 하면 끝!!
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;

        } catch (Exception e) {
            /// TODO : unsuccessfulAuthentication 호출 66번
            e.printStackTrace();
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }


    // 로그인 실패
    /// TODO : 4. userId, password db 조회후 없으면 로그인 실패
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
    CustomResponseUtil.fail(response,"로그인 실패", HttpStatus.UNAUTHORIZED);
    }
    // TODO : authentication 이 잘작동하면 successfulAuthentication 호출된다 . try 부분
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.debug("디버그 : 로그인이 된거임 호출됨");

        ///TODO : 토큰을 생성하고 리턴함.. 뿌려줌



        LoginUser loginUser = (LoginUser)authResult.getPrincipal();
        String jwtToken = JwtProcess.create(loginUser);
        response.addHeader(jwtVo.HEADER,jwtToken);

        JoinResDto.LoginResDto loginResDto = new JoinResDto.LoginResDto(loginUser.getUser());

        /// TODO :JSON 으로 반환
        CustomResponseUtil.success(response,loginResDto);

    }
}
