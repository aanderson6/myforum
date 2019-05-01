package com.myforum.posts.repositories;

import com.myforum.posts.entities.PostEntity;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.ArrayList;
import com.myforum.login.entities.UserEntity;
import com.myforum.subforum.entities.SubforumEntity;

public class PostRepo {

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

  public PostEntity add(PostEntity post) {
    openTransaction();
    em.persist(post);
    closeTransaction();
    return post;
  }

  public void update(PostEntity post) {
    openTransaction();
    em.merge(post);
    closeTransaction();
  }

  public void remove(PostEntity post) {
    openTransaction();
    post = em.merge(post);
    em.remove(post);
    closeTransaction();
  }

  public PostEntity getById(int id) {
    openTransaction();
    PostEntity post = em.find(PostEntity.class, id);
    closeTransaction();
    return post;
  }

  public List<PostEntity> getAll() {
    openTransaction();
    List<PostEntity> postList = em.createQuery("SELECT p FROM PostEntity p", PostEntity.class).getResultList();
    closeTransaction();
    return postList;
  }

  public int getCountBySubforumOrderedFiltered(SubforumEntity subforum, List<UserEntity> userList, Boolean blockAllow) {
    openTransaction();
    List<Number> countList;
    if(!blockAllow) {
      if(userList.isEmpty()) {
        countList = em.createQuery("SELECT COUNT(p) FROM PostEntity p WHERE p.subforum = :subforum", Number.class).setParameter("subforum", subforum).getResultList();
      } else {
        countList = em.createQuery("SELECT COUNT(p) FROM PostEntity p WHERE p.subforum = :subforum AND p.user NOT IN :userList", Number.class).setParameter("subforum", subforum).setParameter("userList", userList).getResultList();
      }
    } else {
      if(userList.isEmpty()) {
        countList = new ArrayList<Number>();
      } else {
        countList = em.createQuery("SELECT COUNT(p) FROM PostEntity p WHERE p.subforum = :subforum AND p.user IN :userList", Number.class).setParameter("subforum", subforum).setParameter("userList", userList).getResultList();
      }
    }
    closeTransaction();
    if(countList.size() == 0) {
      return 0;
    } else {
      return countList.get(0).intValue();
    }
  }

  public List<PostEntity> getBySubforumOrderedFiltered(int start, int returnCount, SubforumEntity subforum, List<UserEntity> userList, Boolean blockAllow) {
    openTransaction();
    List<PostEntity> postList;
    if(!blockAllow) {
      if(userList.isEmpty()) {
        postList = em.createQuery("SELECT p FROM PostEntity p WHERE p.subforum = :subforum ORDER BY p.postedDate DESC", PostEntity.class).setParameter("subforum", subforum).setFirstResult(start).setMaxResults(returnCount).getResultList();
      } else {
        postList = em.createQuery("SELECT p FROM PostEntity p WHERE p.subforum = :subforum AND p.user NOT IN (:userList) ORDER BY p.postedDate DESC", PostEntity.class).setParameter("subforum", subforum).setParameter("userList", userList).setFirstResult(start).setMaxResults(returnCount).getResultList();
      }
    } else {
      if(userList.isEmpty()) {
        postList = new ArrayList<PostEntity>();
      } else {
        postList = em.createQuery("SELECT p FROM PostEntity p WHERE p.subforum = :subforum AND p.user IN (:userList) ORDER BY p.postedDate DESC", PostEntity.class).setParameter("subforum", subforum).setParameter("userList", userList).setFirstResult(start).setMaxResults(returnCount).getResultList();
      }
    }
    closeTransaction();
    return postList;
  }

  public int getCountByUserOrdered(UserEntity user) {
    openTransaction();
    List<Number> countList;
    countList = em.createQuery("SELECT COUNT(p) FROM PostEntity p WHERE p.user = :user", Number.class).setParameter("user", user).getResultList();
    closeTransaction();
    if(countList.size() == 0) {
      return 0;
    } else {
      return countList.get(0).intValue();
    }
  }

  public List<PostEntity> getByUserOrdered(UserEntity user, int start, int returnCount) {
    openTransaction();
    List<PostEntity> postList;
    postList = em.createQuery("SELECT p FROM PostEntity p WHERE p.user = :user ORDER BY p.postedDate DESC", PostEntity.class).setParameter("user", user).setFirstResult(start).setMaxResults(returnCount).getResultList();
    closeTransaction();
    return postList;
  }

  public int getCountBySubforumOrdered(SubforumEntity subforum) {
    openTransaction();
    List<Number> countList = em.createQuery("SELECT COUNT(p) FROM PostEntity p WHERE p.subforum = :subforum", Number.class).setParameter("subforum", subforum).getResultList();
    closeTransaction();
    if(countList.size() == 0) {
      return 0;
    } else {
      return countList.get(0).intValue();
    }
  }

  public List<PostEntity> getBySubforumOrdered(int start, int returnCount, SubforumEntity subforum) {
    openTransaction();
    List<PostEntity> postList = em.createQuery("SELECT p FROM PostEntity p WHERE p.subforum = :subforum ORDER BY p.postedDate DESC", PostEntity.class).setParameter("subforum", subforum).setFirstResult(start).setMaxResults(returnCount).getResultList();
    closeTransaction();
    return postList;
  }

  public int getCountBySubforumListFiltered(List<SubforumEntity> subforumList, List<UserEntity> userList, Boolean blockAllow) {
    if(subforumList.isEmpty()) {
      return 0;
    }
    openTransaction();
    List<Number> countList;
    if(!blockAllow) {
      if(userList.isEmpty()) {
        countList = em.createQuery("SELECT COUNT(p) FROM PostEntity p WHERE p.subforum IN (:subforumList)", Number.class).setParameter("subforumList", subforumList).getResultList();
      } else {
        countList = em.createQuery("SELECT COUNT(p) FROM PostEntity p WHERE p.subforum IN (:subforumList) AND p.user NOT IN (:userList)", Number.class).setParameter("subforumList", subforumList).setParameter("userList", userList).getResultList();
      }
    } else {
      if(userList.isEmpty()) {
        countList = new ArrayList<Number>();
      } else {
        countList = em.createQuery("SELECT COUNT(p) FROM PostEntity p WHERE p.subforum IN (:subforumList) AND p.user IN (:userList)", Number.class).setParameter("subforumList", subforumList).setParameter("userList", userList).getResultList();
      }
    }
    closeTransaction();
    if(countList.size() == 0) {
      return 0;
    } else {
      return countList.get(0).intValue();
    }
  }

  public List<PostEntity> getBySubforumListFilteredOrdered(List<SubforumEntity> subforumList, int start, int returnCount, List<UserEntity> userList, Boolean blockAllow) {
    if(subforumList.isEmpty()) {
      return new ArrayList<PostEntity>();
    }
    openTransaction();
    List<PostEntity> postList;
    if(!blockAllow) {
      if(userList.isEmpty()) {
        postList = em.createQuery("SELECT p FROM PostEntity p WHERE p.subforum IN (:subforumList) ORDER BY p.postedDate DESC", PostEntity.class).setParameter("subforumList", subforumList).setFirstResult(start).setMaxResults(returnCount).getResultList();
      } else {
        postList = em.createQuery("SELECT p FROM PostEntity p WHERE p.subforum IN (:subforumList) AND p.user NOT IN (:userList) ORDER BY p.postedDate DESC", PostEntity.class).setParameter("subforumList", subforumList).setParameter("userList", userList).setFirstResult(start).setMaxResults(returnCount).getResultList();
      }
    } else {
      if(userList.isEmpty()) {
        postList = new ArrayList<PostEntity>();
      } else {
        postList = em.createQuery("SELECT p FROM PostEntity p WHERE p.subforum IN (:subforumList) AND p.user IN (:userList) ORDER BY p.postedDate DESC", PostEntity.class).setParameter("subforumList", subforumList).setParameter("userList", userList).setFirstResult(start).setMaxResults(returnCount).getResultList();
      }
    }
    closeTransaction();
    return postList;
  }

  public int getCountAll() {
    openTransaction();
    List<Number> countList = em.createQuery("SELECT COUNT(p) FROM PostEntity p", Number.class).getResultList();
    closeTransaction();
    if(countList.size() == 0) {
      return 0;
    } else {
      return countList.get(0).intValue();
    }
  }

  public List<PostEntity> getAllPaginated(int start, int returnCount) {
    openTransaction();
    List<PostEntity> postList = em.createQuery("SELECT p FROM PostEntity p ORDER BY p.postedDate DESC", PostEntity.class).setFirstResult(start).setMaxResults(returnCount).getResultList();
    closeTransaction();
    return postList;
  }

  public List<PostEntity> getByUser(UserEntity user) {
    openTransaction();
    List<PostEntity> postList = em.createQuery("SELECT p FROM PostEntity p WHERE p.user = :user", PostEntity.class).setParameter("user", user).getResultList();
    closeTransaction();
    return postList;
  }

  // public List<PostEntity> getByProperty(Property property) {
  //   openTransaction();
  //   List<PostEntity> postList = em..createQuery("SELECT p FROM PostEntity p WHERE p.property = :property").setParameter("property", property).getResultList();
  //   closeTransaction();
  //   return postList;
  // }

}
