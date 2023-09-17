function back() {
    window.location.href = "/sub-main"
}

var btnReport = document.getElementById('btn-report');
var reportModal = document.getElementById('reportModal');
var closeModal = document.getElementById('closeModal');
var reportForm = document.getElementById('reportForm');

// 신고하기 버튼 클릭 시 모달 팝업 열기
btnReport.addEventListener('click', function () {
    reportModal.style.display = 'block';
});

// 모달 닫기 버튼 클릭 시 모달 팝업 닫기
closeModal.addEventListener('click', function () {
    reportModal.style.display = 'none';
});

// 모달 외부 클릭 시 모달 팝업 닫기
window.addEventListener('click', function (event) {
    if (event.target == reportModal) {
        reportModal.style.display = 'none';
    }
});

// 폼 제출 시 이벤트 처리
reportForm.addEventListener('submit', function (event) {
    event.preventDefault();

    // 가져온 ID를 서버로 전송할 데이터에 추가

    let reportData = {
        targetId: document.getElementById('targetId').value,
        title: document.getElementById('reportTitle').value,
        content: document.getElementById('reportContent').value
    };

    fetch('/api/report/user', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(reportData)
    })
        .then(response => response.json())
        .then(data => {
            alert("신고 완료");
            reportModal.style.display = 'none';
            window.location.href = "/sub-main";
        })
        .catch(error => {
            console.error('Error:', error);
        });
});