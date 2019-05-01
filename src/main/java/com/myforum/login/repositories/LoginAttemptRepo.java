package com.myforum.login.repositories;

import com.myforum.login.entities.LoginAttemptEntity;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import java.util.List;
import java.time.Instant;
import com.myforum.login.entities.UserEntity;

public class LoginAttemptRepo {

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

  public LoginAttemptEntity add(LoginAttemptEntity loginAttempt) {
    openTransaction();
    em.persist(loginAttempt);
    closeTransaction();
    return loginAttempt;
  }

  public void update(LoginAttemptEntity loginAttempt) {
    openTransaction();
    em.merge(loginAttempt);
    closeTransaction();
  }

  public void remove(LoginAttemptEntity loginAttempt) {
    openTransaction();
    loginAttempt = em.merge(loginAttempt);
    em.remove(loginAttempt);
    closeTransaction();
  }

  public LoginAttemptEntity getById(int id) {
    openTransaction();
    LoginAttemptEntity loginAttempt = em.find(LoginAttemptEntity.class, id);
    closeTransaction();
    return loginAttempt;
  }

  public List<LoginAttemptEntity> getAll() {
    openTransaction();
    List<LoginAttemptEntity> loginAttempt = em.createQuery("SELECT l FROM LoginAttemptEntity l", LoginAttemptEntity.class).getResultList();
    closeTransaction();
    return loginAttempt;
  }

  public List<LoginAttemptEntity> getByUserAndTime(UserEntity user, Instant time) {
    openTransaction();
    List<LoginAttemptEntity> loginAttempts = em.createQuery("SELECT l FROM LoginAttemptEntity l WHERE l.user = :user AND l.loginTime = :time", LoginAttemptEntity.class).setParameter("user", user).setParameter("time", time).getResultList();
    closeTransaction();
    return loginAttempts;
  }

  public void deleteByUser(UserEntity user) {
    openTransaction();
    em.createQuery("DELETE FROM LoginAttemptEntity l WHERE l.user = :user").setParameter("user", user).executeUpdate();
    closeTransaction();
  }

  // public List<LoginAttemptEntity> getByProperty(Property property) {
  //   openTransaction();
  //   List<LoginAttemptEntity> loginAttemptList = em.createQuery("SELECT l FROM LoginAttemptEntity l WHERE l.property = :property").setParameter("property", property).getResultList();
  //   closeTransaction();
  //   return loginAttemptList;
  // }

}
