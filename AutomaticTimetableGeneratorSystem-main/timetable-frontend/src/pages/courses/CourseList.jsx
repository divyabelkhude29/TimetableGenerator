import { useEffect, useState } from "react";

import {
  Box,
  Typography,
  Button,
  CircularProgress,
  Alert,
} from "@mui/material";

import AddIcon from "@mui/icons-material/Add";

import DashboardLayout from "../../components/layout/DashboardLayout";

import CourseTable from "../../components/courses/CourseTable";
import CourseForm from "../../components/courses/CourseForm";

import courseService from "../../services/courseService";

const CourseList = () => {
  const [courses, setCourses] = useState([]);

  const [loading, setLoading] = useState(true);

  const [error, setError] = useState("");

  const [open, setOpen] = useState(false);

  const [selectedCourse, setSelectedCourse] = useState(null);

  const loadCourses = async () => {
    try {
      setLoading(true);

      const response =
        await courseService.getAllCourses();

      setCourses(response);
    } catch (error) {
      console.error(error);

      setError("Unable to load courses.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadCourses();
  }, []);

  const handleAdd = () => {
    setSelectedCourse(null);

    setOpen(true);
  };

  const handleEdit = (course) => {
    setSelectedCourse(course);

    setOpen(true);
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this course?")) return;

    try {
      await courseService.deleteCourse(id);

      loadCourses();
    } catch (error) {
      console.error(error);

      alert("Unable to delete course.");
    }
  };

  const handleClose = () => {
    setOpen(false);

    setSelectedCourse(null);
  };

  return (
    <DashboardLayout>

      <Box
        display="flex"
        justifyContent="space-between"
        alignItems="center"
        mb={3}
      >
        <Typography
          variant="h4"
          fontWeight="bold"
        >
          Course Management
        </Typography>

        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={handleAdd}
        >
          Add Course
        </Button>

      </Box>

      {error && (
        <Alert
          severity="error"
          sx={{ mb: 2 }}
        >
          {error}
        </Alert>
      )}

      {loading ? (
        <CircularProgress />
      ) : (
        <CourseTable
          courses={courses}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />
      )}

      <CourseForm
        open={open}
        onClose={handleClose}
        reload={loadCourses}
        course={selectedCourse}
      />

    </DashboardLayout>
  );
};

export default CourseList;