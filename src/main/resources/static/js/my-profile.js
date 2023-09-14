function back() {
    window.history.back()
}

var btnUpdate = document.getElementById('btn-update');
btnUpdate.addEventListener('click', update);

function update() {
    window.location.href = "/api/profile/update"
}