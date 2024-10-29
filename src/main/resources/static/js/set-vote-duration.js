function setVoteDuration() {
    const selectedOption = document.querySelector('input[name="voteDuration"]:checked');
    if (selectedOption) {
        document.getElementById('voteDurationHidden').value = selectedOption.value;
    }
}

$('#sendVoteToRegisterForm').on('click', function() {
    const selectedOption = document.querySelector('input[name="voteDuration"]:checked');
    if (selectedOption) {
        document.getElementById('voteDurationHidden').value = selectedOption.value;
    }
    $('#buttonCloseVoteDuration').click();
});
