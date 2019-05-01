package com.myforum.messaging.repositories;

import com.myforum.messaging.entities.NotificationEntity;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import java.util.List;
import com.myforum.login.entities.UserEntity;

public class NotificationRepo {

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

  public NotificationEntity add(NotificationEntity notification) {
    openTransaction();
    em.persist(notification);
    closeTransaction();
    return notification;
  }

  public void update(NotificationEntity notification) {
    openTransaction();
    em.merge(notification);
    closeTransaction();
  }

  public void remove(NotificationEntity notification) {
    openTransaction();
    notification = em.merge(notification);
    em.remove(notification);
    closeTransaction();
  }

  public NotificationEntity getById(int id) {
    openTransaction();
    NotificationEntity notification = em.find(NotificationEntity.class, id);
    closeTransaction();
    return notification;
  }

  public List<NotificationEntity> getAll() {
    openTransaction();
    List<NotificationEntity> notificationList = em.createQuery("SELECT n FROM NotificationEntity n", NotificationEntity.class).getResultList();
    closeTransaction();
    return notificationList;
  }

  public NotificationEntity getByUserAndIdNum(UserEntity user, int idNum) {
    openTransaction();
    List<NotificationEntity> notificationList = em.createQuery("SELECT n FROM NotificationEntity n WHERE n.user = :user AND n.idNumber = :idNum", NotificationEntity.class).setParameter("user", user).setParameter("idNum", idNum).getResultList();
    closeTransaction();
    if(notificationList.size() == 0) {
      return null;
    } else {
      return notificationList.get(0);
    }
  }

  public List<NotificationEntity> getByUser(UserEntity user) {
    openTransaction();
    List<NotificationEntity> notificationList = em.createQuery("SELECT n FROM NotificationEntity n WHERE n.user = :user", NotificationEntity.class).setParameter("user", user).getResultList();
    closeTransaction();
    return notificationList;
  }

  public void removeByUser(UserEntity user) {
    openTransaction();
    em.createQuery("DELETE FROM NotificationEntity n WHERE n.user = :user").setParameter("user", user).executeUpdate();
    closeTransaction();
  }

  // public List<NotificationEntity> getByProperty(Property property) {
  //   openTransaction();
  //   List<NotificationEntity> notificationList = em..createQuery("SELECT n FROM NotificationEntity n WHERE n.property = :property").setParameter("property", property).getResultList();
  //   closeTransaction();
  //   return notificationList;
  // }

}
