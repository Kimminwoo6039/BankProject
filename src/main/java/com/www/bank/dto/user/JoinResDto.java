package com.www.bank.dto.user;

import com.www.bank.domain.user.User;
import com.www.bank.util.CustomDateUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinResDto {

    @Getter
    @Setter
    public static class LoginResDto {
        private Long id;
        private String username;
        private String createdAt;

        public LoginResDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.createdAt = CustomDateUtils.toStringForate(user.getCreateAt());
        }
    }

    private Long id;
    private String username;
    private String fullname;

    public JoinResDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.fullname = user.getFullname();
    }

}
