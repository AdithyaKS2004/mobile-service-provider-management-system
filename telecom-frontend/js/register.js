const registerForm =
    document.getElementById("registerForm");


registerForm.addEventListener(
    "submit",
    async function (event) {

        event.preventDefault();


        const fullName =
            document.getElementById(
                "fullName"
            ).value.trim();


        const email =
            document.getElementById(
                "email"
            ).value.trim();


        const phone =
            document.getElementById(
                "phone"
            ).value.trim();


        const password =
            document.getElementById(
                "password"
            ).value;


        const confirmPassword =
            document.getElementById(
                "confirmPassword"
            ).value;


        const message =
            document.getElementById(
                "message"
            );


        const button =
            document.getElementById(
                "registerButton"
            );


        message.classList.add(
            "hidden"
        );


        /*
         * Frontend validation
         */

        if (password !== confirmPassword) {

            showMessage(
                "Passwords do not match.",
                "error"
            );

            return;
        }


        if (phone.length !== 10) {

            showMessage(
                "Mobile number must contain exactly 10 digits.",
                "error"
            );

            return;
        }


        if (!/^[6-9]\d{9}$/.test(phone)) {

            showMessage(
                "Please enter a valid Indian mobile number.",
                "error"
            );

            return;
        }


        /*
         * Disable button while request
         * is being processed.
         */

        button.disabled = true;

        button.textContent =
            "Creating Account...";


        try {

            const response =
                await apiRequest(
                    "/api/auth/register",
                    {
                        method: "POST",

                        body: JSON.stringify({

                            fullName:
                                fullName,

                            email:
                                email,

                            password:
                                password,

                            phone:
                                phone

                        })
                    }
                );


            console.log(
                "Registration successful:",
                response
            );


            showMessage(
                "Account created successfully! Redirecting to login...",
                "success"
            );


            /*
             * Registration does NOT
             * automatically log the user in.
             *
             * Redirect to login page.
             */

            setTimeout(
                function () {

                    window.location.href =
                        "index.html";

                },
                1500
            );


        } catch (error) {

            console.error(
                "Registration error:",
                error
            );


            let errorText =
                "Unable to create account. Please try again.";


            /*
             * Duplicate email / phone
             */

            if (
                error.data?.message
            ) {

                errorText =
                    error.data.message;

            }


            else if (
                error.data?.error
            ) {

                errorText =
                    error.data.error;

            }


            /*
             * Validation errors
             */

            else if (
                error.status === 400
            ) {

                errorText =
                    "Please check your entered details.";

            }


            showMessage(
                errorText,
                "error"
            );


        } finally {

            button.disabled = false;

            button.textContent =
                "Create Account";

        }

    }
);



function showMessage(
    text,
    type
) {

    const message =
        document.getElementById(
            "message"
        );


    message.textContent =
        text;


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


    message.classList.remove(
        "hidden"
    );

}