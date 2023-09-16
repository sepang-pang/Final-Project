
window.onload = function () {
    const clubId = [[${clubId}]]; // club의 아이디를 PathVariable로 받아옴.
    const currentUsername = [[${currentUsername}]];
    const clubUsername = [[${clubUsername}]];
    const heartBtn = document.querySelector(".btn-heart");
    const sirenBtn = document.querySelector(".btn-siren");

    // 버튼 클릭 시 위치를 잠시 변경하는 함수
    function animateButton(btn) {
        btn.style.transition = "transform 0.1s";
        btn.style.transform = "translate(1px, 1px)";
        setTimeout(() => {
            btn.style.transition = "background-color 0.1s, transform 0.1s";
            btn.style.transform = "translate(0, 0)";
        }, 100);  // 클릭 후 0.1초 지나면 원래 위치로 돌아옴
    }



    // ================================================== //
    // 버튼 클릭 시 섹션을 보여주는 함수
    const buttonToSectionMapping = {
        "세부정보": "detail-info",
        "모임기록": "meeting-record",
        "활동지역": "activity-area",
        "채팅방": "chat-room"
    };

    // 모든 섹션을 가져옵니다.
    let sections = document.querySelectorAll('.content-section');

    document.querySelector('.club-info-btn-group').addEventListener('click', function (e) {
        if (e.target.classList.contains('club-btn')) {
            // 모든 섹션을 숨깁니다.
            sections.forEach(section => {
                section.style.display = 'none';
            });

            // 채팅방을 눌렀을 때 경고창을 띄웁니다.
            if (e.target.textContent === "채팅방") {
                alert("채팅방은 준비 중입니다.");
            }

            if (e.target.textContent === "멤버") {
                if (currentUsername === clubUsername) {
                    document.querySelector('.club-leader-view').style.display = 'block';
                } else {
                    document.querySelector('.regular-user-view').style.display = 'block';
                }
            } else {
                // 클릭된 버튼에 해당하는 섹션만 보여줍니다.
                let sectionToShow = buttonToSectionMapping[e.target.textContent];
                if (sectionToShow) {
                    document.querySelector(`.${sectionToShow}`).style.display = 'block';
                }
            }
        }
    });

    // 동호회장인지 일반 유저인지에 따라 화면을 다르게 표시
    const clubLeaderView = document.querySelector('.club-leader-view');
    const regularUserView = document.querySelector('.regular-user-view');

    if (currentUsername === clubUsername) {
        clubLeaderView.style.display = 'block';
        regularUserView.style.display = 'none';
    } else {
        clubLeaderView.style.display = 'none';
        regularUserView.style.display = 'block';
    }

    // ================================================== //

    // API 호출
    fetch(`/api/clubs/${clubId}`) // 타임리프로 받아온 Id 값을 주입 후 API 호출
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(data => {
            // 받아온 DTO 의 필드값을 화면에 출력
            document.querySelector('h1').textContent = data.name; // 동호회 이름
            document.querySelector('.tag1').textContent = data.major; // 동호회 대주제
            document.querySelector('.tag2').textContent = data.minor; // 동호회 소주제
            document.querySelector('.article-content').textContent = data.description; // 동호회 소개글
            document.querySelector('.leader-name').textContent = data.nickName; // 동호회장 이름
            document.querySelector('.max-members').textContent = data.maxMember; // 최대 인원 수
            document.querySelector('.activity-method').textContent = data.activityType; // 활동 방식
            document.querySelector('.join-method').textContent = data.joinType; // 가입 방식
        })
        .catch(error => {
            console.error("There was a problem with the fetch operation:", error.message);
        });


    // /{clubId}/uncompleted API 호출
    fetch(`/api/meetings/${clubId}/uncompleted`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(meetings => {
            const cardsContainer = document.querySelector('.cards-container');
            meetings.forEach(meeting => {
                const meetingCardHTML = `
                <div class="card">
                    <div class="card-title-section">
                        <h3 class="card-date">${meeting.date}</h3>
                        <h4 class="card-title">${meeting.title}</h4>
                    </div>
                    <div class="card-info-section">
                        <p>일시 <span class="card-time">${meeting.dateDetail}</span></p>
                        <p>위치 <span class="card-location">${meeting.place}</span></p>
                        <p>참여인원 <span class="card-participants">${meeting.memberCount} / ${meeting.maxMember}</span></p>
                    </div>
                </div>
            `;
                cardsContainer.innerHTML += meetingCardHTML;
            });
        })
        .catch(error => {
            console.error("There was a problem with the fetch operation:", error.message);
        });

    fetch(`/api/meetings/${clubId}/completed`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then(completedMeetings => {
            const completedMeetingsList = document.querySelector('.completed-meetings-list');

            completedMeetings.forEach(meeting => {
                const meetingItemHTML = `
            <div class="completed-meeting-item">
                <h4>${meeting.description}</h4>
                <p>날짜: ${meeting.date}</p>
                <p>댓글 수: ${meeting.commentCount}</p>
            </div>
            `;
                completedMeetingsList.innerHTML += meetingItemHTML;
            });
        })
        .catch(error => {
            console.error("There was a problem with the fetch operation:", error.message);
        });


    fetch(`/api/clubs/${clubId}/applies`) // 신청자 목록을 호출
        .then(response => response.json())
        .then(applies => {
            const applicantList = document.querySelector('.applicant-list');
            applies.forEach(apply => {
                const applicantHTML = `
                        <div class="applicant">
                            <div class="profile-pic"></div>
                            <div class="nickname">${apply.nickName}</div>
                            <div class="action-buttons">
                                <button class="accept-btn" data-apply-id="${apply.id}">승인</button>
                                <button class="reject-btn" data-apply-id="${apply.id}">거절</button>
                            </div>
                        </div>
                    `;
                applicantList.innerHTML += applicantHTML;
            });
        });

    // 멤버 목록 불러오기
    fetch(`/api/clubs/${clubId}/members`)
        .then(response => response.json())
        .then(members => {
            const memberList = document.querySelectorAll('.member-list');  // 동호회장 & 일반 유저용
            members.forEach(member => {
                const memberHTML = `
                        <div class="member">
                            <div class="profile-pic"></div>
                            <div class="nickname">${member.nickName}</div>
                        </div>
                    `;
                memberList.forEach(list => list.innerHTML += memberHTML);
            });
        });

    document.body.addEventListener('click', function (event) {
        if (event.target.classList.contains('accept-btn')) {
            const applyId = event.target.getAttribute('data-apply-id');

            fetch(`/api/clubs/applies/${applyId}/approve`, {
                method: 'PUT'
            }).then(response => {
                if (response.ok) {
                    alert('가입 승인 완료!');
                    event.target.parentElement.parentElement.remove();  // 신청자 항목 제거
                } else {
                    alert('오류 발생. 다시 시도하세요.');
                }
            });
        } else if (event.target.classList.contains('reject-btn')) {
            const applyId = event.target.getAttribute('data-apply-id');

            fetch(`/api/clubs/applies/${applyId}/refuse`, {
                method: 'PUT'
            }).then(response => {
                if (response.ok) {
                    alert('가입 거절 완료!');
                    event.target.parentElement.parentElement.remove();  // 신청자 항목 제거
                } else {
                    alert('오류 발생. 다시 시도하세요.');
                }
            });
        }
    });

    document.getElementById('createMeetingBtn').addEventListener('click', function() {
        document.getElementById('meetingModal').style.display = "block";
    });

    document.querySelector('.close').addEventListener('click', function() {
        document.getElementById('meetingModal').style.display = "none";
    });

    document.getElementById('maxMembers').addEventListener('input', function() {
        document.getElementById('maxMemberValue').innerText = this.value;
    });

    document.getElementById('submitMeeting').addEventListener('click', function() {
        const title = document.getElementById('title').value;
        const content = document.getElementById('content').value;
        const maxMembers = document.getElementById('maxMembers').value;
        const location = document.getElementById('location').value;
        const date = document.getElementById('date').value;
        const ampm = document.getElementById('ampm').value;
        const hour = document.getElementById('hour').value;
        const minute = document.getElementById('minute').value;

        const fullDate = `${date} ${(ampm === '오후' && hour !== '12') ? parseInt(hour) + 12 : hour}:${minute}`;

        // API 호출
        fetch(`/api/meetings/${clubId}`, {
            method: 'POST',
            body: JSON.stringify({
                title: title,
                content: content,
                maxMembers: maxMembers,
                location: location,
                date: fullDate
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
                // 로직 처리 (예: 팝업 닫기)
                document.getElementById('meetingModal').style.display = "none";
            })
            .catch(error => console.error('Error:', error));
    });

};