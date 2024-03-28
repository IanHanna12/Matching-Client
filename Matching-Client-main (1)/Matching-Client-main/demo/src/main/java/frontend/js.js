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

function submitFormsajax() {
  const url = 'http://localhost:63342/';
  const dataType = 'json';
  $.ajax({
    type: "POST",
    url: url,
    data: 'data543453',
    // success: success,
    dataType: dataType
  });
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

