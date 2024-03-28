function generateRandomNumber() {
  var randomNumber = Math.floor(Math.random() * 100) + 1; // Zufällige Zahl zwischen 1 und 100 generieren
  document.getElementById("idinputmatch").value = randomNumber; // Zufällige Zahl in den Kasten einfügen
}
generateRandomNumber();

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


function alert() {
  alert("So geht es");
}
function zeigeAlert() {
  alert("Button wurde geklickt!");
}
$(document).ready(function(){
  $("#submitbutton").click(function(){
    alert("Vorname wurde gespeichert");
  });
});

function  getUsers() {
  var xhr = new XMLHttpRequest();
  xhr.open('GET', 'http://localhost:8080/users', true);
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

function findmatch () {
    var timeUser = document.getElementById('timeinput').value;


}

function findmatch2() {

  var timeUser = document.getElementById('timeinput').value;
  const url = 'http://localhost:8080/users'; // Replace with the actual URL
  const data = {
    timeUser
  };


}

function findmatchajax() {
  const url = 'http://localhost:8080/users';

  var timeUser = document.getElementById('timeinput').value;

  $.ajax({
    type: "POST",
    url: url,
    data: JSON.stringify({ timeUser: timeUser }),
    contentType: 'application/json',
    success: function(jsonData) {

      if (jsonData.times.includes(timeUser)) {
        document.getElementById('idinputmatch').value = 'Match gefunden mit:';
      } else {
        document.getElementById('idinputmatch').value = 'Kein Match gefunden';
      }
    },
    error: function(xhr, status, error) {
      console.error('Error:', error);
    }
  });
}


