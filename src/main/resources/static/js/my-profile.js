function back() {
    window.location.href = "/main"
}

var btnUpdate = document.getElementById('btn-update');
btnUpdate.addEventListener('click', update);

function update() {
    window.location.href = "/api/profile/update"
}