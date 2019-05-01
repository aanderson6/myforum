package com.myforum.login.entities;

//imports
import javax.persistence.*;

@Entity
@Table(name = "SecurityQuestions")
public class SecurityQuestionEntity {

    public SecurityQuestionEntity() {    }

    public SecurityQuestionEntity(UserEntity user, String question, String shAnswer) {
      this.user = user;
      this.question = question;
      this.shAnswer = shAnswer;
    }

    @Id
    @Column(name = "qid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    private UserEntity user;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "shanswer", nullable = false)
    private String shAnswer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getShAnswer() {
        return shAnswer;
    }

    public void setShAnswer(String shAnswer) {
        this.shAnswer = shAnswer;
    }

}
