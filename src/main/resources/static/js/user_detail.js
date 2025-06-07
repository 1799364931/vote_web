document.addEventListener("DOMContentLoaded", function() {

    fetch("http://localhost:8888/vote-log/api/get-vote-log",{
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": 'Bearer ' + localStorage.getItem('token')
        }
    }).then(response=> response.json())
        .then(data => {
            /**
             *     private UUID voteId;
             *     private String voteTitle;
             *     private VoteOptionDto voteOption;
             *     private TicketDto ticket;
             *     private Timestamp voteTime;
             */
            if (data.code === 200) {
                const voteLogList = data.data;
                const voteLogTable = document.getElementById("vote-record-table-body");
                //排序 根据时间从现在到过去
                voteLogList.sort((a, b) => new Date(b.voteTime) - new Date(a.voteTime));
                voteLogList.forEach(log => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                        <td>${log.voteTitle}</td>
                        <td>${log.voteOption.description}</td>
                        <td>${log.ticket.description}</td>
                        <td>${new Date(log.voteTime).toLocaleString()}</td>
                    `;
                    voteLogTable.appendChild(row);

                    row.addEventListener("click", function() {
                        window.location.href = `http://localhost:8888/vote/${log.voteId}`;
                    });
                });
            } else {
                alert(data.message);
            }
        })
})
