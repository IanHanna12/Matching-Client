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

var uniqueUsers = [];

function writematchedusersToJSON(user) {
    if (!Array.isArray(user)) {
        user = [user];
    }

    $.ajax({
        type: "POST",
        url: 'http://localhost:8080/writeMatchedUsers',
        contentType: 'application/json',
        data: JSON.stringify(user),
        success: function(response) {
            console.log('Data written to file');
        },
        error: function(error) {
            console.error(error);
        }
    });
}

// Function to find matched users based on entered time
// Function to find matched users based on entered time
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
                // Get the first matched user
                var matchedUser = response[0];

                // Set the value of the "matchedUserInput" textbox to the name of the matched user
                document.getElementById('matchedUserInput').value = matchedUser.name;

                // Set the text content of the "matchedUser" paragraph to the name of the matched user
                document.getElementById('matchedUser').textContent = matchedUser.name;

                // Set the text content of the "matchedUserResult" paragraph to the matched user
                document.getElementById('matchedUserResult').textContent = JSON.stringify(matchedUser, 2);

                writematchedusersToJSON(matchedUser);
            } else {
                alert('No users found with the entered time');
            }
        },
        error: function(error) {
            console.error(error);
        }
    });
}

// Function to find random matched users
function findRandomMatchAjax () {
    var url = 'http://localhost:8080/matchusersRandomly';

    $.ajax({
        type: "GET",
        url: url,
        contentType: 'application/json',
        success: function(response) {
            console.log(response);
            if(response.length > 0) {
                // Get the first matched user
                var matchedUser = response[0];

                // Set the value of the "randomMatchedUserInput" textbox to the name of the matched user
                document.getElementById('randomMatchedUserInput').value = matchedUser.name;

                // Set the text content of the "randomMatchedUserResult" paragraph to the matched user
                document.getElementById('randomMatchedUserResult').textContent = JSON.stringify(matchedUser, 2);

                writematchedusersToJSON(matchedUser);
            } else {
                alert('No users found');
            }
        },
        error: function(error) {
            console.error(error);
        }
    });
}

// Function to write matched users to JSON file

// Bind the functions to the onclick events of the buttons
document.getElementById('matchUsers').onclick = function() {
    findmatchajax();
};

document.getElementById('matchUsersrandomly').onclick = function() {
    findRandomMatchAjax();
};