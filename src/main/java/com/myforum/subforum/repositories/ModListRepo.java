package com.myforum.subforum.repositories;

import com.myforum.login.entities.UserEntity;
import com.myforum.subforum.entities.SubforumEntity;
import com.myforum.subforum.entities.ModListEntity;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import java.util.List;

public class ModListRepo {

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

  public ModListEntity add(ModListEntity mod) {
    openTransaction();
    em.persist(mod);
    closeTransaction();
    return mod;
  }

  public void update(ModListEntity mod) {
    openTransaction();
    em.merge(mod);
    closeTransaction();
  }

  public void remove(ModListEntity mod) {
    openTransaction();
    mod = em.merge(mod);
    em.remove(mod);
    closeTransaction();
  }

  public ModListEntity getById(int id) {
    openTransaction();
    ModListEntity mod = em.find(ModListEntity.class, id);
    closeTransaction();
    return mod;
  }

  public List<ModListEntity> getAll() {
    openTransaction();
    List<ModListEntity> modList = em.createQuery("SELECT m FROM ModListEntity m", ModListEntity.class).getResultList();
    closeTransaction();
    return modList;
  }

  public void removeBySubforum(SubforumEntity subforum) {
    openTransaction();
    em.createQuery("DELETE FROM ModListEntity m WHERE m.subforum = :subforum", ModListEntity.class).setParameter("subforum", subforum).executeUpdate();
    closeTransaction();
  }

  public int getCountBySubforum(SubforumEntity subforum) {
    openTransaction();
    List<Number> countList = em.createQuery("SELECT Count(m) FROM ModListEntity m WHERE m.subforum = :subforum", Number.class).setParameter("subforum", subforum).getResultList();
    closeTransaction();
    if(countList.size() == 0) {
      return 0;
    } else {
      return countList.get(0).intValue();
    }
  }

  public ModListEntity getByUserAndSubforum(UserEntity user, SubforumEntity subforum) {
    openTransaction();
    List<ModListEntity> modList = em.createQuery("SELECT m FROM ModListEntity m WHERE m.user = :user AND m.subforum = :subforum", ModListEntity.class).setParameter("user", user).setParameter("subforum", subforum).getResultList();
    closeTransaction();
    if(modList.size() == 0) {
      return null;
    } else {
      return modList.get(0);
    }
  }

  public List<ModListEntity> getBySubforumAboveRank(SubforumEntity subforum, int rank) {
    openTransaction();
    List<ModListEntity> modList = em.createQuery("SELECT m FROM ModListEntity m WHERE m.subforum = :subforum AND m.rank > :rank", ModListEntity.class).setParameter("subforum", subforum).setParameter("rank", rank).getResultList();
    closeTransaction();
    return modList;
  }

  public Boolean getIsModByUserAndSubforum(UserEntity user, SubforumEntity subforum) {
    openTransaction();
    List<ModListEntity> modList = em.createQuery("SELECT m FROM ModListEntity m WHERE m.user = :user AND m.subforum = :subforum", ModListEntity.class).setParameter("user", user).setParameter("subforum", subforum).getResultList();
    closeTransaction();
    if(modList.size() < 1) {
      return false;
    } else {
      return true;
    }
  }

  public List<ModListEntity> getBySubforumOrderedByRank(SubforumEntity subforum) {
    openTransaction();
    List<ModListEntity> modList = em.createQuery("SELECT m FROM ModListEntity m WHERE m.subforum = :subforum ORDER BY m.rank", ModListEntity.class).setParameter("subforum", subforum).getResultList();
    closeTransaction();
    return modList;
  }

  public List<ModListEntity> getByUser(UserEntity user) {
    openTransaction();
    List<ModListEntity> modList = em.createQuery("SELECT m FROM ModListEntity m WHERE m.user = :user", ModListEntity.class).setParameter("user", user).getResultList();
    closeTransaction();
    return modList;
  }

  public void removeByUser(UserEntity user) {
    openTransaction();
    em.createQuery("DELETE FROM ModListEntity m WHERE m.user = :user").setParameter("user", user).executeUpdate();
    closeTransaction();
  }

  // public List<ModListEntity> getByProperty(Property property) {
  //   openTransaction();
  //   List<ModListEntity> modList = em..createQuery("SELECT m FROM ModListEntity m WHERE m.property = :property").setParameter("property", property).getResultList();
  //   closeTransaction();
  //   return modList;
  // }

}
