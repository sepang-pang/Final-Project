const btnTopic = document.querySelectorAll('.topic-toggle button');
const btnSubtopics = document.querySelectorAll('.subtopics button');
const btnAge = document.querySelectorAll('.age button');
const nextButton1 = document.getElementById('next-btn1');
const goBackButton1 = document.getElementById('goBackButton1');
const container1 = document.getElementById('container1');
const container2 = document.getElementById('container2');

// ************** container1 ************** //
// 버튼 활성화 효과
btnAge.forEach(button => {
    button.addEventListener('click', () => {
        btnAge.forEach(btn => btn.classList.remove('active-button'));
        button.classList.add('active-button');
    });
});

// 다음 버튼을 클릭 할 때 컨테이너 전환 1 -> 2
nextButton1.addEventListener('click', function () {
    container1.style.display = 'none';
    container2.style.display = 'block';
});

// ************** container2 ************** //
btnTopic.forEach(button => {
    button.addEventListener('click', () => {
        btnTopic.forEach(btn => btn.classList.remove('active-button'));
        button.classList.add('active-button');
    });
});

btnSubtopics.forEach(button => {
    button.addEventListener('click', () => {
        btnSubtopics.forEach(btn => btn.classList.remove('active-button'));
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

// 슬라이더 요소와 값을 표시할 요소 가져오기
const slider1 = document.getElementById('slider1');
const sliderValue1 = document.getElementById('sliderValue1');

// 슬라이더 값이 변경될 때 이벤트 리스너 등록
slider1.addEventListener('input', () => {
    // 슬라이더의 현재 값을 가져와 정수로 변환
    const value1 = parseInt(slider1.value);

    // 슬라이더 값을 표시할 요소에 업데이트
    sliderValue1.textContent = value1.toString();
});

// 슬라이더 요소와 값을 표시할 요소 가져오기
const slider2 = document.getElementById('slider2');
const sliderValue2 = document.getElementById('sliderValue2');

// 슬라이더 값이 변경될 때 이벤트 리스너 등록
slider2.addEventListener('input', () => {
    // 슬라이더의 현재 값을 가져와 정수로 변환
    const value2 = parseInt(slider2.value);

    // 슬라이더 값을 표시할 요소에 업데이트
    sliderValue2.textContent = value2.toString();
});

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




var btnSave = document.getElementById('next-btn3');
btnSave.addEventListener('click',submit);

function submit(){
    let clubname = document.getElementById('clubname').value;
    let locate = document.getElementById('locate').value;
    alert(locate);
    let maxMember = document.getElementById('slider1').value;
    alert(maxMember);
    var activeButton1 = document.querySelector('.age-button.active-button');
    var age = Number(activeButton1.getAttribute('data-age'));
    let isOnline = document.getElementById('isOnline').value;
    let openJoinType = document.getElementById('openJoinType').value;
    var activeButton2 = document.querySelector('.subtopic-button.active-button');
    var minorName = activeButton2.getAttribute('data-topic');
    let description = document.getElementById('description').value;
    let file = document.getElementById('image').files[0]; // 선택한 파일 가져오기
    let clubDto = {
        name: clubname,
        minorId: 1,
        description: description,
        trialAvailable: true,
        isOnline: isOnline,
        openJoinType: openJoinType,
        maxMember: maxMember,
        minAge: 20,
        maxAge: age,
        latitude: 0.1,
        longitude: 0.1,
        locate: locate
    };

    let formData = new FormData();
    formData.append('clubRequestDto', new Blob([JSON.stringify(clubDto)], { type: 'application/json' }));
    formData.append('file', file); // 파일 추가

    // Fetch를 사용하여 서버로 데이터 전송
    fetch('/api/clubs', {
        method: 'POST',
        body: formData
    }).then(response => {
        if (!response.ok) {
            throw new Error('네트워크 오류');
        }
        return response.json();
    }).then(data => {
        alert("동호회가 개설되었습니다.");
    }).catch(error => {
        alert(error);
    });
}



