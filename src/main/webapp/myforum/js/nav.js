document.getElementById("navDropButton").addEventListener("click", navDropdownButtonClickHandler);
window.addEventListener("click", windowClickHandler);

function navDropdownButtonClickHandler() {
  document.getElementById("navDropMenu").classList.toggle("hide");
}

function windowClickHandler(event) {
  let navMenu = document.getElementById("navDropMenu");
  if ((!(event.target.id === "navDropButton")) && (!(navMenu.classList.contains("hide")))) {
    navMenu.classList.add("hide");
  }
}
