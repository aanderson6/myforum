package com.myforum.messaging.entities;

public class MembershipDTO {
  ///// VARIABLES /////

  private Integer mid;
  private Integer cid;
  private Integer uid;
  private Integer unreadChainNum;
  private Boolean visible;

  ///// USEFUL METHODS /////

  public Boolean isEmpty() {
    if ((this.mid == null) && (this.cid == null) && (this.uid == null) && (this.unreadChainNum == null) && (this.visible == null)) {
      return true;
    } else {
      return false;
    }
  }

  ///// CONSTRUCTORS /////

  public MembershipDTO() {

  }

  public MembershipDTO(Integer cid, Integer uid, Integer unreadChainNum, Boolean visible) {
    this.cid = cid;
    this.uid = uid;
    this.unreadChainNum = unreadChainNum;
    this.visible = visible;
  }

  public MembershipDTO(Integer mid, Integer cid, Integer uid, Integer unreadChainNum, Boolean visible) {
    this.mid = mid;
    this.cid = cid;
    this.uid = uid;
    this.unreadChainNum = unreadChainNum;
    this.visible = visible;
  }

  ///// GETTERS /////

  public Integer getMid() {
    return this.mid;
  }

  public Integer getCid() {
    return this.cid;
  }

  public Integer getUid() {
    return this.uid;
  }

  public Integer getUnreadChainNum() {
    return this.unreadChainNum;
  }

  public Boolean getVisible() {
    return this.visible;
  }

  ///// SETTERS /////

  public void setMid(Integer mid) {
    this.mid = mid;
  }

  public void setCid(Integer cid) {
    this.cid = cid;
  }

  public void setUid(Integer uid) {
    this.uid = uid;
  }

  public void setUnreadChainNum(Integer unreadChainNum) {
    this.unreadChainNum = unreadChainNum;
  }

  public void setVisible(Boolean visible) {
    this.visible = visible;
  }


}
