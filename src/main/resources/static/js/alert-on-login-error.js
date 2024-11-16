document.addEventListener("DOMContentLoaded", () => {
    const errorElement = document.getElementById("error");
    const alertMessageElement = document.getElementById("alert-message");

    if (errorElement && errorElement.dataset.error === "true") {
        const alertMessage = alertMessageElement ? alertMessageElement.textContent : "Login unsuccessful!";
        alert(alertMessage);
    }
});
