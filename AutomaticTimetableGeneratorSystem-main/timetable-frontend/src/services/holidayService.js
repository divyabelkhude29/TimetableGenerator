import api from "./api";

const BASE_URL = "/holidays";

const holidayService = {

    getAllHolidays: async () => {

        const response =
            await api.get(BASE_URL);

        return response.data;

    },


    getHolidayById: async (id) => {

        const response =
            await api.get(
                `${BASE_URL}/${id}`
            );

        return response.data;

    },


    createHoliday: async (holiday) => {

        const response =
            await api.post(
                BASE_URL,
                holiday
            );

        return response.data;

    },


    updateHoliday: async (
        id,
        holiday
    ) => {

        const response =
            await api.put(
                `${BASE_URL}/${id}`,
                holiday
            );

        return response.data;

    },


    deleteHoliday: async (
        id
    ) => {

        const response =
            await api.delete(
                `${BASE_URL}/${id}`
            );

        return response.data;

    },


    getHolidayByDate: async (
        holidayDate
    ) => {

        const response =
            await api.get(
                `${BASE_URL}/date/${holidayDate}`
            );

        return response.data;

    }

};


export default holidayService;