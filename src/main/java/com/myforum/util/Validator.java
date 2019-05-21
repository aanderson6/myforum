package com.myforum.util;

import java.util.regex.Pattern;

public class Validator {

  public static Boolean validateEmail(String email) {
    Boolean isLongEnough = email.length() >= 7;
    Boolean isShortEnough = email.length() <= 100;
    Boolean isEmailFormat = Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}", email);
    return isLongEnough && isShortEnough && isEmailFormat;
  }

  public static Boolean validateUsername(String username) {
    Boolean isLongEnough = username.length() >= 3;
    Boolean isShortEnough = username.length() <= 20;
    return isLongEnough && isShortEnough;
  }

  public static Boolean validateSHPW(String shpw) {
    Boolean isShortEnough = shpw.length() <= 100;
    Boolean isLongEnough = shpw.length() >= 5;
    return isLongEnough && isShortEnough;
  }

  public static Boolean validateDisplayName(String displayName) {
    Boolean isLongEnough = displayName.length() >= 3;
    Boolean isShortEnough = displayName.length() <= 20;
    return isLongEnough && isShortEnough;
  }

  public static Boolean validateSecQuestion(String question) {
    Boolean isShortEnough = question.length() <= 100;
    Boolean isLongEnough = question.length() >= 5;
    return isLongEnough && isShortEnough;
  }

  public static Boolean validateSHAnswer(String shanswer) {
    Boolean isShortEnough = shanswer.length() <= 100;
    Boolean isLongEnough = shanswer.length() >= 5;
    return isLongEnough && isShortEnough;
  }

  public static Boolean validateSubforumName(String subforumName) {
    Boolean isShortEnough = subforumName.length() <= 50;
    return isShortEnough;
  }

  public static Boolean validateContent(String content) {
    Boolean isShortEnough = content.length() <= 10000;
    return isShortEnough;
  }

  public static Boolean validateTitle(String title) {
    Boolean isShortEnough = title.length() <= 140;
    return isShortEnough;
  }

  public static Boolean validateDescription(String description) {
    Boolean isShortEnough = description.length() <= 10000;
    return isShortEnough;
  }

  public static Boolean validateFlair(String flair) {
    Boolean isShortEnough = flair.length() <= 50;
    return isShortEnough;
  }

  public static Boolean validateRules(String rules) {
    Boolean isShortEnough = rules.length() <= 10000;
    return isShortEnough;
  }

  public static Boolean validateSubject(String subject) {
    Boolean isShortEnough = subject.length() <= 140;
    return isShortEnough;
  }

}
