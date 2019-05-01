package com.myforum.posts.entities;

//imports
import javax.persistence.*;
import java.time.Instant;
import com.myforum.login.entities.UserEntity;

@Entity
@Table(name = "Post_Karma")
public class PostKarmaEntity {

    public PostKarmaEntity() {    }

    public PostKarmaEntity(Instant votedDate, UserEntity user, PostEntity post, Boolean upOrDown /* up = true, down = false */) {
      this.votedDate = votedDate;
      this.user = user;
      this.post = post;
      this.upOrDown = upOrDown;
    }

    @Id
    @Column(name = "post_karma_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "voted_date", nullable = false)
    private Instant votedDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @Column(name = "up_or_down", nullable = false)
    private Boolean upOrDown;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Instant getVotedDate() {
        return votedDate;
    }

    public void setVotedDate(Instant votedDate) {
        this.votedDate = votedDate;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }

    public Boolean getUpOrDown() {
        return upOrDown;
    }

    public void setUpOrDown(Boolean upOrDown) {
        this.upOrDown = upOrDown;
    }

}
