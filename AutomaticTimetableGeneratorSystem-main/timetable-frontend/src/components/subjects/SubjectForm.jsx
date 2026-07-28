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

import subjectService from "../../services/subjectService";
import departmentService from "../../services/departmentService";
import courseService from "../../services/courseService";
import semesterService from "../../services/semesterService";
import facultyService from "../../services/facultyService";

const SubjectForm = ({
  open,
  onClose,
  subject,
  reload,
}) => {

  const [departments, setDepartments] = useState([]);
  const [courses, setCourses] = useState([]);
  const [semesters, setSemesters] = useState([]);
  const [faculties, setFaculties] = useState([]);

  const initialState = {

    subjectCode: "",

    subjectName: "",

    credits: 1,

    hoursPerWeek: 1,

    subjectType: "Theory",

    departmentId: "",

    courseId: "",

    semesterId: "",

    facultyId: "",

    active: true,

  };

  const [formData, setFormData] = useState(initialState);

  useEffect(() => {

    loadMasters();

  }, []);

  useEffect(() => {

    if (subject) {

      setFormData({

        subjectCode: subject.subjectCode || "",

        subjectName: subject.subjectName || "",

        credits: subject.credits || 1,

        hoursPerWeek: subject.hoursPerWeek || 1,

        subjectType: subject.subjectType || "Theory",

        departmentId: subject.departmentId || "",

        courseId: subject.courseId || "",

        semesterId: subject.semesterId || "",

        facultyId: subject.facultyId || "",

        active: subject.active,

      });

    } else {

      setFormData(initialState);

    }

  }, [subject]);

  const loadMasters = async () => {

    try {

      const [

        deptData,

        courseData,

        semesterData,

        facultyData,

      ] = await Promise.all([

        departmentService.getAllDepartments(),

        courseService.getAllCourses(),

        semesterService.getAllSemesters(),

        facultyService.getAllFaculty(),

      ]);

      setDepartments(deptData);

      setCourses(courseData);

      setSemesters(semesterData);

      setFaculties(facultyData);

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

        credits: Number(formData.credits),

        hoursPerWeek: Number(formData.hoursPerWeek),

        departmentId: Number(formData.departmentId),

        courseId: Number(formData.courseId),

        semesterId: Number(formData.semesterId),

        facultyId:

          formData.facultyId === ""

            ? null

            : Number(formData.facultyId),

      };

      if (subject) {

        await subjectService.updateSubject(

          subject.subjectId,

          payload

        );

      } else {

        await subjectService.createSubject(payload);

      }

      reload();

      onClose();

    } catch (error) {

      console.error(error);

      alert(

        error.response?.data?.message ||

        "Unable to save subject."

      );

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

        {

          subject

            ? "Update Subject"

            : "Add Subject"

        }

      </DialogTitle>

      <DialogContent>

        <Grid
          container
          spacing={2}
          mt={1}
        >

          <Grid item xs={12} md={6}>

            <TextField
              fullWidth
              label="Subject Code"
              name="subjectCode"
              value={formData.subjectCode}
              onChange={handleChange}
            />

          </Grid>

          <Grid item xs={12} md={6}>

            <TextField
              fullWidth
              label="Subject Name"
              name="subjectName"
              value={formData.subjectName}
              onChange={handleChange}
            />

          </Grid>

          <Grid item xs={12} md={6}>

            <TextField
              fullWidth
              type="number"
              label="Credits"
              name="credits"
              value={formData.credits}
              onChange={handleChange}
            />

          </Grid>

          <Grid item xs={12} md={6}>

            <TextField
              fullWidth
              type="number"
              label="Hours Per Week"
              name="hoursPerWeek"
              value={formData.hoursPerWeek}
              onChange={handleChange}
            />

          </Grid>

          <Grid item xs={12}>

            <TextField
              select
              fullWidth
              label="Subject Type"
              name="subjectType"
              value={formData.subjectType}
              onChange={handleChange}
            >

              <MenuItem value="Theory">
                Theory
              </MenuItem>

              <MenuItem value="Practical">
                Practical
              </MenuItem>

              <MenuItem value="Lab">
                Lab
              </MenuItem>

            </TextField>

          </Grid>

          <Grid item xs={12}>

            <TextField
              select
              fullWidth
              label="Department"
              name="departmentId"
              value={formData.departmentId}
              onChange={handleChange}
            >

              {departments.map((dept) => (

                <MenuItem
                  key={dept.departmentId}
                  value={dept.departmentId}
                >

                  {dept.departmentName}

                </MenuItem>

              ))}

            </TextField>

          </Grid>

          <Grid item xs={12}>

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

          <Grid item xs={12}>

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

            <TextField
              select
              fullWidth
              label="Faculty (Optional)"
              name="facultyId"
              value={formData.facultyId}
              onChange={handleChange}
            >

              <MenuItem value="">
                Not Assigned
              </MenuItem>

              {faculties.map((faculty) => (

                <MenuItem
                  key={faculty.facultyId}
                  value={faculty.facultyId}
                >

                  {faculty.firstName} {faculty.lastName}

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

        <Button onClick={onClose}>
          Cancel
        </Button>

        <Button
          variant="contained"
          onClick={handleSubmit}
        >

          {

            subject

              ? "Update"

              : "Save"

          }

        </Button>

      </DialogActions>

    </Dialog>

  );

};

export default SubjectForm;