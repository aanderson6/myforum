package com.myforum.login.models;

public class SecurityQuestionModel {

  ///// VARIABLES /////

  private Integer qid;
  private Integer uid;
  private String question;
  private String shAnswer;

  ///// USEFUL METHODS /////

  public Boolean isEmpty() {
    if ((qid == null) && (uid == null) && (question == null) && (shAnswer == null)) {
      return true;
    } else {
      return false;
    }
  }

  ///// CONSTRUCTORS /////

  public SecurityQuestionModel() {

  }

  public SecurityQuestionModel(Integer qid, Integer uid, String question, String shAnswer) {
    this.qid = qid;
    this.uid = uid;
    this.question = question;
    this.shAnswer = shAnswer;
  }

  public SecurityQuestionModel(String question, String shAnswer) {
    this.question = question;
    this.shAnswer = shAnswer;
  }

  ///// GETTERS /////

  public Integer getQid() {
    return this.qid;
  }

  public Integer getUid() {
    return this.uid;
  }

  public String getQuestion() {
    return this.question;
  }

  public String getSHAnswer() {
    return this.shAnswer;
  }

  ///// SETTERS /////

  public void setQid(Integer qid) {
    this.qid = qid;
  }

  public void setUid(Integer uid) {
    this.uid = uid;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public void setSHAnswer(String shAnswer) {
     this.shAnswer = shAnswer;
  }

}
