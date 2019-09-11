function urlencodeFormData(fd){
    var s = '';
    function encode(s){ return encodeURIComponent(s).replace(/%20/g,'+'); }
    for(var pair of fd.entries()){
        if(typeof pair[1]=='string'){
            s += (s?'&':'') + encode(pair[0])+'='+encode(pair[1]);
        }
    }
    return s;
}

function ajaxStartConversation(callbackFunction, fromUsername, toUsernameList, subject, content) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("fromUsername", fromUsername);
  formData.append("toUsernameList", toUsernameList);
  formData.append("subject", subject);
  formData.append("content", content);

  request.open("POST", "http://localhost:8080/rest/messaging/startconversation", true);
  request.send(urlencodeFormData(formData));
}




function ajaxReplyMessage(callbackFunction, fromUsername, conversationId, subject, content) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("fromUsername", fromUsername);
  formData.append("conversationId", conversationId);
  formData.append("subject", subject);
  formData.append("content", content);

  request.open("POST", "http://localhost:8080/rest/messaging/replymessage", true);
  request.send(urlencodeFormData(formData));
}




function ajaxGetConversationsPaged(callbackFunction, username, start, returnCount) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);
  formData.append("start", start);
  formData.append("returnCount", returnCount);

  request.open("POST", "http://localhost:8080/rest/messaging/getorderedconversationsrange", true);
  request.send(urlencodeFormData(formData));
}




function ajaxHideConversation(callbackFunction, username, conversationId) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);
  formData.append("conversationId", conversationId);

  request.open("POST", "http://localhost:8080/rest/messaging/hideconversationbyuser", true);
  request.send(urlencodeFormData(formData));
}




function ajaxHasReadMessage(callbackFunction, username, messageId) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);
  formData.append("messageId", messageId);

  request.open("POST", "http://localhost:8080/rest/messaging/hasreadmessage", true);
  request.send(urlencodeFormData(formData));
}




function ajaxUpdateUserSettings(callbackFunction, username, collapseReadMessages, allOrWhiteList, notifyUnreadMessages, markReadIfOpened) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);
  formData.append("collapseReadMessages", collapseReadMessages);
  formData.append("allOrWhiteList", allOrWhiteList);
  formData.append("notifyUnreadMessages", notifyUnreadMessages);
  formData.append("markReadIfOpened", markReadIfOpened);

  request.open("POST", "http://localhost:8080/rest/messaging/updatesettings", true);
  request.send(urlencodeFormData(formData));
}




function ajaxAddFilter(callbackFunction, username, managedUsername, blockAllow) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);
  formData.append("managedUsername", managedUsername);
  formData.append("blockAllow", blockAllow);

  request.open("POST", "http://localhost:8080/rest/messaging/addfilter", true);
  request.send(urlencodeFormData(formData));
}




function ajaxRemoveFilter(callbackFunction, username, managedUsername, blockAllow) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);
  formData.append("managedUsername", managedUsername);
  formData.append("blockAllow", blockAllow);

  request.open("POST", "http://localhost:8080/rest/messaging/removefilter", true);
  request.send(urlencodeFormData(formData));
}




function ajaxGetUserSettings(callbackFunction, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);

  request.open("POST", "http://localhost:8080/rest/messaging/getsettings", true);
  request.send(urlencodeFormData(formData));
}




function ajaxGetFilters(callbackFunction, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);

  request.open("POST", "http://localhost:8080/rest/messaging/getfilters", true);
  request.send(urlencodeFormData(formData));
}




function ajaxGetNotifications(callbackFunction, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);

  request.open("POST", "http://localhost:8080/rest/messaging/getnotifications", true);
  request.send(urlencodeFormData(formData));
}





function ajaxGetConversationCount(callbackFunction, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);

  request.open("POST", "http://localhost:8080/rest/messaging/getconversationcount", true);
  request.send(urlencodeFormData(formData));
}
