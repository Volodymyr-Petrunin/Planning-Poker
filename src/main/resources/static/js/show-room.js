document.addEventListener("DOMContentLoaded", function() {
    var socket = new SockJS('/ws');
    var stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/updateCurrentStory', function (message) {
            const room = JSON.parse(message.body);
            const currentStorySection = document.querySelector('.text-light.ms-4.mb-4');

            const currentStoryText = document.getElementById('currentStoryText').textContent;
            const noStorySelectedText = document.getElementById('noStorySelectedText').textContent;
            const readStoryText = document.getElementById('readStoryText').textContent;

            if (room.currentStory) {
                currentStorySection.innerHTML = `
            <h3>${currentStoryText} ${room.currentStory.title}</h3>
            <a href="${room.currentStory.storyLink}" class="text-info" target="_blank">${readStoryText}</a>
        `;
            } else {
                currentStorySection.innerHTML = `
            <h3>${noStorySelectedText}</h3>
        `;
            }

            currentStorySection.classList.add('fade-in');

            setTimeout(() => {
                currentStorySection.classList.remove('fade-in');
            }, 500)
        });


        stompClient.subscribe('/topic/roomUpdated', function(message) {
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
            <button class="btn btn-warning me-2" id="buttonUpdateStory" data-story-id="${storyCreated.id}">Update</button>
            <button class="btn btn-danger me-2" data-story-id="${storyCreated.id}">Delete</button>
            <button class="btn btn-success me-2" id="buttonSetCurrentStory" data-story-id="${storyCreated.id}">Set to current</button>
        `;
            }

            newRow.innerHTML = `
        <td>${storyCreated.title}</td>
        <td><a href="${storyCreated.storyLink}" target="_blank">${storyCreated.storyLink}</a></td>
        <td>${actionButtons}</td>
    `;

            storyList.appendChild(newRow);
        });

        stompClient.subscribe('/topic/storyUpdated', function (message) {
            const updatedStory = JSON.parse(message.body);
            const storyRow = document.querySelector(`tr[data-story-id="${updatedStory.id}"]`);

            if (storyRow) {
                storyRow.querySelector('td:nth-child(1)').textContent = updatedStory.title;
                storyRow.querySelector('td:nth-child(2) a').textContent = updatedStory.storyLink;
                storyRow.querySelector('td:nth-child(2) a').setAttribute('href', updatedStory.storyLink);

                storyRow.classList.add('fade-in');
                setTimeout(() => storyRow.classList.remove('fade-in'), 500);
            }
        });
    });
});

document.addEventListener('click', function(event) {
    if (event.target && event.target.id === 'buttonSetCurrentStory') {
        const storyId = event.target.getAttribute('data-story-id');
        var roomId = document.getElementById('roomId').value;

        const row = event.target.closest('tr');

        const title = row.querySelector('td:nth-child(1)').textContent;
        const storyLink = row.querySelector('td:nth-child(2) a').getAttribute('href');

        const request = {
            storyId: roomId,
            responseStoryDto: {
                id: storyId,
                title: title,
                storyLink: storyLink
            }
        };

        stompClient.send('/app/updateCurrentStory', {}, JSON.stringify(request));
    }
});

const style = document.createElement('style');
style.innerHTML = `
    .fade-in {
        animation: fadeIn 0.5s ease-in-out;
    }
    @keyframes fadeIn {
        from { opacity: 0; }
        to { opacity: 1; }
    }
`;
document.head.appendChild(style);



