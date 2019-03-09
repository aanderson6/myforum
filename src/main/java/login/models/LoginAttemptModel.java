package com.myforum.login.models;

import java.time.Instant;

public class LoginAttemptModel {

  ///// VARIABLES /////

  private int lid;
  private int uid;
  private Instant attemptDate;
  private Boolean succeeded;

  ///// USEFUL METHODS /////



  ///// CONSTRUCTORS /////

  public LoginAttemptModel(int lid, int uid, Instant attemptDate, Boolean succeeded) {
    this.lid = lid;
    this.uid = uid;
    this.attemptDate = attemptDate;
    this.succeeded = succeeded;
  }

  public LoginAttemptModel(int uid, Boolean succeeded) {
    this.uid = uid;
    this.succeeded = succeeded;
  }

  public LoginAttemptModel() {

  }

  ///// GETTERS /////

  public int getLid() {
    return this.lid;
  }

  public int getUid() {
    return this.uid;
  }

  public Instant getAttemptDate() {
    return this.attemptDate;
  }

  public Boolean getSucceeded() {
    return this.succeeded;
  }

  ///// SETTERS /////

  public void setLid(int lid) {
    this.lid = lid;
  }

  public void setUid(int uid) {
    this.uid = uid;
  }

  public void setAttemptDate(Instant attemptDate) {
    this.attemptDate = attemptDate;
  }

  public void setSucceeded(Boolean succeeded) {
    this.succeeded = succeeded;
  }

}
