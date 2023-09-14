var fileInput = document.getElementById('file');
// 파일 미리보기 엘리먼트
var imagePreview = document.getElementById('image-preview');
// 파일 입력 엘리먼트의 onchange 이벤트 핸들러
fileInput.addEventListener('change', function () {
    var file = fileInput.files[0]; // 선택한 파일 가져오기

    // 파일 선택 여부 확인
    if (file) {
        // FileReader 객체를 사용하여 이미지를 미리보기로 표시
        var reader = new FileReader();
        reader.onload = function (e) {
            imagePreview.src = e.target.result;
        };
        reader.readAsDataURL(file);
    }
});

// 뒤로가기 버튼 클릭
function back() {
    window.history.back();
}

var btnSave = document.getElementById('btn-save');
btnSave.addEventListener('click', submit);

function submit() {
    let inquiryId = document.getElementById('inquiryId').value;;
    let title = document.getElementById('title').value;
    let description = document.getElementById('description').value;
    let inquiryType = document.getElementById('inquiry-type').value;
    let file = document.getElementById('file').files[0]; // 선택한 파일 가져오기
    let inquiryDto = {
        inquiryId: inquiryId,
        title: title,
        description: description,
        inquiryType: inquiryType
    };

    // 현재 URL 가져오기
    const currentUrl = window.location.href;

    let requestMethod;
    // 프로필 생성 페이지와 수정 페이지에서 다른 요청 받기
    if (currentUrl.includes("/api/inquiry/create")) {
        requestMethod = 'POST';
    } else if (currentUrl.includes("/api/inquiry/update")) {
        requestMethod = 'PATCH';
    } else {
        // 다른 URL인 경우 예외 처리
        console.error("잘못된 경로입니다.");
    }

    // 위치 미입력 시 요청 반환
    if (!title || !description) {
        alert("제목과 내용을 입력해주세요.");
        return;
    }
    // 파일 선택하지 않았을 때 기본 이미지 요청
    if (!file) {
        file = new File([], "empty", {type: "image/png"});
    }

    let formData = new FormData();
    formData.append('requestDto', new Blob([JSON.stringify(inquiryDto)], {type: 'application/json'}));
    formData.append('file', file); // 파일 추가

    // fetch 사용하여 서버로 데이터 전송
    fetch('/api/inquiry', {
        method: requestMethod,
        body: formData
    }).then(response => {
        if (!response.ok) {
            throw new Error('네트워크 오류');
        }
        return response.json();
    }).then(data => {
        alert("문의 저장 완료");
        back();
    }).catch(error => {
        alert(error);
    });
}