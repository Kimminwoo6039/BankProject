# Junit Bank App

### jpa LocalDateTime 자동으로 생성하는 법
- @EnableJpaAuditing (Main 클래스)
- @EntityListeners(AuditingEntityListener.class) (엔티티 클래스)
```java

    @CreatedDate //Insert
    @Column(nullable = false)
    private LocalDateTime createAt;

    @LastModifiedDate // Insert, Update
    @Column(nullable = false)
    private LocalDateTime updateAt;

```