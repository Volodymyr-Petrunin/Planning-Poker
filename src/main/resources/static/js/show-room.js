import {addSubscription, connectWebSocket, sendMessage} from './websocket-client.js';
const roomCode = document.getElementById('roomCode').value;

document.addEventListener("DOMContentLoaded", function() {
    connectWebSocket();

    const updateCurrentStoryTopic = `/topic/updateCurrentStory/` + roomCode;

    addSubscription(updateCurrentStoryTopic, function (message) {
            const room = JSON.parse(message.body);
            const currentStorySection = document.getElementById('currentStorySection');

            const currentStoryText = document.getElementById('currentStoryText').textContent;
            const noStorySelectedText = document.getElementById('noStorySelectedText').textContent;
            const readStoryText = document.getElementById('readStoryText').textContent;

            if (room.currentStory) {
                currentStorySection.innerHTML = `
            <h3>${currentStoryText} ${room.currentStory.title}</h3>
            <a href="${room.currentStory.storyLink}" class="text-info" target="_blank">${readStoryText}</a>
        `;
                document.getElementById('story-id').value = room.currentStory.id || '';
                document.getElementById('story-title').value = room.currentStory.title || '';
                document.getElementById('story-storyLink').value = room.currentStory.storyLink || '';
            } else {
                currentStorySection.innerHTML = `
            <h3>${noStorySelectedText}</h3>
        `;
                document.getElementById('story-id').value = '';
                document.getElementById('story-title').value = '';
                document.getElementById('story-storyLink').value = '';
            }

            currentStorySection.classList.add('fade-in');

            setTimeout(() => {
                currentStorySection.classList.remove('fade-in');
            }, 500)
    });

    const storyCreatedTopic = `/topic/storyCreated/` + roomCode;

    addSubscription(storyCreatedTopic, function (message) {
            var storyCreated = JSON.parse(message.body);

            var storyList = document.getElementById('story-list');
            var isCreator = document.getElementById('isCreator').value === 'true';

            var newRow = document.createElement('tr');
            newRow.setAttribute('data-story-id', storyCreated.id);

            var actionButtons = '';
            if (isCreator) {
                actionButtons = `
            <button class="btn btn-warning me-2" id="buttonUpdateStory" data-story-id="${storyCreated.id}">Update</button>
            <button class="btn btn-danger me-2" id="buttonDeleteStory" data-story-id="${storyCreated.id}">Delete</button>
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

    const storyUpdatedTopic = `/topic/storyUpdated/` + roomCode;

    addSubscription(storyUpdatedTopic, function (message) {
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

    const storyDeletedTopic = `/topic/storyDeleted/` + roomCode;

    addSubscription(storyDeletedTopic, function(message) {
            const deletedStoryId = JSON.parse(message.body);

            const storyRow = document.querySelector(`tr[data-story-id="${deletedStoryId}"]`);
            if (storyRow) {
                storyRow.remove();
            }
        });
    });

    addSubscription('/topic/newEventMessage', (message) => {
    const eventId = document.getElementById('eventId').value;

    if (message.body) {
        const eventMessage = JSON.parse(message.body);

        if (eventMessage.eventId == eventId) {
            const locale = document.documentElement.lang || 'en';
            const formattedTimestamp = moment(eventMessage.responseEventMessageDto.timestamp).format('MMM D, YYYY HH:mm');

            getLocalizedMessage(eventMessage.responseEventMessageDto, locale)
                .then(localizedMessage => {
                    displayMessage(formattedTimestamp, localizedMessage);
                })
                .catch(error => {
                    console.error("Error getting localized message:", error);
                });
        }
    }
    });

    async function getLocalizedMessage(eventMessage, locale) {
        const url = '/messages/localize';

        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept-Language': locale
            },
            body: JSON.stringify(eventMessage)
        });

        if (response.ok) {
            return await response.text();
        } else {
            throw new Error("Failed to fetch localized message");
        }
    }

    function displayMessage(timestamp, localizedMessage) {
        const messagesList = document.getElementById('messagesList');

        const messageItem = document.createElement('li');
        messageItem.classList.add('list-group-item', 'bg-dark', 'text-light');
        messageItem.textContent = `${timestamp} - ${localizedMessage}`;
        messageItem.classList.add('fade-in');
        messagesList.appendChild(messageItem);

        messagesList.scrollTop = messagesList.scrollHeight;
    }

    // Alert block

    addSubscription('/user/alert/argument/not/valid', (errorMessage) => {
        const failureAlert = document.getElementById('failureAlert');
        const alertMessageList = document.getElementById('alertMessageList');

        if (failureAlert && alertMessageList) {
            alertMessageList.innerHTML = '';

            let errors = [];
            try {
                errors = JSON.parse(errorMessage.body);
            } catch (e) {
                console.error('Error parsing errorMessage:', e);
                errors = [errorMessage.body];
            }

            errors.forEach((error) => {
                const li = document.createElement('li');
                li.textContent = error;
                alertMessageList.appendChild(li);
            });

            failureAlert.style.display = 'block';
            failureAlert.classList.add('show');
        }
    });

    window.hideAlert = function () {
        const failureAlert = document.getElementById('failureAlert');
        if (failureAlert) {
            failureAlert.style.display = 'none';
            failureAlert.classList.remove('show');
        }
    };





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
            },
            roomCode: roomCode
        };

        sendMessage('/app/updateCurrentStory', request);
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



