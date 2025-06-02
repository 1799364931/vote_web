document.getElementById("add-vote-option-btn").addEventListener("click", function () {
    const optionsList = document.getElementById("vote-option-list");
    const optionDiv = document.createElement("li");
    optionDiv.className = "vote-option-node";


    optionDiv.innerHTML = `
      <input type="text" class="vote-option-input" placeholder="请输入选项内容"/>
      <button class = "remove-option-btn"> 删除</button>
    `;
    // 将新创建的选项添加到选项容器中
    optionsList.appendChild(optionDiv);

    // 添加删除按钮的事件监听器
    optionDiv.querySelector(".remove-option-btn").addEventListener("click", function () {
        optionsList.removeChild(optionDiv);
    });
});

document.addEventListener("input", function (event) {
    if (event.target.matches('input[type="range"]')) {
        let span = document.getElementById(event.target.id + "value");
        if (span) {
            span.textContent = event.target.value;
        }
    }
});

document.addEventListener("DOMContentLoaded", function () {
    fetch("http://localhost:8888/ticket/api/get-all-tickets")
        .then(response => response.json())
        .then(data => {
            //生成ticket信息
            const voteLimitList = document.getElementById("vote-limit-list");
            if (data.code !== 200) {
                console.error("获取票务信息失败:", data.message);
            } else {
                data.data.forEach(ticket => {
                    const ticketItem = document.createElement("li");
                    ticketItem.className = "vote-limit-item";
                    ticketItem.innerHTML = `
                          <p class = "ticket-description">${ticket.description} 选择可投票数：</p>
                          <label for="${ticket.ticketId}"></label>
                          <input type="range" id="${ticket.ticketId}" min="0" max="10" value="1">
                          <span id="${ticket.ticketId}value">1</span>
                    `;
                    voteLimitList.appendChild(ticketItem);

                });
            }
        })
})

function getVoteLimitDtoList() {
    const voteLimitList = document.getElementById("vote-limit-list");
    const ticketLimitDtoList = [];
    const ticketInputs = voteLimitList.querySelectorAll("input[type='range']");
    ticketInputs.forEach(input => {
        const ticketId = input.id;
        const description = input.parentElement.querySelector("p").innerText;
        const voteCount = parseInt(input.value, 10);
        ticketLimitDtoList.push({
            ticketId: ticketId,
            description: description,
            voteCount: voteCount
        });
    });
    return ticketLimitDtoList;
}

function getVoteDto() {
    let voteTitle = document.getElementById("vote-title-input").value;
    let voteDescription = document.getElementById("vote-description-input").value;
    let startTime = document.getElementById("vote-start-time").value + ":00";
    let endTime = document.getElementById("vote-end-time").value + ":00";
    let isTimeout = false;

    if (new Date(endTime) < new Date()) {
        //如果结束时间早于当前时间，则返回错误
        alert("结束时间不能早于当前时间！");
        return;
    }

    return {
        title: voteTitle,
        description: voteDescription,
        startTime: startTime,
        endTime: endTime,
        isTimeout: isTimeout,
        id:null
    };
}

function getOptionalList() {
    const options = [];
    const optionInputs = document.querySelectorAll(".vote-option-input");
    optionInputs.forEach(input => {
        if (input.value.trim() !== "") { // 确保选项不为空
            options.push({
                id:null,
                description: input.value.trim(),
                position: options.length + 1, // 选项位置
                voteCount: 0 // 初始投票数为0
            });
        }
    });
    return options;
}

document.getElementById("submit-vote-btn").addEventListener("click", function (event) {

    /* <!--

    Header Content-Type: authorization: Bearer {token}
     {
         optionalList:[
             {
                 description: "选项描述",
                 position: "选项位置",
                 voteCount: 0,
             }
             ....
         ]

         voteDto:{
             title: "投票标题",
             description: "投票描述",
             startTime: "2023-10-01T00:00:00",
             endTime: "2023-10-31T23:59:59",
             isTimeout: false,
         }

         ticketLimitDtoList:[
             {
                 ticketId: "票务ID",
                 description: "票务描述"
                 voteCount: 1 // 选择的可投票数
             }
         ]
     }

     -->*/
    event.preventDefault(); // 阻止默认提交行为
    const token = localStorage.getItem("token");
    if (!token) {
        alert("请先登录！");
        window.location.href = "/login";
    }
    const voteDto = getVoteDto();
    const optionalList = getOptionalList();
    const ticketLimitDtoList = getVoteLimitDtoList();
    if (voteDto.title.trim() === "" || voteDto.description.trim() === "" || optionalList.length === 0) {
        alert("请填写完整的投票信息和选项！");
        return;
    }

    fetch("http://localhost:8888/vote/api/create", {
        method: "POST",
        headers: {
            "authorization": `Bearer${localStorage.getItem("token")}`,
            "Content-Type": "application/json; charset=UTF-8",
        },
        body: JSON.stringify({
            voteOptionDtoList: optionalList,
            voteDto: voteDto,
            ticketLimitDtoList: ticketLimitDtoList
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.code === 200) {
                alert("投票创建成功！");
                alert(data.data);
                window.location.href = "/vote/" + data.data; // 跳转到新创建的投票详情页
            } else {
                alert("创建投票失败: " + data.message);
            }
        })
        .catch(error => {
            console.error("创建投票时发生错误:", error);
            alert("创建投票失败，请稍后再试。");
        })
});


