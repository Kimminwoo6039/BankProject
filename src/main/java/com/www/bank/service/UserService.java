package com.www.bank.service;

import com.www.bank.domain.user.User;
import com.www.bank.domain.user.UserEnum;
import com.www.bank.domain.user.UserRepository;
import com.www.bank.dto.user.JoinReqDto;
import com.www.bank.dto.user.JoinResDto;
import com.www.bank.handler.ex.CustomApiException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /// TODO : 서비스는 Dto 를 요청받고 , Dto 로 응답한다.
    @Transactional /// TODO : 트랜잭션이 메서드 시작할때 , 시작되고 종료될때 함께종료
    public JoinResDto 회원가입(JoinReqDto joinReqDto) {

        /// TODO : 동일 유저네임 존재 검사
        Optional<User> userOp = userRepository.findByUsername(joinReqDto.getUsername());

        /// TODO : isPresent 는 존재하냐 ? Optional 문법
        if (userOp.isPresent()) {
            /// TODO : 유저네임 중복일대
            throw new CustomApiException("동일한 username이 존재합니다"); /// TODO : handler 패키지에서 생성
        }

        /// TODO : 패스워드 인코딩
        User userPs = userRepository.save(joinReqDto.toEntity(bCryptPasswordEncoder));

        /// TODO : dto 응답
        return new JoinResDto(userPs);
    }



}

