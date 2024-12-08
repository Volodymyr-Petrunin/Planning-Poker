import {addSubscription, connectWebSocket, sendMessage} from './websocket-client.js';

connectWebSocket();

const roomCode = document.getElementById('roomCode').value;
const roomId = document.getElementById('roomId').value;
const votingActionsTopic = `/topic/updateVoting/` + roomCode;

const votingInProgress = document.getElementById('votingInProgress').textContent;
const votingOver = document.getElementById('votingOver').textContent;
const timeUntilEnd = document.getElementById('timeUntilEnd').textContent;

const startVotingBtn = document.getElementById('start-voting');
const isCreator = document.getElementById('isCreator').value === 'true';

addSubscription(votingActionsTopic, function (result) {
    const roomData = JSON.parse(result.body);

    if (roomData.isVotingOpen) {
        startVotingTimer(roomData.votingEndTime);
    } else {
        const container = document.getElementById('timerContainer');
        container.innerHTML = `
            <div class="text-light mt-3">
                <h3>${votingOver}</h3>
            </div>
        `;
        container.appendChild(startVotingBtn);
    }
});

function startVotingTimer(votingEndTime) {
    const container = document.getElementById('timerContainer');

    container.innerHTML = `
        <div class="text-light mt-3">
            <h1>${votingInProgress}</h1>
            <p id="countdown"></p>
        </div>
    `;

    if (isCreator) {
        const endVotingBtn = document.createElement('button');
        endVotingBtn.id = 'end-voting';
        endVotingBtn.className = 'btn btn-danger mt-3';
        endVotingBtn.innerText = 'End Voting';
        endVotingBtn.addEventListener('click', function () {
            sendMessage(`/app/votingAction`, {roomId: roomId, isVotingOpen: false});
        });

        container.appendChild(endVotingBtn);
    }

    const endTime = moment(votingEndTime);
    const countdownElement = document.getElementById('countdown');

    const interval = setInterval(() => {
        const diff = moment.duration(endTime.diff(moment()));
        if (diff.asSeconds() <= 0) {
            clearInterval(interval);
            countdownElement.innerText = votingOver;
            const endVotingBtn = document.getElementById('end-voting');
            if (endVotingBtn) endVotingBtn.remove();
            container.appendChild(startVotingBtn);
        } else {
            countdownElement.innerText = `${timeUntilEnd} ${diff.minutes()}m ${diff.seconds()}s`;
        }
    }, 1000);
}

window.addEventListener('DOMContentLoaded', function () {
    const votingEndTime = document.getElementById('votingEndTime').textContent;

    if (votingEndTime) {
        startVotingTimer(votingEndTime);
    }
});

window.addEventListener('DOMContentLoaded', function () {
    if (startVotingBtn) {
        startVotingBtn.addEventListener('click', function () {
            sendMessage(`/app/votingAction`, {roomId: roomId, isVotingOpen: true});
        });
    }
});
