let selectedPlanId = null;
let selectedPlan = null;


async function loadPlans() {

    const container =
        document.getElementById("plansContainer");

    try {

        const plans =
            await apiRequest("/api/plans");

        const activePlans =
            plans.filter(plan => plan.active !== false);

        if (activePlans.length === 0) {

            container.innerHTML = `
                <div class="bg-white p-6 rounded-xl shadow">
                    <p class="text-gray-500">
                        No active plans are currently available.
                    </p>
                </div>
            `;

            return;
        }

        container.innerHTML =
            activePlans
                .map(createPlanCard)
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
                Unable to load plans.
            </div>
        `;
    }
}


function createPlanCard(plan) {

    return `
        <div class="bg-white rounded-xl shadow
                    p-6 hover:shadow-lg transition">

            <div class="flex justify-between">

                <h3 class="text-xl font-bold text-gray-800">
                    ${plan.name}
                </h3>

                <span class="text-green-600
                             font-semibold">
                    ₹${plan.price}
                </span>

            </div>


            <div class="mt-6 space-y-3">

                <div class="flex justify-between">
                    <span class="text-gray-500">
                        Data
                    </span>

                    <span class="font-medium">
                        ${plan.dataLimitGbPerDay} GB/day
                    </span>
                </div>


                <div class="flex justify-between">
                    <span class="text-gray-500">
                        Talktime
                    </span>

                    <span class="font-medium">
                        ${plan.talktimeMins} mins
                    </span>
                </div>


                <div class="flex justify-between">
                    <span class="text-gray-500">
                        Validity
                    </span>

                    <span class="font-medium">
                        ${plan.validityDays} days
                    </span>
                </div>


                <div class="flex justify-between">
                    <span class="text-gray-500">
                        SMS
                    </span>

                    <span class="font-medium">
                        ${plan.smsCount}
                    </span>
                </div>


                <div class="flex justify-between">
                    <span class="text-gray-500">
                        Plan Type
                    </span>

                    <span class="font-medium">
                        ${plan.planType}
                    </span>
                </div>

                
            </div>

            <button
                onclick='openRechargeModal(${JSON.stringify(plan)})'
                class="w-full mt-6 bg-blue-600
                       text-white py-3 rounded-lg
                       font-semibold hover:bg-blue-700">

                Recharge

            </button>

        </div>
    `;
}


async function openRechargeModal(plan) {

    selectedPlanId = plan.id;
    selectedPlan = plan;

    document.getElementById(
        "rechargeModal"
    ).classList.remove("hidden");

    document.getElementById(
        "selectedPlan"
    ).innerHTML = `

        <p class="font-semibold">
            ${plan.name}
        </p>

        <p class="text-gray-600 mt-1">
            ₹${plan.price} ·
            ${plan.validityDays} days
        </p>

    `;

    await loadActiveSims();
}


function closeRechargeModal() {

    document.getElementById(
        "rechargeModal"
    ).classList.add("hidden");
}


async function loadActiveSims() {

    const select =
        document.getElementById("simSelect");

    try {

        const sims =
            await apiRequest("/api/sim/my");

        const activeSims =
            sims.filter(
                sim => sim.status === "ACTIVE"
            );

        if (activeSims.length === 0) {

            select.innerHTML = `
                <option value="">
                    No active SIM available
                </option>
            `;

            const button =
                document.getElementById(
                    "confirmRechargeButton"
                );

            button.disabled = true;
            button.textContent =
                "No Active SIM Available";

            return;
        }

        select.innerHTML = `
            <option value="">
                Select SIM
            </option>
        `;

        activeSims.forEach(sim => {

            const option =
                document.createElement("option");

            option.value = sim.id;

            option.textContent =
                `SIM #${sim.id} - ${sim.phoneNumber}`;

            select.appendChild(option);
        });

        const button =
            document.getElementById(
                "confirmRechargeButton"
            );

        button.disabled = false;
        button.textContent =
            "Confirm Recharge";

    } catch (error) {

        console.error(error);

        select.innerHTML = `
            <option value="">
                Unable to load SIMs
            </option>
        `;
    }
}


async function recharge() {

    const simCardId =
        document.getElementById(
            "simSelect"
        ).value;

    const paymentMethod =
        document.getElementById(
            "paymentMethod"
        ).value;


    if (!simCardId) {

        showMessage(
            "Please select an active SIM.",
            "error"
        );

        return;
    }


    if (!selectedPlanId) {

        showMessage(
            "Please select a plan.",
            "error"
        );

        return;
    }


    const button =
        document.getElementById(
            "confirmRechargeButton"
        );

    button.disabled = true;
    button.textContent = "Processing...";


    try {

        const response =
            await apiRequest(
                "/api/recharge",
                {
                    method: "POST",

                    body: JSON.stringify({
                        simCardId: Number(simCardId),
                        planId: selectedPlanId,
                        paymentMethod: paymentMethod
                    })
                }
            );


        closeRechargeModal();

        showMessage(
            "Recharge successful!",
            "success"
        );


        console.log(
            "Recharge response:",
            response
        );


        setTimeout(() => {

            window.location.href =
                "subscriptions.html";

        }, 1000);


    } catch (error) {

        console.error(error);

        showMessage(
            error.data?.error ||
            "Recharge failed.",
            "error"
        );

    } finally {

        button.disabled = false;
        button.textContent = "Confirm Recharge";
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
        "confirmRechargeButton"
    )
    .addEventListener(
        "click",
        recharge
    );


document
    .getElementById(
        "logoutButton"
    )
    .addEventListener(
        "click",
        logout
    );


loadPlans();