// 버튼 클릭 시 활성화 스타일 변경
const buttons1 = document.querySelectorAll('.button-group1 button');
buttons1.forEach(button => {
    button.addEventListener('click', () => {
        buttons1.forEach(btn => btn.classList.remove('active-button'));
        button.classList.add('active-button');
    });
});

const buttons2 = document.querySelectorAll('.button-group2 button');
buttons2.forEach(button => {
    button.addEventListener('click', () => {
        buttons2.forEach(btn => btn.classList.remove('active-button'));
        button.classList.add('active-button');
    });
});

// //주소-좌표 변환 객체를 생성
// var geocoder = new daum.maps.services.Geocoder();
//
// // 위치 검색
// function locate() {
//     new daum.Postcode({
//         oncomplete: function (data) {
//             var locate = data.address; // 최종 주소 변수
//
//             // 주소로 상세 정보를 검색
//             geocoder.addressSearch(data.address, function (results, status) {
//                 // 정상적으로 검색이 완료됐으면
//                 if (status === daum.maps.services.Status.OK) {
//
//                     var result = results[0]; //첫번째 결과의 값을 활용
//                     var latitude = result.y;
//                     var longitude = result.x;
//
//                     document.getElementById("latitude").value = latitude;
//                     document.getElementById("longitude").value = longitude;
//                     document.getElementById("locate").value = locate;
//                 }
//             });
//         }
//     }).open();
// }


const nextButton1 = document.getElementById('next-btn1');
const goBackButton1 = document.getElementById('goBackButton1');
const container1 = document.getElementById('container1');
const container2 = document.getElementById('container2');

// 다음 버튼 클릭 시 container1 숨기고 container2 표시
nextButton1.addEventListener('click', function () {
    container1.style.display = 'none';
    container2.style.display = 'block';
});

// 뒤로가기 버튼 클릭 시 container2 를 숨기고 container1 표시
goBackButton1.addEventListener('click', function () {
    container1.style.display = 'block';
    container2.style.display = 'none';
})

// HTML 요소와 관련된 변수 선언
const topicButtons = document.querySelectorAll('.topic-toggle button');
const subtopicContainers = document.querySelectorAll('.subtopics');

// 선택한 topicButton 과 서브토픽 상태를 초기화
let selectedTopicButton = null;
let selectedSubtopicContainer = null;

// topicButton 클릭 이벤트 처리
topicButtons.forEach((button, index) => {
    button.addEventListener('click', () => {
        // 이전에 선택한 topicButton 의 서브토픽을 숨김
        if (selectedTopicButton) {
            selectedTopicButton.classList.remove('selected');
            selectedSubtopicContainer.style.display = 'none';
        }

        // 선택한 topicButton 의 서브토픽을 표시
        button.classList.add('selected');
        subtopicContainers[index].style.display = 'block';

        // 선택한 topicButton 과 서브토픽을 갱신
        selectedTopicButton = button;
        selectedSubtopicContainer = subtopicContainers[index];
    });
});


// // 뒤로가기 버튼 클릭 시 이전 페이지로 이동
// function goBack() {
//     window.history.back();
// }
//
// function next() {
//     let clubname = $('#clubname').val();
//     let latitude = $('#latitude').val();
//     let longitude = $('#longitude').val();
//     let locate = $('#locate').val();
//     let numberOfMembers = $('#numberOfMembers').val();
//     let online = $('#onlineButton').val();
//     let offline = $('#offlineButton').val();
//     let immediate = $('#directJoinButton').val();
//     let approval = $('#approvalButton').val();
//
//     $.ajax({
//         type: "POST",
//         url: `/api/clubs`,
//         contentType: "application/json",
//         data: JSON.stringify({
//             clubname: clubname,
//             latitude: latitude,
//             longitude: longitude,
//             locate: locate,
//             numberOfMembers: numberOfMembers,
//             online: online,
//             offline: offline,
//             immediate: immediate,
//             approval: approval
//         }),
//     }).done(function () {
//         window.location.href = '/club'; // 클럽 페이지 URL로 변경하세요
//     }).fail(function (res) {
//         // 오류 메시지 표시
//         alert(res.responseText);
//     });
//
//
// }