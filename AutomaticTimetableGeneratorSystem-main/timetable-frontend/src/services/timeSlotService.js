import api from "./api";

const BASE_URL = "/timeslots";

const timeSlotService = {

  getAllTimeSlots: async () => {

    const response = await api.get(BASE_URL);

    return response.data;

  },

  getTimeSlotById: async (id) => {

    const response = await api.get(`${BASE_URL}/${id}`);

    return response.data;

  },

  createTimeSlot: async (timeSlot) => {

    const response = await api.post(
      BASE_URL,
      timeSlot
    );

    return response.data;

  },

  updateTimeSlot: async (id, timeSlot) => {

    const response = await api.put(
      `${BASE_URL}/${id}`,
      timeSlot
    );

    return response.data;

  },

  deleteTimeSlot: async (id) => {

    const response = await api.delete(
      `${BASE_URL}/${id}`
    );

    return response.data;

  },

  getTimeSlotsByDay: async (dayOfWeek) => {

    const response = await api.get(
      `${BASE_URL}/day/${dayOfWeek}`
    );

    return response.data;

  },

  getTimeSlotsByOrder: async (slotOrder) => {

    const response = await api.get(
      `${BASE_URL}/order/${slotOrder}`
    );

    return response.data;

  },

};

export default timeSlotService;