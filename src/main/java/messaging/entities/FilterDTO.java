package com.myforum.messaging.entities;

public class FilterDTO {
  ///// VARIABLES /////

  private Integer fid;
  private Integer uid;
  private Integer managedUid;
  private Boolean blockAllow; // false == block, true == allow

  ///// USEFUL METHODS /////

  public Boolean isEmpty() {
    if ((this.fid == null) && (this.uid == null) && (this.managedUid == null) && (this.blockAllow == null)) {
      return true;
    } else {
      return false;
    }
  }

  ///// CONSTRUCTORS /////

  public FilterDTO() {

  }

  public FilterDTO(Integer uid, Integer managedUid, Boolean blockAllow) {
    this.uid = uid;
    this.managedUid = managedUid;
    this.blockAllow = blockAllow;
  }

  public FilterDTO(Integer fid, Integer uid, Integer managedUid, Boolean blockAllow) {
    this.fid = fid;
    this.uid = uid;
    this.managedUid = managedUid;
    this.blockAllow = blockAllow;
  }

  ///// GETTERS /////

  public Integer getFid() {
    return this.fid;
  }

  public Integer getUid() {
    return this.uid;
  }

  public Integer getManagedUid() {
    return this.managedUid;
  }

  public Boolean getBlockAllow() {
    return this.blockAllow;
  }

  ///// SETTERS /////

  public void setFid(Integer fid) {
    this.fid = fid;
  }

  public void setUid(Integer uid) {
    this.uid = uid;
  }

  public void setManagedUid(Integer managedUid) {
    this.managedUid = managedUid;
  }

  public void setBlockAllow(Boolean blockAllow) {
    this.blockAllow = blockAllow;
  }


}
