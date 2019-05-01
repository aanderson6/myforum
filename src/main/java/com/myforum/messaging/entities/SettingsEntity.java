package com.myforum.messaging.entities;

//imports
import javax.persistence.*;
import com.myforum.login.entities.UserEntity;

@Entity
@Table(name = "Settings")
public class SettingsEntity {

    public SettingsEntity() {    }

    public SettingsEntity(UserEntity user, Boolean collapseReadMessages, Boolean allOrWhitelist, Boolean notifyMessages, Boolean openMarkRead) {
      this.user = user;
      this.collapseReadMessages = collapseReadMessages;
      this.allOrWhitelist = allOrWhitelist;
      this.notifyMessages = notifyMessages;
      this.openMarkRead = openMarkRead;
    }

    @Id
    @Column(name = "settings_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    private UserEntity user;

    @Column(name = "collapse_read_messages", nullable = false)
    private Boolean collapseReadMessages;

    // false = get all but blocked, true = get whitelist only
    @Column(name = "all_or_whitelist", nullable = false)
    private Boolean allOrWhitelist;

    @Column(name = "notify_messages", nullable = false)
    private Boolean notifyMessages;

    @Column(name = "open_mark_read", nullable = false)
    private Boolean openMarkRead;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Boolean getCollapseReadMessages() {
        return collapseReadMessages;
    }

    public void setCollapseReadMessages(Boolean collapseReadMessages) {
        this.collapseReadMessages = collapseReadMessages;
    }

    public Boolean getAllOrWhitelist() {
        return allOrWhitelist;
    }

    public void setAllOrWhitelist(Boolean allOrWhitelist) {
        this.allOrWhitelist = allOrWhitelist;
    }

    public Boolean getNotifyMessages() {
        return notifyMessages;
    }

    public void setNotifyMessages(Boolean notifyMessages) {
        this.notifyMessages = notifyMessages;
    }

    public Boolean getOpenMarkRead() {
        return openMarkRead;
    }

    public void setOpenMarkRead(Boolean openMarkRead) {
        this.openMarkRead = openMarkRead;
    }

}
