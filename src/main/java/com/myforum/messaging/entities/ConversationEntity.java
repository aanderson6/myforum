package com.myforum.messaging.entities;

//imports
import javax.persistence.*;
import java.time.Instant;
import com.myforum.login.entities.UserEntity;

@Entity
@Table(name = "Conversations")
public class ConversationEntity {

    public ConversationEntity() {    }

    public ConversationEntity(UserEntity fromUser, Instant started, Instant mostRecent, int chainLength) {
      this.fromUser = fromUser;
      this.started = started;
      this.mostRecent = mostRecent;
      this.chainLength = chainLength;
    }

    @Id
    @Column(name = "conversation_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "from_uid")
    private UserEntity fromUser;

    @Column(name = "started", nullable = false)
    private Instant started;

    @Column(name = "most_recent", nullable = false)
    private Instant mostRecent;

    @Column(name = "chain_length", nullable = false)
    private int chainLength;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserEntity fromUser) {
        this.fromUser = fromUser;
    }

    public Instant getStarted() {
        return started;
    }

    public void setStarted(Instant started) {
        this.started = started;
    }

    public Instant getMostRecent() {
        return mostRecent;
    }

    public void setMostRecent(Instant mostRecent) {
        this.mostRecent = mostRecent;
    }

    public int getChainLength() {
        return chainLength;
    }

    public void setChainLength(int chainLength) {
        this.chainLength = chainLength;
    }

}
