document.getElementById("openModal").addEventListener("click", function () {
    document.getElementById("voteModal").style.display = "block";
});

document.querySelector(".close").addEventListener("click", function () {
    document.getElementById("voteModal").style.display = "none";
});

window.onclick = function (event) {
    if (event.target.classList.contains("modal")) {
        document.getElementById("voteModal").style.display = "none";
    }
};

document.getElementById("optionA").addEventListener("input", function () {
    document.getElementById("optionAValue").innerText = this.value;
});

document.getElementById("optionB").addEventListener("input", function () {
    document.getElementById("optionBValue").innerText = this.value;
});
