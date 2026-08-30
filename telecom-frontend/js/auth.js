const loginForm = document.getElementById("loginForm");

loginForm.addEventListener("submit", async function (event) {

    event.preventDefault();

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;

    const errorMessage =
        document.getElementById("errorMessage");

    errorMessage.classList.add("hidden");

    try {

        const response = await apiRequest(
            "/api/auth/login",
            {
                method: "POST",

                body: JSON.stringify({
                    email: email,
                    password: password
                })
            }
        );

        console.log("Login successful:", response);

        if (response.role === "ADMIN") {

            window.location.href =
                "admin/dashboard.html";

        } else if (response.role === "CUSTOMER") {

            window.location.href =
                "customer/dashboard.html";

        } else {

            throw new Error("Unknown user role");
        }

    } catch (error) {

        console.error("Login error:", error);

        if (error.status === 401) {

            errorMessage.textContent =
                "Invalid email or password.";

        } else if (error.data?.error) {

            errorMessage.textContent =
                error.data.error;

        } else {

            errorMessage.textContent =
                "Unable to login. Please try again.";
        }

        errorMessage.classList.remove("hidden");
    }
});