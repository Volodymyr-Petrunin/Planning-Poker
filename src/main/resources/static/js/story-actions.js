document.addEventListener("DOMContentLoaded", function () {
    var stories = [];

    var socket = new SockJS('/ws');
    var stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {
        console.log('Connected to WebSocket');
    });

    function addStoryToTable(story) {
        var storyRow = `
            <tr>
                <td>${story.title}</td>
                <td>${story.storyLink}</td>
                <td><button type="button" class="btn btn-danger remove-story-btn">Remove</button></td>
            </tr>
        `;
        document.getElementById('createdStories').insertAdjacentHTML('beforeend', storyRow);
    }

    document.getElementById('add-story-btn').addEventListener('click', function () {
        var storyTitle = document.getElementById('storyTitle').value;
        var storyLink = document.getElementById('storyLink').value;

        if (storyTitle && storyLink) {
            var story = { title: storyTitle, storyLink: storyLink };
            stories.push(story);
            addStoryToTable(story);

            document.getElementById('storyTitle').value = '';
            document.getElementById('storyLink').value = '';
        }
    });

    document.getElementById('createdStories').addEventListener('click', function (event) {
        if (event.target.classList.contains('remove-story-btn')) {
            var row = event.target.closest('tr');
            var title = row.cells[0].textContent;
            stories = stories.filter(story => story.title !== title);
            row.remove();
        }
    });

    document.getElementById('saveStoriesBtn').addEventListener('click', function () {
        var roomId = document.getElementById('roomId').value;
        if (stories.length > 0 && roomId) {
            var payload = { stories: stories, roomId: Number(roomId) };
            console.log(JSON.stringify(payload))
            stompClient.send("/app/createStory", {}, JSON.stringify(payload));
            stories = [];
            document.getElementById('createdStories').innerHTML = '';
        }
    });

});