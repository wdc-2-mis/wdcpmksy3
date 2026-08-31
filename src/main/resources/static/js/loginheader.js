document.addEventListener("DOMContentLoaded", function () {

    const sidebar = document.getElementById("sidebar");
    const toggleBtn = document.getElementById("toggleSidebar");

    toggleBtn.addEventListener("click", function () {
        if(window.innerWidth <= 768){
            sidebar.classList.toggle("show");
        } else {
            sidebar.classList.toggle("collapsed");
        }
    });

    const activeSubmenu = document.querySelector(".active-submenu");
    if(activeSubmenu){
        activeSubmenu.closest(".menu > li").classList.add("active");
    }

	document.querySelectorAll(".menu-toggle").forEach(menu => {

	    menu.addEventListener("click", function () {

	        const current = this.parentElement;

	        // Close all other menus
	        document.querySelectorAll(".menu > li").forEach(item => {
	            if(item !== current){
	                item.classList.remove("active");
	            }
	        });

	        // Toggle current menu
	        current.classList.toggle("active");

	    });

	});

});

let sessionDuration = typeof sessionTimeout !== "undefined"
    ? Number(sessionTimeout)
    : 1800;

let sessionExpiryTime = Date.now() + (sessionDuration * 1000);

let timer;
let warningShown = false;
let expiredShown = false;


// =====================================================
// SESSION TIMER
// =====================================================

function updateSessionTimer() {

    const timerElement = document.getElementById("sessionTimer");

    if (!timerElement) {
        return;
    }

    // Calculate remaining time from real clock
    const remainingMilliseconds = sessionExpiryTime - Date.now();

    const timeLeft = Math.max(
        0,
        Math.ceil(remainingMilliseconds / 1000)
    );

    const minutes = Math.floor(timeLeft / 60);
    const seconds = timeLeft % 60;

    timerElement.innerHTML =
        String(minutes).padStart(2, "0") +
        ":" +
        String(seconds).padStart(2, "0");


    // =================================================
    // WARNING AT 2 MINUTES
    // =================================================

    if (timeLeft <= 120 && timeLeft > 0 && !warningShown) {

        warningShown = true;

        showExtendPopup(timeLeft);
    }


    // =================================================
    // SESSION EXPIRED
    // =================================================

    if (timeLeft <= 0 && !expiredShown) {

        expiredShown = true;

        clearInterval(timer);

        showExpiredPopup();

    }

}


// Start timer immediately
updateSessionTimer();


// Update every second
timer = setInterval(updateSessionTimer, 1000);

function showExtendPopup(timeLeft) {

    const minutes = Math.floor(timeLeft / 60);
    const seconds = timeLeft % 60;

    const remainingText =
        String(minutes).padStart(2, "0") +
        ":" +
        String(seconds).padStart(2, "0");


    const popup = document.createElement("div");

    popup.id = "sessionWarningPopup";

    popup.innerHTML = `

        <div class="session-warning-overlay">

            <div class="session-warning-card">

                <div class="session-warning-icon">
                    <i class="fa-solid fa-clock"></i>
                </div>

                <h3>Session Expiring Soon</h3>

                <p class="session-warning-text">
                    Your session will expire in
                </p>

                <div class="warning-countdown" id="warningCountdown">
                    ${remainingText}
                </div>

                <p class="session-warning-subtext">
                    Would you like to continue your session?
                </p>

                <div class="session-warning-buttons">

                    <button
                        type="button"
                        id="extendSessionBtn"
                        class="extend-session-btn">

                        <i class="fa-solid fa-rotate"></i>
                        Yes, Continue Session

                    </button>

                    <button
                        type="button"
                        id="logoutSessionBtn"
                        class="logout-session-btn">

                        <i class="fa-solid fa-right-from-bracket"></i>
                        Logout

                    </button>

                </div>

            </div>

        </div>

    `;


    document.body.appendChild(popup);


    // =================================================
    // UPDATE POPUP COUNTDOWN
    // =================================================

    const countdownElement =
        document.getElementById("warningCountdown");


    const popupTimer = setInterval(function () {

        const remaining =
            Math.max(
                0,
                Math.ceil(
                    (sessionExpiryTime - Date.now()) / 1000
                )
            );


        const mins = Math.floor(remaining / 60);
        const secs = remaining % 60;


        if (countdownElement) {

            countdownElement.innerHTML =
                String(mins).padStart(2, "0") +
                ":" +
                String(secs).padStart(2, "0");

        }


        if (remaining <= 0) {

            clearInterval(popupTimer);

        }

    }, 1000);


    // =================================================
    // YES / CONTINUE
    // =================================================

    document
        .getElementById("extendSessionBtn")
        .addEventListener("click", function () {

            clearInterval(popupTimer);

            extendSession();

        });


    // =================================================
    // LOGOUT
    // =================================================

    document
        .getElementById("logoutSessionBtn")
        .addEventListener("click", function () {

            clearInterval(popupTimer);

            window.location.href = "/customLogout";

        });

}

function showExpiredPopup(){

    alert("Your session has expired.");

    window.location.href="/login";

}

function extendSession() {

    const button =
        document.getElementById("extendSessionBtn");

    if (button) {

        button.disabled = true;

        button.innerHTML =
            '<i class="fa-solid fa-spinner fa-spin"></i> Extending...';

    }


    fetch("/extendSession", {
        method: "GET",
        credentials: "same-origin"
    })

    .then(response => {

        if (!response.ok) {
            throw new Error("Session extension failed");
        }

        return response.text();

    })

    .then(data => {

        if (data.trim() === "extended") {

            // =========================================
            // RESET CLIENT TIMER TO 30 MINUTES
            // =========================================

            sessionDuration = 30 * 60;

            sessionExpiryTime =
                Date.now() + (sessionDuration * 1000);

            warningShown = false;
            expiredShown = false;


            // Remove popup
            const popup =
                document.getElementById("sessionWarningPopup");

            if (popup) {
                popup.remove();
            }


            // Immediately update timer
            updateSessionTimer();


            console.log("Session extended successfully.");

        } else {

            alert("Unable to extend session. Please login again.");

            window.location.href = "/login";

        }

    })

    .catch(error => {

        console.error(error);

        alert("Unable to extend session. Please login again.");

        window.location.href = "/login";

    });

}


function updateServerTime() {

    const serverTime = document.getElementById("serverTime");

    if (!serverTime) {
        return;
    }

    serverTime.innerHTML = new Date().toLocaleString();
}

updateServerTime();

setInterval(updateServerTime,1000);

function validateInteger(input, maxDigits) {
    // allow only digits
    input.value = input.value.replace(/[^0-9]/g, '');
    if (input.value.length > maxDigits) {
        input.value = input.value.slice(0, maxDigits);
        input.setCustomValidity("Maximum " + maxDigits + " digits allowed.");
    } else {
        input.setCustomValidity("");
    }
}

function validateDecimal(input, maxIntDigits, maxFractionDigits) {
    // allow only digits and one decimal point
    input.value = input.value.replace(/[^0-9.]/g, '');
    let parts = input.value.split('.');

    // if more than one '.', keep only the first
    if (parts.length > 2) {
        input.value = parts[0] + '.' + parts[1];
        parts = input.value.split('.');
    }

    // enforce max digits before decimal
    if (parts[0].length > maxIntDigits) {
        parts[0] = parts[0].slice(0, maxIntDigits);
    }

    // enforce max digits after decimal
    if (parts[1] && parts[1].length > maxFractionDigits) {
        parts[1] = parts[1].slice(0, maxFractionDigits);
    }

    // rebuild value
    input.value = parts.join('.');

    // final regex check
    let regex = new RegExp("^\\d{0," + maxIntDigits + "}(\\.\\d{0," + maxFractionDigits + "})?$");
    if (!regex.test(input.value)) {
        input.setCustomValidity(
            "Enter a number with up to " + maxIntDigits +
            " digits before decimal and " + maxFractionDigits + " digits after."
        );
    } else {
        input.setCustomValidity("");
    }
}


