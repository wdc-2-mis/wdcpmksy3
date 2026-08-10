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

let timeLeft = typeof sessionTimeout !== "undefined"
    ? sessionTimeout
    : 1800;

	function updateSessionTimer(){

	    const timerElement = document.getElementById("sessionTimer");

	    if(!timerElement){
	        return;
	    }

	    let minutes = Math.floor(timeLeft/60);
	    let seconds = timeLeft%60;

	    timerElement.innerHTML =
	        String(minutes).padStart(2,'0') + ":" +
	        String(seconds).padStart(2,'0');

	    if(timeLeft === 120){
	        showExtendPopup();
	    }

	    if(timeLeft <= 0){
	        clearInterval(timer);
	        showExpiredPopup();
	        return;
	    }

	    timeLeft--;
	}
	updateSessionTimer();
	const timer = setInterval(updateSessionTimer,1000);

function showExtendPopup(){

    if(confirm("Your session will expire in 2 minutes.\n\nDo you want to extend your session?")){

        extendSession();

    }

}

function showExpiredPopup(){

    alert("Your session has expired.");

    window.location.href="/login";

}

function extendSession(){

    fetch("/extendSession")

    .then(response=>response.text())

    .then(data=>{

        if(data==="extended"){

            timeLeft = 30 * 60;

            alert("Session extended successfully.");

        }

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


