package com.www.bank.domain.account;

import com.www.bank.domain.user.User;
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
@Table(name = "account_tb")
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false , length = 20)
    private Long number; // 계좌번호

    @Column(unique = true, nullable = false , length = 4)
    private Long password; // 계좌비번

    @Column(nullable = false)
    private Long balance; // 잔액 (기본값 1000원)

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @CreatedDate //Insert
    @Column(nullable = false)
    private LocalDateTime createAt;

    @LastModifiedDate // Insert, Update
    @Column(nullable = false)
    private LocalDateTime updateAt;

    @Builder
    public Account(Long id, Long number, Long password, Long balance, User user, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.number = number;
        this.password = password;
        this.balance = balance;
        this.user = user;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
