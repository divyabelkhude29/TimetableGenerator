import { useEffect, useState } from "react";

import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Grid,
  TextField,
  Button,
  FormControlLabel,
  Switch,
  MenuItem,
} from "@mui/material";

import timeSlotService from "../../services/timeSlotService";

const days = [
  "Monday",
  "Tuesday",
  "Wednesday",
  "Thursday",
  "Friday",
  "Saturday",
  "Sunday",
];

const initialState = {

  slotName: "",

  dayOfWeek: "Monday",

  startTime: "",

  endTime: "",

  slotOrder: "",

  active: true,

};

const TimeSlotForm = ({
  open,
  onClose,
  timeSlot,
  reload,
}) => {

  const [formData, setFormData] =
    useState(initialState);

  useEffect(() => {

    if (timeSlot) {

      setFormData({

        slotName:
          timeSlot.slotName || "",

        dayOfWeek:
          timeSlot.dayOfWeek || "Monday",

        startTime:
          timeSlot.startTime || "",

        endTime:
          timeSlot.endTime || "",

        slotOrder:
          timeSlot.slotOrder || "",

        active:
          timeSlot.active ?? true,

      });

    } else {

      setFormData(initialState);

    }

  }, [timeSlot]);

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

        slotOrder:
          Number(formData.slotOrder),

      };

      if (timeSlot) {

        await timeSlotService.updateTimeSlot(

          timeSlot.timeSlotId,

          payload

        );

      } else {

        await timeSlotService.createTimeSlot(

          payload

        );

      }

      reload();

      onClose();

    } catch (error) {

      console.error(error);

      alert(

        error.response?.data?.message ||

        "Unable to save time slot."

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

        {timeSlot

          ? "Update Time Slot"

          : "Add Time Slot"}

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
              label="Slot Name"
              name="slotName"
              value={formData.slotName}
              onChange={handleChange}
            />

          </Grid>

          <Grid item xs={12} md={6}>

            <TextField
              select
              fullWidth
              label="Day Of Week"
              name="dayOfWeek"
              value={formData.dayOfWeek}
              onChange={handleChange}
            >

              {days.map((day) => (

                <MenuItem
                  key={day}
                  value={day}
                >
                  {day}
                </MenuItem>

              ))}

            </TextField>

          </Grid>

          <Grid item xs={12} md={4}>

            <TextField
              fullWidth
              type="time"
              label="Start Time"
              name="startTime"
              value={formData.startTime}
              onChange={handleChange}
              InputLabelProps={{
                shrink: true,
              }}
            />

          </Grid>

          <Grid item xs={12} md={4}>

            <TextField
              fullWidth
              type="time"
              label="End Time"
              name="endTime"
              value={formData.endTime}
              onChange={handleChange}
              InputLabelProps={{
                shrink: true,
              }}
            />

          </Grid>

          <Grid item xs={12} md={4}>

            <TextField
              fullWidth
              type="number"
              label="Slot Order"
              name="slotOrder"
              value={formData.slotOrder}
              onChange={handleChange}
            />

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

          {timeSlot

            ? "Update"

            : "Save"}

        </Button>

      </DialogActions>

    </Dialog>

  );

};

export default TimeSlotForm;