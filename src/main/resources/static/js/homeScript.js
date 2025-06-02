
document.addEventListener("DOMContentLoaded", function() {
        fetch("http://localhost:8888/vote/api/all-vote", {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        })
        .then(response => response.json())
        .then(data => {
            if (data.code !== 200) {
                throw new Error("获取投票列表失败：" + data.message);
            } else {
                generateVoteLink(data.data);
                // const voteList = document.getElementById("vote-list");
                // voteList.innerHTML = "";
                // data.data.forEach(vote => {
                //     const voteItem = document.createElement("li");
                //     voteItem.className = "vote-item";
                //     voteItem.innerHTML = `
                //         <a href="/vote/${vote.id}" class="vote-item-link" id="vote-item-link">
                //             <p class="vote-item-title" id="vote-item-title">${vote.title}</p>
                //             <p class="vote-item-description" id="vote-item-description">${vote.description}</p>
                //             <p class="vote-item-time">开始时间: ${new Date(vote.startTime).toLocaleString()}</p>
                //             <p class="vote-item-time">结束时间: ${new Date(vote.endTime).toLocaleString()}</p>
                //             ${
                //                 vote.isTimeout
                //                     ? `<p class="vote-item-timeout">已超时</p>`
                //                     : new Date(vote.startTime) > new Date()
                //                         ? `<p class="vote-item-timeout">未开始</p>`
                //                         : `<p class="vote-item-timeout">进行中</p>`
                //             }
                //
                //         </a>`;
                //     // 如果未开始 或者已经超时 则设置背景透明度为50
                //     if (new Date(vote.startTime) > new Date() || vote.isTimeout) {
                //         voteItem.style.opacity = "0.5";
                //     }
                //     if(vote.isTimeout){
                //         voteItem.style.backgroundColor = "#f8d7da"; // 设置超时投票的背景颜色
                //     }
                //     voteList.appendChild(voteItem);
                // });
            }

        })
})


function searchVote() {
    alert("test");
    const query = document.getElementById("search-input").value;
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
            // const voteList = document.getElementById("vote-list");
            // voteList.innerHTML = "";
            // data.data.forEach(vote => {
            //     const voteItem = document.createElement("li");
            //     voteItem.className = "vote-item";
            //     voteItem.innerHTML = `
            //             <a href="/vote/${vote.id}" class="vote-item-link" id="vote-item-link">
            //                 <p class="vote-item-title" id="vote-item-title">${vote.title}</p>
            //                 <p class="vote-item-description" id="vote-item-description">${vote.description}</p>
            //                 <p class="vote-item-time">开始时间: ${new Date(vote.startTime).toLocaleString()}</p>
            //                 <p class="vote-item-time">结束时间: ${new Date(vote.endTime).toLocaleString()}</p>
            //                 ${
            //         vote.isTimeout
            //             ? `<p class="vote-item-timeout">已超时</p>`
            //             : new Date(vote.startTime) > new Date()
            //                 ? `<p class="vote-item-timeout">未开始</p>`
            //                 : `<p class="vote-item-timeout">进行中</p>`
            //     }
            //
            //             </a>`;
            //     // 如果未开始 或者已经超时 则设置背景透明度为50
            //     if (new Date(vote.startTime) > new Date() || vote.isTimeout) {
            //         voteItem.style.opacity = "0.5";
            //     }
            //     if(vote.isTimeout){
            //         voteItem.style.backgroundColor = "#f8d7da"; // 设置超时投票的背景颜色
            //     }
            //     voteList.appendChild(voteItem);
            //});
        })
        .catch(error => console.error("搜索失败:", error));
}


function generateVoteLink(data) {
    const voteList = document.getElementById("vote-list");
    voteList.innerHTML = "";
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
            vote.isTimeout
                ? `<p class="vote-item-timeout">已超时</p>`
                : new Date(vote.startTime) > new Date()
                    ? `<p class="vote-item-timeout">未开始</p>`
                    : `<p class="vote-item-timeout">进行中</p>`
        }
                            
                        </a>`;
        // 如果未开始 或者已经超时 则设置背景透明度为50
        if (new Date(vote.startTime) > new Date() || vote.isTimeout) {
            voteItem.style.opacity = "0.5";
        }
        if (vote.isTimeout) {
            voteItem.style.backgroundColor = "#f8d7da"; // 设置超时投票的背景颜色
        }
        voteList.appendChild(voteItem);
    })
}
