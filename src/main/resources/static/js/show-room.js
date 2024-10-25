document.addEventListener("DOMContentLoaded", function() {
    var socket = new SockJS('/ws');
    var stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/currentStory', function(message) {
            var currentStory = JSON.parse(message.body);
            document.querySelector('#storyTitle').textContent = currentStory.title;
            document.querySelector('#storyLink').setAttribute('href', currentStory.storyLink);
        });

        stompClient.subscribe('/topic/room', function(message) {
            var room = JSON.parse(message.body);
            document.querySelector('#roomName').textContent = room.name;
            document.querySelector('#roomDetails').textContent = room.details;
        });

        stompClient.subscribe('/topic/storyCreated', function (message) {
            var storyCreated = JSON.parse(message.body);

            var storyList = document.getElementById('story-list');
            var isCreator = document.getElementById('isCreator').value === 'true';

            var newRow = document.createElement('tr');
            newRow.setAttribute('data-story-id', storyCreated.id);

            var actionButtons = '';
            if (isCreator) {
                actionButtons = `
            <button class="btn btn-warning me-2" data-story-id="${storyCreated.id}">Update</button>
            <button class="btn btn-danger" data-story-id="${storyCreated.id}">Delete</button>
        `;
            }

            newRow.innerHTML = `
        <td>${storyCreated.title}</td>
        <td><a href="${storyCreated.storyLink}" target="_blank">${storyCreated.storyLink}</a></td>
        <td>${actionButtons}</td>
    `;

            storyList.appendChild(newRow);
        });
    });
});
