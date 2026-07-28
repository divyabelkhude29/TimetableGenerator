import api from "./api";

const BASE_URL = "/subjects";

const subjectService = {

    getAllSubjects: async () => {

        const response = await api.get(BASE_URL);

        return response.data;

    },

    getSubjectById: async (id) => {

        const response = await api.get(`${BASE_URL}/${id}`);

        return response.data;

    },

    createSubject: async (subject) => {

        const response = await api.post(
            BASE_URL,
            subject
        );

        return response.data;

    },

    updateSubject: async (id, subject) => {

        const response = await api.put(
            `${BASE_URL}/${id}`,
            subject
        );

        return response.data;

    },

    deleteSubject: async (id) => {

        const response = await api.delete(
            `${BASE_URL}/${id}`
        );

        return response.data;

    },

    getSubjectByCode: async (subjectCode) => {

        const response = await api.get(
            `${BASE_URL}/code/${subjectCode}`
        );

        return response.data;

    },

    getSubjectsByDepartment: async (departmentId) => {

        const response = await api.get(
            `${BASE_URL}/department/${departmentId}`
        );

        return response.data;

    },

    getSubjectsByCourse: async (courseId) => {

        const response = await api.get(
            `${BASE_URL}/course/${courseId}`
        );

        return response.data;

    },

    getSubjectsBySemester: async (semesterId) => {

        const response = await api.get(
            `${BASE_URL}/semester/${semesterId}`
        );

        return response.data;

    },

    getSubjectsByFaculty: async (facultyId) => {

        const response = await api.get(
            `${BASE_URL}/faculty/${facultyId}`
        );

        return response.data;

    }

};

export default subjectService;