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

function ajaxCreatePost(callbackFunction, username, subforumName, title, content) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);
  formData.append("subforumName", subforumName);
  formData.append("title", title);
  formData.append("content", content);

  request.open("POST", "http://localhost:8080/rest/posts/create", true);
  request.send(urlencodeFormData(formData));
}



function ajaxUpdatePostTitle(callbackFunction, id, title, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("id", id);
  formData.append("title", title);
  formData.append("username", username);

  request.open("POST", "http://localhost:8080/rest/posts/updatetitle", true);
  request.send(urlencodeFormData(formData));
}




function ajaxUpdatePostContent(callbackFunction, id, content, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("id", id);
  formData.append("content", content);
  formData.append("username", username);

  request.open("POST", "http://localhost:8080/rest/posts/updatecontent", true);
  request.send(urlencodeFormData(formData));
}




function ajaxUpvotePost(callbackFunction, id, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("id", id);
  formData.append("username", username);

  request.open("POST", "http://localhost:8080/rest/posts/upvote", true);
  request.send(urlencodeFormData(formData));
}




function ajaxDownvotePost(callbackFunction, id, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("id", id);
  formData.append("username", username);

  request.open("POST", "http://localhost:8080/rest/posts/downvote", true);
  request.send(urlencodeFormData(formData));
}




function ajaxRemoveVotePost(callbackFunction, id, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("id", id);
  formData.append("username", username);

  request.open("POST", "http://localhost:8080/rest/posts/removevote", true);
  request.send(urlencodeFormData(formData));
}




function ajaxRemoveUserOnPost(callbackFunction, id, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("id", id);
  formData.append("username", username);

  request.open("POST", "http://localhost:8080/rest/posts/removeuser", true);
  request.send(urlencodeFormData(formData));
}




function ajaxGetPostsBySubforumPaged(callbackFunction, subforumName, start, returnCount, byUsername) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("subforumName", subforumName);
  formData.append("start", start);
  formData.append("returnCount", returnCount);
  formData.append("byUsername", byUsername);

  request.open("POST", "http://localhost:8080/rest/posts/getbysubforumorderedpaginated", true);
  request.send(urlencodeFormData(formData));
}




function ajaxGetPostCountBySubforum(callbackFunction, subforumName, byUsername) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("subforumName", subforumName);
  formData.append("byUsername", byUsername);

  request.open("POST", "http://localhost:8080/rest/posts/getcountbysubforumfiltered", true);
  request.send(urlencodeFormData(formData));
}




function ajaxGetPostsByUserPaged(callbackFunction, username, start, returnCount) {

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

  request.open("POST", "http://localhost:8080/rest/posts/getbyuserorderedpaginated", true);
  request.send(urlencodeFormData(formData));
}




function ajaxGetPostCountByUser(callbackFunction, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);

  request.open("POST", "http://localhost:8080/rest/posts/getcountbyuser", true);
  request.send(urlencodeFormData(formData));
}




function ajaxGetPostsForUserSubscribedPaged(callbackFunction, username, start, returnCount) {

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

  request.open("POST", "http://localhost:8080/rest/posts/getforusersubscribedorderedpaginated", true);
  request.send(urlencodeFormData(formData));
}




function ajaxGetPostsCountForUserSubscribed(callbackFunction, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);

  request.open("POST", "http://localhost:8080/rest/posts/getcountforusersubscribed", true);
  request.send(urlencodeFormData(formData));
}




function ajaxGetPostById(callbackFunction, id) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("id", id);

  request.open("POST", "http://localhost:8080/rest/posts/getbyid", true);
  request.send(urlencodeFormData(formData));
}
