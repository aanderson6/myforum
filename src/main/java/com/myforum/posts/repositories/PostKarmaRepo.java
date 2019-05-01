package com.myforum.posts.repositories;

import com.myforum.posts.entities.PostKarmaEntity;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import java.util.List;
import com.myforum.login.entities.UserEntity;
import com.myforum.posts.entities.PostEntity;

public class PostKarmaRepo {

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

  public PostKarmaEntity add(PostKarmaEntity postKarma) {
    openTransaction();
    em.persist(postKarma);
    closeTransaction();
    return postKarma;
  }

  public void update(PostKarmaEntity postKarma) {
    openTransaction();
    em.merge(postKarma);
    closeTransaction();
  }

  public void remove(PostKarmaEntity postKarma) {
    openTransaction();
    postKarma = em.merge(postKarma);
    em.remove(postKarma);
    closeTransaction();
  }

  public PostKarmaEntity getById(int id) {
    openTransaction();
    PostKarmaEntity postKarma = em.find(PostKarmaEntity.class, id);
    closeTransaction();
    return postKarma;
  }

  public List<PostKarmaEntity> getAll() {
    openTransaction();
    List<PostKarmaEntity> postKarmaList = em.createQuery("SELECT p FROM PostKarmaEntity p", PostKarmaEntity.class).getResultList();
    closeTransaction();
    return postKarmaList;
  }

  public Boolean hasVoted(UserEntity user, PostEntity post) {
    openTransaction();
    List<PostKarmaEntity> postKarmaList = em.createQuery("SELECT p FROM PostKarmaEntity p WHERE p.user = :user AND p.post = :post", PostKarmaEntity.class).setParameter("user", user).setParameter("post", post).getResultList();
    closeTransaction();
    if(postKarmaList.size() == 0) {
      return false;
    } else {
      return true;
    }
  }

  public PostKarmaEntity getByUserAndPost(UserEntity user, PostEntity post) {
    openTransaction();
    List<PostKarmaEntity> postKarmaList = em.createQuery("SELECT p FROM PostKarmaEntity p WHERE p.user = :user AND p.post = :post", PostKarmaEntity.class).setParameter("user", user).setParameter("post", post).getResultList();
    closeTransaction();
    if(postKarmaList.size() == 0) {
      return null;
    } else {
      return postKarmaList.get(0);
    }
  }

  public List<PostKarmaEntity> getByPost(PostEntity post) {
    openTransaction();
    List<PostKarmaEntity> postKarmaList = em.createQuery("SELECT p FROM PostKarmaEntity p WHERE p.post = :post", PostKarmaEntity.class).setParameter("post", post).getResultList();
    closeTransaction();
    return postKarmaList;
  }

  public List<PostKarmaEntity> getByUser(UserEntity user) {
    openTransaction();
    List<PostKarmaEntity> postKarmaList = em.createQuery("SELECT p FROM PostKarmaEntity p WHERE p.user = :user", PostKarmaEntity.class).setParameter("user", user).getResultList();
    closeTransaction();
    return postKarmaList;
  }

  // public List<PostKarmaEntity> getByProperty(Property property) {
  //   openTransaction();
  //   List<PostKarmaEntity> postKarmaList = em..createQuery("SELECT p FROM PostKarmaEntity p WHERE p.property = :property").setParameter("property", property).getResultList();
  //   closeTransaction();
  //   return postKarmaList;
  // }

}
