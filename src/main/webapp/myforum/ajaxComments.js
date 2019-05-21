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

function ajaxCreateComment(callbackFunction, username, postId, subforumName, content, parentCommentId) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);
  formData.append("postId", postId);
  formData.append("subforumName", subforumName);
  formData.append("content", content);
  if (!(parentCommentId === "")) {
    formData.append("parentCommentId", parentCommentId);
  }

  request.open("POST", "http://localhost:8080/rest/comments/create", true);
  request.send(urlencodeFormData(formData));
}



function ajaxHasReadComment(callbackFunction, id, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("id", id);
  formData.append("username", username);

  request.open("POST", "http://localhost:8080/rest/comments/hasRead", true);
  request.send(urlencodeFormData(formData));
}



function ajaxUpdateCommentContent(callbackFunction, id, content, username) {

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

  request.open("POST", "http://localhost:8080/rest/comments/updatecontent", true);
  request.send(urlencodeFormData(formData));
}




function ajaxUpvoteComment(callbackFunction, id, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("id", id);
  formData.append("username", username);

  request.open("POST", "http://localhost:8080/rest/comments/upvote", true);
  request.send(urlencodeFormData(formData));
}




function ajaxDownvoteComment(callbackFunction, id, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("id", id);
  formData.append("username", username);

  request.open("POST", "http://localhost:8080/rest/comments/downvote", true);
  request.send(urlencodeFormData(formData));
}




function ajaxRemoveVoteComment(callbackFunction, id, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("id", id);
  formData.append("username", username);

  request.open("POST", "http://localhost:8080/rest/comments/removevote", true);
  request.send(urlencodeFormData(formData));
}





function ajaxRemoveUserFromComment(callbackFunction, id, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("id", id);
  formData.append("username", username);

  request.open("POST", "http://localhost:8080/rest/comments/removeuser", true);
  request.send(urlencodeFormData(formData));
}




function ajaxGetCommentById(callbackFunction, id) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("id", id);

  request.open("POST", "http://localhost:8080/rest/comments/getbyid", true);
  request.send(urlencodeFormData(formData));
}




function ajaxGetCommentsByUserPaged(callbackFunction, username, start, returnCount) {

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

  request.open("POST", "http://localhost:8080/rest/comments/getbyuserorderedpaginated", true);
  request.send(urlencodeFormData(formData));
}




function ajaxGetCommentCountByUser(callbackFunction, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);

  request.open("POST", "http://localhost:8080/rest/comments/getcountbyuser", true);
  request.send(urlencodeFormData(formData));
}




function ajaxGetCommentsByPost(callbackFunction, postId, requestingUsername, start, returnCount, depth) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("postId", postId);
  formData.append("requestingUsername", requestingUsername);
  formData.append("start", start);
  formData.append("returnCount", returnCount);
  formData.append("depth", depth);

  request.open("POST", "http://localhost:8080/rest/comments/getbypost", true);
  request.send(urlencodeFormData(formData));
}




function ajaxGetCommentCountByPost(callbackFunction, postId, requestingUsername) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("postId", postId);
  formData.append("requestingUsername", requestingUsername);

  request.open("POST", "http://localhost:8080/rest/comments/getcountbypostfiltered", true);
  request.send(urlencodeFormData(formData));
}




function ajaxGetChildrenOfComment(callbackFunction, commentId, requestingUsername, start, returnCount, depth) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("commentId", commentId);
  formData.append("requestingUsername", requestingUsername);
  formData.append("start", start);
  formData.append("returnCount", returnCount);
  formData.append("depth", depth);

  request.open("POST", "http://localhost:8080/rest/comments/getchildrenof", true);
  request.send(urlencodeFormData(formData));
}




function ajaxGetCommentChildrenCount(callbackFunction, commentId, requestingUsername) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("commentId", commentId);
  formData.append("requestingUsername", requestingUsername);

  request.open("POST", "http://localhost:8080/rest/comments/getcountchildrenof", true);
  request.send(urlencodeFormData(formData));
}
