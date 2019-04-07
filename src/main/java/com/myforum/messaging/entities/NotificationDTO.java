package com.myforum.messaging.entities;

public class NotificationDTO {
  ///// VARIABLES /////

  private Integer nid;
  private Integer uid;
  private Integer idNum;
  private String idNumType;

  ///// USEFUL METHODS /////

  public Boolean isEmpty() {
    if ((nid == null) && (uid == null) && (idNum == null) && (idNumType == null)) {
      return true;
    } else {
      return false;
    }
  }

  ///// CONSTRUCTORS /////

  public NotificationDTO() {

  }

  public NotificationDTO(Integer uid, Integer idNum, String idNumType) {
    this.uid = uid;
    this.idNum = idNum;
    this.idNumType = idNumType;
  }

  public NotificationDTO(Integer nid, Integer uid, Integer idNum, String idNumType) {
    this.nid = nid;
    this.uid = uid;
    this.idNum = idNum;
    this.idNumType = idNumType;
  }

  ///// GETTERS /////

  public Integer getNid() {
    return this.nid;
  }

  public Integer getUid() {
    return this.uid;
  }

  public Integer getIdNum() {
    return this.idNum;
  }

  public String getIdNumType() {
    return this.idNumType;
  }

  ///// SETTERS /////

  public void setNid(Integer nid) {
    this.nid = nid;
  }

  public void setUid(Integer uid) {
    this.uid = uid;
  }

  public void setIdNum(Integer idNum) {
    this.idNum = idNum;
  }

  public void setIdNumType(String idNumType) {
    this.idNumType = idNumType;
  }

}
