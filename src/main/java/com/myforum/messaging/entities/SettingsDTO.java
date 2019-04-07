package com.myforum.messaging.entities;

public class SettingsDTO {
  ///// VARIABLES /////

  private Integer sid;
  private Integer uid;
  private Boolean collapseReadMessages;
  private Boolean allOrWhitelist;
  private Boolean unreadNotify;
  private Boolean markReadIfOpened;

  ///// USEFUL METHODS /////

  public Boolean isEmpty() {
    if ((sid == null) && (uid == null) && (collapseReadMessages == null) && (allOrWhitelist == null) && (unreadNotify == null) && (markReadIfOpened == null)) {
      return true;
    } else {
      return false;
    }
  }

  ///// CONSTRUCTORS /////

  public SettingsDTO() {

  }

  public SettingsDTO(Integer uid, Boolean collapseReadMessages, Boolean allOrWhitelist, Boolean unreadNotify, Boolean markReadIfOpened) {
    this.uid = uid;
    this.collapseReadMessages = collapseReadMessages;
    this.allOrWhitelist = allOrWhitelist;
    this.unreadNotify = unreadNotify;
    this.markReadIfOpened = markReadIfOpened;
  }

  public SettingsDTO(Integer sid, Integer uid, Boolean collapseReadMessages, Boolean allOrWhitelist, Boolean unreadNotify, Boolean markReadIfOpened) {
    this.sid = sid;
    this.uid = uid;
    this.collapseReadMessages = collapseReadMessages;
    this.allOrWhitelist = allOrWhitelist;
    this.unreadNotify = unreadNotify;
    this.markReadIfOpened = markReadIfOpened;
  }

  ///// GETTERS /////

  public Integer getSid() {
    return this.sid;
  }

  public Integer getUid() {
    return this.uid;
  }

  public Boolean getCollapseReadMessages() {
    return this.collapseReadMessages;
  }

  public Boolean getAllOrWhitelist() {
    return this.allOrWhitelist;
  }

  public Boolean getUnreadNotify() {
    return this.unreadNotify;
  }

  public Boolean getMarkReadIfOpened() {
    return this.markReadIfOpened;
  }

  ///// SETTERS /////

  public void setSid(Integer sid) {
    this.sid = sid;
  }

  public void setUid(Integer uid) {
    this.uid = uid;
  }

  public void setCollapseReadMessages(Boolean collapseReadMessages) {
    this.collapseReadMessages = collapseReadMessages;
  }

  public void setAllOrWhitelist(Boolean allOrWhitelist) {
    this.allOrWhitelist = allOrWhitelist;
  }

  public void setUnreadNotify(Boolean unreadNotify) {
    this.unreadNotify = unreadNotify;
  }

  public void setMarkReadIfOpened(Boolean markReadIfOpened) {
    this.markReadIfOpened = markReadIfOpened;
  }

}
