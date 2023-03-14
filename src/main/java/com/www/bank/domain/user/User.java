package com.www.bank.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor // Builder 생성시 ( 스프링이 User 객체 생성할때 빈생성자로 new 를 하기 때문!! )
@Getter
@EntityListeners(AuditingEntityListener.class) //등록시간
@Table(name = "user_tb")
@Entity
public class User { // extends 시간설정 (상속)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String username;

    @Column(nullable = false, length = 60) // 패스워드 인코딩(Bcrypt)
    private String password;

    @Column(nullable = false, length = 20)
    private String email;

    @Column(nullable = false, length = 20)
    private String fullname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserEnum role; // ADMIN ,CUSTOMER

    @CreatedDate //Insert
    @Column(nullable = false)
    private LocalDateTime createAt;

    @LastModifiedDate // Insert, Update
    @Column(nullable = false)
    private LocalDateTime updateAt;

    @Builder
    public User(Long id, String username, String password, String email, String fullname, UserEnum role, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.role = role;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
