async function loadSubscriptions() {

    const container =
        document.getElementById(
            "subscriptionsContainer"
        );

    try {

        const subscriptions =
            await apiRequest(
                "/api/user/subscriptions"
            );


        if (subscriptions.length === 0) {

            container.innerHTML = `
                <div class="bg-white p-6 rounded-xl shadow">

                    <p class="text-gray-500">
                        You don't have an active subscription.
                    </p>

                    <a
                        href="plans.html"
                        class="inline-block mt-4
                               text-blue-600 font-semibold">

                        Browse Plans →

                    </a>

                </div>
            `;

            return;
        }


        container.innerHTML =
            subscriptions
                .map(createSubscriptionCard)
                .join("");


    } catch (error) {

        console.error(error);

        if (
            error.status === 401 ||
            error.status === 403
        ) {

            window.location.href =
                "../index.html";

            return;
        }


        container.innerHTML = `
            <div class="bg-red-100 text-red-700
                        p-4 rounded-lg">

                Unable to load subscriptions.

            </div>
        `;
    }
}


function createSubscriptionCard(subscription) {

    return `
        <div class="bg-white rounded-xl shadow p-6">

            <div class="flex justify-between
                        items-start">

                <div>

                    <h3 class="text-xl font-bold">
                        ${subscription.planName}
                    </h3>

                    <p class="text-gray-500 text-sm">
                        SIM #${subscription.simCardId}
                    </p>

                </div>


                <span
                    class="bg-green-100
                           text-green-700
                           px-3 py-1
                           rounded-full
                           text-xs font-semibold">

                    ${subscription.status}

                </span>

            </div>


            <div class="mt-6 space-y-4">

                <div>

                    <p class="text-sm text-gray-500">
                        Start Date
                    </p>

                    <p class="font-medium">
                        ${subscription.startDate}
                    </p>

                </div>


                <div>

                    <p class="text-sm text-gray-500">
                        Expiry Date
                    </p>

                    <p class="font-medium">
                        ${subscription.expiryDate}
                    </p>

                </div>


                <div>

                    <p class="text-sm text-gray-500">
                        Remaining Data
                    </p>

                    <p class="font-medium">
                        ${subscription.remainingDataMb} MB
                    </p>

                </div>


                <div>

                    <p class="text-sm text-gray-500">
                        Remaining Talktime
                    </p>

                    <p class="font-medium">
                        ${subscription.remainingTalktimeMins}
                        minutes
                    </p>

                </div>

            </div>

        </div>
    `;
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
    .addEventListener("click", logout);


loadSubscriptions();