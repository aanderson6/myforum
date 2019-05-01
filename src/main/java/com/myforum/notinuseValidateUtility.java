/*package com.myforum.login;

import java.util.regex.Pattern;

public class ValidateUtility {

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
    return isShortEnough;
  }

  public static Boolean validateDisplayName(String displayName) {
    Boolean isLongEnough = displayName.length() >= 3;
    Boolean isShortEnough = displayName.length() <= 20;
    return isLongEnough && isShortEnough;
  }

  public static Boolean validateSecQuestion(String question) {
    Boolean isShortEnough = question.length() <= 100;
    return isShortEnough;
  }

  public static Boolean validateSHAnswer(String shanswer) {
    Boolean isShortEnough = shanswer.length() <= 100;
    return isShortEnough;
  }

}

*/
