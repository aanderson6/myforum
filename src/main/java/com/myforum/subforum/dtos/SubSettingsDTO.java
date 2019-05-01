package com.myforum.subforum.dtos;

public class SubSettingsDTO {

  private String subforumName;
  private String rules;

  public SubSettingsDTO() {  }

  public SubSettingsDTO(String subforumName, String rules) {
    this.subforumName = subforumName;
    this.rules = rules;
  }

  public String getSubforumName() {
    return this.subforumName;
  }

  public void setSubforumName(String subforumName) {
    this.subforumName = subforumName;
  }

  public String getRules() {
    return this.rules;
  }

  public void setRules(String rules) {
    this.rules = rules;
  }
}
