package com.myforum.comments.entities;

//imports
import javax.persistence.*;
import java.time.Instant;
import com.myforum.login.entities.UserEntity;

@Entity
@Table(name = "Comment_Karma")
public class CommentKarmaEntity {

    public CommentKarmaEntity() {    }

    public CommentKarmaEntity(Instant votedDate, UserEntity user, CommentEntity comment, Boolean upOrDown /* up = true, down = false */) {
      this.votedDate = votedDate;
      this.user = user;
      this.comment = comment;
      this.upOrDown = upOrDown;
    }

    @Id
    @Column(name = "comment_karma_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "voted_date", nullable = false)
    private Instant votedDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private CommentEntity comment;

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

    public CommentEntity getComment() {
        return comment;
    }

    public void setComment(CommentEntity comment) {
        this.comment = comment;
    }

    public Boolean getUpOrDown() {
        return upOrDown;
    }

    public void setUpOrDown(Boolean upOrDown) {
        this.upOrDown = upOrDown;
    }

}
