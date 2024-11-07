import { connectWebSocket, addSubscription, sendMessage } from './websocket-client.js';

connectWebSocket();

addSubscription('/topic/userRoleChanged', function(message) {
    let roleUpdate = JSON.parse(message.body);
    updateRoleInTable(roleUpdate.id, roleUpdate.role);
});

window.changeRole = function (selectElement) {
    let userId = selectElement.getAttribute('data-user-id');
    let newRole = selectElement.value;

    sendMessage('/app/changeUserRole', {
        userId: userId,
        newRole: newRole
    });
}

function updateRoleInTable(userId, newRole) {
    let userRow = document.querySelector(`tr[data-user-id="${userId}"]`);
    if (userRow) {
        let roleCell = userRow.querySelector('td:nth-child(3)');
        let selectElement = roleCell.querySelector('select');
        let spanElement = roleCell.querySelector('span');

        let localizedRole;
        if (newRole === "USER_PRESENTER") {
            localizedRole = document.getElementById("userStatusPresenter").textContent;
        } else if (newRole === "USER_ELECTOR") {
            localizedRole = document.getElementById("userStatusElector").textContent;
        } else if (newRole === "USER_SPECTATOR") {
            localizedRole = document.getElementById("userStatusSpectator").textContent;
        }

        if (selectElement) {
            selectElement.value = newRole;
        } else if (spanElement) {
            spanElement.textContent = localizedRole;
        }
    }
}
