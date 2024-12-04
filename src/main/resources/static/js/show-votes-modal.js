document.addEventListener('DOMContentLoaded', () => {
    const noVotesAvailable = document.getElementById('noVotesAvailable').textContent;
    const anonymousVotingMessage = document.getElementById('anonymousVotingMessage').textContent;
    const isAnonymousVoting = document.getElementById('isAnonymousVoting').value === 'true';

    document.querySelectorAll('td[data-votes]').forEach(cell => {
        cell.addEventListener('click', function () {
            const votesData = JSON.parse(this.getAttribute('data-votes')) || [];

            const votesList = document.getElementById('votesList');
            votesList.innerHTML = '';

            if (isAnonymousVoting) {
                votesList.innerHTML = `<li class="list-group-item">${anonymousVotingMessage}</li>`;
            } else {
                if (votesData.length > 0) {
                    votesData.forEach(vote => {
                        const listItem = document.createElement('li');
                        listItem.textContent = `${vote.voter.firstName} ${vote.voter.lastName}: ${vote.points}`;
                        listItem.classList.add('list-group-item');
                        votesList.appendChild(listItem);
                    });
                } else {
                    votesList.innerHTML = `<li class="list-group-item">${noVotesAvailable}</li>`;
                }
            }

            const modal = new bootstrap.Modal(document.getElementById('showVotesModal'));
            modal.show();
        });
    });
});
