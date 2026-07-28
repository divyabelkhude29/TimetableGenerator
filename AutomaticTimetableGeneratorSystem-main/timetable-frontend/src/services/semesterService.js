import api from "./api";

const semesterService = {
  getAllSemesters: async () => {
    const response = await api.get("/semesters");
    return response.data;
  },

  getSemesterById: async (id) => {
    const response = await api.get(`/semesters/${id}`);
    return response.data;
  },

  createSemester: async (semester) => {
    const response = await api.post("/semesters", semester);
    return response.data;
  },

  updateSemester: async (id, semester) => {
    const response = await api.put(`/semesters/${id}`, semester);
    return response.data;
  },

  deleteSemester: async (id) => {
    const response = await api.delete(`/semesters/${id}`);
    return response.data;
  },
};

export default semesterService;