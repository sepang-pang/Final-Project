const btnSubtopics = document.querySelectorAll('.subtopics button');
window.onload = function () {

    const interestIdsElement = document.querySelector('#interestIds');
    if (interestIdsElement) {
        const interestIdsText = interestIdsElement.textContent.trim();

        // 페이지 로드 시 interestIds에 해당하는 관심사를 활성화
        btnSubtopics.forEach(button => {
            const interestId = button.getAttribute('data-topic');
            if (interestIdsText.includes(interestId)) {
                button.classList.add('active-button');
            }
        });

        // 관심사 버튼을 클릭할 때 선택 상태를 토글하는 기능은 그대로 유지
        btnSubtopics.forEach(button => {
            button.addEventListener('click', () => {
                const isActive = button.classList.contains('active-button');
                if (isActive) {
                    button.classList.remove('active-button');
                } else {
                    button.classList.add('active-button');
                }
            });
        });
    }
};


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

// 이전 페이지
const goBackButton = document.getElementById('goBackButton');
goBackButton.addEventListener('click', function () {
    window.history.back(); // 이전 페이지로 이동
});

// 저장
const btnSave = document.getElementById('btn-save');
btnSave.addEventListener('click', () => {
    const selectedInterests = Array.from(btnSubtopics)
        .filter(button => button.classList.contains('active-button'))
        .map(button => parseInt(button.getAttribute('data-topic')));

    let requestBody = {minorId: selectedInterests};
    fetch('/api/profile/interests', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(requestBody),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            alert("저장 완료");
            window.location.href = '/sub-main';
        })
        .catch(error => {
            console.error('Error:', error);
        });
});