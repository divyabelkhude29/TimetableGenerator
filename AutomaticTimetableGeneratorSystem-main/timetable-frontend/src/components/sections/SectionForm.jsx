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

import sectionService from "../../services/sectionService";
import departmentService from "../../services/departmentService";
import courseService from "../../services/courseService";
import semesterService from "../../services/semesterService";

const SectionForm = ({
  open,
  onClose,
  section,
  reload,
}) => {
  const [formData, setFormData] = useState({
    sectionName: "",
    sectionCode: "",
    academicYear: "",
    strength: "",
    departmentId: "",
    courseId: "",
    semesterId: "",
    active: true,
  });

  const [departments, setDepartments] = useState([]);

  const [courses, setCourses] = useState([]);

  const [semesters, setSemesters] = useState([]);

  useEffect(() => {
    if (open) {
      loadDropdowns();
    }
  }, [open]);

  useEffect(() => {
    if (section) {
      setFormData({
        sectionName: section.sectionName,
        sectionCode: section.sectionCode,
        academicYear: section.academicYear,
        strength: section.strength,
        departmentId: section.departmentId,
        courseId: section.courseId,
        semesterId: section.semesterId,
        active: section.active,
      });
    } else {
      resetForm();
    }
  }, [section]);

  const loadDropdowns = async () => {
    try {
      const deptData =
        await departmentService.getAllDepartments();

      const courseData =
        await courseService.getAllCourses();

      const semesterData =
        await semesterService.getAllSemesters();

      setDepartments(deptData);

      setCourses(courseData);

      setSemesters(semesterData);
    } catch (error) {
      console.error(error);
    }
  };

  const resetForm = () => {
    setFormData({
      sectionName: "",
      sectionCode: "",
      academicYear: "",
      strength: "",
      departmentId: "",
      courseId: "",
      semesterId: "",
      active: true,
    });
  };

  const handleChange = (e) => {
    const { name, value, checked, type } =
      e.target;

    setFormData({
      ...formData,
      [name]:
        type === "checkbox"
          ? checked
          : value,
    });
  };

  const handleSubmit = async () => {
    try {
      if (section) {
        await sectionService.updateSection(
          section.sectionId,
          formData
        );
      } else {
        await sectionService.createSection(
          formData
        );
      }

      reload();

      onClose();

      resetForm();
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <Dialog
      open={open}
      onClose={onClose}
      fullWidth
      maxWidth="md"
    >
      <DialogTitle>
        {section
          ? "Edit Section"
          : "Add Section"}
      </DialogTitle>

      <DialogContent>

        <Grid
          container
          spacing={2}
          mt={1}
        >
          <Grid
            item
            xs={12}
            md={6}
          >
            <TextField
              fullWidth
              label="Section Name"
              name="sectionName"
              value={formData.sectionName}
              onChange={handleChange}
            />
          </Grid>

          <Grid
            item
            xs={12}
            md={6}
          >
            <TextField
              fullWidth
              label="Section Code"
              name="sectionCode"
              value={formData.sectionCode}
              onChange={handleChange}
            />
          </Grid>

          <Grid
            item
            xs={12}
            md={6}
          >
            <TextField
              fullWidth
              label="Academic Year"
              name="academicYear"
              value={formData.academicYear}
              onChange={handleChange}
            />
          </Grid>

          <Grid
            item
            xs={12}
            md={6}
          >
            <TextField
              fullWidth
              type="number"
              label="Strength"
              name="strength"
              value={formData.strength}
              onChange={handleChange}
            />
          </Grid>

          <Grid
            item
            xs={12}
            md={4}
          >
            <TextField
              select
              fullWidth
              label="Department"
              name="departmentId"
              value={formData.departmentId}
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

          <Grid
            item
            xs={12}
            md={4}
          >
            <TextField
              select
              fullWidth
              label="Course"
              name="courseId"
              value={formData.courseId}
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

          <Grid
            item
            xs={12}
            md={4}
          >
            <TextField
              select
              fullWidth
              label="Semester"
              name="semesterId"
              value={formData.semesterId}
              onChange={handleChange}
            >
              {semesters.map((semester) => (
                <MenuItem
                  key={semester.semesterId}
                  value={semester.semesterId}
                >
                  {semester.semesterName}
                </MenuItem>
              ))}
            </TextField>
          </Grid>

          <Grid
            item
            xs={12}
          >
            <FormControlLabel
              control={
                <Switch
                  checked={formData.active}
                  name="active"
                  onChange={handleChange}
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
          {section ? "Update" : "Save"}
        </Button>

      </DialogActions>

    </Dialog>
  );
};

export default SectionForm;