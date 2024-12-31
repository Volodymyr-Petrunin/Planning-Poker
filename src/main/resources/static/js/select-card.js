import {addSubscription, connectWebSocket, sendMessage} from './websocket-client.js';

connectWebSocket();

const noStorySelectedText = document.getElementById('noStorySelectedText').textContent;
const isAnonymousVoting = document.getElementById('isAnonymousVoting').value === 'true';
const eventId = document.getElementById('eventId').value;
const roomCode = document.getElementById('roomCode').value;
const newVoteTopic = `/topic/newVote/` + roomCode;

addSubscription(newVoteTopic, function (result) {
    const voteData = JSON.parse(result.body);

    const storyRow = document.querySelector(`tr[data-story-id="${voteData.storyId}"]`);
    if (storyRow) {
        const votesCell = storyRow.querySelector('td[data-votes]');
        if (votesCell) {
            const currentVotes = JSON.parse(votesCell.getAttribute('data-votes')) || [];

            const existingVoteIndex = currentVotes.findIndex(v => v.voter.id === voteData.voter.id);
            if (existingVoteIndex !== -1) {
                currentVotes[existingVoteIndex] = voteData;
            } else {
                currentVotes.push(voteData);
            }

            votesCell.setAttribute('data-votes', JSON.stringify(currentVotes));

            const averageVoteCell = storyRow.querySelector('.average-vote');
            if (averageVoteCell) {
                const newAverage = calculateAverageVotePoints(currentVotes);
                averageVoteCell.textContent = newAverage ?? '&#11834;';
            }
        }
    }
});

function calculateAverageVotePoints(votes) {
    if (!votes || votes.length === 0) {
        return null;
    }

    const totalPoints = votes.reduce((sum, vote) => sum + vote.points, 0);
    return Math.floor(totalPoints / votes.length);
}


window.selectCard = function (points) {
    document.getElementById('selected-points').value = points;

    let cards = document.querySelectorAll('.card');
    cards.forEach(card => card.classList.remove('active'));
    cards.forEach(card => card.classList.remove('border-2'));
    cards.forEach(card => card.classList.remove('border-primary'));

    event.target.closest('.card').classList.add('active');
    event.target.closest('.card').classList.add('border');
    event.target.closest('.card').classList.add('border-2');
    event.target.closest('.card').classList.add('border-primary');
}

function generateFibonacciCards() {
    let fibonacciSequence = [0, 1];

    for (let i = 2; i <= 11; i++) {
        let nextNumber = fibonacciSequence[i - 1] + fibonacciSequence[i - 2];
        fibonacciSequence.push(nextNumber);
    }

    let uniqueFibonacciSequence = [...new Set(fibonacciSequence)];

    let container = document.getElementById('cards-container');
    container.innerHTML = '';

    uniqueFibonacciSequence.forEach(number => {
        let card = `
            <div class="col">
                <button type="button" class="card btn btn-light mb-3" data-points="${number}" onclick="selectCard(${number})">
                    <div class="card-body">
                       <b>Estimate</b>
                       <h1>${number}</h1>
                       <small>Planning Poker</small>
                    </div>
                </button>
            </div>
        `;
        container.innerHTML += card;
    });

    let moreTimeCard = `
    <div class="col">
        <button type="button" class="card btn btn-light mb-3">
            <div class="card-body">
                <b>Estimate</b>
                <h1>⏳</h1>
                <small>Planning Poker</small>
            </div>
        </button>
    </div>
    `;

    let blockCard = `
    <div class="col">
        <button type="button" class="card btn btn-light mb-3">
            <div class="card-body">
                <b>Estimate</b>
                <h1>⛔</h1>
                <small>Planning Poker</small>
            </div>
        </button>
    </div>
`;

    container.innerHTML += moreTimeCard;
    container.innerHTML += blockCard;
}

window.onload = function() {
    generateFibonacciCards();
}

function sendVote() {
    var points = document.getElementById('selected-points').value;
    var storyId = document.getElementById('story-id').value;
    var storyTitle = document.getElementById('story-title').value;
    var storyLink = document.getElementById('story-storyLink').value;

    if (points && storyId) {
        sendMessage("/app/sendVote", {
            points: points,
            story: { id: storyId, title: storyTitle, storyLink: storyLink },
            roomCode: roomCode,
            isAnonymousVoting: isAnonymousVoting,
            eventId: eventId
        });
    } else {
        alert(noStorySelectedText)
    }
}

$(document).ready(function () {
    $('#submit-estimate').on('click', function () {
        sendVote();
    });
});
