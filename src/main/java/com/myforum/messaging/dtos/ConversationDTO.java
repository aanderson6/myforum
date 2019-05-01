package com.myforum.messaging.dtos;

import java.time.Instant;
import java.util.List;

public class ConversationDTO {

    public ConversationDTO() {    }

    public ConversationDTO(int id, String username, Instant started, Instant mostRecent, int chainLength, List<MessageDTO> messages) {
      this.id = id;
      this.username = username;
      this.started = started;
      this.mostRecent = mostRecent;
      this.chainLength = chainLength;
      this.messages = messages;
    }

    private int id;
    private String username;
    private Instant started;
    private Instant mostRecent;
    private int chainLength;
    private List<MessageDTO> messages;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getStarted() {
        return started;
    }

    public void setStarted(Instant started) {
      if(started == null) {
        throw new NullPointerException();
      }
        this.started = started;
    }

    public Instant getMostRecent() {
        return mostRecent;
    }

    public void setMostRecent(Instant mostRecent) {
      if(mostRecent == null) {
        throw new NullPointerException();
      }
        this.mostRecent = mostRecent;
    }

    public int getChainLength() {
        return chainLength;
    }

    public void setChainLength(int chainLength) {
        this.chainLength = chainLength;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
      if(messages == null) {
        throw new NullPointerException();
      }
      for(MessageDTO message : messages) {
        if(message == null) {
          throw new NullPointerException();
        }
      }
        this.messages = messages;
    }
}
