async function loadAdminProfile() {

    try {

        const user =
            await apiRequest(
                "/api/auth/me"
            );


        /*
         * Security check.
         *
         * Even though Spring Security already protects
         * /api/admin/**, the frontend should also avoid
         * displaying the admin dashboard to a customer.
         */

        if (user.role !== "ADMIN") {

            window.location.href =
                "../index.html";

            return;
        }


        const nameElement =
            document.getElementById(
                "adminName"
            );


        if (user.fullName) {

            nameElement.textContent =
                user.fullName;

        } else if (user.email) {

            nameElement.textContent =
                user.email;

        }

    } catch (error) {

        console.error(
            "Unable to load admin profile:",
            error
        );


        /*
         * 401 / 403 means the session is no longer
         * valid or the user isn't authorized.
         */

        if (
            error.status === 401 ||
            error.status === 403
        ) {

            window.location.href =
                "../index.html";

        }

    }
}



async function logout() {

    try {

        await apiRequest(
            "/api/auth/logout",
            {
                method: "POST"
            }
        );

    } catch (error) {

        console.error(
            "Logout error:",
            error
        );

    } finally {

        /*
         * Whether the backend logout succeeds or not,
         * don't leave the user on the protected page.
         */

        window.location.href =
            "../index.html";
    }
}



document
    .getElementById("logoutButton")
    .addEventListener(
        "click",
        logout
    );



loadAdminProfile();