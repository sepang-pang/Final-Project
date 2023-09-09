const buttons1 = document.querySelectorAll('.button-group1 button');
const buttons2 = document.querySelectorAll('.button-group2 button');
const buttons3 = document.querySelectorAll('.topic-toggle button');
const buttons4 = document.querySelectorAll('.subtopics button');
const nextButton1 = document.getElementById('next-btn1');
const goBackButton1 = document.getElementById('goBackButton1');
const container1 = document.getElementById('container1');
const container2 = document.getElementById('container2');

// ************** container1 ************** //
// 버튼 활성화 효과
buttons1.forEach(button => {
    button.addEventListener('click', () => {
        buttons1.forEach(btn => btn.classList.remove('active-button'));
        button.classList.add('active-button');
    });
});

buttons2.forEach(button => {
    button.addEventListener('click', () => {
        buttons2.forEach(btn => btn.classList.remove('active-button'));
        button.classList.add('active-button');
    });
});

// 다음 버튼을 클릭 할 때 컨테이너 전환 1 -> 2
nextButton1.addEventListener('click', function () {
    container1.style.display = 'none';
    container2.style.display = 'block';
});

// ************** container2 ************** //
buttons3.forEach(button => {
    button.addEventListener('click', () => {
        buttons3.forEach(btn => btn.classList.remove('active-button'));
        button.classList.add('active-button');
    });
});

buttons4.forEach(button => {
    button.addEventListener('click', () => {
        buttons4.forEach(btn => btn.classList.remove('active-button'));
        button.classList.add('active-button');
    });
});

// topicButtons 와 subtopicContainers 를 사용하여 토픽을 선택하고 해당 하위 토픽을 표시
const topicButtons = document.querySelectorAll('.topic-toggle button');
const subtopicContainers = document.querySelectorAll('.subtopics');

let selectedTopicButton = null;
let selectedSubtopicContainer = null;

topicButtons.forEach((button, index) => {
    button.addEventListener('click', () => {
        // 이전에 선택된 토픽 버튼의 'selected' 클래스를 제거하고 해당 하위 토픽 컨테이너를 숨김
        if (selectedTopicButton) {
            selectedTopicButton.classList.remove('selected');
            selectedSubtopicContainer.style.display = 'none';
        }
        // 현재 클릭한 토픽 버튼에 'selected' 클래스를 추가하여 선택 표시
        button.classList.add('selected');
        subtopicContainers[index].style.display = 'block'; // CHANGED: This makes sure the correct subtopic is displayed

        // 현재 선택된 토픽 버튼과 하위 토픽 컨테이너를 변수에 저장합니다.
        selectedTopicButton = button;
        selectedSubtopicContainer = subtopicContainers[index];
    });
});

// 뒤로가기 버튼을 클릭 할 때 컨테이너 전환 2- -> 1
goBackButton1.addEventListener('click', function () {
    container1.style.display = 'block';
    container2.style.display = 'none';
});

// 다음 버튼을 클릭 할 때 컨테이너 전환 2 -> 3
const nextButton2 = document.getElementById('next-btn2');
const container3 = document.getElementById('container3');
nextButton2.addEventListener('click', function () {
    container2.style.display = 'none';
    container3.style.display = 'block';
});

// ************** container3 ************** //
// 뒤로가기 버튼을 클릭 할 때 컨테이너 전환 2- -> 1
const goBackButton2 = document.getElementById('goBackButton2');
goBackButton2.addEventListener('click', function () {
    container2.style.display = 'block';
    container3.style.display = 'none';
});

// ************** 연결 ************** //

// 위치 검색
//주소-좌표 변환 객체를 생성
var geocoder = new daum.maps.services.Geocoder();
function locate1() {
    new daum.Postcode({
        oncomplete: function(data) {
            var addr = data.address; // 최종 주소 변수

            // 주소 정보를 해당 필드에 넣는다.
            document.getElementById("locate").value = addr;
            // 주소로 상세 정보를 검색
            geocoder.addressSearch(data.address, function(results, status) {

            });
        }
    }).open();
}





// 개설하기 버튼 클릭 이벤트 핸들러
document.getElementById('next-btn3').addEventListener('click', function () {
    // container1 정보 수집
    const clubname = document.getElementById('clubname').value;
    const locate = document.getElementById('locate').value;
    const numberOfMembers = document.getElementById('numberOfMembers').value;
    const activityType = document.querySelector('.button-group1 .selected').getAttribute('data-join-type');
    const joinType = document.querySelector('.button-group2 .selected').getAttribute('data-join-type');

    // container2 정보 수집
    const selectedSubtopics = [];
    const subtopicButtons = document.querySelectorAll('.subtopics .subtopic-button');
    subtopicButtons.forEach(button => {
        if (button.classList.contains('selected')) {
            selectedSubtopics.push(button.getAttribute('data-topic'));
        }
    });

    // container3 정보 수집
    const description = document.getElementById('description').value;
    const imageFile = document.getElementById('image').files[0];

    // 수집한 정보를 서버로 전송하거나 필요한 처리를 수행
    sendDataToServer(clubname, locate, numberOfMembers, activityType, joinType, selectedSubtopics, description, imageFile);
});

// 선택된 버튼 표시 및 선택된 상태 변경
document.querySelectorAll('.button-group1 button, .button-group2 button').forEach(button => {
    button.addEventListener('click', function () {
        // 선택된 버튼 표시를 해제
        document.querySelectorAll('.button-group1 button, .button-group2 button').forEach(btn => {
            btn.classList.remove('selected');
        });

        // 현재 선택한 버튼을 표시
        button.classList.add('selected');
    });
});

// 서브토픽 버튼 클릭 이벤트 핸들러
document.querySelectorAll('.subtopics .subtopic-button').forEach(button => {
    button.addEventListener('click', function () {
        // 버튼의 선택 상태를 변경
        button.classList.toggle('selected');
    });
});

function sendDataToServer(clubname, locate, numberOfMembers, activityType, joinType, selectedSubtopics, description, imageFile){
    $.ajax({
        type: "POST",
        url: `/api/clubs`,
        contentType: "application/json",
        data: JSON.stringify({clubname: clubname, numberOfMembers: numberOfMembers,
            activityType: activityType, joinType: joinType, selectedSubtopics: selectedSubtopics, description: description, imageFile: imageFile}),
    }).done(function () {
        alert("동호회가 개설되었습니다.")
        back()
    }).fail(function (res) {
        alert(res.responseText);
    })
}



