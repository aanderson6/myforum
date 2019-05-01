package com.myforum.messaging.dtos;

public class FilterDTO {

    public FilterDTO() {    }

    public FilterDTO(String username, String managedUsername, Boolean blockAllow) {
      this.username = username;
      this.managedUsername = managedUsername;
      this.blockAllow = blockAllow;
    }

    private String username;
    private String managedUsername;
    private Boolean blockAllow;

    public String getUser() {
        return username;
    }

    public void setUser(String username) {
      if(username == null) {
        throw new NullPointerException();
      }
        this.username = username;
    }

    public String getManagedUser() {
        return managedUsername;
    }

    public void setManagedUser(String managedUsername) {
      if(managedUsername == null) {
        throw new NullPointerException();
      }
        this.managedUsername = managedUsername;
    }

    public Boolean getBlockAllow() {
        return blockAllow;
    }

    public void setBlockAllow(Boolean blockAllow) {
      if(blockAllow == null) {
        throw new NullPointerException();
      }
        this.blockAllow = blockAllow;
    }

}
