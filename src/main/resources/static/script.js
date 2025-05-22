document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("userForm");
    const formTitle = document.getElementById("formTitle");
    const toggleForm = document.getElementById("toggleForm");

    let isLogin = true;

    toggleForm.addEventListener("click", () => {
        isLogin = !isLogin;
        formTitle.textContent = isLogin ? "登录" : "注册";
        toggleForm.innerHTML = isLogin ? "没有账号？<span>注册</span>" : "已有账号？<span>登录</span>";
    });

    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        const account = document.getElementById("account").value;
        const password = document.getElementById("password").value;

        const requestData = {
            account,
            password,
            role: 0
        };

        const endpoint = isLogin ? "/login" : "/register";

        try {
            const response = await fetch(endpoint, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(requestData)
            });

            const result = await response.json();
            alert(result.message || (isLogin ? "登录成功！" : "注册成功！"));
        } catch (error) {
            alert("请求失败，请检查网络连接！");
        }
    });
});
