function submitForms() {
  document.getElementById("id").submit();
  document.getElementById("restaurant").submit('');
}

function validateForm() {
  let x = document.forms["myForm"]["fname"].value;
  if (x === "") {
    alert("Name must be filled out");
    return false;
  }
}

function showAlert() {
  alert("So geht es");
}

function zeigeAlert() {
  alert("Button wurde geklickt!");
}

function getUsers() {
  var xhr = new XMLHttpRequest();
  xhr.open('GET', 'http://localhost:8080/getUsers', true);
  xhr.onload = function() {
    if (xhr.status >= 200 && xhr.status < 300) {
      var users = JSON.parse(xhr.responseText);
      document.getElementById('idinputmatch').value = JSON.stringify(users, null, 2);
    } else {
      console.error('Request failed with status:', xhr.status);
    }
  };
  xhr.onerror = function() {
    console.error('Request failed');
  };
  xhr.send();
}



function findmatchajax() {
  var enteredTime = document.getElementById('timeinput').value;
  var url = 'http://localhost:8080/readUsers?data=' + encodeURIComponent(JSON.stringify({ time: enteredTime }));

  $.ajax({
      type: "GET",
      url: url,
      contentType: 'application/json',
      success: function(response) {
          console.log(response);
          if(response.length > 0) {
              var matchedUserNames = response.map(user => user.name).join(', ');

              // Set the value of the "matchedUserInput" textbox to the names of all matched users
              document.getElementById('matchedUserInput').value = matchedUserNames;

              // Set the text content of the "matchedUser" paragraph to the names of all matched users
              document.getElementById('matchedUser').textContent = matchedUserNames;

              // Set the text content of the "matchedUserResult" paragraph to the entire result
              document.getElementById('matchedUserResult').textContent = JSON.stringify(response, null, 2);
          } else {
              alert('No users found with the entered time');
          }
      },
      error: function(error) {
          console.error(error);
      }
  });
}

function findRandomMatchAjax() {
  var url = 'http://localhost:8080/matchusersRandomly';

  $.ajax({
      type: "GET",
      url: url,
      contentType: 'application/json',
      success: function(response) {
          console.log(response);
          if(response.length > 0) {
              var matchedUserNames = response.map(user => user.name).join(', ');

              // Set the value of the "randomMatchedUserInput" textbox to the names of all matched users
              document.getElementById('randomMatchedUserInput').value = matchedUserNames;

              // Set the text content of the "randomMatchedUserResult" paragraph to the entire result
              document.getElementById('randomMatchedUserResult').textContent = JSON.stringify(response, null, 2);
          } else {
              alert('No users found');
          }
      },
      error: function(error) {
          console.error(error);
      }
  });
}

// Bind the functions to the onclick events of the buttons
document.getElementById('matchUsers').onclick = findmatchajax;
document.getElementById('matchUsersrandomly').onclick = findRandomMatchAjax;