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

function ajaxCreateSubforum(callbackFunction, username, name, description, flair, subforumRules) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);
  formData.append("name", name);
  formData.append("description", description);
  formData.append("flair", flair);
  formData.append("subforumRules", subforumRules);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/subforums/create", true);
  request.send(urlencodeFormData(formData));
}




function ajaxUpdateSubforumDescription(callbackFunction, subforumName, description) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("subforumName", subforumName);
  formData.append("description", description);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/subforums/updatedescription", true);
  request.send(urlencodeFormData(formData));
}


function ajaxUpdateSubforumFlair(callbackFunction, subforumName, flair) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("subforumName", subforumName);
  formData.append("flair", flair);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/subforums/updateflair", true);
  request.send(urlencodeFormData(formData));
}



function ajaxUpdateSubforumRules(callbackFunction, subforumName, rules) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("subforumName", subforumName);
  formData.append("rules", rules);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/subforums/updaterules", true);
  request.send(urlencodeFormData(formData));
}



function ajaxSubscribeToSubforum(callbackFunction, username, subforumName) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);
  formData.append("subforumName", subforumName);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/subforums/subscribe", true);
  request.send(urlencodeFormData(formData));
}




function ajaxUnsubscribeFromSubforum(callbackFunction, username, subforumName) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);
  formData.append("subforumName", subforumName);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/subforums/unsubscribe", true);
  request.send(urlencodeFormData(formData));
}





function ajaxAddModToSubforum(callbackFunction, username, subforumName) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);
  formData.append("subforumName", subforumName);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/subforums/addmod", true);
  request.send(urlencodeFormData(formData));
}




function ajaxRemoveModFromSubforum(callbackFunction, username, subforumName) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);
  formData.append("subforumName", subforumName);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/subforums/removemod", true);
  request.send(urlencodeFormData(formData));
}




function ajaxIsModOfSubforum(callbackFunction, subforumName, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("subforumName", subforumName);
  formData.append("username", username);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/subforums/ismod", true);
  request.send(urlencodeFormData(formData));
}




function ajaxIsSubscribedToSubforum(callbackFunction, subforumName, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("subforumName", subforumName);
  formData.append("username", username);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/subforums/issubscribed", true);
  request.send(urlencodeFormData(formData));
}




function ajaxGetSubforumsSubscribedByUser(callbackFunction, username) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("username", username);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/subforums/getsubscriptionsbyuser", true);
  request.send(urlencodeFormData(formData));
}




function ajaxGetSubforumsPaged(callbackFunction, start, returnCount) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("start", start);
  formData.append("returnCount", returnCount);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/subforums/getorderedpaginated", true);
  request.send(urlencodeFormData(formData));
}




function ajaxGetSubforumCount(callbackFunction) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/subforums/getcount", true);
  request.send();
}




function ajaxGetModsBySubforumRankOrder(callbackFunction, subforumName) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("subforumName", subforumName);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/subforums/getmodsorderedbyrank", true);
  request.send(urlencodeFormData(formData));
}




function ajaxSubforumExists(callbackFunction, subforumName) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("subforumName", subforumName);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/subforums/exists", true);
  request.send(urlencodeFormData(formData));
}





function ajaxGetSubforumByName(callbackFunction, subforumName) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("subforumName", subforumName);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/subforums/getbyname", true);
  request.send(urlencodeFormData(formData));
}




function ajaxGetSubforumSettingsByName(callbackFunction, subforumName) {

  let request = new XMLHttpRequest();
  request.onreadystatechange = function() {
    if (this.readyState === 4) {
      callbackFunction(this.status, this.responseText);
    }
  };

  let formData = new FormData();
  formData.append("subforumName", subforumName);

  request.open("POST", "http://ec2-18-220-208-15.us-east-2.compute.amazonaws.com/rest/subforums/getsettingsbyname", true);
  request.send(urlencodeFormData(formData));
}
