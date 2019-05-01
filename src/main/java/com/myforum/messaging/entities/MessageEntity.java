package com.myforum.messaging.entities;

//imports
import javax.persistence.*;
import com.myforum.login.entities.UserEntity;
import java.time.Instant;

@Entity
@Table(name = "Messages")
public class MessageEntity {

    public MessageEntity() {    }

    public MessageEntity(ConversationEntity conversation, UserEntity fromUser, Instant sent, String subject, String content, int chainNum) {
      this.conversation = conversation;
      this.fromUser = fromUser;
      this.sent = sent;
      this.subject = subject;
      this.content = content;
      this.chainNum = chainNum;
    }

    @Id
    @Column(name = "message_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "conversation_id", nullable = false)
    private ConversationEntity conversation;

    @ManyToOne
    @JoinColumn(name = "from_uid")
    private UserEntity fromUser;

    @Column(name = "sent", nullable = false)
    private Instant sent;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "chain_num", nullable = false)
    private int chainNum;

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

    public UserEntity getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserEntity fromUser) {
        this.fromUser = fromUser;
    }

    public Instant getSent() {
        return sent;
    }

    public void setSent(Instant sent) {
        this.sent = sent;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getChainNum() {
        return chainNum;
    }

    public void setChainNum(int chainNum) {
        this.chainNum = chainNum;
    }

}
