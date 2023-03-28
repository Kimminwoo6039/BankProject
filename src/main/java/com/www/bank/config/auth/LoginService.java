package com.www.bank.config.auth;

import com.www.bank.domain.user.User;
import com.www.bank.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 로그인이 될대 세션을 만들어주는 기능
     * 시큐리티로 로그인이 될때 , 시큐리티가 loadUserByUsername 실행해서 username 을 체크
     * 체크해서 없으면 !  오류
     * 체크해서 있으면 정상적으로 시큐리티 컨텍스트 내부 세션에 로그인 된 세션이 만들어진다.
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userPS = userRepository.findByUsername(username).orElseThrow(
                ()-> new InternalAuthenticationServiceException("인증 실패")  /// TODO : 인증실패시 InternalAuthenticationServiceException 테스트 필요 꼭!!
        );
        return new LoginUser(userPS);
    }
}
