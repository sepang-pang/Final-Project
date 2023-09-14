function back() {
    window.history.back()
}

var btnMain = document.getElementById('btn-main');
btnMain.addEventListener('click', main);

function main() {
    window.location.href = "/main"
}