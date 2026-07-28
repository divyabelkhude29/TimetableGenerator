import api from "./api";

const dashboardService = {

    async getDashboardCounts() {

        const [

            departments,

            courses,

            faculties,

            students,

            subjects,

            classrooms

        ] = await Promise.all([

            api.get("/departments"),

            api.get("/courses"),

            api.get("/faculties"),

            api.get("/students"),

            api.get("/subjects"),

            api.get("/classrooms")

        ]);

        return {

            departments: departments.data.length,

            courses: courses.data.length,

            faculties: faculties.data.length,

            students: students.data.length,

            subjects: subjects.data.length,

            classrooms: classrooms.data.length

        };

    }

};

export default dashboardService;