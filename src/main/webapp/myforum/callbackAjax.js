var globalUsername = "";

function callbackCreateUser(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
    document.getElementById("usernameParagraph").innerHTML = globalUsername;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("createUserOutput").innerHTML = returnValue;
}

function callbackLogin(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
    document.getElementById("usernameParagraph").innerHTML = globalUsername;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("loginOutput").innerHTML = returnValue;
}

function callbackLogout(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
    document.getElementById("usernameParagraph").innerHTML = globalUsername;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("logoutOutput").innerHTML = returnValue;
}

function callbackChangePassword(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
    document.getElementById("usernameParagraph").innerHTML = globalUsername;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("changePasswordOutput").innerHTML = returnValue;
}

function callbackResetPassword(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
    document.getElementById("usernameParagraph").innerHTML = globalUsername;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("resetPasswordOutput").innerHTML = returnValue;
}

function callbackUpdateDisplayName(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("updateDisplayNameOutput").innerHTML = returnValue;
}

function callbackUpdateEmail(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("updateEmailOutput").innerHTML = returnValue;
}

function callbackGetUserInfo(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
    // return JSON.parse(this.responseText);
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getUserInfoOutput").innerHTML = returnValue;
}

function callbackGetUserQs(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
    // return JSON.parse(this.responseText);
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getUserQsOutput").innerHTML = returnValue;
}

function callbackUpdateUserQs(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("updateUserQsOutput").innerHTML = returnValue;
}

function callbackDeleteUser(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("deleteUserOutput").innerHTML = returnValue;
}





function callbackStartConversation(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("startConversationOutput").innerHTML = returnValue;
}

function callbackReplyMessage(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("replyMessageOutput").innerHTML = returnValue;
}

function callbackGetConversationsPaged(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getConversationsPagedOutput").innerHTML = returnValue;
}

function callbackHideConversationByUser(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("hideByUserOutput").innerHTML = returnValue;
}

function callbackHasReadMessage(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("hasReadMessageOutput").innerHTML = returnValue;
}

function callbackUpdateSettings(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("updateSettingsOutput").innerHTML = returnValue;
}

function callbackAddFilter(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("addFilterOutput").innerHTML = returnValue;
}

function callbackRemoveFilter(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("removeFilterOutput").innerHTML = returnValue;
}

function callbackGetSettings(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getUserSettingsOutput").innerHTML = returnValue;
}

function callbackGetFilters(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getFiltersOutput").innerHTML = returnValue;
}

function callbackGetNotifications(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getNotificationsOutput").innerHTML = returnValue;
}

function callbackGetConversationCount(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getConversationCountOutput").innerHTML = returnValue;
}





function callbackCreateSubforum(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("createSubforumOutput").innerHTML = returnValue;
}

function callbackUpdateDescription(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("updateSubDescriptionOutput").innerHTML = returnValue;
}

function callbackUpdateFlair(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("updateSubFlairOutput").innerHTML = returnValue;
}

function callbackUpdateRules(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("updateSubRulesOutput").innerHTML = returnValue;
}

function callbackSubscribe(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("subscribeOutput").innerHTML = returnValue;
}

function callbackUnsubscribe(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("unsubscribeOutput").innerHTML = returnValue;
}

function callbackAddMod(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("addModOutput").innerHTML = returnValue;
}

function callbackRemoveMod(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("removeModOutput").innerHTML = returnValue;
}

function callbackIsMod(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("isModOutput").innerHTML = returnValue;
}

function callbackIsSubscribed(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("isSubscribedOutput").innerHTML = returnValue;
}

function callbackGetSubscriptionsByUser(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getSubscriptionsByUserOutput").innerHTML = returnValue;
}

function callbackGetSubforumsPaged(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getSubforumsPagedOutput").innerHTML = returnValue;
}

function callbackGetSubforumCount(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getSubforumsCountOutput").innerHTML = returnValue;
}

function callbackGetModsBySubforum(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getModsBySubforumOutput").innerHTML = returnValue;
}

function callbackSubforumExists(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("subforumExistsOutput").innerHTML = returnValue;
}

function callbackGetSubforumByName(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getSubforumByNameOutput").innerHTML = returnValue;
}

function callbackGetSubSettingsByName(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getSubSettingsByNameOutput").innerHTML = returnValue;
}





function callbackCreatePost(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("createPostOutput").innerHTML = returnValue;
}

function callbackUpdatePostTitle(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("updateTitleOutput").innerHTML = returnValue;
}

function callbackUpdatePostContent(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("updatePostContentOutput").innerHTML = returnValue;
}

function callbackUpvotePost(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("upvotePostOutput").innerHTML = returnValue;
}

function callbackDownvotePost(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("downvotePostOutput").innerHTML = returnValue;
}

function callbackRemoveVotePost(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("removeVotePostOutput").innerHTML = returnValue;
}

function callbackRemoveUserFromPost(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("removeUserFromPostOutput").innerHTML = returnValue;
}

function callbackGetBySubforumPaged(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getPostsBySubforumPagedOutput").innerHTML = returnValue;
}

function callbackGetCountBySubforum(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getPostCountBySubforumOutput").innerHTML = returnValue;
}

function callbackGetPostsByUserPaged(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getPostsByUserOutput").innerHTML = returnValue;
}

function callbackGetPostCountByUser(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getPostCountByUserOutput").innerHTML = returnValue;
}

function callbackGetForUserSubscribedPaged(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getForUserSubscribedPagedOutput").innerHTML = returnValue;
}

function callbackGetCountForUserSubscribed(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getCountForUserSubscribedOutput").innerHTML = returnValue;
}

function callbackGetPostById(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getPostByIdOutput").innerHTML = returnValue;
}







function callbackCreateComment(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("createCommentOutput").innerHTML = returnValue;
}

function callbackHasReadComment(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("hasReadCommentOutput").innerHTML = returnValue;
}

function callbackUpdateCommentContent(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("updateCommentContentOutput").innerHTML = returnValue;
}

function callbackUpvoteComment(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("upvoteCommentOutput").innerHTML = returnValue;
}

function callbackDownvoteComment(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("downvoteCommentOutput").innerHTML = returnValue;
}

function callbackRemoveVoteComment(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("removeVoteCommentOutput").innerHTML = returnValue;
}

function callbackRemoveUserFromComment(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = "true";
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("removeUserFromCommentOutput").innerHTML = returnValue;
}

function callbackGetCommentById(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getCommentByIdOutput").innerHTML = returnValue;
}

function callbackGetCommentByUserPaged(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getCommentByUserPagedOutput").innerHTML = returnValue;
}

function callbackGetCommentCountByUser(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getCommentCountByUserOutput").innerHTML = returnValue;
}

function callbackGetCommentsByPostPaged(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getCommentsByPostOutput").innerHTML = returnValue;
}

function callbackGetCommentCountByPost(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getCommentCountByPostOutput").innerHTML = returnValue;
}

function callbackGetCommentChildrenOf(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getCommentChildrenOutput").innerHTML = returnValue;
}

function callbackGetCommentCountChildrenOf(status, responseText) {
  let returnValue = "";
  if (status === 200) {
    returnValue = responseText;
  } else {
    returnValue = status + ": " + responseText;
  }

  document.getElementById("getCommentChildrenCountOutput").innerHTML = returnValue;
}
