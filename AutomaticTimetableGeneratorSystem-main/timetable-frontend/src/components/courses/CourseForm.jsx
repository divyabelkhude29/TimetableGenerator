import { useEffect, useState } from "react";

import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Grid,
  TextField,
  Button,
  MenuItem,
  FormControlLabel,
  Switch,
} from "@mui/material";

import courseService from "../../services/courseService";
import departmentService from "../../services/departmentService";

const initialState = {
  courseCode: "",
  courseName: "",
  durationYears: 4,
  totalSemesters: 8,
  description: "",
  departmentId: "",
  active: true,
};

const CourseForm = ({
  open,
  onClose,
  reload,
  course,
}) => {
  const [form, setForm] = useState(initialState);

  const [departments, setDepartments] = useState([]);

  useEffect(() => {
    if (course) {
      setForm({
        courseCode: course.courseCode,
        courseName: course.courseName,
        durationYears: course.durationYears,
        totalSemesters: course.totalSemesters,
        description: course.description || "",
        departmentId: course.departmentId,
        active: course.active,
      });
    } else {
      setForm(initialState);
    }
  }, [course]);

  useEffect(() => {
    loadDepartments();
  }, []);

  const loadDepartments = async () => {
    try {
      const response =
        await departmentService.getAllDepartments();

      setDepartments(response);
    } catch (error) {
      console.error(error);
    }
  };

  const handleChange = (e) => {
    const { name, value, checked, type } = e.target;

    setForm({
      ...form,
      [name]:
        type === "checkbox"
          ? checked
          : value,
    });
  };

  const handleSubmit = async () => {
    try {
      if (course) {
        await courseService.updateCourse(
          course.courseId,
          form
        );
      } else {
        await courseService.createCourse(form);
      }

      reload();

      onClose();
    } catch (error) {
      console.error(error);
      alert("Unable to save course.");
    }
  };

  return (
    <Dialog
      open={open}
      fullWidth
      maxWidth="md"
    >
      <DialogTitle>
        {course
          ? "Update Course"
          : "Add Course"}
      </DialogTitle>

      <DialogContent>

        <Grid
          container
          spacing={2}
          mt={1}
        >

          <Grid item xs={6}>
            <TextField
              fullWidth
              label="Course Code"
              name="courseCode"
              value={form.courseCode}
              onChange={handleChange}
            />
          </Grid>

          <Grid item xs={6}>
            <TextField
              fullWidth
              label="Course Name"
              name="courseName"
              value={form.courseName}
              onChange={handleChange}
            />
          </Grid>

          <Grid item xs={6}>
            <TextField
              select
              fullWidth
              label="Department"
              name="departmentId"
              value={form.departmentId}
              onChange={handleChange}
            >
              {departments.map((department) => (
                <MenuItem
                  key={department.departmentId}
                  value={department.departmentId}
                >
                  {department.departmentName}
                </MenuItem>
              ))}
            </TextField>
          </Grid>

          <Grid item xs={3}>
            <TextField
              fullWidth
              type="number"
              label="Duration"
              name="durationYears"
              value={form.durationYears}
              onChange={handleChange}
            />
          </Grid>

          <Grid item xs={3}>
            <TextField
              fullWidth
              type="number"
              label="Semesters"
              name="totalSemesters"
              value={form.totalSemesters}
              onChange={handleChange}
            />
          </Grid>

          <Grid item xs={12}>
            <TextField
              fullWidth
              multiline
              rows={3}
              label="Description"
              name="description"
              value={form.description}
              onChange={handleChange}
            />
          </Grid>

          <Grid item xs={12}>
            <FormControlLabel
              control={
                <Switch
                  checked={form.active}
                  onChange={handleChange}
                  name="active"
                />
              }
              label="Active"
            />
          </Grid>

        </Grid>

      </DialogContent>

      <DialogActions>

        <Button
          onClick={onClose}
        >
          Cancel
        </Button>

        <Button
          variant="contained"
          onClick={handleSubmit}
        >
          {course
            ? "Update"
            : "Save"}
        </Button>

      </DialogActions>

    </Dialog>
  );
};

export default CourseForm;