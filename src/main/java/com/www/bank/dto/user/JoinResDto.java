package com.www.bank.dto.user;

import com.www.bank.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinResDto {


    private Long id;
    private String username;
    private String fullname;

    public JoinResDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.fullname = user.getFullname();
    }

}
