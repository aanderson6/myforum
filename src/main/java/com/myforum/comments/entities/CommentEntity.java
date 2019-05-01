package com.myforum.comments.entities;

//imports
import javax.persistence.*;
import java.time.Instant;
import com.myforum.login.entities.UserEntity;
import com.myforum.posts.entities.PostEntity;
import com.myforum.subforum.entities.SubforumEntity;

@Entity
@Table(name = "Comments")
public class CommentEntity {

    public CommentEntity() {    }

    public CommentEntity(Instant commentDate, UserEntity user, PostEntity post, SubforumEntity subforum, int karma, String content, CommentEntity parentComment, int commentDepth, Boolean hasChildren) {
      this.commentDate = commentDate;
      this.user = user;
      this.post = post;
      this.subforum = subforum;
      this.karma = karma;
      this.content = content;
      this.parentComment = parentComment;
      this.commentDepth = commentDepth;
      this.hasChildren = hasChildren;
    }

    @Id
    @Column(name = "comment_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "comment_date", nullable = false)
    private Instant commentDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @ManyToOne
    @JoinColumn(name = "subforum_id", nullable = false)
    private SubforumEntity subforum;

    @Column(name = "karma", nullable = false)
    private int karma;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private CommentEntity parentComment;

    @Column(name = "comment_depth", nullable = false)
    private int commentDepth;

    @Column(name = "has_children", nullable = false)
    private Boolean hasChildren;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Instant getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Instant commentDate) {
        this.commentDate = commentDate;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CommentEntity getParentComment() {
        return parentComment;
    }

    public void setParentComment(CommentEntity parentComment) {
        this.parentComment = parentComment;
    }

    public int getCommentDepth() {
        return commentDepth;
    }

    public void setCommentDepth(int commentDepth) {
        this.commentDepth = commentDepth;
    }

    public Boolean getHasChildren() {
      return hasChildren;
    }

    public void setHasChildren(Boolean hasChildren) {
      this.hasChildren = hasChildren;
    }

}
