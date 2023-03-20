package com.www.bank.config;

import com.www.bank.domain.user.User;
import com.www.bank.domain.user.UserEnum;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;


public class DummyObejct {

    /**
     * 실제 저장용
     * */
    protected User newUser(String username, String fullname) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodePassword = bCryptPasswordEncoder.encode("1234");
        return   User.builder()
                .username(username)
                .password(encodePassword)
                .email(username+"@naver.com")
                .fullname(fullname)
                .role(UserEnum.CUSTOMER)
                .build();
    }

    /**
     * 테스트용
     * */

    protected User newMockUser(Long id, String username, String fullname) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodePassword = bCryptPasswordEncoder.encode("1234");
        return   User.builder()
                .id(id)
                .username(username)
                .password(encodePassword)
                .email(username+"@naver.com")
                .fullname(fullname)
                .role(UserEnum.CUSTOMER)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }
}
