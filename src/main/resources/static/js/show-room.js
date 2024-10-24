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
    });
});
