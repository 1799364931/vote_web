// script.js
document.getElementById('toggle-form').addEventListener('click', function(event) {
    event.preventDefault();
    const title = document.getElementById('form-title');
    const submitBtn = document.getElementById('submitBtn');
    const toggleText = document.getElementById('toggle-form');
    const error_message = document.getElementById("error-message");
    if (title.innerText === "登录") {
        title.innerText = "注册";
        submitBtn.innerText = "注册";
        toggleText.innerHTML = '已有账号？<a href="#">登录</a>';
        error_message.innerText = ""; // 清除错误信息
    } else {
        title.innerText = "登录";
        submitBtn.innerText = "登录";
        toggleText.innerHTML = '没有账号？<a href="#">注册</a>';
        error_message.innerText = ""; // 清除错误信息

    }
});

document.getElementById("submitBtn").addEventListener("click", function(event) {
    event.preventDefault(); // 阻止默认提交行为
    const error_message = document.getElementById("error-message");
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    if(document.getElementById('form-title').innerHTML === "登录"){
        fetch("http://localhost:8888/login/api/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({account: username, password: password, role: 1, token: null})
        })
            .then(response => response.json())
            .then(data => {
                if(data.code === 200) {
                    localStorage.setItem("token", data.data.token); // 存储 Token
                    window.location.href = "/home";
                }
                else{
                    error_message.innerText = data.message;
                }
            })
            .catch(error => console.error("提交失败:", error));
    }
    else{
        fetch("http://localhost:8888/register/api/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({account: username, password: password, role: 1, token: null})
        })
            .then(response => response.json())
            .then(data => {
                if(data.code === 200){
                    alert("注册成功：");
                }
                else{
                    error_message.innerText = data.message;
                }
            })
            .catch(error => console.error("提交失败:", error));
    }
});
