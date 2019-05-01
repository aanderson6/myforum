package com.myforum.messaging.repositories;

import com.myforum.messaging.entities.FilterEntity;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import java.util.List;
import com.myforum.login.entities.UserEntity;

public class FilterRepo {

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

  public FilterEntity add(FilterEntity filter) {
    openTransaction();
    em.persist(filter);
    closeTransaction();
    return filter;
  }

  public void update(FilterEntity filter) {
    openTransaction();
    em.merge(filter);
    closeTransaction();
  }

  public void remove(FilterEntity filter) {
    openTransaction();
    filter = em.merge(filter);
    em.remove(filter);
    closeTransaction();
  }

  public FilterEntity getById(int id) {
    openTransaction();
    FilterEntity filter = em.find(FilterEntity.class, id);
    closeTransaction();
    return filter;
  }

  public List<FilterEntity> getAll() {
    openTransaction();
    List<FilterEntity> filterList = em.createQuery("SELECT f FROM FilterEntity f", FilterEntity.class).getResultList();
    closeTransaction();
    return filterList;
  }

  public List<FilterEntity> getBlockedByUser(UserEntity user) {
    openTransaction();
    List<FilterEntity> filterList = em.createQuery("SELECT f FROM FilterEntity f WHERE f.blockAllow = false AND f.user = :user", FilterEntity.class).setParameter("user", user).getResultList();
    closeTransaction();
    return filterList;
  }

  public List<FilterEntity> getAllowedByUser(UserEntity user) {
    openTransaction();
    List<FilterEntity> filterList = em.createQuery("SELECT f FROM FilterEntity f WHERE f.blockAllow = true AND f.user = :user", FilterEntity.class).setParameter("user", user).getResultList();
    closeTransaction();
    return filterList;
  }

  public void removeByUserManagedUserStatus(UserEntity user, UserEntity managedUser, Boolean blockAllow) {
    openTransaction();
    em.createQuery("DELETE FROM FilterEntity f WHERE f.user = :user AND f.managedUser = :managedUser AND f.blockAllow = :blockAllow").setParameter("user", user).setParameter("managedUser", managedUser).setParameter("blockAllow", blockAllow).executeUpdate();
    closeTransaction();
  }

  public List<FilterEntity> getByUser(UserEntity user) {
    openTransaction();
    List<FilterEntity> filterList = em.createQuery("SELECT f FROM FilterEntity f WHERE f.user = :user", FilterEntity.class).setParameter("user", user).getResultList();
    closeTransaction();
    return filterList;
  }

  public void removeByUser(UserEntity user) {
    openTransaction();
    em.createQuery("DELETE FROM FilterEntity f WHERE f.user = :user").setParameter("user", user).executeUpdate();
    closeTransaction();
  }

  // public List<FilterEntity> getByProperty(Property property) {
  //   openTransaction();
  //   List<FilterEntity> filterList = em.createQuery("SELECT f FROM FilterEntity f WHERE f.property = :property").setParameter("property", property).getResultList();
  //   closeTransaction();
  //   return filterList;
  // }

}
