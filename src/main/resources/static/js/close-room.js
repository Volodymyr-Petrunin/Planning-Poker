import { connectWebSocket, addSubscription, sendMessage } from './websocket-client.js';

document.addEventListener('DOMContentLoaded', () => {
    connectWebSocket();

    const roomId = document.getElementById('roomId').value;
    const btnCloseRoom = document.getElementById('btnCloseRoom');
    const closeRoomModal = document.getElementById('closeRoomModal');
    const confirmCloseRoomButton  = document.getElementById('confirmCloseRoomButton');
    const roomWasClosedAlertMessage = document.getElementById('roomWasClosed').textContent;

    const modal = new bootstrap.Modal(closeRoomModal);

    if (btnCloseRoom) {
        btnCloseRoom.addEventListener('click', () => {
            modal.show();
        });
    }

    if (confirmCloseRoomButton) {
        confirmCloseRoomButton.addEventListener('click', () => {
            sendMessage('/app/closeRoom', roomId);

            modal.hide();
        });
    }

    addSubscription('/topic/roomClosed', (message) => {
        const deletedRoomId = JSON.parse(message.body);

        if (roomId == deletedRoomId){
            alert(roomWasClosedAlertMessage);
            window.location.replace("/");
        }
    });
});