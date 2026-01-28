import axios from "axios";

// All calls use relative /api base; in dev Vite proxies to http://localhost:8080
export const api = axios.create({
  baseURL: "/api",
  headers: {
    "Content-Type": "application/json"
  }
});

// For endpoints that expect form-url-encoded rather than JSON
export function postForm(url, data) {
  const params = new URLSearchParams();
  Object.entries(data).forEach(([key, value]) => {
    if (value !== undefined && value !== null) {
      params.append(key, value);
    }
  });
  return api.post(url, params, {
    headers: { "Content-Type": "application/x-www-form-urlencoded" }
  });
}

