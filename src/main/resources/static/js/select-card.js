var stompClient = null;

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/voteResult', function (result) {
            showUserResults(JSON.parse(result.body));
        });
    });
}

function selectCard(points) {
    document.getElementById('selected-points').value = points;

    let cards = document.querySelectorAll('.card');
    cards.forEach(card => card.classList.remove('active'));
    cards.forEach(card => card.classList.remove('border-2'));
    cards.forEach(card => card.classList.remove('border-primary'));

    event.target.closest('.card').classList.add('active');
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

    console.log('POINTS', points);
    console.log(storyId, storyTitle, storyLink);

    if (points && storyId) {
        var vote = {
            points: points,
            story: {
                id: storyId,
                title: storyTitle,
                storyLink: storyLink
            }
        };

        console.log('VOTE', vote)

        stompClient.send("/app/sendVote", {}, JSON.stringify(vote));
    }
}

$(document).ready(function () {
    connect();

    $('#submit-estimate').on('click', function () {
        sendVote();
    });
});
