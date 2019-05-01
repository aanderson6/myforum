package com.myforum.subforum.repositories;

import com.myforum.subforum.entities.SubforumSettingsEntity;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import java.util.List;
import com.myforum.subforum.entities.SubforumEntity;

public class SubforumSettingsRepo {

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

  public SubforumSettingsEntity add(SubforumSettingsEntity subforumSettings) {
    openTransaction();
    em.persist(subforumSettings);
    closeTransaction();
    return subforumSettings;
  }

  public void update(SubforumSettingsEntity subforumSettings) {
    openTransaction();
    em.merge(subforumSettings);
    closeTransaction();
  }

  public void remove(SubforumSettingsEntity subforumSettings) {
    openTransaction();
    subforumSettings = em.merge(subforumSettings);
    em.remove(subforumSettings);
    closeTransaction();
  }

  public SubforumSettingsEntity getById(int id) {
    openTransaction();
    SubforumSettingsEntity subforumSettings = em.find(SubforumSettingsEntity.class, id);
    closeTransaction();
    return subforumSettings;
  }

  public List<SubforumSettingsEntity> getAll() {
    openTransaction();
    List<SubforumSettingsEntity> subforumSettingsList = em.createQuery("SELECT s FROM SubforumSettingsEntity s", SubforumSettingsEntity.class).getResultList();
    closeTransaction();
    return subforumSettingsList;
  }

  public SubforumSettingsEntity getBySubforum(SubforumEntity subforum) {
    openTransaction();
    List<SubforumSettingsEntity> subforumSettingsList = em.createQuery("SELECT s FROM SubforumSettingsEntity s WHERE s.subforum = :subforum", SubforumSettingsEntity.class).setParameter("subforum", subforum).getResultList();
    closeTransaction();
    if (subforumSettingsList.size() == 0) {
      return null;
    } else {
      return subforumSettingsList.get(0);
    }
  }

  public void removeBySubforum(SubforumEntity subforum) {
    openTransaction();
    em.createQuery("DELETE FROM SubforumSettingsEntity s WHERE s.subforum = :subforum").setParameter("subforum", subforum).executeUpdate();
    closeTransaction();
  }

  // public List<SubforumSettingsEntity> getByProperty(Property property) {
  //   openTransaction();
  //   List<SubforumSettingsEntity> subforumSettingsList = em..createQuery("SELECT s FROM SubforumSettingsEntity s WHERE s.property = :property").setParameter("property", property).getResultList();
  //   closeTransaction();
  //   return subforumSettingsList;
  // }

}
