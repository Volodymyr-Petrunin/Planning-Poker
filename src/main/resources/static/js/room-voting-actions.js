import {addSubscription, connectWebSocket, sendMessage} from './websocket-client.js';

connectWebSocket();

const roomCode = document.getElementById('roomCode').value;
const roomId = document.getElementById('roomId').value;
const votingActionsTopic = `/topic/updateVoting/` + roomCode;
const votingInProgress = document.getElementById('votingInProgress').textContent;
const votingOver = document.getElementById('votingOver').textContent;
const timeUntilEnd = document.getElementById('timeUntilEnd').textContent;

addSubscription(votingActionsTopic, function (result) {
    const isVotingOpen = JSON.parse(result.body);

    if (isVotingOpen) {
        startVotingTimer();
    }
});

document.getElementById('start-voting').addEventListener('click', function () {
    sendMessage(`/app/votingAction`, {roomId: roomId, isVotingOpen: true});
});

function startVotingTimer() {
    const voteDuration = document.getElementById('voteDuration').value;
    const container = document.getElementById('timerContainer');

    container.innerHTML = `
        <div class="text-light mt-3">
            <h1>${votingInProgress}</h1>
            <p id="countdown"></p>
        </div>
    `;

    const endTime = moment().add(voteDuration, 'minutes');
    const countdownElement = document.getElementById('countdown');

    const interval = setInterval(() => {
        const diff = moment.duration(endTime.diff(moment()));
        if (diff.asSeconds() <= 0) {
            clearInterval(interval);
            countdownElement.innerText = votingOver;
        } else {
            countdownElement.innerText =`${timeUntilEnd} ${diff.minutes()}m ${diff.seconds()}s`;
        }
    }, 1000);
}

