var newPasswordInput = document.getElementById("new-password");
var usernameInput = document.getElementById("username");
var idErrorMessage = document.getElementById("id-error-message");

// 비밀번호 ID 포함 불가
newPasswordInput.addEventListener("input", function () {
    var newPassword = newPasswordInput.value;
    var currentUsername = usernameInput.value;

    // ID 포함 여부 검사
    if (newPassword.includes(currentUsername)) {
        idErrorMessage.textContent = "ID를 포함할 수 없습니다.";
    } else {
        idErrorMessage.textContent = "";
    }
});

// 뒤로 버튼 클릭
function back() {
    window.history.back();
}

var btnSave = document.getElementById('btn-save');
btnSave.addEventListener('click', changePassword);
// 확인 버튼 클릭
function changePassword() {
    var currentPassword = document.getElementById("current-password").value;
    var newPassword = document.getElementById("new-password").value;
    var checkPassword = document.getElementById("check-password").value;

    // 비밀번호 확인
    if (newPassword !== checkPassword) {
        alert("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        return;
    }

    let passwordDto = {
        currentPassword: currentPassword,
        newPassword: newPassword,
        checkPassword: checkPassword
    };

    fetch('/api/users/password', {
        method: 'PATCH',
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(passwordDto)
    })
        .then(function (response) {
            if (response.ok) {
                alert("비밀번호가 변경되었습니다.");
                window.location.href = "/main";
            } else {
                // 서버에서 예외 메시지를 반환하면 이 부분에서 처리할 수 있습니다.
                response.json().then(function (data) {
                    if (data.message) {
                        alert("오류: " + data.message);
                    } else {
                        alert("비밀번호 변경에 실패했습니다.");
                    }
                });
            }
        })
        .catch(function (error) {
            console.error('Error:', error);
            alert(error);
        });
}