package com.myforum.messaging.entities;

//imports
import javax.persistence.*;
import com.myforum.login.entities.UserEntity;

@Entity
@Table(name = "Membership")
public class MembershipEntity {

    public MembershipEntity() {    }

    public MembershipEntity(ConversationEntity conversation, UserEntity user, int unreadChainNum, Boolean visible) {
      this.conversation = conversation;
      this.user = user;
      this.unreadChainNum = unreadChainNum;
      this.visible = visible;
    }

    @Id
    @Column(name = "membership_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "conversation_id", nullable = false)
    private ConversationEntity conversation;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    private UserEntity user;

    @Column(name = "unread_chain_num", nullable = false)
    private int unreadChainNum;

    @Column(name = "visible", nullable = false)
    private Boolean visible;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ConversationEntity getConversation() {
        return conversation;
    }

    public void setConversation(ConversationEntity conversation) {
        this.conversation = conversation;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUserId(UserEntity user) {
        this.user = user;
    }

    public int getUnreadChainNum() {
        return unreadChainNum;
    }

    public void setUnreadChainNum(int unreadChainNum) {
        this.unreadChainNum = unreadChainNum;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

}
