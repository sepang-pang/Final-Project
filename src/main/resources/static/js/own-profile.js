let main = "http://localhost:8080/main"
let updateURL = "http://localhost:8080/api/profile/update"

function back() {
    window.location.href = main
}

var btnUpdate = document.getElementById('btn-update');
btnUpdate.addEventListener('click', update);

function update() {
    window.location.href = updateURL
}