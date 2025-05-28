async function fetchVotes() {
    try {
        const response = await fetch("api/allvote");
        const data = await response.json();

        const container = document.getElementById("voteContainer");
        container.innerHTML = "";

        data.forEach(vote => {
            const card = document.createElement("div");
            card.className = "vote-card";

            card.innerHTML = `
                <div class="vote-title">${vote.title}</div>
                <div class="vote-description">${vote.description}</div>
                <div class="vote-time">开始: ${vote.startTime}</div>
                <div class="vote-time">结束: ${vote.endTime}</div>
                <div class="vote-timeout">${vote.isTimeout ? "已超时" : "进行中"}</div>
            `;

            container.appendChild(card);
        });
    } catch (error) {
        console.error("获取投票信息失败:", error);
    }
}

function createVote() {
    window.location.href = "/create";
}

function searchVote() {
    const query = document.getElementById("searchBox").value;
    fetch("http://localhost:8888/home/api/search", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({keyword: query}) // 确保键名与后端匹配
    })
        .then(response => response.json())
        .then(data => {
            const container = document.getElementById("voteContainer");
            container.innerHTML = ""; // 清空原列表

            data.forEach(vote => {
                const card = document.createElement("div");
                card.className = "vote-card";

                card.innerHTML = `
                <div class="vote-title">${vote.title}</div>
                <div class="vote-description">${vote.description}</div>
                <div class="vote-time">开始: ${vote.startTime}</div>
                <div class="vote-time">结束: ${vote.endTime}</div>
                <div class="vote-timeout">${vote.isTimeout ? "已超时" : "进行中"}</div>
            `;
                container.appendChild(card);
            });
        })
        .catch(error => console.error("搜索失败:", error));
}


function userInfo() {
    alert("用户信息功能暂未实现，你可以跳转到相关页面！");
}

fetchVotes();
