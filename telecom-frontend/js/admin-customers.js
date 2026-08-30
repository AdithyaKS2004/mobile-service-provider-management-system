document.addEventListener(
    "DOMContentLoaded",
    async () => {

        await loadAdmin();
        await loadCustomers();

        document
            .getElementById("logoutButton")
            .addEventListener(
                "click",
                logout
            );
    }
);



/*
 * Verify admin session
 */
async function loadAdmin() {

    try {

        const user =
            await apiRequest(
                "/api/auth/me"
            );

        if (
            !user ||
            user.role !== "ADMIN"
        ) {

            window.location.href =
                "../index.html";

            return;
        }

        document
            .getElementById("adminName")
            .textContent =
                user.fullName ||
                "Administrator";

    } catch (error) {

        console.error(
            "Unable to load admin:",
            error
        );

        window.location.href =
            "../index.html";
    }
}



/*
 * Load customers
 */
async function loadCustomers() {

    const loading =
        document.getElementById(
            "loading"
        );

    const emptyState =
        document.getElementById(
            "emptyState"
        );

    const tableContainer =
        document.getElementById(
            "tableContainer"
        );

    const tableBody =
        document.getElementById(
            "customerTableBody"
        );

    const customerCount =
        document.getElementById(
            "customerCount"
        );


    try {

        const customers =
            await apiRequest(
                "/api/admin/customers"
            );


        loading.classList.add(
            "hidden"
        );


        if (
            !customers ||
            customers.length === 0
        ) {

            emptyState.classList.remove(
                "hidden"
            );

            customerCount.textContent =
                "0";

            return;
        }


        customerCount.textContent =
            customers.length;


        tableContainer.classList.remove(
            "hidden"
        );


        tableBody.innerHTML = "";


        customers.forEach(
            customer => {

                const row =
                    document.createElement(
                        "tr"
                    );

                row.className =
                    "hover:bg-gray-50";


                row.innerHTML = `

                    <td class="px-6 py-4
                               text-sm
                               text-gray-600">

                        ${customer.id}

                    </td>


                    <td class="px-6 py-4">

                        <div
                            class="font-semibold
                                   text-gray-800">

                            ${escapeHtml(
                                customer.fullName
                            )}

                        </div>

                    </td>


                    <td class="px-6 py-4
                               text-sm
                               text-gray-600">

                        ${escapeHtml(
                            customer.email
                        )}

                    </td>


                    <td class="px-6 py-4
                               text-sm
                               text-gray-600">

                        ${escapeHtml(
                            customer.phone
                        )}

                    </td>


                    <td class="px-6 py-4">

                        <span
                            class="inline-flex
                                   px-3 py-1
                                   rounded-full
                                   text-xs
                                   font-semibold
                                   bg-blue-100
                                   text-blue-700">

                            ${customer.role}

                        </span>

                    </td>

                `;


                tableBody.appendChild(
                    row
                );
            }
        );

    } catch (error) {

        console.error(
            "Unable to load customers:",
            error
        );


        loading.classList.add(
            "hidden"
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
            "Unable to load customer details.",
            "error"
        );
    }
}



/*
 * Logout
 */
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

        window.location.href =
            "../index.html";
    }
}



/*
 * Basic HTML escaping
 */
function escapeHtml(value) {

    if (value === null ||
        value === undefined) {

        return "";
    }

    return String(value)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}



/*
 * Message
 */
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

    if (type === "error") {

        element.classList.add(
            "bg-red-100",
            "text-red-700"
        );

    } else {

        element.classList.add(
            "bg-green-100",
            "text-green-700"
        );
    }
}