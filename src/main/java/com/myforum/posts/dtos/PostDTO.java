package com.myforum.posts.dtos;

import java.time.Instant;

public class PostDTO {

    public PostDTO() {    }

    public PostDTO(Instant postedDate, String username, String subforumName, int karma, String title, String content) {
      this.postedDate = postedDate;
      this.username = username;
      this.subforumName = subforumName;
      this.karma = karma;
      this.title = title;
      this.content = content;
    }

    private Instant postedDate;
    private String username;
    private String subforumName;
    private int karma;
    private String title;
    private String content;

    public Instant getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Instant postedDate) {
      if(postedDate == null) {
        throw new NullPointerException();
      }
        this.postedDate = postedDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSubforumName() {
        return subforumName;
    }

    public void setSubforumName(String subforumName) {
      if(subforumName == null) {
        throw new NullPointerException();
      }
        this.subforumName = subforumName;
    }

    public int getKarma() {
        return karma;
    }

    public void setKarma(int karma) {
        this.karma = karma;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
      if(title == null) {
        throw new NullPointerException();
      }
        this.title = title;
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

}
