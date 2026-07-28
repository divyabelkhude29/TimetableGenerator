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

import classroomService from "../../services/classroomService";

const roomTypes = [
  "CLASSROOM",
  "LAB",
  "COMPUTER LAB",
  "SEMINAR HALL",
  "AUDITORIUM",
];

const initialState = {
  roomNumber: "",
  buildingName: "",
  floorNumber: "",
  capacity: "",
  roomType: "CLASSROOM",
  active: true,
};

const ClassroomForm = ({
  open,
  onClose,
  classroom,
  reload,
}) => {

  const [formData, setFormData] =
    useState(initialState);

  useEffect(() => {

    if (classroom) {

      setFormData({

        roomNumber:
          classroom.roomNumber || "",

        buildingName:
          classroom.buildingName || "",

        floorNumber:
          classroom.floorNumber || "",

        capacity:
          classroom.capacity || "",

        roomType:
          classroom.roomType || "CLASSROOM",

        active:
          classroom.active ?? true,

      });

    } else {

      setFormData(initialState);

    }

  }, [classroom]);

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

        floorNumber: Number(
          formData.floorNumber
        ),

        capacity: Number(
          formData.capacity
        ),

      };

      if (classroom) {

        await classroomService.updateClassroom(
          classroom.classroomId,
          payload
        );

      } else {

        await classroomService.createClassroom(
          payload
        );

      }

      reload();

      onClose();

    } catch (error) {

      console.error(error);

      alert(
        error.response?.data?.message ||
        "Unable to save classroom."
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

        {classroom
          ? "Update Classroom"
          : "Add Classroom"}

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
              label="Room Number"
              name="roomNumber"
              value={formData.roomNumber}
              onChange={handleChange}
            />

          </Grid>

          <Grid item xs={12} md={6}>

            <TextField
              fullWidth
              label="Building Name"
              name="buildingName"
              value={formData.buildingName}
              onChange={handleChange}
            />

          </Grid>

          <Grid item xs={12} md={3}>

            <TextField
              fullWidth
              type="number"
              label="Floor"
              name="floorNumber"
              value={formData.floorNumber}
              onChange={handleChange}
            />

          </Grid>

          <Grid item xs={12} md={3}>

            <TextField
              fullWidth
              type="number"
              label="Capacity"
              name="capacity"
              value={formData.capacity}
              onChange={handleChange}
            />

          </Grid>

          <Grid item xs={12} md={6}>

            <TextField
              select
              fullWidth
              label="Room Type"
              name="roomType"
              value={formData.roomType}
              onChange={handleChange}
            >

              {roomTypes.map((type) => (

                <MenuItem
                  key={type}
                  value={type}
                >

                  {type}

                </MenuItem>

              ))}

            </TextField>

          </Grid>

          <Grid item xs={12}>

            <FormControlLabel
              control={
                <Switch
                  checked={formData.active}
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

          {classroom
            ? "Update"
            : "Save"}

        </Button>

      </DialogActions>

    </Dialog>

  );

};

export default ClassroomForm;