package com.myforum.subforum.entities;

// imports
import javax.persistence.*;
import com.myforum.login.entities.UserEntity;

@Entity
@Table(name = "subscriptions")
public class SubscriptionEntity {

    public SubscriptionEntity() {   }

    public SubscriptionEntity(SubforumEntity subforum, UserEntity user) {
      this.subforum = subforum;
      this.user = user;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "subforum_id", nullable = false)
    private SubforumEntity subforum;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SubforumEntity getSubforum() {
        return subforum;
    }

    public void setSubforum(SubforumEntity subforum) {
        this.subforum = subforum;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
