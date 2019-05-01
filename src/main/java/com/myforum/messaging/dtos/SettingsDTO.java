package com.myforum.messaging.dtos;

public class SettingsDTO {

    public SettingsDTO() {    }

    public SettingsDTO(Boolean collapseReadMessages, Boolean allOrWhitelist, Boolean notifyMessages, Boolean openMarkRead) {
      this.collapseReadMessages = collapseReadMessages;
      this.allOrWhitelist = allOrWhitelist;
      this.notifyMessages = notifyMessages;
      this.openMarkRead = openMarkRead;
    }

    private Boolean collapseReadMessages;
    private Boolean allOrWhitelist;
    private Boolean notifyMessages;
    private Boolean openMarkRead;

    public Boolean getCollapseReadMessages() {
        return collapseReadMessages;
    }

    public void setCollapseReadMessages(Boolean collapseReadMessages) {
      if(collapseReadMessages == null) {
        throw new NullPointerException();
      }
        this.collapseReadMessages = collapseReadMessages;
    }

    public Boolean getAllOrWhitelist() {
        return allOrWhitelist;
    }

    public void setAllOrWhitelist(Boolean allOrWhitelist) {
      if(allOrWhitelist == null) {
        throw new NullPointerException();
      }
        this.allOrWhitelist = allOrWhitelist;
    }

    public Boolean getNotifyMessages() {
        return notifyMessages;
    }

    public void setNotifyMessages(Boolean notifyMessages) {
      if(notifyMessages == null) {
        throw new NullPointerException();
      }
        this.notifyMessages = notifyMessages;
    }

    public Boolean getOpenMarkRead() {
        return openMarkRead;
    }

    public void setOpenMarkRead(Boolean openMarkRead) {
      if(openMarkRead == null) {
        throw new NullPointerException();
      }
        this.openMarkRead = openMarkRead;
    }

}
