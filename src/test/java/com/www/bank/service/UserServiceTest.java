package com.www.bank.service;

import com.www.bank.config.DummyObejct;
import com.www.bank.domain.user.User;
import com.www.bank.domain.user.UserEnum;
import com.www.bank.domain.user.UserRepository;
import com.www.bank.dto.user.JoinReqDto;
import com.www.bank.dto.user.JoinResDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// Spring 관련 Bean 들이 하나도 없는 환경 !!
@ExtendWith(MockitoExtension.class) /// TODO : 서비스 테스트
public class UserServiceTest extends DummyObejct {

    @InjectMocks /// TODO : 레포지리토리가 서비스에 주입됨
    private UserService userService;
    
    @Mock /// TODO : 가짜로 메모리에 띄움
    private UserRepository userRepository;
    
    @Spy /// TODO : 진짜 를 꺼냄
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    

    @Test
    public void 회원가입() throws Exception {
        //given
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("ssar");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("ssar@naver.com");
        joinReqDto.setFullname("쌀");

        // stub 1
       when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
   //     when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));

        System.out.println("Optional.empty() = " + Optional.empty());
        System.out.println("Optional.of() = " + Optional.of(new User()));

        // stub 2
        User ssar = newMockUser(1L,"ssar","쌀");
        when(userRepository.save(any())).thenReturn(ssar);

        // when
        JoinResDto joinResDto = userService.회원가입(joinReqDto);
        System.out.println("joinResDto = " + joinResDto);


        // then

        Assertions.assertThat(joinResDto.getId()).isEqualTo(1L);
        Assertions.assertThat(joinResDto.getUsername()).isEqualTo("ssar");
    }
}
