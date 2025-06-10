function searchVote() {
    const query = document.getElementById("search-input").value;
    window.location.href= `http://localhost:8888/vote/search/${query}`;
}

function logout(){
    if(!confirm("确定要退出登录吗？")){
        return;
    }
    fetch("http://localhost:8888/login/api/logout", {
        method: "POST",
        headers:{
            "Content-Type": "application/json",
            "authorization": "Bearer " + localStorage.getItem("token"),
        }
    }).then(response => response.json())
        .then(data => {
            if(data.code === 200){
                localStorage.removeItem("token");
                window.location.href = "http://localhost:8888/login";
            }
            else{
                alert("退出登录失败: " + data.message);
            }
        })
}

document.addEventListener("DOMContentLoaded", function() {
    const token = localStorage.getItem("token");
    fetch("http://localhost:8888/user", {
        method: "GET",
        headers:{
            "Content-Type": "application/json",
            "authorization": "Bearer " + token,
        }
    }).then(response => response.json()
    ).then(data => {
        if (data.code === 200) {
            const userInfo = data.data;
            document.getElementById("username").textContent = userInfo.account;
        } else {
            console.error("获取用户信息失败:", data.message);
        }
    }).catch(error => {
        console.error("请求错误:", error);
    });
})