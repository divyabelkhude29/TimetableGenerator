import api from "./api";

const BASE_URL = "/students";

const studentService = {

  // Get All Students
  getAllStudents: async () => {

    const response = await api.get(BASE_URL);

    return response.data;

  },

  // Get Student By Id
  getStudentById: async (id) => {

    const response = await api.get(`${BASE_URL}/${id}`);

    return response.data;

  },

  // Create Student
  createStudent: async (student) => {

    const response = await api.post(
      BASE_URL,
      student
    );

    return response.data;

  },

  // Update Student
  updateStudent: async (id, student) => {

    const response = await api.put(
      `${BASE_URL}/${id}`,
      student
    );

    return response.data;

  },

  // Delete Student
  deleteStudent: async (id) => {

    const response = await api.delete(
      `${BASE_URL}/${id}`
    );

    return response.data;

  },

  // Get Student By Roll Number
  getStudentByRollNumber: async (rollNumber) => {

    const response = await api.get(
      `${BASE_URL}/roll/${rollNumber}`
    );

    return response.data;

  },

  // Get Students By Department
  getStudentsByDepartment: async (departmentId) => {

    const response = await api.get(
      `${BASE_URL}/department/${departmentId}`
    );

    return response.data;

  },

  // Get Students By Course
  getStudentsByCourse: async (courseId) => {

    const response = await api.get(
      `${BASE_URL}/course/${courseId}`
    );

    return response.data;

  },

  // Get Students By Semester
  getStudentsBySemester: async (semesterId) => {

    const response = await api.get(
      `${BASE_URL}/semester/${semesterId}`
    );

    return response.data;

  }

};

export default studentService;