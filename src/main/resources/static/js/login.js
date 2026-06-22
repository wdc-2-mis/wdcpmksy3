

function generateSalt(length) {
    var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var salt = '';
    for (var i = 0; i < length; i++) {
        salt += characters.charAt(Math.floor(Math.random() * characters.length));
    }
    return salt;
}

$(document).ready(function () {

    $("#login").on("click", function (e) {
        e.preventDefault();

        let salt = generateSalt(10);

        let plainPwd = $("#encrypted_pass").val();
        let password = sha512(plainPwd);

        let userid = $("#userId").val().toUpperCase();

        password = sha512(userid + password);

        let encryptedPwd = sha512(password + salt);

        $("#encrypted_pass").val(encryptedPwd);
        $("#userId").val(userid);

        let result = true;

        const fields = [
            $("#userId"),
            $("#encrypted_pass"),
            $("#captcha")
        ];

        $.each(fields, function (i, field) {

            if (!loginblankCheck(field)) {
                SetErrorFor(field, "cannot be blank");
                result = false;
                return false;
            } else {
                SetSuccessFor(field);
            }
        });

        if (!result) {
            return false;
        }

        $.ajax({
            url: "/login",
            type: "POST",
            data: $("#loginForm").serialize() +
                  "&salt=" + encodeURIComponent(salt),

            success: function (data) {

                console.log("Response: " + data);

                if (data.includes("alreadylogin")) {

                    showError("You are already signed in with the same User Id. Kindly logout first.");

                } else if (data.includes("captchaerror")) {

                    showError("Captcha is not matched !");
                    $("#captcha").focus();

                } else if (data.includes("failed")) {

                    showError("Unregistered User!");

                } else if (data.includes("loginsuccess")) {

                    window.location.href = "/loginsuccess";

                } else if (data.includes("invalidcredentials")) {

                    showError("Invalid Credential !");
                    $("#user_id").val("");

                } else if (data.includes("loginerror")) {

                    showError("User authentication failed. You have only one more chance to Login!");
                    $("#user_id").val("");

                } else if (data.includes("userlocked")) {

                    showError("User authentication failed. Your user-id has been locked!");
                    $("#user_id").val("");

                } else if (data.includes("locked")) {

                    showError("Your user-id has been locked for some time!");
                    $("#user_id").val("");

                } else if (data.includes("logout")) {

                    $(".text-danger").html("Successfully Logout!!");
                    clearFields();
                }
            },

            error: function (xhr, status, error) {
                console.error("AJAX Error:", error);
            }
        });

    });

});

/* Utility Functions */

function clearFields() {
    $("#encrypted_pass").val("");
    $("#captcha").val("");

    $("#captcha_id").attr("src", "/captcha.jpg?" + Math.random());
}

function showError(message) {
    $(".text-danger").html(message);

    $("#encrypted_pass").val("");
    $("#captcha").val("");

    $("#captcha_id").attr("src", "/captcha.jpg?" + Math.random());
}