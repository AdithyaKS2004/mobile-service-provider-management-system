const API_BASE_URL =
    window.location.hostname === "localhost" ||
    window.location.hostname === "127.0.0.1"
        ? "http://localhost:8080"
        : "https://YOUR-BACKEND-DOMAIN";

async function apiRequest(endpoint, options = {}) {

    const response = await fetch(
        API_BASE_URL + endpoint,
        {
            ...options,
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
                ...(options.headers || {})
            }
        }
    );

    let data = null;

    const contentType = response.headers.get("content-type");

    if (contentType && contentType.includes("application/json")) {
        data = await response.json();
    }

    if (!response.ok) {

        throw {
            status: response.status,
            data: data
        };
    }

    return data;
}