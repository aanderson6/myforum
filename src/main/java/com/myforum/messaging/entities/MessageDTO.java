package com.myforum.messaging.entities;

import java.time.Instant;

public class MessageDTO {
  ///// VARIABLES /////

  private Integer mid;
  private Integer cid;
  private Integer fromUid;
  private Instant sent;
  private String subject;
  private String content;
  private Integer chainNum;

  ///// USEFUL METHODS /////

  public Boolean isEmpty() {
    if ((this.mid == null) && (this.cid == null) && (this.fromUid == null) && (this.sent == null) && (this.subject == null) && (this.content == null) && (this.chainNum == null)) {
      return true;
    } else {
      return false;
    }
  }

  ///// CONSTRUCTORS /////

  public MessageDTO() {

  }

  public MessageDTO(Integer cid, Integer fromUid, Instant sent, String subject, String content, Integer chainNum) {
    this.cid = cid;
    this.fromUid = fromUid;
    this.sent = sent;
    this.subject = subject;
    this.content = content;
    this.chainNum = chainNum;
  }

  public MessageDTO(Integer mid, Integer cid, Integer fromUid, Instant sent, String subject, String content, Integer chainNum) {
    this.mid = mid;
    this.cid = cid;
    this.fromUid = fromUid;
    this.sent = sent;
    this.subject = subject;
    this.content = content;
    this.chainNum = chainNum;
  }

  ///// GETTERS /////

  public Integer getMid() {
    return this.mid;
  }

  public Integer getCid() {
    return this.cid;
  }

  public Integer getFromUid() {
    return this.fromUid;
  }

  public Instant getSent() {
    return this.sent;
  }

  public String getSubject() {
    return this.subject;
  }

  public String getContent() {
    return this.content;
  }

  public Integer getChainNum() {
    return this.chainNum;
  }

  ///// SETTERS /////

  public void setMid(Integer mid) {
    this.mid = mid;
  }

  public void setCid(Integer cid) {
    this.cid = cid;
  }

  public void setFromUid(Integer fromUid) {
    this.fromUid = fromUid;
  }

  public void setSent(Instant sent) {
    this.sent = sent;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setChainNum(Integer chainNum) {
    this.chainNum = chainNum;
  }

}
