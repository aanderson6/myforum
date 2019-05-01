package com.myforum.comments.repositories;

import com.myforum.comments.entities.CommentKarmaEntity;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import java.util.List;

import com.myforum.login.entities.UserEntity;
import com.myforum.comments.entities.CommentEntity;


public class CommentKarmaRepo {

  private EntityManagerFactory emfactory;
  private EntityManager em;

  private void openTransaction() {
    emfactory = Persistence.createEntityManagerFactory("MyForum");
    em = emfactory.createEntityManager();
    em.getTransaction().begin();
  }

  private void closeTransaction() {
    em.getTransaction().commit();
    em.close();
    emfactory.close();
  }

  public CommentKarmaEntity add(CommentKarmaEntity commentKarma) {
    openTransaction();
    em.persist(commentKarma);
    closeTransaction();
    return commentKarma;
  }

  public void update(CommentKarmaEntity commentKarma) {
    openTransaction();
    em.merge(commentKarma);
    closeTransaction();
  }

  public void remove(CommentKarmaEntity commentKarma) {
    openTransaction();
    commentKarma = em.merge(commentKarma);
    em.remove(commentKarma);
    closeTransaction();
  }

  public CommentKarmaEntity getById(int id) {
    openTransaction();
    CommentKarmaEntity commentKarma = em.find(CommentKarmaEntity.class, id);
    closeTransaction();
    return commentKarma;
  }

  public List<CommentKarmaEntity> getAll() {
    openTransaction();
    List<CommentKarmaEntity> commentKarmaList = em.createQuery("SELECT c FROM CommentKarmaEntity c", CommentKarmaEntity.class).getResultList();
    closeTransaction();
    return commentKarmaList;
  }

  public Boolean hasVoted(UserEntity user, CommentEntity comment) {
    openTransaction();
    List<CommentKarmaEntity> commentKarmaList = em.createQuery("SELECT c FROM CommentKarmaEntity c WHERE c.user = :user AND c.comment = :comment", CommentKarmaEntity.class).setParameter("user", user).setParameter("comment", comment).getResultList();
    closeTransaction();
    Boolean hasVoted = commentKarmaList.size() > 0;
    return hasVoted;
  }

  public CommentKarmaEntity getByUserAndComment(UserEntity user, CommentEntity comment) {
    openTransaction();
    List<CommentKarmaEntity> commentKarmaList = em.createQuery("SELECT c FROM CommentKarmaEntity c WHERE c.user = :user AND c.comment = :comment", CommentKarmaEntity.class).setParameter("user", user).setParameter("comment", comment).getResultList();
    closeTransaction();
    if(commentKarmaList.size() == 0) {
      return null;
    } else {
      return commentKarmaList.get(0);
    }
  }

  public List<CommentKarmaEntity> getByComment(CommentEntity comment) {
    openTransaction();
    List<CommentKarmaEntity> commentKarmaList = em.createQuery("SELECT c FROM CommentKarmaEntity c WHERE c.comment = :comment", CommentKarmaEntity.class).setParameter("comment", comment).getResultList();
    closeTransaction();
    return commentKarmaList;
  }

  public List<CommentKarmaEntity> getByUser(UserEntity user) {
    openTransaction();
    List<CommentKarmaEntity> commentKarmaList = em.createQuery("SELECT c FROM CommentKarmaEntity c WHERE c.user = :user", CommentKarmaEntity.class).setParameter("user", user).getResultList();
    closeTransaction();
    return commentKarmaList;
  }

  // public List<CommentKarmaEntity> getByProperty(Property property) {
  //   openTransaction();
  //   List<CommentKarmaEntity> commentKarmaList = em..createQuery("SELECT c FROM CommentKarmaEntity c WHERE c.property = :property").setParameter("property", property).getResultList();
  //   closeTransaction();
  //   return commentKarmaList;
  // }

}
