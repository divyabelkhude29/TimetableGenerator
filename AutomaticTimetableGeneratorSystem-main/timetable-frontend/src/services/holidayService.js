import api from "./api";

const BASE_URL = "/holidays";

const holidayService = {

    // Get All Holidays
    getAllHolidays: async () => {

        const response = await api.get(BASE_URL);

        return response.data;

    },

    // Get Holiday By Id
    getHolidayById: async (id) => {

        const response = await api.get(
            `${BASE_URL}/${id}`
        );

        return response.data;

    },

    // Create Holiday
    createHoliday: async (holiday) => {

        const response = await api.post(
            BASE_URL,
            holiday
        );

        return response.data;

    },

    // Update Holiday
    updateHoliday: async (id, holiday) => {

        const response = await api.put(
            `${BASE_URL}/${id}`,
            holiday
        );

        return response.data;

    },

    // Delete Holiday
    deleteHoliday: async (id) => {

        const response = await api.delete(
            `${BASE_URL}/${id}`
        );

        return response.data;

    },

    // Get Holiday By Date
    getHolidayByDate: async (holidayDate) => {

        const response = await api.get(
            `${BASE_URL}/date/${holidayDate}`
        );

        return response.data;

    }

};

export default holidayService;