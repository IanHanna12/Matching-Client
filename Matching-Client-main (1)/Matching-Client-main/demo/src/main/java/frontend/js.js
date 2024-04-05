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

function writematchedusersToJSON(uniqueUsers) {
    $.ajax({
        type: "POST",
        url: 'http://localhost:8080/writeMatchedUsers',
        contentType: 'application/json',
        data: JSON.stringify(uniqueUsers),
        success: function(response) {
            console.log('Data written to file');
        },
        error: function(error) {
            console.error(error);
        }
    });
}

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
                // Filter out duplicate IDs
                response.forEach(user => {
                    if (!uniqueUsers.find(u => u.ID === user.ID)) {
                        uniqueUsers.push(user);
                    }
                });

                var matchedUserNames = uniqueUsers.map(user => user.name).join(', ');

                // Set the value of the "matchedUserInput" textbox to the names of all matched users
                document.getElementById('matchedUserInput').value = matchedUserNames;

                // Set the text content of the "matchedUser" paragraph to the names of all matched users
                document.getElementById('matchedUser').textContent = matchedUserNames;

                if (uniqueUsers.length > 0) {
                    document.getElementById('matchedUserResult').textContent = JSON.stringify(uniqueUsers, null, 2);
                }
            } else {
                alert('No users found with the entered time');
            }

            writematchedusersToJSON(uniqueUsers);
        },
        error: function(error) {
            console.error(error);
        }
    });
}

// Function to find random matched users
function findRandomMatchAjax (writematchedusersToJSON) {
    var url = 'http://localhost:8080/matchusersRandomly';

    $.ajax({
        type: "GET",
        url: url,
        contentType: 'application/json',
        success: function(response) {
            console.log(response);
            if(response.length > 0) {
                // Filter out duplicate IDs
                response.forEach(user => {
                    if (!uniqueUsers.find(u => u.ID === user.ID)) {
                        uniqueUsers.push(user);
                    }
                });

                // Get the names of all unique users
                var matchedUserNames = uniqueUsers.map(user => user.name);

                // Set the value of the "randomMatchedUserInput" textbox to the first user's name
                if(matchedUserNames.length > 0) {
                    document.getElementById('randomMatchedUserInput').value = matchedUserNames[0];
                }

                // Set the text content of the "randomMatchedUserResult" paragraph to the first user
                if(uniqueUsers.length > 0) {
                    document.getElementById('randomMatchedUserResult').textContent = JSON.stringify(uniqueUsers[0], 2);
                }
            } else {
                alert('No users found');
            }

            writematchedusersToJSON(uniqueUsers);
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