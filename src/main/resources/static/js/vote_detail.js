// document.getElementById("vote-button").addEventListener("click", function () {
//     document.getElementById("vote-modal").style.display = "block";
// });


let voteOptionId = undefined;//全局变量 当用户点击投票按钮时，获取当前选项的值
// document.getElementById("vote-button").addEventListener("click", function () {
//     voteOptionId = document.querySelector(".vote-option-item .vote-button").id; // 获取当前选项的ID
//     //让小窗显示
//     document.getElementById("vote-modal").style.display = "block";
// });

document.querySelector(".close").addEventListener("click", function () {
    document.getElementById("vote-modal").style.display = "none";
});

window.onclick = function (event) {
    if (event.target.classList.contains("modal")) {
        document.getElementById("vote-modal").style.display = "none";
    }
};


document.addEventListener("DOMContentLoaded", function () {
    /*
    private VoteDto voteDto;
    private UserDto creator;
    private List<VoteOptionDto> voteOptionDtoList;
    private List<TicketLimitDto> ticketLimitDtoList;
     */
    const path = window.location.pathname;
    const voteId = path.split('/').pop();
    fetch(`http://localhost:8888/vote-detail/api/vote=${voteId}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": 'Bearer ' + localStorage.getItem('token')
        }
    }).then(response => response.json())
        .then(data => {
            if (data.code === 200) {
                const vote = data.data.voteDto;
                const voteOptionList = data.data.voteOptionDtoList;
                const voteLimitList = data.data.ticketLimitDtoList;
                document.getElementById("vote-title").innerText = vote.title;
                document.getElementById("vote-description").innerText = vote.description;
                document.getElementById("vote-start-time").innerText = new Date(vote.startTime).toLocaleString();
                document.getElementById("vote-end-time").innerText = new Date(vote.endTime).toLocaleString();
                document.getElementById("vote-creator").innerText = data.data.creator.account;
                const optionsList = document.getElementById("vote-option-list");

                //排序 根据vote count
                voteOptionList.sort((a, b) => b.voteCount - a.voteCount);
                optionsList.innerHTML = ""; // 清空现有选项
                voteOptionList.forEach(option => {
                    const optionItem = document.createElement("li");
                    optionItem.className = "vote-option-item";
                    optionItem.id = `vote-option-item-${option.id}`; // 设置选项ID
                    optionItem.innerHTML = `
                        <p class = "vote-option-description">${option.description}</p>
                        <p class = "vote-count">已投票数:${option.voteCount}</p>
                        <button class ="vote-button" id="vote-button">投票！</button>
                    `;
                    optionsList.appendChild(optionItem);
                    const voteButton = optionItem.querySelector(".vote-button");
                    voteButton.addEventListener("click", function () {
                        voteOptionId = option.id; // 获取当前选项的ID
                        document.getElementById("vote-modal").style.display = "block";
                        if(localStorage.getItem('token') === null) {
                            alert("请先登录！");
                            document.getElementById("vote-modal").style.display = "none";
                        }
                    });
                });

                //生成投票小窗口

                if(localStorage.getItem("token") === null){
                    return;
                }

                voteLimitList.forEach(
                    ticket => {
                        const ticketItem = document.createElement("li");
                        ticketItem.className = "vote-limit-item-modal";
                        ticketItem.innerHTML = `
                            <p>${ticket.description}
                            剩余票数：<span id="${ticket.ticketId}remaining">${ticket.voteCount}</span></p>
                            <button class = "vote-limit-item-button-modal" id = "vote-limit-item-button-modal">投票</button>
                        `;
                        document.getElementById("vote-limit-list-modal").appendChild(ticketItem);
                        const voteButton = ticketItem.querySelector(".vote-limit-item-button-modal");
                        voteButton.addEventListener("click", () => {
                            //const voteLimitDtoList = getVoteLimitDtoList();
                            // const voteDto = getVoteDto();
                            // if (!voteDto) return; // 如果获取投票信息失败，则返回
                            fetch(`http://localhost:8888/vote-detail/api/vote/vote=${voteId}/vote-option=${voteOptionId}/ticket=${ticket.ticketId}`, {
                                method: "POST",
                                headers: {
                                    "Content-Type": "application/json",
                                    "Authorization": 'Bearer ' + localStorage.getItem('token')
                                }
                            }).then(response => response.json())
                                .then(data => {
                                    if (data.code === 200) {
                                        alert("投票成功！");
                                        //更新剩余票数
                                        const remainingSpan = document.getElementById(`${ticket.ticketId}remaining`);
                                        remainingSpan.innerText = (parseInt(remainingSpan.innerText, 10) - 1).toString();
                                        window.location.reload(); // 刷新页面以显示最新的投票结果
                                    } else {
                                        alert(data.message);
                                    }
                                });
                        });
                    }
                );

            } else {
                alert(data.message);
            }
        })
})

