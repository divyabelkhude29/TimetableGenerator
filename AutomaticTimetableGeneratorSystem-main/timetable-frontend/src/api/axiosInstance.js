import axios from "axios";
import { getToken } from "../utils/token";


const axiosInstance = axios.create({

  baseURL: "http://localhost:9097/api",

  timeout: 15000,

  headers: {
    "Content-Type": "application/json",
  },

});


/*
 * ============================================================
 * REQUEST INTERCEPTOR
 * ============================================================
 */
axiosInstance.interceptors.request.use(

  (config) => {

    const token = getToken();


    /*
     * Do not attach JWT to login/register.
     *
     * Login and registration are public endpoints.
     */
    const isPublicEndpoint =
      config.url === "/auth/login" ||
      config.url === "/auth/register";


    if (
      token &&
      !isPublicEndpoint
    ) {

      config.headers.Authorization =
        `Bearer ${token}`;

    }


    config.headers["Content-Type"] =
      "application/json";


    return config;

  },

  (error) => {

    return Promise.reject(error);

  }

);


/*
 * ============================================================
 * RESPONSE INTERCEPTOR
 * ============================================================
 */
axiosInstance.interceptors.response.use(

  (response) => {

    return response;

  },

  (error) => {

    console.error(
      "API Error:",
      error.response?.data || error.message
    );


    /*
     * Do not automatically redirect here.
     *
     * Otherwise an expired JWT can cause unexpected
     * redirects during API calls.
     */
    return Promise.reject(error);

  }

);


export default axiosInstance;