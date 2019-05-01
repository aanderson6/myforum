package com.myforum.subforum.repositories;

import com.myforum.subforum.entities.SubforumEntity;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import java.util.List;
import com.myforum.login.entities.UserEntity;

public class SubforumRepo {

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

  public SubforumEntity add(SubforumEntity subforum) {
    openTransaction();
    em.persist(subforum);
    closeTransaction();
    return subforum;
  }

  public void update(SubforumEntity subforum) {
    openTransaction();
    em.merge(subforum);
    closeTransaction();
  }

  public void remove(SubforumEntity subforum) {
    openTransaction();
    subforum = em.merge(subforum);
    em.remove(subforum);
    closeTransaction();
  }

  public SubforumEntity getById(int id) {
    openTransaction();
    SubforumEntity subforum = em.find(SubforumEntity.class, id);
    closeTransaction();
    return subforum;
  }

  public List<SubforumEntity> getAll() {
    openTransaction();
    List<SubforumEntity> subforumList = em.createQuery("SELECT s FROM SubforumEntity s", SubforumEntity.class).getResultList();
    closeTransaction();
    return subforumList;
  }

  public SubforumEntity getByName(String subforumName) {
    openTransaction();
    List<SubforumEntity> subforumList = em.createQuery("SELECT s FROM SubforumEntity s WHERE s.name = :subforumName", SubforumEntity.class).setParameter("subforumName", subforumName).getResultList();
    closeTransaction();
    if(subforumList.size() == 0) {
      return null;
    } else {
      return subforumList.get(0);
    }
  }

  public List<SubforumEntity> getOrderedPaginated(int start, int returnCount) {
    openTransaction();
    List<SubforumEntity> subforumList = em.createQuery("SELECT s FROM SubforumEntity s ORDER BY s.name ASC", SubforumEntity.class).setFirstResult(start).setMaxResults(returnCount).getResultList();
    closeTransaction();
    return subforumList;
  }

  public int getCount() {
    openTransaction();
    List<Number> countList = em.createQuery("SELECT COUNT(s) FROM SubforumEntity s", Number.class).getResultList();
    closeTransaction();
    if(countList.size() == 0) {
      return 0;
    } else {
      return countList.get(0).intValue();
    }
  }

  public List<SubforumEntity> getByUser(UserEntity user) {
    openTransaction();
    List<SubforumEntity> subforumList = em.createQuery("SELECT s FROM SubforumEntity s WHERE s.createdBy = :user", SubforumEntity.class).setParameter("user", user).getResultList();
    closeTransaction();
    return subforumList;
  }

  // public List<SubforumEntity> getByProperty(Property property) {
  //   openTransaction();
  //   List<SubforumEntity> subforumList = em.createQuery("SELECT s FROM SubforumEntity s WHERE s.property = :property").setParameter("property", property).getResultList();
  //   closeTransaction();
  //   return subforumList;
  // }

}
