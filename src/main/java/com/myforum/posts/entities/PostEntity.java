package com.myforum.posts.entities;

//imports
import javax.persistence.*;
import java.time.Instant;
import com.myforum.login.entities.UserEntity;
import com.myforum.subforum.entities.SubforumEntity;

@Entity
@Table(name = "Posts")
public class PostEntity {

    public PostEntity() {    }

    public PostEntity(Instant postedDate, UserEntity user, SubforumEntity subforum, int karma, String title, String content) {
      this.postedDate = postedDate;
      this.user = user;
      this.subforum = subforum;
      this.karma = karma;
      this.title = title;
      this.content = content;
    }

    @Id
    @Column(name = "post_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "posted_date", nullable = false)
    private Instant postedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "subforum_id", nullable = false)
    private SubforumEntity subforum;

    @Column(name = "karma", nullable = false)
    private int karma;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Instant getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Instant postedDate) {
        this.postedDate = postedDate;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public SubforumEntity getSubforum() {
        return subforum;
    }

    public void setSubforum(SubforumEntity subforum) {
        this.subforum = subforum;
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
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
