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

import studentService from "../../services/studentService";
import departmentService from "../../services/departmentService";
import courseService from "../../services/courseService";
import semesterService from "../../services/semesterService";

const StudentForm = ({
  open,
  onClose,
  student,
  reload,
}) => {

  const [departments, setDepartments] = useState([]);

  const [courses, setCourses] = useState([]);

  const [semesters, setSemesters] = useState([]);

  const initialState = {

    rollNumber: "",

    registerNumber: "",

    firstName: "",

    lastName: "",

    gender: "",

    dateOfBirth: "",

    email: "",

    phone: "",

    address: "",

    admissionYear: "",

    currentYear: "",

    departmentId: "",

    courseId: "",

    semesterId: "",

    active: true,

  };

  const [formData, setFormData] = useState(initialState);

  useEffect(() => {

    loadDropdowns();

  }, []);

  useEffect(() => {

    if (student) {

      setFormData({

        rollNumber: student.rollNumber || "",

        registerNumber: student.registerNumber || "",

        firstName: student.firstName || "",

        lastName: student.lastName || "",

        gender: student.gender || "",

        dateOfBirth: student.dateOfBirth || "",

        email: student.email || "",

        phone: student.phone || "",

        address: student.address || "",

        admissionYear: student.admissionYear || "",

        currentYear: student.currentYear || "",

        departmentId: student.departmentId || "",

        courseId: student.courseId || "",

        semesterId: student.semesterId || "",

        active: student.active,

      });

    } else {

      setFormData(initialState);

    }

  }, [student]);

  const loadDropdowns = async () => {

    try {

      const deptResponse =
        await departmentService.getAllDepartments();

      const courseResponse =
        await courseService.getAllCourses();

      const semesterResponse =
        await semesterService.getAllSemesters();

      setDepartments(deptResponse);

      setCourses(courseResponse);

      setSemesters(semesterResponse);

    } catch (error) {

      console.error(error);

    }

  };

  const handleChange = (e) => {

    const {

      name,

      value,

      checked,

      type,

    } = e.target;

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

      const payload = {

        ...formData,

        departmentId: Number(formData.departmentId),

        courseId: Number(formData.courseId),

        semesterId: Number(formData.semesterId),

        admissionYear: Number(formData.admissionYear),

        currentYear: Number(formData.currentYear),

      };

      if (student) {

        await studentService.updateStudent(

          student.studentId,

          payload

        );

      } else {

        await studentService.createStudent(

          payload

        );

      }

      reload();

      onClose();

    } catch (error) {

      console.error(error);

      alert(

        error.response?.data?.message ||

        "Unable to save student."

      );

    }

  };
    return (

    <Dialog
      open={open}
      onClose={onClose}
      fullWidth
      maxWidth="lg"
    >

      <DialogTitle>

        {student ? "Update Student" : "Add Student"}

      </DialogTitle>

      <DialogContent>

        <Grid
          container
          spacing={2}
          mt={1}
        >

          <Grid item xs={12} md={4}>
            <TextField
              fullWidth
              label="Roll Number"
              name="rollNumber"
              value={formData.rollNumber}
              onChange={handleChange}
            />
          </Grid>

          <Grid item xs={12} md={4}>
            <TextField
              fullWidth
              label="Register Number"
              name="registerNumber"
              value={formData.registerNumber}
              onChange={handleChange}
            />
          </Grid>

          <Grid item xs={12} md={4}>
            <TextField
              fullWidth
              label="First Name"
              name="firstName"
              value={formData.firstName}
              onChange={handleChange}
            />
          </Grid>

          <Grid item xs={12} md={4}>
            <TextField
              fullWidth
              label="Last Name"
              name="lastName"
              value={formData.lastName}
              onChange={handleChange}
            />
          </Grid>

          <Grid item xs={12} md={4}>
            <TextField
              select
              fullWidth
              label="Gender"
              name="gender"
              value={formData.gender}
              onChange={handleChange}
            >
              <MenuItem value="Male">
                Male
              </MenuItem>

              <MenuItem value="Female">
                Female
              </MenuItem>

              <MenuItem value="Other">
                Other
              </MenuItem>
            </TextField>
          </Grid>

          <Grid item xs={12} md={4}>
            <TextField
              fullWidth
              type="date"
              label="Date Of Birth"
              name="dateOfBirth"
              value={formData.dateOfBirth}
              onChange={handleChange}
              InputLabelProps={{
                shrink: true,
              }}
            />
          </Grid>

          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              label="Email"
              name="email"
              value={formData.email}
              onChange={handleChange}
            />
          </Grid>

          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              label="Phone"
              name="phone"
              value={formData.phone}
              onChange={handleChange}
            />
          </Grid>

          <Grid item xs={12}>
            <TextField
              fullWidth
              multiline
              rows={3}
              label="Address"
              name="address"
              value={formData.address}
              onChange={handleChange}
            />
          </Grid>

          <Grid item xs={12} md={3}>
            <TextField
              fullWidth
              type="number"
              label="Admission Year"
              name="admissionYear"
              value={formData.admissionYear}
              onChange={handleChange}
            />
          </Grid>

          <Grid item xs={12} md={3}>
            <TextField
              fullWidth
              type="number"
              label="Current Year"
              name="currentYear"
              value={formData.currentYear}
              onChange={handleChange}
            />
          </Grid>

          <Grid item xs={12} md={2}>
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

          <Grid item xs={12} md={2}>
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

          <Grid item xs={12} md={2}>
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

          <Grid item xs={12}>
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
          {student ? "Update" : "Save"}
        </Button>

      </DialogActions>

    </Dialog>

  );

};

export default StudentForm;