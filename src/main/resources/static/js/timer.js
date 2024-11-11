window.addEventListener('DOMContentLoaded', function () {
    const timerContainer = document.querySelector('[data-start-time]');
    const startTimeIso = timerContainer.getAttribute('data-start-time');

    function startCountdown(startTimeIso) {
        const timerElement = document.getElementById('timer');

        function updateTimer() {
            const now = moment();
            const startTime = moment(startTimeIso);

            const duration = moment.duration(startTime.diff(now));

            if (duration > 0) {
                const days = duration.days();
                const hours = duration.hours();
                const minutes = duration.minutes();
                const seconds = duration.seconds();

                timerElement.innerText = `Time until start: ${days}d ${hours}h ${minutes}m ${seconds}s`;
            } else {
                timerElement.innerText = "The event has started!";
            }
        }

        setInterval(updateTimer, 1000);
    }

    startCountdown(startTimeIso);
});