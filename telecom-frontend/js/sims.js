async function loadMySims() {

    const container =
        document.getElementById("mySims");

    try {

        const sims =
            await apiRequest("/api/sim/my");

        if (sims.length === 0) {

            container.innerHTML = `
                <div class="bg-white p-6 rounded-xl shadow">
                    <p class="text-gray-500">
                        You don't have any SIM cards yet.
                    </p>
                </div>
            `;

            return;
        }

        container.innerHTML = sims
            .map(sim => createSimCard(sim, false))
            .join("");

    } catch (error) {

        handleApiError(error, container);
    }
}


async function loadAvailableSims() {

    const container =
        document.getElementById("availableSims");

    try {

        const sims =
            await apiRequest("/api/sim/available");

        if (sims.length === 0) {

            container.innerHTML = `
                <div class="bg-white p-6 rounded-xl shadow">
                    <p class="text-gray-500">
                        No SIM cards are currently available.
                    </p>
                </div>
            `;

            return;
        }

        container.innerHTML = sims
            .map(sim => createSimCard(sim, true))
            .join("");

    } catch (error) {

        handleApiError(error, container);
    }
}


function createSimCard(sim, available) {

    const statusClass =
        getStatusClass(sim.status);

    return `
        <div class="bg-white rounded-xl shadow p-6">

            <div class="flex justify-between items-start">

                <h4 class="text-lg font-semibold">
                    SIM #${sim.id}
                </h4>

                <span class="${statusClass}
                             px-3 py-1 rounded-full
                             text-xs font-semibold">
                    ${sim.status}
                </span>

            </div>


            <div class="mt-5 space-y-2">

                <div>
                    <p class="text-xs text-gray-500">
                        Phone Number
                    </p>

                    <p class="font-medium">
                        ${sim.phoneNumber}
                    </p>
                </div>


                <div>
                    <p class="text-xs text-gray-500">
                        IMSI Number
                    </p>

                    <p class="font-medium">
                        ${sim.imsiNumber}
                    </p>
                </div>

            </div>


            ${
                available
                ?
                `
                <button
                    onclick="activateSim(${sim.id})"
                    class="w-full mt-6 bg-blue-600
                           text-white py-2 rounded-lg
                           hover:bg-blue-700">

                    Request Activation

                </button>
                `
                :
                ""
            }

        </div>
    `;
}


function getStatusClass(status) {

    switch (status) {

        case "ACTIVE":
            return "bg-green-100 text-green-700";

        case "PENDING_KYC":
            return "bg-yellow-100 text-yellow-700";

        case "AVAILABLE":
            return "bg-blue-100 text-blue-700";

        default:
            return "bg-gray-100 text-gray-700";
    }
}


async function activateSim(simId) {

    const confirmed =
        confirm(
            "Do you want to request activation for this SIM?"
        );

    if (!confirmed) {
        return;
    }

    try {

        await apiRequest(
            "/api/sim/activate",
            {
                method: "POST",

                body: JSON.stringify({
                    simCardId: simId
                })
            }
        );

        showMessage(
            "SIM activation request submitted successfully.",
            "success"
        );

        await loadMySims();
        await loadAvailableSims();

    } catch (error) {

        console.error(error);

        const message =
            error.data?.error ||
            "Unable to activate SIM.";

        showMessage(
            message,
            "error"
        );
    }
}


function showMessage(message, type) {

    const element =
        document.getElementById("message");

    element.textContent = message;

    element.className =
        "mt-6 p-4 rounded-lg";

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


function handleApiError(error, container) {

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
            Unable to load SIM information.
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


loadMySims();
loadAvailableSims();