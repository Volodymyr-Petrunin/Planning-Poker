var stompClient = null;

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/userResults', function (result) {
            showUserResults(JSON.parse(result.body));
        });
    });
}

function searchUsers() {
    var formData = {
        firstName: $('#searchFirstName').val(),
        lastName: $('#searchLastName').val(),
        nickname: $('#searchNickname').val()
    };

    console.log("Sending search request:", formData);

    stompClient.send("/app/searchUsers", {}, JSON.stringify(formData));
}

var selectedUsersMap = new Map();

function showUserResults(users) {
    $('#userResults').empty();

    if (users.length > 0) {
        users.forEach(function (user) {
            var userElement = '<tr>' +
                '<td><input type="checkbox" class="user-select" value="' + user.id + '"></td>' +
                '<td>' + user.firstName + '</td>' +
                '<td>' + user.lastName + '</td>' +
                '<td>' + user.nickname + '</td>' +
                '</tr>';
            $('#userResults').append(userElement);
        });
    } else {
        $('#userResults').append('<tr><td colspan="4">' + $('#noUsersMessage').text() + '</td></tr>');
    }

    $('.user-select').on('change', function() {
        var userId = $(this).val();
        if ($(this).is(':checked')) {
            var user = users.find(u => u.id == userId);
            addUserToSelected(user);
        } else {
            removeUserFromSelected(userId);
        }
    });
}

function addUserToSelected(user) {
    if (!selectedUsersMap.has(user.id)) {
        selectedUsersMap.set(user.id, user);

        var removeButtonText = $('#removeButtonText').text();

        var selectedUserElement = '<tr data-user-id="' + user.id + '">' +
            '<td>' + user.firstName + '</td>' +
            '<td>' + user.lastName + '</td>' +
            '<td>' + user.nickname + '</td>' +
            '<td><button class="btn btn-danger btn-sm remove-user" data-user-id="' + user.id + '">' + removeButtonText + '</button></td>' +
            '</tr>';
        $('#selectedUsers').append(selectedUserElement);

        $('.remove-user').on('click', function() {
            var userId = $(this).data('user-id');
            removeUserFromSelected(userId);
        });
    }
}

function removeUserFromSelected(userId) {
    selectedUsersMap.delete(userId);
    $('tr[data-user-id="' + userId + '"]').remove();
    $('input[value="' + userId + '"]').prop('checked', false);
}

$(document).ready(function () {
    connect();

    $('#searchUserBtn').on('click', function () {
        searchUsers();
    });
});
