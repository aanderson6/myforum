package com.myforum.messaging.entities;

//imports
import javax.persistence.*;
import com.myforum.login.entities.UserEntity;

@Entity
@Table(name = "Filters")
public class FilterEntity {

    public FilterEntity() {    }

    public FilterEntity(UserEntity user, UserEntity managedUser, Boolean blockAllow) {
      this.user = user;
      this.managedUser = managedUser;
      this.blockAllow = blockAllow;
    }

    @Id
    @Column(name = "filter_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "managed_uid", nullable = false)
    private UserEntity managedUser;

    // false = block, true = allow
    @Column(name = "block_allow", nullable = false)
    private Boolean blockAllow;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public UserEntity getManagedUser() {
        return managedUser;
    }

    public void setManagedUser(UserEntity managedUser) {
        this.managedUser = managedUser;
    }

    public Boolean getBlockAllow() {
        return blockAllow;
    }

    public void setBlockAllow(Boolean blockAllow) {
        this.blockAllow = blockAllow;
    }

}
