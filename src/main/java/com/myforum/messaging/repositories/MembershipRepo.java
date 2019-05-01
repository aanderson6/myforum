package com.myforum.messaging.repositories;

import com.myforum.messaging.entities.MembershipEntity;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.ArrayList;
import com.myforum.messaging.entities.ConversationEntity;
import com.myforum.login.entities.UserEntity;

public class MembershipRepo {

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

  public MembershipEntity add(MembershipEntity membership) {
    openTransaction();
    em.persist(membership);
    closeTransaction();
    return membership;
  }

  public void update(MembershipEntity membership) {
    openTransaction();
    em.merge(membership);
    closeTransaction();
  }

  public void remove(MembershipEntity membership) {
    openTransaction();
    membership = em.merge(membership);
    em.remove(membership);
    closeTransaction();
  }

  public MembershipEntity getById(int id) {
    openTransaction();
    MembershipEntity membership = em.find(MembershipEntity.class, id);
    closeTransaction();
    return membership;
  }

  public List<MembershipEntity> getAll() {
    openTransaction();
    List<MembershipEntity> membershipList = em.createQuery("SELECT m FROM MembershipEntity m", MembershipEntity.class).getResultList();
    closeTransaction();
    return membershipList;
  }

  public List<MembershipEntity> getByConversation(ConversationEntity conversation) {
    openTransaction();
    List<MembershipEntity> membershipList = em.createQuery("SELECT m FROM MembershipEntity m WHERE m.conversation = :conversation", MembershipEntity.class).setParameter("conversation", conversation).getResultList();
    closeTransaction();
    return membershipList;
  }

  public int getCountByUser(UserEntity user) {
    openTransaction();
    List<Number> countList = em.createQuery("SELECT COUNT(m) FROM MembershipEntity m WHERE m.user = :user", Number.class).setParameter("user", user).getResultList();
    closeTransaction();
    if(countList.size() == 0) {
      return 0;
    } else {
      return countList.get(0).intValue();
    }
  }

// block = false
  public List<MembershipEntity> getByUserOrderedVisibleOnlyFiltered(UserEntity user, int start, int returnCount, List<UserEntity> filterUserList, Boolean blockAllow) {
    openTransaction();
    List<MembershipEntity> membershipList;
    if(!blockAllow) {
      if(filterUserList.isEmpty()) {
        membershipList = em.createQuery("SELECT m FROM MembershipEntity m WHERE m.user = :user AND m.visible = TRUE ORDER BY m.id DESC", MembershipEntity.class).setParameter("user", user).setFirstResult(start).setMaxResults(returnCount).getResultList();
      } else {
        membershipList = em.createQuery("SELECT m FROM MembershipEntity m WHERE m.user = :user AND m.visible = TRUE AND NOT EXISTS (SELECT x FROM MembershipEntity x WHERE m.conversation = x.conversation AND x.user IN (:userList)) ORDER BY m.id DESC", MembershipEntity.class).setParameter("user", user).setParameter("userList", filterUserList).setFirstResult(start).setMaxResults(returnCount).getResultList();
      }
    } else {
      if(filterUserList.isEmpty()) {
        membershipList = new ArrayList<MembershipEntity>();
      } else {
        membershipList = em.createQuery("SELECT m FROM MembershipEntity m WHERE m.user = :user AND m.visible = TRUE AND EXISTS (SELECT x FROM MembershipEntity x WHERE m.conversation = x.conversation AND x.user IN (:userList)) ORDER BY m.id DESC", MembershipEntity.class).setParameter("user", user).setParameter("userList", filterUserList).setFirstResult(start).setMaxResults(returnCount).getResultList();
      }
    }
    closeTransaction();
    return membershipList;
  }

  public int getCountByUserOrderedVisibleOnlyFiltered(UserEntity user, List<UserEntity> filterUserList, Boolean blockAllow) {
    openTransaction();
    List<Number> countList;
    if(!blockAllow) {
      if(filterUserList.isEmpty()) {
        countList = em.createQuery("SELECT COUNT(m) FROM MembershipEntity m WHERE ((m.user = :user) AND (m.visible = TRUE))", Number.class).setParameter("user", user).getResultList();
      } else {
        countList = em.createQuery("SELECT COUNT(m) FROM MembershipEntity m WHERE ((m.user = :user) AND (m.visible = TRUE) AND NOT EXISTS (SELECT x FROM MembershipEntity x WHERE (m.conversation = x.conversation) AND (x.user IN (:userList))))", Number.class).setParameter("user", user).setParameter("userList", filterUserList).getResultList();
      }
    } else {
      if(filterUserList.isEmpty()) {
        countList = new ArrayList<Number>();
      } else {
        countList = em.createQuery("SELECT COUNT(m) FROM MembershipEntity m WHERE ((m.user = :user) AND (m.visible = TRUE) AND EXISTS (SELECT x FROM MembershipEntity x WHERE (m.conversation = x.conversation) AND (x.user IN (:userList))))", Number.class).setParameter("user", user).setParameter("userList", filterUserList).getResultList();
      }
    }
    closeTransaction();
    if(countList.size() == 0) {
      return 0;
    } else {
      return countList.get(0).intValue();
    }
  }

  public MembershipEntity getByUserAndConversation(UserEntity user, ConversationEntity conversation) {
    openTransaction();
    List<MembershipEntity> membershipList = em.createQuery("SELECT m FROM MembershipEntity m WHERE m.user = :user AND m.conversation = :conversation", MembershipEntity.class).setParameter("user", user).setParameter("conversation", conversation).getResultList();
    closeTransaction();
    if(membershipList.size() == 0) {
      return null;
    } else {
      return membershipList.get(0);
    }
  }

  public void removeByUser(UserEntity user) {
    openTransaction();
    em.createQuery("DELETE FROM MembershipEntity m WHERE m.user = :user").setParameter("user", user).executeUpdate();
    closeTransaction();
  }

  // public List<MembershipEntity> getByProperty(Property property) {
  //   openTransaction();
  //   List<MembershipEntity> membershipList = em..createQuery("SELECT m FROM MembershipEntity m WHERE m.property = :property").setParameter("property", property).getResultList();
  //   closeTransaction();
  //   return membershipList;
  // }

}
