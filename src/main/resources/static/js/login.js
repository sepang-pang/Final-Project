function login() {
    alert("버튼 클릭")
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    let userData = {
        username: username,
        password: password
    };

    let xhr = new XMLHttpRequest();
    xhr.open("POST", "/api/login", true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");


    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                // 서버에서 성공적으로 처리되었을 경우 동작
                alert("로그인이 완료되었습니다.");
                window.location.href = "/main"; //
            } else {
                // 서버에서 오류가 발생한 경우 동작
                alert("로그인 중 오류가 발생했습니다.");
            }
        }
    };
    xhr.send(JSON.stringify(userData));
}
