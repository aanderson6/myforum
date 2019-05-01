package com.myforum.login.repositories;

import com.myforum.login.entities.SecurityQuestionEntity;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import java.util.List;
import com.myforum.login.entities.UserEntity;

public class SecurityQuestionRepo {

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

  public SecurityQuestionEntity add(SecurityQuestionEntity securityQuestion) {
    openTransaction();
    em.persist(securityQuestion);
    closeTransaction();
    return securityQuestion;
  }

  public void update(SecurityQuestionEntity securityQuestion) {
    openTransaction();
    em.merge(securityQuestion);
    closeTransaction();
  }

  public void remove(SecurityQuestionEntity securityQuestion) {
    openTransaction();
    securityQuestion = em.merge(securityQuestion);
    em.remove(securityQuestion);
    closeTransaction();
  }

  public SecurityQuestionEntity getById(int id) {
    openTransaction();
    SecurityQuestionEntity securityQuestion = em.find(SecurityQuestionEntity.class, id);
    closeTransaction();
    return securityQuestion;
  }

  public List<SecurityQuestionEntity> getAll() {
    openTransaction();
    List<SecurityQuestionEntity> securityQuestionList = em.createQuery("SELECT s FROM SecurityQuestionEntity s", SecurityQuestionEntity.class).getResultList();
    closeTransaction();
    return securityQuestionList;
  }

  public List<SecurityQuestionEntity> getByUser(UserEntity user) {
    openTransaction();
    List<SecurityQuestionEntity> securityQuestionList = em.createQuery("SELECT s FROM SecurityQuestionEntity s WHERE s.user = :user", SecurityQuestionEntity.class).setParameter("user", user).getResultList();
    closeTransaction();
    return securityQuestionList;
  }

  public void deleteByUser(UserEntity user) {
    openTransaction();
    em.createQuery("DELETE FROM SecurityQuestionEntity s WHERE s.user = :user").setParameter("user", user).executeUpdate();
    closeTransaction();
  }

  // public List<SecurityQuestionEntity> getByProperty(Property property) {
  //   openTransaction();
  //   List<SecurityQuestionEntity> securityQuestionList = em.createQuery("SELECT s FROM SecurityQuestionEntity s WHERE s.property = :property").setParameter("property", property).getResultList();
  //   closeTransaction();
  //   return securityQuestionList;
  // }

}
