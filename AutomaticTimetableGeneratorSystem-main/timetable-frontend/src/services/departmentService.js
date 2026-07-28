import api from "./api";

const BASE_URL = "/departments";

const departmentService = {

    getAllDepartments: async () => {

        const response = await api.get(BASE_URL);

        return response.data;

    },

    getDepartmentById: async (id) => {

        const response = await api.get(`${BASE_URL}/${id}`);

        return response.data;

    },

    createDepartment: async (department) => {

        const response = await api.post(

            BASE_URL,

            department

        );

        return response.data;

    },

    updateDepartment: async (id, department) => {

        const response = await api.put(

            `${BASE_URL}/${id}`,

            department

        );

        return response.data;

    },

    deleteDepartment: async (id) => {

        const response = await api.delete(

            `${BASE_URL}/${id}`

        );

        return response.data;

    }

};

export default departmentService;