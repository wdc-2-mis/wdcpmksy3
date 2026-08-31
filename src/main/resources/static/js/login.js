/*=============================================================
    WDC-PMKSY 3.0
    Login.js
    PART - 1
=============================================================*/

/*-------------------------------------------------------------
    GLOBAL VARIABLES
-------------------------------------------------------------*/

let captchaVerified = false;
let loginMethod = "";
let resendSeconds = 60;
let resendTimer = null;

/*-------------------------------------------------------------
    PAGE INITIALIZATION
-------------------------------------------------------------*/

$(document).ready(function () {

    initializePage();

    bindEvents();

});

/*-------------------------------------------------------------
    INITIALIZE PAGE
-------------------------------------------------------------*/

function initializePage() {

    $("#pass").hide();
    $("#otpe").hide();
    $("#otp1").hide();

    $("#login").prop("disabled", true);

    $(".field-error").hide();

    $("#emailMessage").hide();

   
    const selectedMethod =
        $('input[name="loginMethod"]:checked').val();

    console.log("Selected login method:", selectedMethod);

    if (selectedMethod === "pass") {

        $("#pass").show();

        $("#otpe").hide();
        $("#otp1").hide();

    } 
    else if (selectedMethod === "otp") {

        $("#pass").hide();

        $("#otpe").show();
        $("#otp1").show();

    }
}

function startResendCountdown() {

    resendSeconds = 60;

    $("#resendOtpBtn")
        .show()
        .prop("disabled", true)
        .text("Resend OTP (" + resendSeconds + "s)");

    resendTimer = setInterval(function () {

        resendSeconds--;

        $("#resendOtpBtn")
            .text("Resend OTP (" + resendSeconds + "s)");

			if (resendSeconds <= 0) {

			    clearInterval(resendTimer);

			    resendTimer = null;

			    otpGenerated = false;

			    $('input[name="loginMethod"][value="otp"]')
			        .prop("disabled", false);

			    $("#resendOtpBtn")
			        .prop("disabled", false)
			        .text("Resend OTP");
			}

    }, 1000);
}

/*-------------------------------------------------------------
    BIND EVENTS
-------------------------------------------------------------*/

function bindEvents() {

    $('input[name="loginMethod"]').off("change").on("change", function () {
        changeLoginMethod();
    });

}

/*-------------------------------------------------------------
    AUTHENTICATION TYPE
-------------------------------------------------------------*/

function changeLoginMethod() {

     const loginMethod = $("#loginForm input[name='loginMethod']:checked").val();

    console.log("changeLoginMethod :", loginMethod);

    if (loginMethod === "pass") {

        resetOTPSection();

        $("#pass").stop(true, true).slideDown(250);
        $("#otpe").stop(true, true).slideUp(250);
        $("#otp1").stop(true, true).slideUp(250);

        $("#emailMessage").hide();

        clearFieldError("#otp", "#otpError");

    }

	else if (loginMethod === "otp") {

	    $("#pass").stop(true, true).slideUp(250);

	    // Keep OTP section hidden until OTP is generated
	    $("#otpe").hide();
	    $("#otp1").hide();

	    requestOTP();
	}
}

function requestOTP() {

    // avoid multiple calls
    if (otpGenerated) return;

    setTimeout(() => {
        sendOTP();
    }, 200);   // 🔥 IMPORTANT: give UI time to settle
}
/*-------------------------------------------------------------
    SEND OTP
-------------------------------------------------------------*/
/*=========================================================
    SEND OTP
=========================================================*/

let otpGenerated = false;

function sendOTP() {

    let userid = $("#userId").val().trim().toUpperCase();

    /*-------------------------------
        Validate User ID
    -------------------------------*/

    if (userid === "") {

        showFieldError(
            "#userId",
            "#userIdError",
            "Please enter User ID."
        );

        $("#userId").focus();

        return;
    }

    /*-------------------------------
        Prevent Multiple Requests
    -------------------------------*/

    if (otpGenerated) {
        return;
    }

    otpGenerated = true;

    /*-------------------------------
        Disable OTP Option
    -------------------------------*/

    $('input[name="loginMethod"][value="otp"]').prop("disabled", true);

    /*-------------------------------
        Reset UI
    -------------------------------*/

    $("#otpStatus")
        .html('<i class="fa fa-spinner fa-spin"></i> Generating and sending OTP...')
        .show();

    $("#emailMessage").hide();

    $(".otp-success-title").html("");

    $("#dimail").html("");

    $("#resendOtpBtn")
        .hide()
        .prop("disabled", true);

    /*-------------------------------
        Generate OTP
    -------------------------------*/

    fetch("/getEmailandGenerateotp?value=" + encodeURIComponent(userid))

        .then(response => {

            if (!response.ok) {
                throw new Error("Unable to generate OTP. User Not Found!");
            }

            return response.text();

        })

        .then(function (email) {

            if (email === "USER_NOT_FOUND") {

                otpGenerated = false;

                $('input[name="loginMethod"][value="otp"]')
                    .prop("disabled", false);

                $("#otpStatus").hide();

                showFieldError(
                    "#userId",
                    "#userIdError",
                    "User ID is not registered."
                );

                return;
            }

            /*-------------------------------
                Success
            -------------------------------*/

            $("#emailid").val(email);

            $("#otpStatus").hide();

            $(".otp-success-title").html(
                '<i class="fa-solid fa-circle-check text-success"></i> OTP has been sent successfully'
            );

            $("#dimail").html(
                '<i class="fa-solid fa-envelope-open-text text-primary"></i> ' +
                'OTP sent to: <strong>' +
                maskEmail(email) +
                '</strong>'
            );

            $("#emailMessage").fadeIn(300);
			// Show OTP input only after OTP is successfully generated
			$("#otpe").slideDown(250);
			$("#otp1").slideDown(250);

            $("#resendOtpBtn")
                .show()
                .prop("disabled", true);

            startResendCountdown();

        })

        .catch(function (err) {

            otpGenerated = false;

            $('input[name="loginMethod"][value="otp"]')
                .prop("disabled", false);

            $("#otpStatus")
                .html(
                    '<i class="fa-solid fa-circle-xmark text-danger"></i> ' +
                    err.message
                )
                .show();

        });

}
/*-------------------------------------------------------------
    MASK EMAIL
-------------------------------------------------------------*/

function resetOTPSection() {

    if (resendTimer) {
        clearInterval(resendTimer);
        resendTimer = null;
    }

    otpGenerated = false;

    $("#otp").val("");

    $("#otpStatus").hide().html("");

    $("#emailMessage").hide();

    $("#dimail").html("");

    $('input[name="loginMethod"][value="otp"]').prop("disabled", false);

	$("#resendOtpBtn")
	    .show()
	    .prop("disabled", true)
	    .text("Resend OTP");
}

$("#resendOtpBtn").on("click", function () {

    if (!$(this).prop("disabled")) {

        sendOTP();

    }

});

function maskEmail(email) {

    if (!email) {
        return "";
    }

    let parts = email.split("@");

    if (parts.length !== 2) {
        return email;
    }

    let username = parts[0];
    let domain = parts[1];

    if (username.length <= 2) {
        return username.charAt(0) + "***@" + domain;
    }

    return username.charAt(0)
        + "*".repeat(username.length - 2)
        + username.charAt(username.length - 1)
        + "@"
        + domain;
}

/*-------------------------------------------------------------
    SHOW ERROR
-------------------------------------------------------------*/

function showFieldError(field, errorDiv, message) {

    $(field)
        .addClass("is-invalid")
        .focus();

    $(errorDiv)
        .text(message)
        .fadeIn(150);

}

/*-------------------------------------------------------------
    CLEAR ERROR
-------------------------------------------------------------*/

function clearFieldError(field, errorDiv) {

    $(field).removeClass("is-invalid");

    $(errorDiv).hide();

}

/*-------------------------------------------------------------
    CAPTCHA CALLBACK
-------------------------------------------------------------*/

function recaptchaVerified() {

    captchaVerified = true;

    $("#login")
        .prop("disabled", false);

    $("#captchaError").hide();

}

/*-------------------------------------------------------------
    CAPTCHA EXPIRED
-------------------------------------------------------------*/

function recaptchaExpired() {

    captchaVerified = false;

    $("#login")
        .prop("disabled", true);

    $("#captchaError")
        .text("Please complete reCAPTCHA verification.")
        .show();

}

/*-------------------------------------------------------------
    GENERATE RANDOM SALT
-------------------------------------------------------------*/

function generateSalt(length) {

    let chars =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    let salt = "";

    for (let i = 0; i < length; i++) {

        salt += chars.charAt(

            Math.floor(Math.random() * chars.length)

        );

    }

    return salt;

}

/*=============================================================
    WDC-PMKSY 3.0
    Login.js
    PART - 2
=============================================================*/

/*-------------------------------------------------------------
    LOGIN BUTTON
-------------------------------------------------------------*/

$("#login").click(function () {
	
	console.log("UserId :", $("#userId").val());
	    console.log("OTP    :", $("#otp").val());

    if (!validateLogin()) {
        return;
    }

	$('input[name="loginMethod"]').prop("disabled", false);

	    $("#loginForm").submit();

});

/*-------------------------------------------------------------
    LOGIN
-------------------------------------------------------------*/

/*function loginUser() {

    if (!validateLogin()) {
        return;
    }

    showLoading();

	let salt = "";

	let userId = $("#userId")
	    .val()
	    .trim()
	    .toUpperCase();

	$("#userId").val(userId);

	-----------------------------------------
	    Encrypt password ONLY for Password Login
	------------------------------------------

	if (loginMethod === "pass") {

	    salt = generateSalt(10);

	    let password = $("#encrypted_pass").val();

	    let hash = sha512(password);

	    hash = sha512(userId + hash);

	    hash = sha512(hash + salt);

	    $("#encrypted_pass").val(hash);

	}

	$.ajax({

	    url: "/login",

	    type: "POST",

	    data: $("#loginForm").serialize()
	            + "&salt=" + encodeURIComponent(salt)
	            + "&loginMethod=" + encodeURIComponent(loginMethod),

	    success: function(response){

			console.log("Server Response:");
			    console.log(response);
				
	        hideLoading();

	        processLoginResponse(response);

	    },

	    error:function(){

	        hideLoading();

	        showError("Unable to connect to server. Please try again.");

	    }

	});

}*/

/*-------------------------------------------------------------
    VALIDATION
-------------------------------------------------------------*/

function validateLogin() {

    let valid = true;

    $(".field-error").hide();

    /* User ID */

    if ($("#userId").val().trim() === "") {

        showFieldError(

            "#userId",

            "#userIdError",

            "Please enter User ID."

        );

        valid = false;

    }

    /* Authentication Type */

	let loginMethod = $('input[name="loginMethod"]:checked').val();

	if (!loginMethod) {

	    showError("Please select Password or OTP login.");

	    valid = false;

	    return valid;
	}

    /* Password */

    if (loginMethod === "pass") {

        if ($("#encrypted_pass").val().trim() === "") {

            showFieldError(

                "#encrypted_pass",

                "#passwordError",

                "Please enter Password."

            );

            valid = false;

        }

    }

    /* OTP */

    else {

        if ($("#otp").val().trim() === "") {

            showFieldError(

                "#otp",

                "#otpError",

                "Please enter OTP."

            );

            valid = false;

        }

    }

    /* Captcha */

    

    if (!captchaVerified) {

        $("#captchaError")

            .text(

                "Please complete reCAPTCHA verification."

            )

            .show();

        valid = false;

    }

    return valid;

}

/*-------------------------------------------------------------
    PROCESS RESPONSE
-------------------------------------------------------------*/

/*function processLoginResponse(response) {

    if (response.includes("loginsuccess")) {

        $("#login")

            .html(

                '<i class="fa-solid fa-circle-check"></i> Redirecting...'

            );

        window.location.href = "/loginsuccess";

        return;

    }

    if (response.includes("alreadylogin")) {

        showError(

            "You are already signed in with the same User ID. Kindly logout first."

        );

    }

    else if (response.includes("captchaerror")) {

        showError(

            "Captcha is not matched."

        );

        $("#captcha").focus();

    }

    else if (response.includes("failed")) {

        showError(

            "Unregistered User."

        );

    }

    else if (response.includes("invalidcredentials")) {

        showError(

            "Invalid User ID or Password."

        );

    }

    else if (response.includes("loginerror")) {

        showError(

            "Authentication failed. You have only one more attempt."

        );

    }

    else if (response.includes("userlocked")) {

        showError(

            "Your User ID has been locked."

        );

    }

    else if (response.includes("locked")) {

        showError(

            "Your User ID is temporarily locked."

        );

    }

    else {

        showError(

            "Unexpected server response."

        );

    }

    clearSensitiveFields();

}*/

/*-------------------------------------------------------------
    LOADING
-------------------------------------------------------------*/

function showLoading() {

    $("body")

        .css(

            "cursor",

            "wait"

        );

    $("#login")

        .prop(

            "disabled",

            true

        )

        .html(

            '<i class="fa fa-spinner fa-spin"></i> Signing In...'

        );

}

/*-------------------------------------------------------------
    HIDE LOADING
-------------------------------------------------------------*/

function hideLoading() {

    $("body")

        .css(

            "cursor",

            "default"

        );

    enableLoginButton();

}

/*-------------------------------------------------------------
    ENABLE BUTTON
-------------------------------------------------------------*/

function enableLoginButton() {

    $("#login")

        .prop(

            "disabled",

            false

        )

        .html(

            '<i class="fa-solid fa-right-to-bracket"></i> Login'

        );

}

/*=============================================================
    WDC-PMKSY 3.0
    Login.js
    PART - 3
=============================================================*/

/*-------------------------------------------------------------
    SHOW ERROR MESSAGE
-------------------------------------------------------------*/

function showError(message) {

    $(".text-danger")
        .html(message)
        .fadeIn(200);

}

/*-------------------------------------------------------------
    SHOW SUCCESS MESSAGE
-------------------------------------------------------------*/

function showSuccess(message) {

    $(".text-success")
        .html(message)
        .fadeIn(200);

}

/*-------------------------------------------------------------
    CLEAR PASSWORD / OTP / CAPTCHA
-------------------------------------------------------------*/

function clearSensitiveFields() {

    $("#encrypted_pass").val("");

    $("#otp").val("");

    $("#captcha").val("");

    refreshCaptcha();

    enableLoginButton();

}

/*-------------------------------------------------------------
    REFRESH CAPTCHA
-------------------------------------------------------------*/

function refreshCaptcha() {

    if ($("#captcha_id").length) {

        $("#captcha_id").attr(

            "src",

            "/captcha.jpg?" + new Date().getTime()

        );

    }

}

/*-------------------------------------------------------------
    RESET COMPLETE FORM
-------------------------------------------------------------*/

function resetLoginForm() {

    $("#loginForm")[0].reset();

    $(".field-error").hide();

    $(".is-invalid").removeClass("is-invalid");

    $("#emailMessage").hide();

    captchaVerified = false;

    $("#pass").hide();

    $("#otpe").hide();

    $("#otp1").hide();

    loginMethod = "";

    refreshCaptcha();

    enableLoginButton();

}

/*-------------------------------------------------------------
    LOGOUT MESSAGE
-------------------------------------------------------------*/

function logoutSuccess() {

    showSuccess("Successfully logged out.");

    resetLoginForm();

}

/*-------------------------------------------------------------
    PASSWORD STRENGTH (OPTIONAL)
-------------------------------------------------------------*/

$("#encrypted_pass").on("keyup", function () {

    let password = $(this).val();

    if (password.length === 0)
        return;

    if (password.length < 8) {

        $("#passwordHint")

            .text("Weak Password")

            .css("color", "#dc3545");

    }

    else if (password.length < 12) {

        $("#passwordHint")

            .text("Medium Password")

            .css("color", "#ffc107");

    }

    else {

        $("#passwordHint")

            .text("Strong Password")

            .css("color", "#198754");

    }

});

/*-------------------------------------------------------------
    USER ID FORMAT
-------------------------------------------------------------*/

$("#userId").on("blur", function () {

    $(this).val(

        $(this)

            .val()

            .trim()

            .toUpperCase()

    );

});

/*-------------------------------------------------------------
    FADE ANIMATION
-------------------------------------------------------------*/

$(window).on("load", function () {

    $(".login-card")

        .css({

            opacity: 0,

            transform: "translateY(20px)"

        })

        .animate({

            opacity: 1

        }, 500);

});

/*-------------------------------------------------------------
    FLOATING BACKGROUND ANIMATION
-------------------------------------------------------------*/

setInterval(function () {

    $(".bg-circle").each(function () {

        let random = Math.random() * 12;

        $(this).css({

            transform:

            "translateY(" +

            random +

            "px)"

        });

    });

}, 2500);

/*-------------------------------------------------------------
    PREVENT MULTIPLE SUBMISSIONS
-------------------------------------------------------------*/

window.onbeforeunload = function () {

    $("#login").prop("disabled", true);

};

/*-------------------------------------------------------------
    REMOVE MESSAGE WHEN USER STARTS TYPING
-------------------------------------------------------------*/

$("#userId,#encrypted_pass,#otp,#captcha").on(

    "input",

    function () {

        $(".text-danger").html("");

    }

);

/*-------------------------------------------------------------
    AUTO HIDE SUCCESS MESSAGE
-------------------------------------------------------------*/

setTimeout(function () {

    $(".text-success").fadeOut();

}, 5000);

/*-------------------------------------------------------------
    AUTO HIDE ERROR MESSAGE
-------------------------------------------------------------*/

setTimeout(function () {

    $(".text-danger").fadeOut();

}, 7000);

/*=============================================================
    END OF FILE
=============================================================*/