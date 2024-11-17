import { connectWebSocket, addSubscription, sendMessage } from './websocket-client.js';

document.addEventListener('DOMContentLoaded', () => {
    connectWebSocket();

    const btnChangeRoomName = document.getElementById('btnChangeRoomName');
    const modalElement = document.getElementById('changeRoomNameModal');
    const changeRoomNameInput = document.getElementById('changeRoomName');
    const changeRoomNameButton = document.getElementById('change-room-name-btn');
    const roomNameCannotBeEmptyMessage = document.getElementById('roomNameCannotBeEmpty').textContent;
    const roomId = document.getElementById('roomId').value;

    const modal = new bootstrap.Modal(modalElement);

    if (btnChangeRoomName) {
        btnChangeRoomName.addEventListener('click', () => {
            modal.show();
        });
    }

    if (changeRoomNameButton) {
        changeRoomNameButton.addEventListener('click', () => {
            const newRoomName = changeRoomNameInput.value.trim();

            if (!newRoomName) {
                alert(roomNameCannotBeEmptyMessage);
                return;
            }

            sendMessage('/app/updateRoomName', {
                roomId: roomId,
                roomName: newRoomName,
            });

            modal.hide();
        });
    }

    addSubscription('/topic/roomUpdated', (message) => {
        const updatedRoom = JSON.parse(message.body);

        if (updatedRoom.id.toString() === roomId) {
            const roomNameElement = document.getElementById('roomNameElement');
            if (roomNameElement) {
                    roomNameElement.textContent = updatedRoom.roomName;
            }
        }
    });
});
