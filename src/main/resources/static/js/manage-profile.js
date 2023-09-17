function back() {
    window.history.back()
}


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

var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨
    };

//지도를 미리 생성
var map = new daum.maps.Map(mapContainer, mapOption);
//주소-좌표 변환 객체를 생성
var geocoder = new daum.maps.services.Geocoder();
//마커를 미리 생성
var marker = new daum.maps.Marker({
    position: new daum.maps.LatLng(37.537187, 127.005476),
    map: map
});

function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            var locate = data.address; // 최종 주소 변수

            // 주소로 상세 정보를 검색
            geocoder.addressSearch(data.address, function (results, status) {
                // 정상적으로 검색이 완료됐으면
                if (status === daum.maps.services.Status.OK) {

                    var result = results[0]; //첫번째 결과의 값을 활용

                    // 해당 주소에 대한 좌표를 받아서
                    var coords = new daum.maps.LatLng(result.y, result.x);
                    // 지도를 보여준다.
                    mapContainer.style.display = "block";
                    map.relayout();
                    // 지도 중심을 변경한다.
                    map.setCenter(coords);
                    // 마커를 결과값으로 받은 위치로 옮긴다.
                    marker.setPosition(coords)

                    var latitude = result.y;
                    var longitude = result.x;

                    // 주소 정보를 해당 필드에 넣는다.
                    document.getElementById("latitude").value = latitude;
                    document.getElementById("longitude").value = longitude;
                    document.getElementById("locate").value = locate;
                }
            });
        }
    }).open();
}

var btnSave = document.getElementById('btn-save');
btnSave.addEventListener('click', submit);

function submit() {
    let nickname = document.getElementById('nickname').value;
    let introduction = document.getElementById('introduction').value;
    let latitude = document.getElementById('latitude').value;
    let longitude = document.getElementById('longitude').value;
    let locate = document.getElementById('locate').value;
    let file = document.getElementById('file').files[0]; // 선택한 파일 가져오기
    let profileDto = {
        nickname: nickname,
        introduction: introduction,
        latitude: latitude,
        longitude: longitude,
        locate: locate
    };

    // 현재 URL 가져오기
    const currentUrl = window.location.href;

    let requestMethod;
    // 프로필 생성 페이지와 수정 페이지에서 다른 요청 받기
    if (currentUrl.includes("/api/profile/create")) {
        requestMethod = 'POST';
    } else if (currentUrl.includes("/api/profile/update")) {
        requestMethod = 'PATCH';
    } else {
        // 다른 URL인 경우 예외 처리
        console.error("잘못된 경로입니다.");
    }

    // 위치 미입력 시 요청 반환
    if (!nickname || !locate) {
        alert("이름과 위치를 입력해주세요.");
        return;
    }
    // 파일 선택하지 않았을 때 기본 이미지 요청
    if (!file) {
        file = new File([], "empty", {type: "image/png"});
    }

    let formData = new FormData();
    formData.append('requestDto', new Blob([JSON.stringify(profileDto)], {type: 'application/json'}));
    formData.append('file', file); // 파일 추가

    // fetch 사용하여 서버로 데이터 전송
    fetch('/api/profile', {
        method: requestMethod,
        body: formData
    }).then(response => {
        if (!response.ok) {
            throw new Error('네트워크 오류');
        }
        return response.json();
    }).then(data => {
        alert("프로필이 변경되었습니다.");
        back();
    }).catch(error => {
        alert(error);
    });
}