package com.myforum.login.entities;

//imports
import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "LoginAttempts")
public class LoginAttemptEntity {

    public LoginAttemptEntity() {    }

    public LoginAttemptEntity(UserEntity user, Instant loginTime, Boolean succeeded) {
      this.user = user;
      this.loginTime = loginTime;
      this.succeeded = succeeded;
    }

    @Id
    @Column(name = "logid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    private UserEntity user;

    @Column(name = "logintime", nullable = false)
    private Instant loginTime;

    @Column(name = "succeeded", nullable = false)
    private Boolean succeeded;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Instant getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Instant loginTime) {
        this.loginTime = loginTime;
    }

    public Boolean getSucceeded() {
        return succeeded;
    }

    public void setSucceeded(Boolean succeeded) {
        this.succeeded = succeeded;
    }

}
