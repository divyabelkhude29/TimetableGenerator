import api from "./api";

const BASE_URL = "/subjects";

const subjectService = {

  // Get All Subjects
  getAllSubjects: async () => {

    const response = await api.get(BASE_URL);

    return response.data;

  },

  // Get Subject By Id
  getSubjectById: async (id) => {

    const response = await api.get(`${BASE_URL}/${id}`);

    return response.data;

  },

  // Create Subject
  createSubject: async (subject) => {

    const response = await api.post(
      BASE_URL,
      subject
    );

    return response.data;

  },

  // Update Subject
  updateSubject: async (id, subject) => {

    const response = await api.put(
      `${BASE_URL}/${id}`,
      subject
    );

    return response.data;

  },

  // Delete Subject
  deleteSubject: async (id) => {

    const response = await api.delete(
      `${BASE_URL}/${id}`
    );

    return response.data;

  },

  // Get Subject By Code
  getSubjectByCode: async (subjectCode) => {

    const response = await api.get(
      `${BASE_URL}/code/${subjectCode}`
    );

    return response.data;

  },

  // Get Subjects By Department
  getSubjectsByDepartment: async (departmentId) => {

    const response = await api.get(
      `${BASE_URL}/department/${departmentId}`
    );

    return response.data;

  },

  // Get Subjects By Course
  getSubjectsByCourse: async (courseId) => {

    const response = await api.get(
      `${BASE_URL}/course/${courseId}`
    );

    return response.data;

  },

  // Get Subjects By Semester
  getSubjectsBySemester: async (semesterId) => {

    const response = await api.get(
      `${BASE_URL}/semester/${semesterId}`
    );

    return response.data;

  },

  // Get Subjects By Faculty
  getSubjectsByFaculty: async (facultyId) => {

    const response = await api.get(
      `${BASE_URL}/faculty/${facultyId}`
    );

    return response.data;

  }

};

export default subjectService;