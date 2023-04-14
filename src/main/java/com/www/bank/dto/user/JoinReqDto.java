package com.www.bank.dto.user;

import com.www.bank.domain.user.User;
import com.www.bank.domain.user.UserEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class JoinReqDto {

    @Getter
    @Setter
    public static class LoginReqDto {
        private String username;
        private String password;
    }

    /// TODO : 유효성 검사
    // 영문,숫자는 되고 길이 최소 2~20자이내
    @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$",message = "영문/숫자 2~20자 이내로 작성해주세요.")
    @NotEmpty
    private String username;

    // 길이 4~20
    @NotEmpty
    @Size(min = 4,max = 20)
    private String password;


    // 이메일 형식
    @Pattern(regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$",message = "이메일형식으로 작성해주세요.")
    @NotEmpty
    private String email;

    // 영어 ,한글 1~20
    @Pattern(regexp = "^[a-zA-Z가-힣]{1,20}$",message = "영어/한글 1자에서20자내로 작성해주세요.")
    @NotEmpty
    private String fullname;

    public User toEntity(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return User.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .email(email)
                .fullname(fullname)
                .role(UserEnum.CUSTOMER)
                .build();
    }

}
