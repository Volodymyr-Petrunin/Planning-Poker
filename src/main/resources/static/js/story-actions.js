document.addEventListener("DOMContentLoaded", function () {
    var stories = [];

    var socket = new SockJS('/ws');
    var stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {
        console.log('Connected to WebSocket');
    });

    // Create Story action!
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
            stompClient.send("/app/createStory", {}, JSON.stringify(payload));
            stories = [];
            document.getElementById('createdStories').innerHTML = '';
        }
    });

    // Update Story Action
    const updateStoryModal = new bootstrap.Modal(document.getElementById('updateStoryModal'));
    const updateStoryTitleInput = document.getElementById('updateStoryTitle');
    const updateStoryLinkInput = document.getElementById('updateStoryLink');
    const updateStoryButton = document.getElementById('update-story-btn');

    let selectedStoryId = null;

    document.addEventListener('click', function(event) {
        if (event.target && event.target.id === 'buttonUpdateStory') {
            selectedStoryId = event.target.getAttribute('data-story-id');

            const row = event.target.closest('tr');

            const title = row.querySelector('td:nth-child(1)').textContent;
            const storyLink = row.querySelector('td:nth-child(2) a').getAttribute('href');

            updateStoryTitleInput.value = title;
            updateStoryLinkInput.value = storyLink;

            updateStoryModal.show();
        }
    });

    updateStoryButton.addEventListener('click', function () {
        if (!selectedStoryId) return;

        const request = {
            storyId: selectedStoryId,
            requestStoryDto: {
                title: updateStoryTitleInput.value,
                storyLink: updateStoryLinkInput.value
            }
        };

        stompClient.send('/app/updateStory', {}, JSON.stringify(request));
        updateStoryModal.hide();
    });

    // Delete Story Action
    document.addEventListener('click', function(event) {
        if (event.target && event.target.id === 'buttonDeleteStory') {
            const storyId = event.target.getAttribute('data-story-id');
            const confirmText = document.getElementById('confirmDeleteStory').textContent;

            const confirmation = confirm(confirmText);
            if (confirmation) {
                stompClient.send('/app/deleteStory', {}, JSON.stringify({ storyId: storyId }));
            }
        }
    });
});
