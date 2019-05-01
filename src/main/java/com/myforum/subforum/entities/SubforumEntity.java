package com.myforum.subforum.entities;

// imports...
import java.time.Instant;
import javax.persistence.*;
import com.myforum.login.entities.UserEntity;

@Entity
@Table(name = "subforums")
public class SubforumEntity {

    public SubforumEntity() {    }

    public SubforumEntity(String name, String description, String flair, int subscriberCount, Instant createdDate, UserEntity createdBy) {
      this.name = name;
      this.description = description;
      this.flair = flair;
      this.subscriberCount = subscriberCount;
      this.createdDate = createdDate;
      this.createdBy = createdBy;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "flair", nullable = false)
    private String flair;

    @Column(name = "subscriber_count", nullable = false)
    private int subscriberCount;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserEntity createdBy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFlair() {
        return flair;
    }

    public void setFlair(String flair) {
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
        this.createdDate = createdDate;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

}
