function submitForms() {
    document.getElementById("id").submit();
    document.getElementById("time").submit('');

}

function validateFields() {
    let id = document.getElementById("idinput").value;
    let time = document.getElementById("timeinput").value;

    if (id === "") {
        alert("ID must be filled out");
        return false;
    }

    if (time === "") {
        alert("Time must be filled out");
        return false;
    }

    return true;
}


function getUsers() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'http://localhost:8080/getUsers', true);
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            const users = JSON.parse(xhr.responseText);
            document.getElementById('idinputmatch').value = JSON.stringify(users, null, 2);
        } else {
            console.error('Request failed with status:', xhr.status);
        }
    };
    xhr.onerror = function () {
        console.error('Request failed');
    };
    xhr.send();
}

function writeMatchedUsersToJSON(user) {
    let enteredId = document.getElementById('idinput').value;
    let selectedRestaurant = document.getElementById('restaurantdrop').value;
    let data = {
        initiator: enteredId,
        matchedUser: user,
        restaurant: selectedRestaurant
    };
    if (!Array.isArray(data)) {
        data = [data];
    }
    $.ajax({
        type: "POST",
        url: 'http://localhost:8080/writeMatchedUsers',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function () {
            console.log('Data written to file');
        },
        error: function (error) {
            console.error(error);
        }
    });
}







// Function to find matched users based on entered time
function findMatchAjax() {
    let enteredTime = document.getElementById('timeinput').value;
    let selectedUserName = document.getElementById('dropdownMenufortimeMatchedUsers').value;
    let url = 'http://localhost:8080/readUsers?data=' + encodeURIComponent(JSON.stringify({time: enteredTime}));

    $.ajax({
        type: "GET",
        url: url,
        contentType: 'application/json',
        success: function (response) {
            if (response.length > 0) {
                let matchedUsers = response.filter(user => user.name === selectedUserName);
                if (matchedUsers.length > 0) {
                    document.getElementById('matchedUser').textContent = selectedUserName;
                    document.getElementById('dropdownMenufortimeMatchedUsers').textContent = JSON.stringify(matchedUsers, 2);
                    writeMatchedUsersToJSON(matchedUsers);
                } else {
                    alert('No user found with the selected name');
                }
            } else {
                alert('No users found with the entered time');
            }
        },
        error: function (error) {
            console.error(error);
        }
    });
}

// Function to find random matched users
function findRandomMatchAjax() {
    let enteredTime = document.getElementById('timeinput').value;
    let enteredId = document.getElementById('idinput').value;
    let url = 'http://localhost:8080/readUsers?data=' + encodeURIComponent(JSON.stringify({time: enteredTime}));

    $.ajax({
        type: "GET",
        url: url,
        contentType: 'application/json',
        cache: false,
        success: function (response) {
            if (response.length > 0) {
                let randIndex = Math.floor(Math.random() * response.length);
                let matchedUser = response[randIndex];
                matchedUser.matchedWith = enteredId;
                document.getElementById('randomMatchedUserInput').value = matchedUser.name;
                document.getElementById('randomMatchedUserInput').textContent = JSON.stringify(matchedUser, 2);
                writeMatchedUsersToJSON(matchedUser);
            } else {
                alert('No users found with the entered time');
            }
        },
        error: function (error) {
            console.error(error);
        }
    });
}

function showUsersInDropdown() {
    let enteredTime = document.getElementById('timeinput').value;
    let url = 'http://localhost:8080/readUsers?data=' + encodeURIComponent(JSON.stringify({time: enteredTime}));

    $.ajax({
        type: "GET",
        url: url,
        dataType: 'json',
        success: function (users) {
            const select = document.getElementById('dropdownMenufortimeMatchedUsers');
            // Clear  dropdown
            select.innerHTML = '';

            // Remove duplicates with list of unique users
            let uniqueUsers = [];
            for (let i = 0; i < users.length; i++) {
                let current = users[i];
                let x = uniqueUsers.find(uniqueName => uniqueName.name === current.name);
                if (!x) {
                    uniqueUsers.push(current);
                }
            }

            // Write uniqueUsers to JSON file
            $.ajax({
                type: "POST",
                url: 'http://localhost:8080/writeUniqueUsers',
                contentType: 'application/json',
                data: JSON.stringify(uniqueUsers),
                success: function () {
                    console.log('Unique users written to file');
                },
                error: function (error) {
                    console.error(error);
                }
            });

            uniqueUsers.forEach(user => {
                const option = new Option(user.name, user.name);
                select.add(option);
            });
        },
        error: function (error) {
            console.error(error);
        }
    });
}

// Bind the functions to the onclick events
function bindEvents() {
    document.getElementById('matchUsers').onclick = findMatchAjax;
    document.getElementById('matchUsersrandomly').onclick = findRandomMatchAjax;
}

bindEvents()
