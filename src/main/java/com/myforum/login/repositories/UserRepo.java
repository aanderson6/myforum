package com.myforum.login.repositories;

import com.myforum.login.entities.UserEntity;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import java.util.List;

public class UserRepo {

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

  public UserEntity add(UserEntity user) {
    openTransaction();
    em.persist(user);
    closeTransaction();
    return user;
  }

  public void update(UserEntity user) {
    openTransaction();
    em.merge(user);
    closeTransaction();
  }

  public void remove(UserEntity user) {
    openTransaction();
    user = em.merge(user);
    em.remove(user);
    closeTransaction();
  }

  public UserEntity getById(int id) {
    openTransaction();
    UserEntity user = em.find(UserEntity.class, id);
    closeTransaction();
    return user;
  }

  public List<UserEntity> getAll() {
    openTransaction();
    List<UserEntity> userList = em.createQuery("SELECT u FROM UserEntity u", UserEntity.class).getResultList();
    closeTransaction();
    return userList;
  }

  public UserEntity getByUsername(String username) {
    openTransaction();
    List<UserEntity> userList = em.createQuery("SELECT u FROM UserEntity u WHERE u.username = :username", UserEntity.class).setParameter("username", username).getResultList();
    closeTransaction();
    if(userList.size() == 0) {
      return null;
    } else {
      return userList.get(0);
    }
  }

  // public List<UserEntity> getByProperty(Property property) {
  //   openTransaction();
  //   List<UserEntity> userList = em.createQuery("SELECT u FROM UserEntity u WHERE u.property = :property").setParameter("property", property).getResultList();
  //   closeTransaction();
  //   return userList;
  // }

}
