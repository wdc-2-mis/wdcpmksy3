document.addEventListener("DOMContentLoaded", function () {

	const sidebar = document.getElementById("sidebar");
	const toggleBtn = document.getElementById("toggleSidebar");

	toggleBtn.addEventListener("click", function () {

	    if (window.innerWidth <= 768) {

	        sidebar.classList.toggle("show");

	    } else {

	        sidebar.classList.toggle("collapsed");

	    }

	});

});


let timeLeft = 30 * 60;

function updateSessionTimer() {

    let minutes = Math.floor(timeLeft / 60);
    let seconds = timeLeft % 60;

    document.getElementById("sessionTimer").innerHTML =
        String(minutes).padStart(2, '0') + ":" +
        String(seconds).padStart(2, '0');

    if(timeLeft > 0){
        timeLeft--;
    }

}

updateSessionTimer();

setInterval(updateSessionTimer,1000);


function updateServerTime() {

    const serverTime = document.getElementById("serverTime");

    if (!serverTime) {
        return;
    }

    serverTime.innerHTML = new Date().toLocaleString();
}

updateServerTime();

setInterval(updateServerTime,1000);


document.querySelectorAll(".menu-toggle").forEach(menu => {

    menu.addEventListener("click", function () {

        const current = this.parentElement;

        if(current.querySelector(".active-submenu")){
            return;
        }

        current.classList.toggle("active");

    });

});

