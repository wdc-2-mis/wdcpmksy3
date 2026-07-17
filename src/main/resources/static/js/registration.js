function show(id){
    document.getElementById(id).classList.add("show");
}

function hide(id){
    document.getElementById(id).classList.remove("show");
}

function resetAll() {

    hide("districtBox");

    hide("nameField");
    hide("deptField");
    hide("desgField");

    hide("ngoNameField");
    hide("ngoIdField");
    hide("ngoRegField");
}

function handleUserTypeChange(type) {

    const state = document.getElementById("userState");
    const district = document.getElementById("userDistrict");
    const title = document.getElementById("orgSectionTitle");

    state.value = "";
    state.disabled = true;

    if (district) {
        district.value = "";
    }
    
    if (type === "DL") {
        state.multiple = true;
        state.size = 8;
        state.name = "userState";     // or "userState[]" if required
    } else {
        // All other users -> Single State Selection
        state.multiple = false;
        state.size = 1;
        state.name = "userState";
    }
    
    district.innerHTML = '<option value="">--Select District--</option>';
    district.value = "";

     if (type === "PI" || type === "DI" || type === "NGO") {

        state.disabled = false;

    } else {
        state.disabled = false;
    }

    resetAll();

    show("nameField");
    show("deptField");
    show("desgField");
    const savedLang = localStorage.getItem("siteLanguage") || "en";

    // Default title based on language
    if (title) {
        title.textContent = savedLang === "hi"
          ? "उपयोगकर्ता विवरण"
          : "User Details";
    }
    if (type === "PI" || type === "DI" || type === "NGO") {
        show("districtBox");
    }

    if (type === "NGO") {
    
     title.textContent = savedLang === "hi"
          ? "NGO की जानकारी"
          : "NGO Details";

        hide("nameField");
        hide("deptField");
        hide("desgField");

        show("ngoNameField");
        show("ngoIdField");
        show("ngoRegField");
    }
}

document.addEventListener("DOMContentLoaded", function () {

    // Default form
    show("nameField");
    show("deptField");
    show("desgField");

    // Hide NGO fields
    hide("ngoNameField");
    hide("ngoIdField");
    hide("ngoRegField");

    // Hide district
    hide("districtBox");

    // Default title
    const orgTitle = document.getElementById("orgSectionTitle");
    const savedLang = localStorage.getItem("siteLanguage") || "en";
    if (orgTitle) {
        orgTitle.textContent = savedLang === "hi"
          ? "उपयोगकर्ता विवरण"
          : "User Details";
    }
});

function recaptchaVerified() {

    const btn = document.getElementById("registerBtn");

    btn.disabled = false;
    btn.innerHTML = "Register";
}

function recaptchaExpired() {

    const btn = document.getElementById("registerBtn");

    btn.disabled = true;
    btn.innerHTML = "Verify reCAPTCHA to Register";
}

function loadDistricts(stateCode) {

    const district = document.getElementById("userDistrict");

    // CLEAR EVERYTHING FIRST
    district.innerHTML = '<option value="">--Select District--</option>';

    if (!stateCode) return;

    fetch('/register/districts/' + stateCode)
        .then(res => res.json())
        .then(data => {

            let options = '<option value="">--Select District--</option>';

            data.forEach(d => {
                options += `
                    <option value="${d.distCode}">
                        ${d.distName}
                    </option>
                `;
            });

            district.innerHTML = options;
        });
}

function validateForm() {

    let valid = true;
    let type = document.getElementById("userType").value;

    // CLEAR OLD ERRORS
    document.querySelectorAll(".error-text").forEach(e => e.innerText = "");

    let state = document.getElementById("userState").value;
    let district = document.getElementById("userDistrict").value;
    let name = document.getElementById("userName").value;
    let userType = document.getElementById("userType").value;
    let email = document.getElementById("emailField").value;
    let mobile = document.getElementById("mobileField").value;
    let address = document.getElementById("addressField").value;    
     
    let dept = document.getElementById("userDepartment").value;    
    let desg = document.getElementById("userDesignation").value;    
    
    let ngoname = document.getElementById("userNameNgo").value;    
    let ngoid = document.getElementById("userNgoid").value;    
    let ngoregid = document.getElementById("userRegwith").value;     

    // STATE validation (all users)
    if (!state) {
        document.getElementById("stateError").innerText =
            "Please select State";
        valid = false;
    }
    
    if (!userType) {
        document.getElementById("userError").innerText =
            "Please select User Type";
        valid = false;
    }
    
     if (!validateEmail()) {
        valid = false;
    }
    
     if (!validateMobile()) {
         valid = false;
    }
    
     if (!validateAddress()) {
        valid = false;
    }

    // DISTRICT validation (PIA/WCDC/NGO)
    if (type === "PI" || type === "DI" || type === "NGO") {
        if (!district) {
            document.getElementById("districtError").innerText =
                "Please select district";
            valid = false;
        }
    }

    // NAME validation (non-NGO)
    if (type !== "NGO") {
        if (!name) {
            document.getElementById("nameError").innerText =
                "Please enter Name";
            valid = false;
        }
        if (!dept) {
            document.getElementById("departmentError").innerText =
                "Please enter Department";
            valid = false;
        }
        if (!desg) {
            document.getElementById("designationError").innerText =
                "Please enter Designation";
            valid = false;
        }
    }
    else{
    if (!ngoname) {
            document.getElementById("ngonameError").innerText =
                "Please enter NGO Name";
            valid = false;
        }
        if (!ngoid) {
            document.getElementById("ngoidError").innerText =
                "Please enter NGO ID";
            valid = false;
        }
        if (!ngoregid) {
            document.getElementById("registeredError").innerText =
                "Please enter NGO registered with Details";
            valid = false;
        }
    }

    return valid;
}

function validateEmail() {

    let email = document.getElementById("emailField").value.trim();

    let emailError = document.getElementById("emailError");

    emailError.innerHTML = "";

    if (email === "") {
        emailError.innerHTML = "Email is required.";
        return false;
    }

    const pattern = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;

    if (!pattern.test(email)) {
        emailError.innerHTML = "Please enter a valid email address.";
        return false;
    }

    return true;
}

function validateMobile() {

    let mobile = document.getElementById("mobileField").value.trim();

    let mobileError = document.getElementById("mobileError");

    mobileError.innerHTML = "";

    // Allow only digits
    mobile = mobile.replace(/\D/g, "");
    document.getElementById("mobileField").value = mobile;

    if (mobile === "") {
        mobileError.innerHTML = "Mobile number is required.";
        return false;
    }

    if (!/^[6-9]\d{9}$/.test(mobile)) {
        mobileError.innerHTML = "Please enter a valid 10-digit mobile number.";
        return false;
    }

    return true;
}

function validateAddress() {

    let address = document.getElementById("addressField").value.trim();

    let addressError = document.getElementById("addressError");

    addressError.innerHTML = "";

	if (address === "") {
	        addressError.innerHTML = "Address is required.";
	        return false;
	    }
   else if (address.length < 10) {
		    addressError.innerHTML =
		        "Address must contain at least 10 characters.";
		    return false;
		}    

    return true;
}

function sendOtp() {

    if (!validateForm()) {
        return;
    }

	const btn = document.getElementById("registerBtn");

	btn.disabled = true;
	btn.innerHTML = "Sending OTP...";
	
    let data = {

        userType: document.getElementById("userType").value,

        userState: $("#userState").val(),

        userDistrict: $("#userDistrict").val(),

        userName: $("#userName").val(),

        userDepartment: $("#userDepartment").val(),

        userDesignation: $("#userDesignation").val(),

        userNameNgo: $("#userNameNgo").val(),

        userNgoid: $("#userNgoid").val(),

        userRegwith: $("#userRegwith").val(),

        userEmailId: $("#emailField").val(),

        userMobileNo: $("#mobileField").val(),

        userAddres: $("#addressField").val()
    };

    fetch("/register/sendOtp", {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(data)

    })

	.then(response => {

	    if (!response.ok) {
	        throw new Error("Request Failed");
	    }

	    return response.text();
	})

    .then(result => {

        if(result === "OTP_SENT"){

            document.getElementById("otpEmail").innerHTML =
                    data.userEmailId;

            const modal = new bootstrap.Modal(
                    document.getElementById("otpModal"));

            modal.show();

            startTimer();

        }
        else if(result === "EMAIL_ALREADY_EXISTS"){

            document.getElementById("emailError").innerHTML =
                    "Email is already registered.";

        }
        else{

            alert("Unable to send OTP.");

        }

    })

    .catch(error => {

        console.error(error);

        alert("Server Error");

    });

}

let countdown;

function startTimer(){

    let seconds = 60;

    let resendBtn = document.getElementById("resendOtpBtn");

    resendBtn.disabled = true;

    countdown = setInterval(function(){

        document.getElementById("timer").innerHTML =
                "Resend in " + seconds + " sec";

        seconds--;

        if(seconds < 0){

            clearInterval(countdown);

            document.getElementById("timer").innerHTML="";

            resendBtn.disabled=false;

        }

    },1000);

}



function resendOtp() {

	document.getElementById("otpError").innerHTML = "";
    const email = document.getElementById("emailField").value;

    fetch("/register/resendOtp?email=" + encodeURIComponent(email), {
        method: "POST"
    })
    .then(response => response.text())
    .then(result => {

        if (result === "OTP_RESENT") {

            document.getElementById("otpError").innerHTML =
                "<span class='text-success'>A new OTP has been sent to your email.</span>";

            startTimer();

        } else if (result === "NOT_FOUND") {

            document.getElementById("otpError").innerHTML =
                "OTP session not found. Please register again.";

        } 
		else if (result === "MAX_RESEND_LIMIT") {
		    document.getElementById("otpError").innerHTML =
		        "Maximum resend limit reached. Please register again.";
		}
		else {

            document.getElementById("otpError").innerHTML =
                "Unable to resend OTP.";

        }
		

    })
    .catch(error => {

        console.error(error);

        document.getElementById("otpError").innerHTML =
            "Server error.";

    });
}


function verifyOtp() {

    let email = document.getElementById("emailField").value.trim();
    let otp = document.getElementById("enteredOtp").value.trim();

    document.getElementById("otpError").innerHTML = "";

    if (otp === "") {
        document.getElementById("otpError").innerHTML =
            "Please enter OTP.";
        return;
    }

    if (otp.length !== 6) {
        document.getElementById("otpError").innerHTML =
            "OTP must be 6 digits.";
        return;
    }

    fetch("/register/verifyOtp", {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({

            email: email,
            otp: otp

        })

    })

    .then(response => response.text())

    .then(result => {

        if (result === "SUCCESS") {

            bootstrap.Modal.getInstance(
                document.getElementById("otpModal")
            ).hide();

            const successModal = new bootstrap.Modal(
                document.getElementById("successModal")
            );

            successModal.show();

            document.getElementById("regForm").reset();

            grecaptcha.reset();

        }
        else if (result === "INVALID_OTP") {

            document.getElementById("otpError").innerHTML =
                "Invalid OTP.";

        }
        else if (result === "OTP_EXPIRED") {

            document.getElementById("otpError").innerHTML =
                "OTP has expired. Please click Resend OTP.";

        }
        else if (result === "ALREADY_VERIFIED") {

            document.getElementById("otpError").innerHTML =
                "This email has already been verified.";

        }
        else {

            document.getElementById("otpError").innerHTML =
                "Unable to verify OTP. Please try again.";

        }

    })

    .catch(error => {

        console.error(error);

        document.getElementById("otpError").innerHTML =
            "Server error. Please try again.";

    });

}