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

import facultyService from "../../services/facultyService";
import departmentService from "../../services/departmentService";

const FacultyForm = ({
  open,
  onClose,
  faculty,
  reload,
}) => {

  const [departments, setDepartments] = useState([]);

  const initialState = {

    facultyCode: "",

    firstName: "",

    lastName: "",

    email: "",

    phone: "",

    designation: "",

    qualification: "",

    experienceYears: 0,

    departmentId: "",

    active: true,

  };

  const [formData, setFormData] = useState(initialState);

  useEffect(() => {

    loadDepartments();

  }, []);

  useEffect(() => {

    if (faculty) {

      setFormData({

        facultyCode: faculty.facultyCode || "",

        firstName: faculty.firstName || "",

        lastName: faculty.lastName || "",

        email: faculty.email || "",

        phone: faculty.phone || "",

        designation: faculty.designation || "",

        qualification: faculty.qualification || "",

        experienceYears: faculty.experienceYears || 0,

        departmentId: faculty.departmentId || "",

        active: faculty.active,

      });

    } else {

      setFormData(initialState);

    }

  }, [faculty]);

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

        experienceYears: Number(formData.experienceYears),

      };

      if (faculty) {

        await facultyService.updateFaculty(

          faculty.facultyId,

          payload

        );

      } else {

        await facultyService.createFaculty(

          payload

        );

      }

      reload();

      onClose();

    } catch (error) {

      console.error(error);

      alert(

        error.response?.data?.message ||

        "Unable to save faculty."

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

          faculty

            ? "Update Faculty"

            : "Add Faculty"

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

              label="Faculty Code"

              name="facultyCode"

              value={formData.facultyCode}

              onChange={handleChange}

            />

          </Grid>

          <Grid item xs={12} md={6}>

            <TextField

              fullWidth

              label="Designation"

              name="designation"

              value={formData.designation}

              onChange={handleChange}

            />

          </Grid>

          <Grid item xs={12} md={6}>

            <TextField

              fullWidth

              label="First Name"

              name="firstName"

              value={formData.firstName}

              onChange={handleChange}

            />

          </Grid>

          <Grid item xs={12} md={6}>

            <TextField

              fullWidth

              label="Last Name"

              name="lastName"

              value={formData.lastName}

              onChange={handleChange}

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

          <Grid item xs={12} md={6}>

            <TextField

              fullWidth

              label="Qualification"

              name="qualification"

              value={formData.qualification}

              onChange={handleChange}

            />

          </Grid>

          <Grid item xs={12} md={6}>

            <TextField

              fullWidth

              type="number"

              label="Experience (Years)"

              name="experienceYears"

              value={formData.experienceYears}

              onChange={handleChange}

            />

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

              {

                departments.map((department) => (

                  <MenuItem

                    key={department.departmentId}

                    value={department.departmentId}

                  >

                    {department.departmentName}

                  </MenuItem>

                ))

              }

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

          {

            faculty

              ? "Update"

              : "Save"

          }

        </Button>

      </DialogActions>

    </Dialog>

  );

};

export default FacultyForm;