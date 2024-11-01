let stompClient = null;
const subscriptions = [];

export function connectWebSocket() {
    if (stompClient) return;

    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        subscriptions.forEach((subscription) => {
            subscription();
        });
    });
}

export function addSubscription(topic, callback) {
    if (stompClient && stompClient.connected) {
        stompClient.subscribe(topic, callback);
    } else {
        subscriptions.push(() => stompClient.subscribe(topic, callback));
    }
}

export function sendMessage(destination, payload) {
    if (stompClient && stompClient.connected) {
        stompClient.send(destination, {}, JSON.stringify(payload));
    }
}
