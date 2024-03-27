function generateRandomNumber() {
  var randomNumber = Math.floor(Math.random() * 100) + 1; // Zufällige Zahl zwischen 1 und 100 generieren
  document.getElementById("idinputmatch").value = randomNumber; // Zufällige Zahl in den Kasten einfügen
}
generateRandomNumber();
