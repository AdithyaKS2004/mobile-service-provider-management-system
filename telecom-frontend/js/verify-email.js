const verifyEmailForm =
    document.getElementById("verifyEmailForm");

const verifyButton =
    document.getElementById("verifyButton");

const resendButton =
    document.getElementById("resendButton");

const codeInput =
    document.getElementById("code");

const message =
    document.getElementById("message");

const emailDisplay =
    document.getElementById("emailDisplay");


const email =
    sessionStorage.getItem("verificationEmail");


/*
 * Make sure the user reached this page
 * through registration.
 */

if (!email) {

    window.location.href = "register.html";
}


/*
 * Display email address.
 */

emailDisplay.textContent = email;


/*
 * Only allow digits in OTP field.
 */

codeInput.addEventListener(
    "input",
    function () {

        this.value = this.value
            .replace(/\D/g, "")
            .slice(0, 6);

    }
);


/*
 * Email verification
 */

verifyEmailForm.addEventListener(
    "submit",
    async function (event) {

        event.preventDefault();

        const code =
            codeInput.value.trim();


        if (code.length !== 6) {

            showMessage(
                "Please enter the 6-digit verification code.",
                "error"
            );

            return;
        }


        verifyButton.disabled = true;

        verifyButton.textContent =
            "Verifying...";


        try {

            const response =
                await apiRequest(
                    "/api/auth/verify-email",
                    {
                        method: "POST",

                        body: JSON.stringify({
                            email: email,
                            code: code
                        })
                    }
                );


            console.log(
                "Email verification successful:",
                response
            );


            showMessage(
                response ||
                "Email verified successfully.",
                "success"
            );


            /*
             * Remove temporary registration data.
             */

            sessionStorage.removeItem(
                "verificationEmail"
            );


            /*
             * Give the user a moment
             * to read the success message.
             */

            setTimeout(
                function () {

                    window.location.href =
                        "index.html";

                },
                2000
            );

        } catch (error) {

            console.error(
                "Email verification error:",
                error
            );


            let errorText =
                "Unable to verify email. Please try again.";


            if (error.data?.message) {

                errorText =
                    error.data.message;

            }

            else if (error.data?.error) {

                errorText =
                    error.data.error;

            }


            showMessage(
                errorText,
                "error"
            );

        } finally {

            verifyButton.disabled = false;

            verifyButton.textContent =
                "Verify Email";

        }

    }
);


/*
 * Resend verification code
 */

resendButton.addEventListener(
    "click",
    async function () {

        resendButton.disabled = true;

        resendButton.textContent =
            "Sending...";


        try {

            const response =
                await apiRequest(
                    "/api/auth/resend-email-verification",
                    {
                        method: "POST",

                        body: JSON.stringify({
                            email: email
                        })
                    }
                );


            showMessage(
                response ||
                "A new verification code has been sent.",
                "success"
            );

        } catch (error) {

            console.error(
                "Resend verification error:",
                error
            );


            let errorText =
                "Unable to resend verification code.";


            if (error.data?.message) {

                errorText =
                    error.data.message;

            }

            else if (error.data?.error) {

                errorText =
                    error.data.error;

            }


            showMessage(
                errorText,
                "error"
            );

        } finally {

            resendButton.disabled = false;

            resendButton.textContent =
                "Resend Code";

        }

    }
);


/*
 * Display success/error message.
 */

function showMessage(text, type) {

    message.textContent = text;

    message.className =
        "mb-4 p-3 rounded-lg text-sm";


    if (type === "success") {

        message.classList.add(
            "bg-green-100",
            "text-green-700"
        );

    } else {

        message.classList.add(
            "bg-red-100",
            "text-red-700"
        );

    }


    message.classList.remove("hidden");
}