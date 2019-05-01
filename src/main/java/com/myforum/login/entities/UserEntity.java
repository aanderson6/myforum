package com.myforum.login.entities;

//imports
import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "Users")
public class UserEntity {

    public UserEntity() {    }

    public UserEntity(String username, String shPassword, String displayName, String email, Instant joinDate, int karma, Instant isLocked) {
      this.username = username;
      this.shPassword = shPassword;
      this.displayName = displayName;
      this.email = email;
      this.joinDate = joinDate;
      this.karma = karma;
      this.isLocked = isLocked;
    }

    @Id
    @Column(name = "uid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "shpw", nullable = false)
    private String shPassword;

    @Column(name = "displayname", nullable = false)
    private String displayName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "joindate", nullable = false)
    private Instant joinDate;

    @Column(name = "karma", nullable = false)
    private int karma;

    @Column(name = "islocked", nullable = false)
    private Instant isLocked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getShPassword() {
        return shPassword;
    }

    public void setShPassword(String shPassword) {
        this.shPassword = shPassword;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Instant joinDate) {
        this.joinDate = joinDate;
    }

    public int getKarma() {
        return karma;
    }

    public void setKarma(int karma) {
        this.karma = karma;
    }

    public Instant getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Instant isLocked) {
        this.isLocked = isLocked;
    }

}
