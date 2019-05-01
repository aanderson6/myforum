package com.myforum.subforum.dtos;

public class ModDTO {

    public ModDTO() {    }

    public ModDTO(String username, int rank) {
      this.username = username;
      this.rank = rank;
    }

    private String username;
    private int rank;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
      if(username == null) {
        throw new NullPointerException();
      }
        this.username = username;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

}
