package com.myforum.login.dtos;

import java.time.Instant;

public class UserDTO {

    public UserDTO() {    }

    public UserDTO(String username, String displayName, String email, Instant joinDate, int karma) {
      this.username = username;
      this.displayName = displayName;
      this.email = email;
      this.joinDate = joinDate;
      this.karma = karma;
    }

    private String username;
    private String displayName;
    private String email;
    private Instant joinDate;
    private int karma;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if(username == null) {
          throw new NullPointerException();
        }
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
      if(displayName == null) {
        throw new NullPointerException();
      }
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
      if(email == null) {
        throw new NullPointerException();
      }
        this.email = email;
    }

    public Instant getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Instant joinDate) {
      if(joinDate == null) {
        throw new NullPointerException();
      }
        this.joinDate = joinDate;
    }

    public int getKarma() {
        return karma;
    }

    public void setKarma(int karma) {
        this.karma = karma;
    }

}
