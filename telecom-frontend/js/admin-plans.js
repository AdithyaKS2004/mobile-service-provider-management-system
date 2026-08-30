async function loadPlans() {

    const container =
        document.getElementById(
            "plansContainer"
        );


    try {

        const plans =
            await apiRequest(
                "/api/admin/plans"
            );


        if (!plans || plans.length === 0) {

            container.innerHTML = `
                <div class="bg-white rounded-xl
                            shadow p-6">

                    <p class="text-gray-500">
                        No plans available.
                    </p>

                </div>
            `;

            return;
        }


        container.innerHTML =
            plans
                .map(createPlanCard)
                .join("");


    } catch (error) {

        console.error(
            "Unable to load plans:",
            error
        );


        container.innerHTML = `
            <div class="bg-red-100
                        text-red-700
                        p-4 rounded-lg">

                Unable to load plans.

            </div>
        `;
    }
}



function createPlanCard(plan) {

    const active =
        plan.active === true;


    return `
        <div
            class="bg-white rounded-xl
                   shadow p-6">


            <!-- HEADER -->

            <div
                class="flex justify-between
                       items-start">


                <div>

                    <h4
                        class="text-lg
                               font-bold
                               text-gray-800">

                        ${escapeHtml(
                            plan.name
                        )}

                    </h4>

                    <p
                        class="text-sm
                               text-gray-500">

                        Plan #${plan.id}

                    </p>

                </div>


                ${
                    active
                    ?
                    `
                    <span
                        class="bg-green-100
                               text-green-700
                               px-3 py-1
                               rounded-full
                               text-xs
                               font-semibold">

                        ACTIVE

                    </span>
                    `
                    :
                    `
                    <span
                        class="bg-red-100
                               text-red-700
                               px-3 py-1
                               rounded-full
                               text-xs
                               font-semibold">

                        INACTIVE

                    </span>
                    `
                }

            </div>



            <!-- PRICE -->

            <div class="mt-5">

                <p
                    class="text-3xl
                           font-bold
                           text-gray-800">

                    ₹${Number(
                        plan.price
                    ).toFixed(2)}

                </p>

            </div>



            <!-- DETAILS -->

            <div
                class="mt-5
                       space-y-3
                       text-sm">


                <div
                    class="flex justify-between">

                    <span
                        class="text-gray-500">

                        Validity

                    </span>

                    <span
                        class="font-medium">

                        ${plan.validityDays}
                        days

                    </span>

                </div>


                <div
                    class="flex justify-between">

                    <span
                        class="text-gray-500">

                        Data / Day

                    </span>

                    <span
                        class="font-medium">

                        ${plan.dataLimitGbPerDay}
                        GB

                    </span>

                </div>


                <div
                    class="flex justify-between">

                    <span
                        class="text-gray-500">

                        Talktime

                    </span>

                    <span
                        class="font-medium">

                        ${plan.talktimeMins}
                        mins

                    </span>

                </div>

            </div>



            <!-- ACTION -->

            <button
                onclick="togglePlan(
                    ${plan.id},
                    ${active}
                )"
                class="w-full mt-6
                       ${
                           active
                           ?
                           "bg-red-600 hover:bg-red-700"
                           :
                           "bg-green-600 hover:bg-green-700"
                       }
                       text-white
                       py-3
                       rounded-lg
                       font-semibold">

                ${
                    active
                    ?
                    "Deactivate Plan"
                    :
                    "Activate Plan"
                }

            </button>


        </div>
    `;
}



async function createPlan(event) {

    event.preventDefault();


    const name =
        document.getElementById(
            "planName"
        ).value.trim();


    const price =
        Number(
            document.getElementById(
                "price"
            ).value
        );


    const validityDays =
        Number(
            document.getElementById(
                "validityDays"
            ).value
        );


    const dataLimitGbPerDay =
        Number(
            document.getElementById(
                "dataLimitGbPerDay"
            ).value
        );


    const talktimeMins =
        Number(
            document.getElementById(
                "talktimeMins"
            ).value
        );
    
    
    const smsCount =
        Number(
            document.getElementById(
                "smsCount"
            ).value
        );
    
    
    const planType =
        document.getElementById(
            "planType"
        ).value;



    if (!name) {

        showMessage(
            "Plan name is required.",
            "error"
        );

        return;
    }


    if (
        !Number.isFinite(price) ||
        price <= 0
    ) {

        showMessage(
            "Price must be greater than zero.",
            "error"
        );

        return;
    }


    if (
        !Number.isInteger(validityDays) ||
        validityDays <= 0
    ) {

        showMessage(
            "Validity must be a positive number of days.",
            "error"
        );

        return;
    }


    if (
        !Number.isFinite(dataLimitGbPerDay) ||
        dataLimitGbPerDay < 0
    ) {

        showMessage(
            "Data limit cannot be negative.",
            "error"
        );

        return;
    }


    if (
        !Number.isInteger(talktimeMins) ||
        talktimeMins < 0
    ) {

        showMessage(
            "Talktime cannot be negative.",
            "error"
        );

        return;
    }


    const button =
        document.getElementById(
            "createPlanButton"
        );


    button.disabled = true;
    button.textContent = "Creating...";


    try {

        await apiRequest(
            "/api/admin/plans",
            {
                method: "POST",

                body: JSON.stringify({
                    name,
                    price,
                    validityDays,
                    dataLimitGbPerDay,
                    talktimeMins,
                    smsCount,
                    planType
                })
            }
        );


        showMessage(
            "Plan created successfully.",
            "success"
        );


        document
            .getElementById(
                "createPlanForm"
            )
            .reset();


        await loadPlans();


    } catch (error) {

        console.error(error);


        showMessage(
            getApiErrorMessage(
                error,
                "Unable to create plan."
            ),
            "error"
        );

    } finally {

        button.disabled = false;
        button.textContent = "Create Plan";
    }
}


async function togglePlan(
    planId,
    currentlyActive
) {

    const newStatus =
        !currentlyActive;


    const action =
        newStatus
        ? "activate"
        : "deactivate";


    const confirmed =
        confirm(
            `Are you sure you want to ${action} this plan?`
        );


    if (!confirmed) {
        return;
    }


    try {

        await apiRequest(
            `/api/admin/plans/${planId}/status?active=${newStatus}`,
            {
                method: "PATCH"
            }
        );


        showMessage(
            `Plan ${action}d successfully.`,
            "success"
        );


        await loadPlans();


    } catch (error) {

        console.error(
            "Unable to update plan status:",
            error
        );


        showMessage(
            getApiErrorMessage(
                error,
                `Unable to ${action} plan.`
            ),
            "error"
        );
    }
}



function showMessage(
    message,
    type
) {

    const element =
        document.getElementById(
            "message"
        );


    element.textContent =
        message;


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
    .getElementById(
        "createPlanForm"
    )
    .addEventListener(
        "submit",
        createPlan
    );


document
    .getElementById(
        "refreshPlansButton"
    )
    .addEventListener(
        "click",
        loadPlans
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