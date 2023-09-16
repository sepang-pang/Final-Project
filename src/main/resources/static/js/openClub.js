
// ************** container1 ************** //
// 버튼 활성화 효과
var selectedAges = []; // 선택한 연령대 값을 저장할 배열
document.addEventListener("DOMContentLoaded", function () {
    const buttons = document.querySelectorAll(".age-button");

    buttons.forEach(function (button) {
        button.addEventListener("click", function () {
            // 버튼의 상태를 토글 (누를 때마다 상태가 변경됨)
            this.classList.toggle("selected");

            // 선택한 버튼의 연령대 값을 얻기
            const age = parseInt(this.getAttribute('data-age'));

            if (this.classList.contains("selected")) {
                selectedAges.push(age); // 새로 선택한 연령대면 배열에 추가
            } else {
                const index = selectedAges.indexOf(age);
                if (index !== -1) {
                    selectedAges.splice(index, 1); // 이미 선택된 연령대면 배열에서 제거
                }
            }

            // 선택한 연령대 값들을 출력하거나 다른 작업을 수행
            console.log("선택한 연령대:", selectedAges.join(", "));
        });
    });
});

// ************** container2 ************** //
const btnTopic = document.querySelectorAll('.topic-toggle button');
const btnSubtopics = document.querySelectorAll('.subtopics button');
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
const subtopicContainers = document.querySelectorAll('.subtopics');

let selectedTopicButton = null;
let selectedSubtopicContainer = null;

btnTopic.forEach((button, index) => {
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


var btnSave = document.getElementById('next-btn3');
btnSave.addEventListener('click',submit);

function submit(){
    let clubname = document.getElementById('clubname').value;
    let locate = document.getElementById('locate').value;
    let maxMember = Number(document.getElementById('maxMember').value);
    var minAge = Math.min(...selectedAges);
    var maxAge = Math.max(...selectedAges);
    let isOnline = document.getElementById('isOnline').value;
    let openJoinType = document.getElementById('openJoinType').value;
    var activeButton2 = document.querySelector('.subtopic-button.active-button');
    var minorId = Number(activeButton2.getAttribute('data-topic'));
    let description = document.getElementById('description').value;
    let file = document.getElementById('image').files[0]; // 선택한 파일 가져오기
    let clubDto = {
        name: clubname,
        minorId: minorId,
        description: description,
        trialAvailable: true,
        isOnline: isOnline,
        openJoinType: openJoinType,
        maxMember: maxMember,
        minAge: minAge,
        maxAge: maxAge,
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
    }).then(data => {
        console.log(data);
        window.location.href = "/sub-main";
        alert("동호회가 개설되었습니다.");
    }).catch(error => {
        alert(error);
    });
}



