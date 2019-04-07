package com.myforum.login.models;

import java.time.Instant;

public class UserModel {

  ///// VARIABLES /////

  private Integer uid;
  private String username;
  private String displayName;
  private String shPassword;
  private String email;
  private Instant joinDate;
  private Integer karma;
  private Instant isLocked;

  ///// USEFUL METHODS /////

  public Boolean isEmpty() {
    if ((uid == null) && (username == null) && (displayName == null) && (shPassword == null) && (email == null) && (joinDate == null) && (karma == null) && (isLocked == null)) {
      return true;
    } else {
      return false;
    }
  }

  ///// CONSTRUCTORS /////

  public UserModel() {

  }

  public UserModel(String username, String displayName, String shPassword, String email) {
    this.username = username;
    this.displayName = displayName;
    this.shPassword = shPassword;
    this.email = email;
  }

  public UserModel(Integer uid, String username, String displayName, String shPassword, String email, Instant joinDate, Integer karma, Instant isLocked) {
    this.uid = uid;
    this.username = username;
    this.displayName = displayName;
    this.shPassword = shPassword;
    this.email = email;
    this.joinDate = joinDate;
    this.karma = karma;
    this.isLocked = isLocked;
  }

  ///// GETTERS /////

  public Integer getUid() {
    return this.uid;
  }

  public String getUsername() {
    return this.username;
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public String getSHPassword() {
    return this.shPassword;
  }

  public String getEmail() {
    return this.email;
  }

  public Instant getJoinDate() {
    return this.joinDate;
  }

  public Integer getKarma() {
    return this.karma;
  }

  public Instant getIsLocked() {
    return this.isLocked;
  }

  ///// SETTERS /////

  public void setUid(Integer uid) {
    this.uid = uid;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public void setSHPassword(String shPassword) {
    this.shPassword = shPassword;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setJoinDate(Instant joinDate) {
    this.joinDate = joinDate;
  }

  public void setKarma(Integer karma) {
    this.karma = karma;
  }

  public void setIsLocked(Instant isLocked) {
    this.isLocked = isLocked;
  }

}
