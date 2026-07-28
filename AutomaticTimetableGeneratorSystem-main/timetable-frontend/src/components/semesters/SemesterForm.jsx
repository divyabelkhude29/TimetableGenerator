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

import semesterService from "../../services/semesterService";
import courseService from "../../services/courseService";

const initialState = {
  semesterNumber: "",
  semesterName: "",
  academicYear: "",
  startDate: "",
  endDate: "",
  courseId: "",
  active: true,
};

const SemesterForm = ({
  open,
  onClose,
  reload,
  semester,
}) => {
  const [form, setForm] = useState(initialState);

  const [courses, setCourses] = useState([]);

  useEffect(() => {
    loadCourses();
  }, []);

  useEffect(() => {
    if (semester) {
      setForm({
        semesterNumber: semester.semesterNumber,
        semesterName: semester.semesterName,
        academicYear: semester.academicYear,
        startDate: semester.startDate,
        endDate: semester.endDate,
        courseId: semester.courseId,
        active: semester.active,
      });
    } else {
      setForm(initialState);
    }
  }, [semester]);

  const loadCourses = async () => {
    try {
      const data = await courseService.getAllCourses();
      setCourses(data);
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
      if (semester) {
        await semesterService.updateSemester(
          semester.semesterId,
          form
        );
      } else {
        await semesterService.createSemester(form);
      }

      reload();

      onClose();

    } catch (error) {
      console.error(error);
      alert("Unable to save semester.");
    }
  };

  return (
    <Dialog
      open={open}
      fullWidth
      maxWidth="md"
    >
      <DialogTitle>
        {semester
          ? "Update Semester"
          : "Add Semester"}
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
              type="number"
              label="Semester Number"
              name="semesterNumber"
              value={form.semesterNumber}
              onChange={handleChange}
            />
          </Grid>

          <Grid item xs={6}>
            <TextField
              fullWidth
              label="Semester Name"
              name="semesterName"
              value={form.semesterName}
              onChange={handleChange}
            />
          </Grid>

          <Grid item xs={6}>
            <TextField
              fullWidth
              label="Academic Year"
              name="academicYear"
              value={form.academicYear}
              onChange={handleChange}
            />
          </Grid>

          <Grid item xs={6}>
            <TextField
              select
              fullWidth
              label="Course"
              name="courseId"
              value={form.courseId}
              onChange={handleChange}
            >
              {courses.map((course) => (
                <MenuItem
                  key={course.courseId}
                  value={course.courseId}
                >
                  {course.courseName}
                </MenuItem>
              ))}
            </TextField>
          </Grid>

          <Grid item xs={6}>
            <TextField
              fullWidth
              type="date"
              label="Start Date"
              name="startDate"
              value={form.startDate}
              onChange={handleChange}
              InputLabelProps={{
                shrink: true,
              }}
            />
          </Grid>

          <Grid item xs={6}>
            <TextField
              fullWidth
              type="date"
              label="End Date"
              name="endDate"
              value={form.endDate}
              onChange={handleChange}
              InputLabelProps={{
                shrink: true,
              }}
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

        <Button onClick={onClose}>
          Cancel
        </Button>

        <Button
          variant="contained"
          onClick={handleSubmit}
        >
          {semester ? "Update" : "Save"}
        </Button>

      </DialogActions>

    </Dialog>
  );
};

export default SemesterForm;