import api from "./api";

const courseService = {

  // GET /courses
  getAllCourses: async () => {

    const response = await api.get("/courses");

    return response.data;

  },

  // GET /courses/{id}
  getCourseById: async (courseId) => {

    const response = await api.get(`/courses/${courseId}`);

    return response.data;

  },

  // POST /courses
  createCourse: async (course) => {

    const response = await api.post("/courses", course);

    return response.data;

  },

  // PUT /courses/{id}
  updateCourse: async (courseId, course) => {

    const response = await api.put(`/courses/${courseId}`, course);

    return response.data;

  },

  // DELETE /courses/{id}
  deleteCourse: async (courseId) => {

    const response = await api.delete(`/courses/${courseId}`);

    return response.data;

  }

};

export default courseService;