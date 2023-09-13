// 문의 수정 버튼 클릭 시 이벤트 핸들러
var updateButton = document.getElementById('btn-update');
updateButton.addEventListener('click', function () {
    // 현재 문의의 ID 가져오기
    var pathArray = window.location.pathname.split('/');
    var inquiryId = pathArray[pathArray.length - 1];

    // '/api/inquiry/update' URL에 inquiryId 파라미터를 추가하여 리다이렉트
    window.location.href = '/api/inquiry/update/' + inquiryId;
});