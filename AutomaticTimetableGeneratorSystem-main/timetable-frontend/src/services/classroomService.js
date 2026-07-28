import api from "./api";

const BASE_URL = "/classrooms";

const classroomService = {

  getAllClassrooms: async () => {
    const response = await api.get(BASE_URL);
    return response.data;
  },

  getClassroomById: async (id) => {
    const response = await api.get(`${BASE_URL}/${id}`);
    return response.data;
  },

  createClassroom: async (data) => {
    const response = await api.post(BASE_URL, data);
    return response.data;
  },

  updateClassroom: async (id, data) => {
    const response = await api.put(`${BASE_URL}/${id}`, data);
    return response.data;
  },

  deleteClassroom: async (id) => {
    const response = await api.delete(`${BASE_URL}/${id}`);
    return response.data;
  },

  getByRoomNumber: async (roomNumber) => {
    const response = await api.get(`${BASE_URL}/room/${roomNumber}`);
    return response.data;
  }

};

export default classroomService;