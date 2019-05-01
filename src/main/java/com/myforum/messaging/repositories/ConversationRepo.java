package com.myforum.messaging.repositories;

import com.myforum.messaging.entities.ConversationEntity;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import java.util.List;
import com.myforum.login.entities.UserEntity;

public class ConversationRepo {

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

  public ConversationEntity add(ConversationEntity conversation) {
    openTransaction();
    em.persist(conversation);
    closeTransaction();
    return conversation;
  }

  public void update(ConversationEntity conversation) {
    openTransaction();
    em.merge(conversation);
    closeTransaction();
  }

  public void remove(ConversationEntity conversation) {
    openTransaction();
    conversation = em.merge(conversation);
    em.remove(conversation);
    closeTransaction();
  }

  public ConversationEntity getById(int id) {
    openTransaction();
    ConversationEntity conversation = em.find(ConversationEntity.class, id);
    closeTransaction();
    return conversation;
  }

  public List<ConversationEntity> getAll() {
    openTransaction();
    List<ConversationEntity> conversationList = em.createQuery("SELECT c FROM ConversationEntity c", ConversationEntity.class).getResultList();
    closeTransaction();
    return conversationList;
  }

  public List<ConversationEntity> getByUser(UserEntity fromUser) {
    openTransaction();
    List<ConversationEntity> conversationList = em.createQuery("SELECT c FROM ConversationEntity c WHERE c.fromUser = :fromUser", ConversationEntity.class).setParameter("fromUser", fromUser).getResultList();
    closeTransaction();
    return conversationList;
  }

  // public List<ConversationEntity> getByProperty(Property property) {
  //   openTransaction();
  //   List<ConversationEntity> conversationList = em.createQuery("SELECT c FROM ConversationEntity c WHERE c.property = :property").setParameter("property", property).getResultList();
  //   closeTransaction();
  //   return conversationList;
  // }

}
