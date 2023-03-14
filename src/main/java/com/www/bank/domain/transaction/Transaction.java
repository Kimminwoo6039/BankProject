package com.www.bank.domain.transaction;

import com.www.bank.domain.account.Account;
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
@Table(name = "transaction_tb")
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account withdrawAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account depositAccount;

    @Column(nullable = false)
    private Long amount;
    
    private Long withdrawAccountBalance; // 111 계좌 - > 1000원 - > 500 > 200원 히스토리 작성
    private Long depositAccountBalance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionEnum gubun; // WITHDRAW , DEPOSIT , TRANSFER , ALL

    // 계좌가 사라져도 로그는 남아야 한다.
    private String sender;
    private String receiver;
    private String tel;

    @CreatedDate //Insert
    @Column(nullable = false)
    private LocalDateTime createAt;

    @LastModifiedDate // Insert, Update
    @Column(nullable = false)
    private LocalDateTime updateAt;

    @Builder
    public Transaction(Long id, Account withdrawAccount, Account depositAccount, Long amount, Long withdrawAccountBalance, Long depositAccountBalance, TransactionEnum gubun, String sender, String receiver, String tel, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.withdrawAccount = withdrawAccount;
        this.depositAccount = depositAccount;
        this.amount = amount;
        this.withdrawAccountBalance = withdrawAccountBalance;
        this.depositAccountBalance = depositAccountBalance;
        this.gubun = gubun;
        this.sender = sender;
        this.receiver = receiver;
        this.tel = tel;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
