let userNameInput = document.getElementById("userName");
let userNameFeedback = document.getElementById("userNameFeedback");
let userNameRules = document.getElementById("userNameRules");

userNameInput.addEventListener("input", function () {
    let isValid = /^[a-z0-9]{4,10}$/.test(userNameInput.value);
    if (!isValid) {
        userNameFeedback.innerHTML = "양식에 맞지않습니다.";
    } else {
        userNameFeedback.innerHTML = "";
    }
});

let passwordInput = document.getElementById("password");
let passwordFeedback = document.getElementById("passwordFeedback");
passwordInput.addEventListener("input", function () {
    let isValid = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,15}$/.test(passwordInput.value);
    if (!isValid) {
        passwordFeedback.innerHTML = "양식에 맞지않습니다.";
    } else {
        passwordFeedback.innerHTML = "";
    }
});

function signup() {
    alert("버튼 클릭")
    let userName = document.getElementById("userName").value;
    let password = document.getElementById("password").value;
    let password_confirm = document.getElementById("password_confirm").value;
    let email = document.getElementById("email").value;
    let birth = document.getElementById("birth").value;
    let role = document.getElementById("role").value;

    if (password !== password_confirm) {
        alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        return;
    }

    // 생년월일을 가져와서 JavaScript Date 객체로 변환합니다.
    let birthDate = new Date(birth);

    // 현재 날짜를 가져와서 JavaScript Date 객체로 변환합니다.
    let currentDate = new Date();

    // 생년월일과 현재 날짜 간의 차이를 계산합니다.
    let ageDiff = currentDate.getFullYear() - birthDate.getFullYear();

    // 생일이 아직 지나지 않았다면 1을 빼서 정확한 나이를 계산합니다.
    if (currentDate.getMonth() < birthDate.getMonth() || (currentDate.getMonth() === birthDate.getMonth() && currentDate.getDate() < birthDate.getDate())) {
        ageDiff--;
    }

    let userData = {
        username: userName,
        password: password,
        email: email,
        birth: birth,
        age: ageDiff,
        role: role
    };

    // 만 19세 미만의 경우 회원 가입을 막습니다.
    if (ageDiff >= 19) {
        let xhr = new XMLHttpRequest();
        xhr.open("POST", "/signup", true);
        xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    // 서버에서 성공적으로 처리되었을 경우 동작
                    alert("회원 가입이 완료되었습니다.");
                    window.location.href = "/login"; // 로그인 페이지로 이동
                } else {
                    // 서버에서 오류가 발생한 경우 동작
                    alert("회원 가입 중 오류가 발생했습니다.");
                }
            }
        };

        xhr.send(JSON.stringify(userData));
    }
}
