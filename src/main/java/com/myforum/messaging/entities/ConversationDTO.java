package com.myforum.messaging.entities;

import java.time.Instant;

public class ConversationDTO {
  ///// VARIABLES /////

  private Integer cid;
  private Integer uid;
  private Instant started;
  private Instant recent;


  ///// USEFUL METHODS /////

  public Boolean isEmpty() {
    if ((this.cid == null) && (this.uid == null) && (this.started == null) && (this.recent == null)) {
      return true;
    } else {
      return false;
    }
  }

  ///// CONSTRUCTORS /////

  public ConversationDTO() {

  }

  public ConversationDTO(Integer uid, Instant started, Instant recent) {
    this.uid = uid;
    this.started = started;
    this.recent = recent;
  }

  public ConversationDTO(Integer cid, Integer uid, Instant started, Instant recent) {
    this.cid = cid;
    this.uid = uid;
    this.started = started;
    this.recent = recent;
  }

  ///// GETTERS /////

  public Integer getCid() {
    return this.cid;
  }

  public Integer getUid() {
    return this.uid;
  }

  public Instant getStarted() {
    return this.started;
  }

  public Instant getRecent() {
    return this.recent;
  }

  ///// SETTERS /////

  public void setCid(Integer cid) {
    this.cid = cid;
  }

  public void setUid(Integer uid) {
    this.uid = uid;
  }

  public void setStarted(Instant started) {
    this.started = started;
  }

  public void setRecent(Instant recent) {
    this.recent = recent;
  }

}
