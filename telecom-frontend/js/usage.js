async function loadActiveSims() {

    const select =
        document.getElementById("simCard");

    try {

        const sims =
            await apiRequest("/api/sim/my");


        const activeSims =
            sims.filter(
                sim => sim.status === "ACTIVE"
            );


        select.innerHTML = "";


        if (activeSims.length === 0) {

            select.innerHTML = `
                <option value="">
                    No active SIM available
                </option>
            `;

            document.getElementById(
                "simulateButton"
            ).disabled = true;

            document.getElementById(
                "simulateButton"
            ).classList.add(
                "opacity-50",
                "cursor-not-allowed"
            );

            return;
        }


        const defaultOption =
            document.createElement("option");

        defaultOption.value = "";

        defaultOption.textContent =
            "Select an active SIM";

        select.appendChild(
            defaultOption
        );


        activeSims.forEach(sim => {

            const option =
                document.createElement("option");

            option.value =
                sim.id;

            option.textContent =
                `SIM #${sim.id} - ${sim.phoneNumber}`;

            select.appendChild(
                option
            );

        });


    } catch (error) {

        console.error(
            "Unable to load SIMs:",
            error
        );


        if (
            error.status === 401 ||
            error.status === 403
        ) {

            window.location.href =
                "../index.html";

            return;
        }


        showMessage(
            "Unable to load your SIMs.",
            "error"
        );
    }
}



async function simulateUsage() {

    const simCardId =
        document.getElementById(
            "simCard"
        ).value;


    const usageType =
        document.getElementById(
            "usageType"
        ).value;


    const amount =
        document.getElementById(
            "amount"
        ).value;


    if (!simCardId) {

        showMessage(
            "Please select an active SIM.",
            "error"
        );

        return;
    }


    if (!amount || Number(amount) <= 0) {

        showMessage(
            "Please enter a valid amount.",
            "error"
        );

        return;
    }


    const button =
        document.getElementById(
            "simulateButton"
        );


    button.disabled = true;

    button.textContent =
        "Processing...";


    try {

        const response =
            await apiRequest(
                "/api/sim/simulate-usage",
                {
                    method: "POST",

                    body: JSON.stringify({

                        simCardId:
                            Number(simCardId),

                        usageType:
                            usageType,

                        amount:
                            Number(amount)

                    })
                }
            );


        showUsageResult(
            response
        );


        showMessage(
            "Usage simulated successfully.",
            "success"
        );


        /*
         * Clear amount after successful
         * simulation.
         */

        document.getElementById(
            "amount"
        ).value = "";


    } catch (error) {

        console.error(
            "Usage simulation failed:",
            error
        );
    
        /*
         * Session expired / access denied
         */
        if (
            error.status === 401 ||
            error.status === 403
        ) {
    
            window.location.href =
                "../index.html";
    
            return;
        }
    
    
        /*
         * Default message
         */
        let message =
            "Unable to simulate usage.";
    
    
        /*
         * Read backend error response
         */
        if (error.data) {
    
            if (
                typeof error.data === "string"
            ) {
    
                message =
                    error.data;
    
            } else if (
                error.data.message
            ) {
    
                message =
                    error.data.message;
    
            } else if (
                error.data.error
            ) {
    
                message =
                    error.data.error;
    
            } else if (
                error.data.detail
            ) {
    
                message =
                    error.data.detail;
            }
        }
    
    
        showMessage(
            message,
            "error"
        );
    } finally {

        button.disabled = false;

        button.textContent =
            "Simulate Usage";
    }
}



function showUsageResult(
    response
) {

    document.getElementById(
        "resultCard"
    ).classList.remove(
        "hidden"
    );


    const used =
        response.usedAmount;


    const type =
        response.usageType;


    if (type === "DATA") {

        document.getElementById(
            "usedAmount"
        ).textContent =
            `${used} MB`;

    } else {

        document.getElementById(
            "usedAmount"
        ).textContent =
            `${used} minutes`;
    }


    document.getElementById(
        "remainingData"
    ).textContent =
        `${response.remainingDataMb} MB`;


    document.getElementById(
        "remainingTalktime"
    ).textContent =
        `${response.remainingTalktimeMins} minutes`;
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
        "usageType"
    )
    .addEventListener(
        "change",
        function () {

            const hint =
                document.getElementById(
                    "amountHint"
                );


            if (this.value === "DATA") {

                hint.textContent =
                    "Data amount is measured in MB.";

            } else {

                hint.textContent =
                    "Call amount is measured in minutes.";
            }

        }
    );



document
    .getElementById(
        "simulateButton"
    )
    .addEventListener(
        "click",
        simulateUsage
    );



document
    .getElementById(
        "logoutButton"
    )
    .addEventListener(
        "click",
        logout
    );



loadActiveSims();