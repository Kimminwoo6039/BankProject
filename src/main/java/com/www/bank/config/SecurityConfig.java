package com.www.bank.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.www.bank.config.jwt.JwtAuthenticationFilter;
import com.www.bank.domain.user.UserEnum;
import com.www.bank.dto.ResponseDto;
import com.www.bank.util.CustomResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration /// TODO : 설정파일
public class SecurityConfig {

    private final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean  /// TODO : Ioc 컨테이너에 BCryptPasswordEncoder() 객체가 등록됨  ( configration 등록되어있는 Bean 만등록 )
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        log.debug("디버그 : BCryptPasswordEncoder 빈 등록됨");
        return new BCryptPasswordEncoder();
    }
    
    /// TODO : JWT 필터 등록이 필요함
    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager,HttpSecurity> {

        @Override
        public void configure(HttpSecurity builder) throws Exception {
            /// TODO : 필터만들고 59 번 만들어줌
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            builder.addFilter(new JwtAuthenticationFilter(authenticationManager));
            super.configure(builder);
        }
    }

    /// TODO : JWT 서버를 만들 예정 !! Sesson 사용안하는 방식
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.debug(" 디버그 : filterChain 빈 등록됨");
        http.headers().frameOptions().disable(); /// TODO : iframe 허용안함.
        http.csrf().disable(); /// TODO : csrf 있으면 enable 이면 post 맨 작동안함
        http.cors().configurationSource(configurationSource()); /// TODO : 자바스크립트로 요청되는것을 막겠다.

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); /// TODO : JsessionId 를 서버쪽에서 관리 안하겠다는 뜻
        /// TODO: react , 앱으로 요청할대
        http.formLogin().disable(); /// TODO : 아이디,비밀번호 전송 방식을 사용안하겠다는 뜻 , 리액트나 앱으로 던져줄때 사용
        http.httpBasic().disable(); /// TODO : httpBasic 은 브라우저가 팝업창을 이용해서 사용자 인증을 진행한다.

        /// TODO : 필터 적용 (39번)
        http.apply(new CustomSecurityFilterManager());

        // Exception 가로채기 **
        http.exceptionHandling().authenticationEntryPoint( (request,response,authenticationException)->{ /// TODO : 예외처리
            String requestURI = request.getRequestURI();
            log.debug("디버그 : {}",requestURI);

//            if (requestURI.contains("admin")) {
//                CustomResponseUtil.unAuthorization(response,"관리자로 로그인을 진행해주세요");
//            } else {
//                CustomResponseUtil.unAuthentication(response,"로그인을 진행해주세요");
//            }

            CustomResponseUtil.unAuthentication(response,"로그인을 진행해주세요");
        } );

        http.authorizeRequests()
                .antMatchers("/api/s/**").authenticated()  /// TODO : api에 /api/s/ 가 들어오면 인증을 해야하고.
                .antMatchers("/api/admin/**").hasRole("UserEnum.ADMIN")  /// TODO : ROLE_ DEFAULT 값이 있음 (최근공식문서는 ROLE 안붙여두됨)
                .anyRequest().permitAll(); /// TODO : 나머지 요청은 모두 허용하겠다.
        /// TODO : Post 맨에 Forbidden 이뜨면 시큐리티 설정이 잘된거임

        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        log.debug(" 디버그 : configurationSource cors 설정이 SecurityFilterChain 등록됨");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*"); /// TODO : 모든 헤더는 다 받는다
        configuration.addAllowedMethod("*"); /// TODO : 모든 메서드를 받는다 , GET,POST,PUT,DELETE (JavaScript 요청 허용함)
        configuration.addAllowedOriginPattern("*"); /// TODO : 모든 IP 주소 허용 ( 프론트 앤드 Ip 만 허용 react)
        configuration.setAllowCredentials(true); /// TODO : 클라이언트에서 쿠키 요청 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration); /// TODO : 모든 주소요청에서 위에설정을 해주겠다.
        return source;
    }
}
