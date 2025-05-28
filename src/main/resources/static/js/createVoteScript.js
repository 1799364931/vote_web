document.getElementById("addOption").addEventListener("click", function () {
    const container = document.getElementById("optionsContainer");
    const optionDiv = document.createElement("div");
    optionDiv.className = "option";

    optionDiv.innerHTML = `
        <input type="text" placeholder="选项内容">
        <button onclick="removeOption(this)">删除</button>
    `;

    container.appendChild(optionDiv);
});

function removeOption(button) {
    button.parentElement.remove();
}

document.getElementById("voteLimit").addEventListener("input", function () {
    document.getElementById("voteLimitValue").textContent = this.value;
});


document.getElementById("submitVote").addEventListener("click", function () {
    const title = document.getElementById("title").value;
    const description = document.getElementById("description").value;
    const options = Array.from(document.querySelectorAll("#optionsContainer input")).map(input => input.value);

    if (!title || !description || options.length === 0) {
        alert("请填写完整信息！");
        return;
    }

    const voteData = {
        title,
        description,
        options
    };

    console.log("投票数据:", voteData); // 这里可以替换为 API 调用
    alert("投票已提交！");
});
