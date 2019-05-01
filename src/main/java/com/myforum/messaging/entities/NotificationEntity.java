package com.myforum.messaging.entities;

//imports
import javax.persistence.*;
import com.myforum.login.entities.UserEntity;

@Entity
@Table(name = "Notifications")
public class NotificationEntity {

    public NotificationEntity() {    }

    public NotificationEntity(UserEntity user, int idNumber, String idNumberType) {
      this.user = user;
      this.idNumber = idNumber;
      this.idNumberType = idNumberType;
    }

    @Id
    @Column(name = "notification_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    private UserEntity user;

    @Column(name = "id_num", nullable = false)
    private int idNumber;

    @Column(name = "id_num_type", nullable = false)
    private String idNumberType;

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

    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdNumberType() {
        return idNumberType;
    }

    public void setIdNumberType(String idNumberType) {
        this.idNumberType = idNumberType;
    }

}
