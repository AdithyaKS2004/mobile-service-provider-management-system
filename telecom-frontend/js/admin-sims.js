async function createSim(event) {

    event.preventDefault();


    const phoneNumber =
        document.getElementById(
            "phoneNumber"
        ).value.trim();


    const imsiNumber =
        document.getElementById(
            "imsiNumber"
        ).value.trim();


    if (!/^[0-9]{10}$/.test(phoneNumber)) {

        showMessage(
            "Phone number must contain exactly 10 digits.",
            "error"
        );

        return;
    }


    if (!/^[0-9]{15}$/.test(imsiNumber)) {

        showMessage(
            "IMSI number must contain exactly 15 digits.",
            "error"
        );

        return;
    }


    const button =
        document.getElementById(
            "createSimButton"
        );


    button.disabled = true;
    button.textContent = "Creating...";


    try {

        const sim =
            await apiRequest(
                "/api/admin/sims",
                {
                    method: "POST",

                    body: JSON.stringify({
                        phoneNumber: phoneNumber,
                        imsiNumber: imsiNumber
                    })
                }
            );


        showMessage(
            `SIM #${sim.id} created successfully.`,
            "success"
        );


        document
            .getElementById("createSimForm")
            .reset();


        await loadPendingSims();


    } catch (error) {

        console.error(error);

        showMessage(
            getApiErrorMessage(
                error,
                "Unable to create SIM."
            ),
            "error"
        );

    } finally {

        button.disabled = false;
        button.textContent = "Create SIM";
    }
}


async function loadPendingSims() {

    const container =
        document.getElementById(
            "pendingSims"
        );


    try {

        const sims =
            await apiRequest(
                "/api/admin/sims/pending"
            );


        if (sims.length === 0) {

            container.innerHTML = `
                <div class="bg-white rounded-xl
                            shadow p-6">

                    <p class="text-gray-500">
                        There are no pending activation requests.
                    </p>

                </div>
            `;

            return;
        }


        container.innerHTML =
            sims
                .map(createPendingSimCard)
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

                Unable to load pending SIM requests.

            </div>
        `;
    }
}


function createPendingSimCard(sim) {

    return `
        <div class="bg-white rounded-xl
                    shadow p-6">

            <div class="flex justify-between
                        items-start">

                <div>

                    <h4 class="text-lg font-bold">
                        SIM #${sim.id}
                    </h4>

                    <p class="text-sm text-gray-500">
                        Customer ID:
                        ${sim.userId ?? "N/A"}
                    </p>

                </div>


                <span
                    class="bg-yellow-100
                           text-yellow-700
                           px-3 py-1
                           rounded-full
                           text-xs font-semibold">

                    ${sim.status}

                </span>

            </div>


            <div class="mt-5 space-y-3">

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


            <button
                onclick="approveSim(${sim.id})"
                class="w-full mt-6
                       bg-green-600 text-white
                       py-3 rounded-lg
                       font-semibold
                       hover:bg-green-700">

                Approve SIM

            </button>

        </div>
    `;
}


async function approveSim(simId) {

    const confirmed =
        confirm(
            `Approve SIM #${simId}?`
        );


    if (!confirmed) {
        return;
    }


    try {

        await apiRequest(
            `/api/admin/sims/${simId}/approve`,
            {
                method: "PATCH"
            }
        );


        showMessage(
            `SIM #${simId} approved successfully.`,
            "success"
        );


        await loadPendingSims();


    } catch (error) {

        console.error(error);

        showMessage(
            getApiErrorMessage(
                error,
                "Unable to approve SIM."
            ),
            "error"
        );
    }
}


function showMessage(message, type) {

    const element =
        document.getElementById(
            "message"
        );


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


function getApiErrorMessage(
    error,
    fallback
) {

    if (error?.data?.error) {
        return error.data.error;
    }

    if (error?.data?.message) {
        return error.data.message;
    }

    return fallback;
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
    .getElementById(
        "createSimForm"
    )
    .addEventListener(
        "submit",
        createSim
    );


document
    .getElementById(
        "refreshPendingButton"
    )
    .addEventListener(
        "click",
        loadPendingSims
    );


document
    .getElementById(
        "logoutButton"
    )
    .addEventListener(
        "click",
        logout
    );


loadPendingSims();