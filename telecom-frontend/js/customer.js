async function loadCustomerDashboard() {

    try {

        /*
         * Load logged-in customer
         */

        const user =
            await apiRequest(
                "/api/auth/me"
            );


        if (user.role !== "CUSTOMER") {

            window.location.href =
                "../index.html";

            return;
        }


        /*
         * Display welcome message
         */

        document.getElementById(
            "welcomeMessage"
        ).textContent =
            `Welcome, ${user.fullName}`;


        /*
         * Display account information
         */

        document.getElementById(
            "userInfo"
        ).innerHTML = `

            <div
                class="grid grid-cols-1
                       md:grid-cols-3
                       gap-4">

                <div>

                    <p class="text-sm text-gray-500">
                        Name
                    </p>

                    <p class="font-semibold">
                        ${escapeHtml(user.fullName)}
                    </p>

                </div>


                <div>

                    <p class="text-sm text-gray-500">
                        Email
                    </p>

                    <p class="font-semibold">
                        ${escapeHtml(user.email)}
                    </p>

                </div>


                <div>

                    <p class="text-sm text-gray-500">
                        Role
                    </p>

                    <p class="font-semibold">
                        ${escapeHtml(user.role)}
                    </p>

                </div>

            </div>
        `;


        /*
         * Load dashboard information
         */

        await loadDashboardData();


    } catch (error) {

        console.error(
            "Unable to load customer dashboard:",
            error
        );


        if (
            error.status === 401 ||
            error.status === 403
        ) {

            window.location.href =
                "../index.html";
        }
    }
}



async function loadDashboardData() {

    try {

        /*
         * Load customer's SIMs
         */

        const sims =
            await apiRequest(
                "/api/sim/my"
            );


        /*
         * Count SIM statuses
         */

        const activeSims =
            sims.filter(
                sim =>
                    sim.status === "ACTIVE"
            );


        const pendingSims =
            sims.filter(
                sim =>
                    sim.status === "PENDING_KYC"
            );


        document.getElementById(
            "activeSimCount"
        ).textContent =
            activeSims.length;


        document.getElementById(
            "pendingSimCount"
        ).textContent =
            pendingSims.length;



        /*
         * Load subscriptions
         */

        const subscriptions =
            await apiRequest(
                "/api/user/subscriptions"
            );


        document.getElementById(
            "subscriptionCount"
        ).textContent =
            subscriptions.length;


        renderCurrentSubscription(
            subscriptions
        );



        /*
         * Load available plans
         */

        const plans =
            await apiRequest(
                "/api/plans"
            );


        document.getElementById(
            "planCount"
        ).textContent =
            plans.length;


    } catch (error) {

        console.error(
            "Unable to load dashboard data:",
            error
        );


        /*
         * Authentication failure
         */

        if (
            error.status === 401 ||
            error.status === 403
        ) {

            window.location.href =
                "../index.html";

            return;
        }


        showDashboardMessage(
            "Some dashboard information could not be loaded.",
            "error"
        );
    }
}



function renderCurrentSubscription(
    subscriptions
) {

    const container =
        document.getElementById(
            "currentSubscription"
        );


    if (
        !subscriptions ||
        subscriptions.length === 0
    ) {

        container.innerHTML = `

            <div>

                <p class="text-gray-500">
                    You don't have an active subscription.
                </p>

                <a
                    href="plans.html"
                    class="inline-block mt-4
                           text-blue-600
                           font-semibold
                           hover:underline">

                    Browse Plans →

                </a>

            </div>

        `;

        return;
    }


    /*
     * Your backend currently returns active
     * subscriptions from /api/user/subscriptions.
     *
     * Therefore the first result can be
     * displayed as the current subscription.
     */

    const subscription =
        subscriptions[0];


    container.innerHTML = `

        <div
            class="grid grid-cols-1
                   md:grid-cols-2
                   lg:grid-cols-4
                   gap-6">


            <div>

                <p class="text-sm text-gray-500">
                    Plan
                </p>

                <p class="font-semibold text-lg">
                    ${escapeHtml(
                        subscription.planName
                    )}
                </p>

            </div>


            <div>

                <p class="text-sm text-gray-500">
                    SIM
                </p>

                <p class="font-semibold">
                    SIM #${subscription.simCardId}
                </p>

            </div>


            <div>

                <p class="text-sm text-gray-500">
                    Expiry Date
                </p>

                <p class="font-semibold">
                    ${subscription.expiryDate}
                </p>

            </div>


            <div>

                <p class="text-sm text-gray-500">
                    Status
                </p>

                <span
                    class="inline-block
                           mt-1
                           bg-green-100
                           text-green-700
                           px-3 py-1
                           rounded-full
                           text-xs
                           font-semibold">

                    ${subscription.status}

                </span>

            </div>

        </div>


        <div
            class="grid grid-cols-1
                   md:grid-cols-2
                   gap-6 mt-6">


            <div>

                <p class="text-sm text-gray-500">
                    Remaining Data
                </p>

                <p class="text-xl font-bold">
                    ${subscription.remainingDataMb} MB
                </p>

            </div>


            <div>

                <p class="text-sm text-gray-500">
                    Remaining Talktime
                </p>

                <p class="text-xl font-bold">
                    ${subscription.remainingTalktimeMins}
                    minutes
                </p>

            </div>

        </div>

    `;
}



function showDashboardMessage(
    message,
    type
) {

    const element =
        document.getElementById(
            "dashboardMessage"
        );


    element.textContent =
        message;


    element.className =
        "mb-6 p-4 rounded-lg";


    if (type === "success") {

        element.classList.add(
            "bg-green-100",
            "text-green-700"
        );

    } else {

        element.classList.add(
            "bg-red-100",
            "text-red-700"
        );
    }
}



function escapeHtml(value) {

    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}



async function logout() {

    try {

        await apiRequest(
            "/api/auth/logout",
            {
                method: "POST"
            }
        );

    } finally {

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


loadCustomerDashboard();