function generateVoteLink(data) {
    const voteList = document.getElementById("vote-list");
    voteList.innerHTML = "";

    if(data.length === 0) {
        const noVoteItem = document.createElement("li");
        noVoteItem.className = "vote-item";
        noVoteItem.innerHTML = `<p class="vote-item-title">暂无投票</p>`;
        voteList.appendChild(noVoteItem);
        return;
    }
    //未超时 =>未开始 =>已超时 的排列顺序
    data.sort((a, b) => {
        if (a.timeout && !b.timeout) return 1; // a 超时，b 未超时
        if (!a.timeout && b.timeout) return -1; // a 未超时，b 超时
        if (new Date(a.startTime) > new Date(b.startTime)) return 1; // a 未开始，b 已开始
        if (new Date(a.startTime) < new Date(b.startTime)) return -1; // a 已开始，b 未开始
        return 0; // 相同状态
    });
    data.forEach(vote => {
        const voteItem = document.createElement("li");
        voteItem.className = "vote-item";
        voteItem.innerHTML = `
                        <a href="/vote/${vote.id}" class="vote-item-link" id="vote-item-link">
                            <p class="vote-item-title" id="vote-item-title">${vote.title}</p>
                            <p class="vote-item-description" id="vote-item-description">${vote.description}</p>
                            <p class="vote-item-time">开始时间: ${new Date(vote.startTime).toLocaleString()}</p>
                            <p class="vote-item-time">结束时间: ${new Date(vote.endTime).toLocaleString()}</p>
                            ${
            vote.timeout
                ? `<p class="vote-item-timeout">已超时</p>`
                : new Date(vote.startTime) > new Date()
                    ? `<p class="vote-item-timeout">未开始</p>`
                    : `<p class="vote-item-timeout">进行中</p>`
        }
                            
                        </a>`;
        // 如果未开始 或者已经超时 则设置背景透明度为50
        if (new Date(vote.startTime) > new Date() || vote.timeout) {
            voteItem.style.opacity = "0.5";
        }
        if (vote.timeout) {
            voteItem.style.backgroundColor = "#f8d7da"; // 设置超时投票的背景颜色
        }
        voteList.appendChild(voteItem);
    })
}


document.addEventListener("DOMContentLoaded", function() {
    const path = window.location.pathname;
    const query = path.split("/").pop();
    fetch(`http://localhost:8888/vote/api/search/keyword=${query}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({keyword: query}) // 确保键名与后端匹配
    })
        .then(response => response.json())
        .then(data => {
            if(data.code === 200){
                generateVoteLink(data.data);
            }
            else{
                alert(data.message);
            }
        })
        .catch(error => console.error("搜索失败:", error));
})





