package com.myforum.messaging.repositories;

import com.myforum.messaging.entities.SettingsEntity;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import java.util.List;
import com.myforum.login.entities.UserEntity;

public class SettingsRepo {

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

  public SettingsEntity add(SettingsEntity settings) {
    openTransaction();
    em.persist(settings);
    closeTransaction();
    return settings;
  }

  public void update(SettingsEntity settings) {
    openTransaction();
    em.merge(settings);
    closeTransaction();
  }

  public void remove(SettingsEntity settings) {
    openTransaction();
    settings = em.merge(settings);
    em.remove(settings);
    closeTransaction();
  }

  public SettingsEntity getById(int id) {
    openTransaction();
    SettingsEntity settings = em.find(SettingsEntity.class, id);
    closeTransaction();
    return settings;
  }

  public List<SettingsEntity> getAll() {
    openTransaction();
    List<SettingsEntity> settingsList = em.createQuery("SELECT s FROM SettingsEntity s", SettingsEntity.class).getResultList();
    closeTransaction();
    return settingsList;
  }

  public SettingsEntity getByUser(UserEntity user) {
    openTransaction();
    List<SettingsEntity> settingsList = em.createQuery("SELECT s FROM SettingsEntity s WHERE s.user = :user", SettingsEntity.class).setParameter("user", user).getResultList();
    closeTransaction();
    if(settingsList.size() == 0) {
      return null;
    } else {
      return settingsList.get(0);
    }
  }

  public void removeByUser(UserEntity user) {
    openTransaction();
    em.createQuery("DELETE FROM SettingsEntity s WHERE s.user = :user").setParameter("user", user).executeUpdate();
    closeTransaction();
  }

  // public List<SettingsEntity> getByProperty(Property property) {
  //   openTransaction();
  //   List<SettingsEntity> settingsList = em..createQuery("SELECT s FROM SettingsEntity s WHERE s.property = :property").setParameter("property", property).getResultList();
  //   closeTransaction();
  //   return settingsList;
  // }

}
