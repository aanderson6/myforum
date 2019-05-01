package com.myforum.subforum.repositories;

import com.myforum.login.entities.UserEntity;
import com.myforum.subforum.entities.SubforumEntity;
import com.myforum.subforum.entities.SubscriptionEntity;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import java.util.List;

public class SubscriptionRepo {

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

  public SubscriptionEntity add(SubscriptionEntity subscription) {
    openTransaction();
    em.persist(subscription);
    closeTransaction();
    return subscription;
  }

  public void update(SubscriptionEntity subscription) {
    openTransaction();
    em.merge(subscription);
    closeTransaction();
  }

  public void remove(SubscriptionEntity subscription) {
    openTransaction();
    subscription = em.merge(subscription);
    em.remove(subscription);
    closeTransaction();
  }

  public SubscriptionEntity getById(int id) {
    openTransaction();
    SubscriptionEntity subscription = em.find(SubscriptionEntity.class, id);
    closeTransaction();
    return subscription;
  }

  public List<SubscriptionEntity> getAll() {
    openTransaction();
    List<SubscriptionEntity> subscriptionList = em.createQuery("SELECT s FROM SubscriptionEntity s", SubscriptionEntity.class).getResultList();
    closeTransaction();
    return subscriptionList;
  }

  public void removeBySubforum(SubforumEntity subforum) {
    openTransaction();
    em.createQuery("DELETE FROM SubscriptionEntity s WHERE s.subforum = :subforum").setParameter("subforum", subforum).executeUpdate();
    closeTransaction();
  }

  public List<SubscriptionEntity> getByUserAndSubforum(UserEntity user, SubforumEntity subforum) {
    openTransaction();
    List<SubscriptionEntity> subscriptionList = em.createQuery("SELECT s FROM SubscriptionEntity s WHERE s.user = :user AND s.subforum = :subforum", SubscriptionEntity.class).setParameter("user", user).setParameter("subforum", subforum).getResultList();
    closeTransaction();
    return subscriptionList;
  }

  public Boolean getIsSubscribedByUserAndSubforum(UserEntity user, SubforumEntity subforum) {
    openTransaction();
    List<SubscriptionEntity> subscriptionList = em.createQuery("SELECT s FROM SubscriptionEntity s WHERE s.user = :user AND s.subforum = :subforum", SubscriptionEntity.class).setParameter("user", user).setParameter("subforum", subforum).getResultList();
    closeTransaction();
    return subscriptionList.size() >= 1;
  }

  public List<SubscriptionEntity> getByUser(UserEntity user) {
    openTransaction();
    List<SubscriptionEntity> subscriptionList = em.createQuery("SELECT s FROM SubscriptionEntity s WHERE s.user = :user", SubscriptionEntity.class).setParameter("user", user).getResultList();
    closeTransaction();
    return subscriptionList;
  }

  public void removeByUser(UserEntity user) {
    openTransaction();
    em.createQuery("DELETE FROM SubscriptionEntity s WHERE s.user = :user").setParameter("user", user).executeUpdate();
    closeTransaction();
  }

  // public List<SubscriptionEntity> getByProperty(Property property) {
  //   openTransaction();
  //   List<SubscriptionEntity> subscriptionList = em..createQuery("SELECT s FROM SubscriptionEntity s WHERE s.property = :property").setParameter("property", property).getResultList();
  //   closeTransaction();
  //   return subscriptionList;
  // }

}
