package com.myforum.messaging.dtos;

import java.time.Instant;

public class MessageDTO {

    public MessageDTO() {    }

    public MessageDTO(int id, String username, Instant sent, String subject, String content, int chainNum) {
      this.id = id;
      this.username = username;
      this.sent = sent;
      this.subject = subject;
      this.content = content;
      this.chainNum = chainNum;
    }

    private int id;
    private String username;
    private Instant sent;
    private String subject;
    private String content;
    private int chainNum;

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

    public Instant getSent() {
        return sent;
    }

    public void setSent(Instant sent) {
      if(sent == null) {
        throw new NullPointerException();
      }
        this.sent = sent;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
      if(subject == null) {
        throw new NullPointerException();
      }
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
      if(content == null) {
        throw new NullPointerException();
      }
        this.content = content;
    }

    public int getChainNum() {
        return chainNum;
    }

    public void setChainNum(int chainNum) {
        this.chainNum = chainNum;
    }

}
