package com.myforum.subforum.dtos;

// imports...
import java.time.Instant;

public class SubforumDTO {

    public SubforumDTO() {    }

    public SubforumDTO(String name, String description, String flair, int subscriberCount, Instant createdDate, String creatorUsername) {
      this.name = name;
      this.description = description;
      this.flair = flair;
      this.subscriberCount = subscriberCount;
      this.createdDate = createdDate;
      this.creatorUsername = creatorUsername;
    }

    private String name;
    private String description;
    private String flair;
    private int subscriberCount;
    private Instant createdDate;
    private String creatorUsername;

    public String getName() {
        return name;
    }

    public void setName(String name) {
      if(name == null) {
        throw new NullPointerException();
      }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
      if(description == null) {
        throw new NullPointerException();
      }
        this.description = description;
    }

    public String getFlair() {
        return flair;
    }

    public void setFlair(String flair) {
      if(flair == null) {
        throw new NullPointerException();
      }
        this.flair = flair;
    }

    public int getSubscriberCount() {
        return subscriberCount;
    }

    public void setSubscriberCount(int subscriberCount) {
        this.subscriberCount = subscriberCount;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
      if(createdDate == null) {
        throw new NullPointerException();
      }
        this.createdDate = createdDate;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

}
