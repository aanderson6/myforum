package com.myforum.comments.dtos;

import java.time.Instant;

public class CommentDTO {

    public CommentDTO() {    }

    public CommentDTO(int id, Instant commentDate, String username, int postId, String subforumName, int karma, String content, Integer parentCommentId, int commentDepth, Boolean hasChildren) {
      this.id = id;
      this.commentDate = commentDate;
      this.username = username;
      this.postId = postId;
      this.subforumName = subforumName;
      this.karma = karma;
      this.content = content;
      this.parentCommentId = parentCommentId;
      this.commentDepth = commentDepth;
      this.hasChildren = hasChildren;
    }

    private int id;
    private Instant commentDate;
    private String username;
    private int postId;
    private String subforumName;
    private int karma;
    private String content;
    private Integer parentCommentId;
    private int commentDepth;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getSubforumName() {
        return subforumName;
    }

    public void setSubforumName(String subforumName) {
        this.subforumName = subforumName;
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

    public Integer getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
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
