package com.myforum.comments.repositories;

import com.myforum.comments.entities.CommentEntity;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.ArrayList;

import com.myforum.login.entities.UserEntity;
import com.myforum.posts.entities.PostEntity;

public class CommentRepo {

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

  public CommentEntity add(CommentEntity comment) {
    openTransaction();
    em.persist(comment);
    closeTransaction();
    return comment;
  }

  public void update(CommentEntity comment) {
    openTransaction();
    em.merge(comment);
    closeTransaction();
  }

  public void remove(CommentEntity comment) {
    openTransaction();
    comment = em.merge(comment);
    em.remove(comment);
    closeTransaction();
  }

  public CommentEntity getById(int id) {
    openTransaction();
    CommentEntity comment = em.find(CommentEntity.class, id);
    closeTransaction();
    return comment;
  }

  public List<CommentEntity> getAll() {
    openTransaction();
    List<CommentEntity> commentList = em.createQuery("SELECT c FROM CommentEntity c", CommentEntity.class).getResultList();
    closeTransaction();
    return commentList;
  }

  public int getCountByUser(UserEntity user) {
    openTransaction();
    List<Number> countList = em.createQuery("SELECT COUNT(c) FROM CommentEntity c WHERE c.user = :user", Number.class).setParameter("user", user).getResultList();
    closeTransaction();
    if(countList.size() == 0) {
      return 0;
    } else {
      return countList.get(0).intValue();
    }
  }

  public List<CommentEntity> getByUserOrderedPaginated(UserEntity user, int start, int returnCount) {
    openTransaction();
    List<CommentEntity> commentList;
    commentList = em.createQuery("SELECT c FROM CommentEntity c WHERE c.user = :user ORDER BY c.commentDate DESC", CommentEntity.class).setParameter("user", user).setFirstResult(start).setMaxResults(returnCount).getResultList();
    closeTransaction();
    return commentList;
  }

  public int getCountByPostFiltered(PostEntity post, List<UserEntity> userList, Boolean blockAllow) {
    openTransaction();
    List<Number> countList;
    if(!blockAllow) {
      if(userList.isEmpty()) {
        countList = em.createQuery("SELECT COUNT(c) FROM CommentEntity c WHERE c.parentComment IS NULL AND c.post = :post", Number.class).setParameter("post", post).getResultList();
      } else {
        countList = em.createQuery("SELECT COUNT(c) FROM CommentEntity c WHERE c.parentComment IS NULL AND c.post = :post AND c.user NOT IN (:userList)", Number.class).setParameter("userList", userList).setParameter("post", post).getResultList();
      }
    } else {
      if(userList.isEmpty()) {
        countList = new ArrayList<Number>();
      } else {
        countList = em.createQuery("SELECT COUNT(c) FROM CommentEntity c WHERE c.parentComment IS NULL AND c.post = :post AND c.user IN (:userList)", Number.class).setParameter("userList", userList).setParameter("post", post).getResultList();
      }
    }
    closeTransaction();
    if(countList.size() == 0) {
      return 0;
    } else {
      return countList.get(0).intValue();
    }
  }

  public List<CommentEntity> getByPostOrderedPaginatedFiltered(PostEntity post, int start, int returnCount, List<UserEntity> userList, Boolean blockAllow) {
    openTransaction();
    List<CommentEntity> commentList;
    if(!blockAllow) {
      if(userList.isEmpty()) {
        commentList = em.createQuery("SELECT c FROM CommentEntity c WHERE c.parentComment IS NULL AND c.post = :post ORDER BY c.commentDate DESC", CommentEntity.class).setParameter("post", post).setFirstResult(start).setMaxResults(returnCount).getResultList();
      } else {
        commentList = em.createQuery("SELECT c FROM CommentEntity c WHERE c.parentComment IS NULL AND c.post = :post AND c.user NOT IN (:userList) ORDER BY c.commentDate DESC", CommentEntity.class).setParameter("userList", userList).setParameter("post", post).setFirstResult(start).setMaxResults(returnCount).getResultList();
      }
    } else {
      if(userList.isEmpty()) {
        commentList = new ArrayList<CommentEntity>();
      } else {
        commentList = em.createQuery("SELECT c FROM CommentEntity c WHERE c.parentComment IS NULL AND c.post = :post AND c.user IN (:userList) ORDER BY c.commentDate DESC", CommentEntity.class).setParameter("userList", userList).setParameter("post", post).setFirstResult(start).setMaxResults(returnCount).getResultList();
      }
    }
    closeTransaction();
    return commentList;
  }

  public int getCountByPost(PostEntity post) {
    openTransaction();
    List<Number> countList = em.createQuery("SELECT COUNT(c) FROM CommentEntity c WHERE c.parentComment IS NULL AND c.post = :post", Number.class).setParameter("post", post).getResultList();
    closeTransaction();
    if(countList.size() == 0) {
      return 0;
    } else {
      return countList.get(0).intValue();
    }
  }

  public List<CommentEntity> getByPostOrderedPaginated(PostEntity post, int start, int returnCount) {
    openTransaction();
    List<CommentEntity> commentList = em.createQuery("SELECT c FROM CommentEntity c WHERE c.parentComment IS NULL AND c.post = :post ORDER BY c.commentDate DESC", CommentEntity.class).setParameter("post", post).setFirstResult(start).setMaxResults(returnCount).getResultList();
    closeTransaction();
    return commentList;
  }

  public int getCountByCommentFiltered(List<CommentEntity> parentComment, List<UserEntity> userList, Boolean blockAllow) {
    if(parentComment.isEmpty()) {
      return 0;
    }
    openTransaction();
    List<Number> countList;
    if(!blockAllow) {
      if(userList.isEmpty()) {
        countList = em.createQuery("SELECT COUNT(c) FROM CommentEntity c WHERE c.parentComment IN (:commentList)", Number.class).setParameter("commentList", parentComment).getResultList();
      } else {
        countList = em.createQuery("SELECT COUNT(c) FROM CommentEntity c WHERE c.parentComment IN (:commentList) AND c.user NOT IN (:userList)", Number.class).setParameter("userList", userList).setParameter("commentList", parentComment).getResultList();
      }
    } else {
      if(userList.isEmpty()) {
        countList = new ArrayList<Number>();
      } else {
        countList = em.createQuery("SELECT COUNT(c) FROM CommentEntity c WHERE c.parentComment IN (:commentList) AND c.user IN (:userList)", Number.class).setParameter("userList", userList).setParameter("commentList", parentComment).getResultList();
      }
    }
    closeTransaction();
    if(countList.size() == 0) {
      return 0;
    } else {
      return countList.get(0).intValue();
    }
  }

  public List<CommentEntity> getByCommentOrderedPaginatedFiltered(List<CommentEntity> parentComment, int start, int returnCount, List<UserEntity> userList, Boolean blockAllow) {
    if(parentComment.isEmpty()) {
      return new ArrayList<CommentEntity>();
    }
    openTransaction();
    List<CommentEntity> commentList;
    if(!blockAllow) {
      if(userList.isEmpty()) {
        commentList = em.createQuery("SELECT c FROM CommentEntity c WHERE c.parentComment IN (:commentList) ORDER BY c.commentDate DESC", CommentEntity.class).setParameter("commentList", parentComment).setFirstResult(start).setMaxResults(returnCount).getResultList();
      } else {
        commentList = em.createQuery("SELECT c FROM CommentEntity c WHERE c.parentComment IN (:commentList) AND c.user NOT IN (:userList) ORDER BY c.commentDate DESC", CommentEntity.class).setParameter("userList", userList).setParameter("commentList", parentComment).setFirstResult(start).setMaxResults(returnCount).getResultList();
      }
    } else {
      if(userList.isEmpty()) {
        commentList = new ArrayList<CommentEntity>();
      } else {
        commentList = em.createQuery("SELECT c FROM CommentEntity c WHERE c.parentComment IN (:commentList) AND c.user IN (:userList) ORDER BY c.commentDate DESC", CommentEntity.class).setParameter("userList", userList).setParameter("commentList", parentComment).setFirstResult(start).setMaxResults(returnCount).getResultList();
      }
    }
    closeTransaction();
    return commentList;
  }

  public List<CommentEntity> getByCommentOrderedFiltered(List<CommentEntity> parentComment, List<UserEntity> userList, Boolean blockAllow) {
    if(parentComment.isEmpty()) {
      return new ArrayList<CommentEntity>();
    }
    openTransaction();
    List<CommentEntity> commentList;
    if(!blockAllow) {
      if(userList.isEmpty()) {
        commentList = em.createQuery("SELECT c FROM CommentEntity c WHERE c.parentComment IN (:commentList) ORDER BY c.commentDate DESC", CommentEntity.class).setParameter("commentList", parentComment).getResultList();
      } else {
        commentList = em.createQuery("SELECT c FROM CommentEntity c WHERE c.parentComment IN (:commentList) AND c.user NOT IN (:userList) ORDER BY c.commentDate DESC", CommentEntity.class).setParameter("userList", userList).setParameter("commentList", parentComment).getResultList();
      }
    } else {
      if(userList.isEmpty()) {
        commentList = new ArrayList<CommentEntity>();
      } else {
        commentList = em.createQuery("SELECT c FROM CommentEntity c WHERE c.parentComment IN (:commentList) AND c.user IN (:userList) ORDER BY c.commentDate DESC", CommentEntity.class).setParameter("userList", userList).setParameter("commentList", parentComment).getResultList();
      }
    }
    closeTransaction();
    return commentList;
  }

  public int getCountByComment(List<CommentEntity> parentComment) {
    if(parentComment.isEmpty()) {
      return 0;
    }
    openTransaction();
    List<Number> countList = em.createQuery("SELECT COUNT(c) FROM CommentEntity c WHERE c.parentComment IN (:commentList)", Number.class).setParameter("commentList", parentComment).getResultList();
    closeTransaction();
    if(countList.size() == 0) {
      return 0;
    } else {
      return countList.get(0).intValue();
    }
  }

  public List<CommentEntity> getByCommentOrderedPaginated(List<CommentEntity> parentComment, int start, int returnCount) {
    if(parentComment.isEmpty()) {
      return new ArrayList<CommentEntity>();
    }
    openTransaction();
    List<CommentEntity> commentList = em.createQuery("SELECT c FROM CommentEntity c WHERE c.parentComment IN (:commentList) ORDER BY c.commentDate DESC", CommentEntity.class).setParameter("commentList", parentComment).setFirstResult(start).setMaxResults(returnCount).getResultList();
    closeTransaction();
    return commentList;
  }

  public List<CommentEntity> getByComment(List<CommentEntity> parentComment) {
    if(parentComment.isEmpty()) {
      return new ArrayList<CommentEntity>();
    }
    openTransaction();
    List<CommentEntity> commentList = em.createQuery("SELECT c FROM CommentEntity c WHERE c.parentComment IN (:commentList) ORDER BY c.commentDate DESC", CommentEntity.class).setParameter("commentList", parentComment).getResultList();
    closeTransaction();
    return commentList;
  }

  public List<CommentEntity> getByUser(UserEntity user) {
    openTransaction();
    List<CommentEntity> commentList = em.createQuery("SELECT c FROM CommentEntity c WHERE c.user = :user", CommentEntity.class).setParameter("user", user).getResultList();
    closeTransaction();
    return commentList;
  }

  // public List<CommentEntity> getByProperty(Property property) {
  //   openTransaction();
  //   List<CommentEntity> commentList = em..createQuery("SELECT c FROM CommentEntity c WHERE c.property = :property").setParameter("property", property).getResultList();
  //   closeTransaction();
  //   return commentList;
  // }

}
