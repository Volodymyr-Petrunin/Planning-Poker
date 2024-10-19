$(document).ready(function() {
    const doneButton = $('#doneButton');

    function validateForm() {

        const invitedUsers = $('#invitedUsers').val();
        const stories = $('#storiesInput').val();
        const voteDuration = $('#voteDurationHidden').val();

        // 2 is not a magic number $('prop').val(); return string (not array), and if 'prop' is empty, the result will be []
        if (invitedUsers.length > 2 && stories.length > 2 && voteDuration.length > 2) {
            doneButton.prop('disabled', false);
        } else {
            doneButton.prop('disabled', true);
        }
    }

    $('#saveStoriesBtn').on('click', validateForm);
    $('#sendToRegisterForm').on('click', validateForm);
    $('#sendVoteToRegisterForm').on('click', validateForm);

    validateForm();
});
