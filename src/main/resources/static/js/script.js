// script.js
document.getElementById('toggle-form').addEventListener('click', function(event) {
    event.preventDefault();
    const title = document.getElementById('form-title');
    const submitBtn = document.getElementById('submitBtn');
    const toggleText = document.getElementById('toggle-form');

    if (title.innerText === "登录") {
        title.innerText = "注册";
        submitBtn.innerText = "注册";
        toggleText.innerHTML = '已有账号？<a href="#">登录</a>';
    } else {
        title.innerText = "登录";
        submitBtn.innerText = "登录";
        toggleText.innerHTML = '没有账号？<a href="#">注册</a>';
    }
});

document.getElementById("submitBtn").addEventListener("click", function(event) {
    event.preventDefault(); // 阻止默认提交行为

    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    if(document.getElementById('form-title').innerHTML === "登录"){
        fetch("http://localhost:8888/login/dologin", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ account:username, password:password,role:1 })
        })
            .then(response => response.json())
            .then(data => {
                if(data.code === 200){
                    alert("登录成功：");
                }
                else{
                    alert(data.message);
                }
            })
            .catch(error => console.error("提交失败:", error));
    }
    else{
        fetch("http://localhost:8888/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ account:username, password:password,role:1 })
        })
            .then(response => response.json())
            .then(data => {
                if(data.code === 200){
                    alert("注册成功：");
                }
                else{
                    alert(data.message);
                }
            })
            .catch(error => console.error("提交失败:", error));
    }
});
