// 현재 문의의 ID 가져오기
var pathArray = window.location.pathname.split('/');
var inquiryId = pathArray[pathArray.length - 1];

// 삭제 모달 열기
function openModal() {
    var modal = document.getElementById("deleteModal");
    modal.style.display = "block";
}
// 삭제 모달 닫기
function closeModal() {
    var modal = document.getElementById("deleteModal");
    modal.style.display = "none";
}

// 뒤로가기 버튼 클릭
function back() {
    window.history.back();
}

// 삭제 확인 버튼 클릭 시 실행되는 함수
function deleteInquiry() {
    // fetch 사용하여 서버로 데이터 전송
    fetch('/api/inquiry/' + inquiryId, {
        method: 'DELETE'
    }).then(response => {
        if (!response.ok) {
            throw new Error('네트워크 오류');
        }
        return response.json();
    }).then(data => {
        alert("문의 삭제 완료");
        closeModal(); // 모달 닫기
        window.history.back()
    }).catch(error => {
        alert(error);
    });
}

// 문의 수정 버튼 클릭 시 이벤트 핸들러
var updateButton = document.getElementById('btn-update');
updateButton.addEventListener('click', function () {
    // '/api/inquiry/update' URL에 inquiryId 파라미터를 추가하여 리다이렉트
    window.location.href = '/api/inquiry/update/' + inquiryId;
});