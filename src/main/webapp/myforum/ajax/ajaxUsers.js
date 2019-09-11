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

function ajaxCreateUser(callbackFunction, username, password, displayName, email, question1, answer1, question2, answer2, question3, answer3) {

  globalUsername = username;

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {

    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);
  formData.append("password", password);
  formData.append("displayName", displayName);
  formData.append("email", email);
  formData.append("question1", question1);
  formData.append("answer1", answer1);
  formData.append("question2", question2);
  formData.append("answer2", answer2);
  formData.append("question3", question3);
  formData.append("answer3", answer3);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/users/create", true);
  request.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
  request.send(urlencodeFormData(formData));
}



function ajaxLogin(callbackFunction, username, password) {

  globalUsername = username;

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);
  formData.append("password", password);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/users/login", true);
  request.send(urlencodeFormData(formData));
}



function ajaxLogout(callbackFunction, username) {

  globalUsername = "";

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/users/logout", true);
  request.send(urlencodeFormData(formData));
}




function ajaxChangePassword(callbackFunction, username, oldPassword, newPassword) {

  globalUsername = username;

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);
  formData.append("oldPassword", oldPassword);
  formData.append("newPassword", newPassword);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/users/changepassword", true);
  request.send(urlencodeFormData(formData));
}



function ajaxResetPassword(callbackFunction, username, newPassword, question1, answer1, question2, answer2, question3, answer3) {

  globalUsername = username;

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);
  formData.append("newPassword", newPassword);
  formData.append("question1", question1);
  formData.append("answer1", answer1);
  formData.append("question2", question2);
  formData.append("answer2", answer2);
  formData.append("question3", question3);
  formData.append("answer3", answer3);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/users/resetpassword", true);
  request.send(urlencodeFormData(formData));
}




function ajaxUpdateDisplayName(callbackFunction, username, displayName) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);
  formData.append("displayName", displayName);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/users/updatedisplayname", true);
  request.send(urlencodeFormData(formData));
}




function ajaxUpdateEmail(callbackFunction, username, email) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);
  formData.append("email", email);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/users/updateemail", true);
  request.send(urlencodeFormData(formData));
}



function ajaxGetInfo(callbackFunction, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/users/getinfo", true);
  request.send(urlencodeFormData(formData));
}




function ajaxGetQs(callbackFunction, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/users/getqs", true);
  request.send(urlencodeFormData(formData));
}




function ajaxUpdateQs(callbackFunction, username, password, question1, answer1, question2, answer2, question3, answer3) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);
  formData.append("password", password);
  formData.append("question1", question1);
  formData.append("answer1", answer1);
  formData.append("question2", question2);
  formData.append("answer2", answer2);
  formData.append("question3", question3);
  formData.append("answer3", answer3);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/users/updateqs", true);
  request.send(urlencodeFormData(formData));
}




function ajaxDeleteUser(callbackFunction, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/users/delete", true);
  request.send(urlencodeFormData(formData));
}
