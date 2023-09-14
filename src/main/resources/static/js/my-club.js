// 뒤로 버튼
function back() {
    window.history.back();
}

// 메인 버튼
var btnCreate = document.getElementById('btn-create');
btnCreate.addEventListener('click', create);
function create() {
    window.location.href = "http://localhost:8081/api/open-club";
}