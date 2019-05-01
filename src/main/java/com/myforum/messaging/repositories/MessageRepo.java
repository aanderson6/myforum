package com.myforum.messaging.repositories;

import com.myforum.messaging.entities.MessageEntity;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import java.util.List;
import com.myforum.messaging.entities.ConversationEntity;
import com.myforum.login.entities.UserEntity;

public class MessageRepo {

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

  public MessageEntity add(MessageEntity message) {
    openTransaction();
    em.persist(message);
    closeTransaction();
    return message;
  }

  public void update(MessageEntity message) {
    openTransaction();
    em.merge(message);
    closeTransaction();
  }

  public void remove(MessageEntity message) {
    openTransaction();
    message = em.merge(message);
    em.remove(message);
    closeTransaction();
  }

  public MessageEntity getById(int id) {
    openTransaction();
    MessageEntity message = em.find(MessageEntity.class, id);
    closeTransaction();
    return message;
  }

  public List<MessageEntity> getAll() {
    openTransaction();
    List<MessageEntity> messageList = em.createQuery("SELECT m FROM MessageEntity m", MessageEntity.class).getResultList();
    closeTransaction();
    return messageList;
  }

    public List<MessageEntity> getByConversation(ConversationEntity conversation) {
      openTransaction();
      List<MessageEntity> messageList = em.createQuery("SELECT m FROM MessageEntity m WHERE m.conversation = :conversation ORDER BY m.sent", MessageEntity.class).setParameter("conversation", conversation).getResultList();
      closeTransaction();
      return messageList;
    }

    public List<MessageEntity> getByUser(UserEntity fromUser) {
      openTransaction();
      List<MessageEntity> messageList = em.createQuery("SELECT m FROM MessageEntity m WHERE m.fromUser = :fromUser", MessageEntity.class).setParameter("fromUser", fromUser).getResultList();
      closeTransaction();
      return messageList;
    }

  // public List<MessageEntity> getByProperty(Property property) {
  //   openTransaction();
  //   List<MessageEntity> messageList = em..createQuery("SELECT m FROM MessageEntity m WHERE m.property = :property").setParameter("property", property).getResultList();
  //   closeTransaction();
  //   return messageList;
  // }

}
