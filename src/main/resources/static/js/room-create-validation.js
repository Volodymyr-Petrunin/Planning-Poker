$(document).ready(function() {
    const doneButton = $('#doneButton');

    const startDateInput = $('#startDate');
    const startTimeInput = $('#startTime');

    function setMinDateTime() {
        const now = new Date();
        const yyyy = now.getFullYear();
        const mm = String(now.getMonth() + 1).padStart(2, '0');
        const dd = String(now.getDate()).padStart(2, '0');
        const minDate = `${yyyy}-${mm}-${dd}`;
        const currentHours = String(now.getHours()).padStart(2, '0');
        const currentMinutes = String(now.getMinutes()).padStart(2, '0');
        const minTime = `${currentHours}:${currentMinutes}`;

        startDateInput.attr('min', minDate);

        if (startDateInput.val() === minDate) {
            startTimeInput.attr('min', minTime);
        } else {
            startTimeInput.removeAttr('min');
        }
    }

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

    setMinDateTime();
    validateForm();
});
