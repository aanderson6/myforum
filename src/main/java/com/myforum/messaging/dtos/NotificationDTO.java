package com.myforum.messaging.dtos;

public class NotificationDTO {

    public NotificationDTO() {    }

    public NotificationDTO(int idNumber, String idNumberType) {
      this.idNumber = idNumber;
      this.idNumberType = idNumberType;
    }

    private int idNumber;
    private String idNumberType;

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
      if(idNumberType == null) {
        throw new NullPointerException();
      }
        this.idNumberType = idNumberType;
    }

}
