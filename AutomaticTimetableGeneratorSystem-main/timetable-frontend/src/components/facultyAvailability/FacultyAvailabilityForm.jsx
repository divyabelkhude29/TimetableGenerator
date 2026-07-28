import { useEffect, useState } from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  Grid,
  TextField,
  MenuItem,
  FormControlLabel,
  Switch,
  CircularProgress,
  Alert,
} from "@mui/material";

import facultyService from "../../services/facultyService";
import timeSlotService from "../../services/timeSlotService";
import facultyAvailabilityService from "../../services/facultyAvailabilityService";

const DAYS = [
  "MONDAY",
  "TUESDAY",
  "WEDNESDAY",
  "THURSDAY",
  "FRIDAY",
  "SATURDAY",
  "SUNDAY",
];

const initialState = {
  facultyId: "",
  dayOfWeek: "",
  timeSlotId: "",
  available: true,
};

const FacultyAvailabilityForm = ({
  open,
  onClose,
  availability,
  reload,
}) => {
  const [formData, setFormData] = useState(initialState);

  const [facultyList, setFacultyList] = useState([]);

  const [timeSlotList, setTimeSlotList] = useState([]);

  const [errors, setErrors] = useState({});

  const [loading, setLoading] = useState(false);

  const [saving, setSaving] = useState(false);

  const [message, setMessage] = useState("");

  useEffect(() => {
    if (open) {
      loadDropdowns();
    }
  }, [open]);

  useEffect(() => {
    if (availability) {
      setFormData({
        facultyId: availability.facultyId || "",
        dayOfWeek: availability.dayOfWeek || "",
        timeSlotId: availability.timeSlotId || "",
        available:
          availability.available === undefined
            ? true
            : availability.available,
      });
    } else {
      setFormData(initialState);
    }

    setErrors({});
    setMessage("");
  }, [availability, open]);

  const loadDropdowns = async () => {
    try {
      setLoading(true);

      const faculty = await facultyService.getAllFaculty();

      const slots = await timeSlotService.getAllTimeSlots();

      setFacultyList(Array.isArray(faculty) ? faculty : []);

      setTimeSlotList(Array.isArray(slots) ? slots : []);
    } catch (error) {
      console.error(error);

      setMessage("Unable to load Faculty or Time Slots.");
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (event) => {
    const { name, value, checked, type } = event.target;

    setFormData((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));

    setErrors((prev) => ({
      ...prev,
      [name]: "",
    }));
  };

  const validate = () => {
    let temp = {};

    if (!formData.facultyId)
      temp.facultyId = "Faculty is required.";

    if (!formData.dayOfWeek)
      temp.dayOfWeek = "Day is required.";

    if (!formData.timeSlotId)
      temp.timeSlotId = "Time Slot is required.";

    setErrors(temp);

    return Object.keys(temp).length === 0;
  };

  const handleSave = async () => {
    if (!validate()) return;

    try {
      setSaving(true);

      if (availability) {
        await facultyAvailabilityService.updateAvailability(
          availability.availabilityId,
          formData
        );
      } else {
        await facultyAvailabilityService.createAvailability(
          formData
        );
      }

      reload();

      onClose();
    } catch (error) {
      console.error(error);

      setMessage(
        error.response?.data?.message ||
          "Unable to save Faculty Availability."
      );
    } finally {
      setSaving(false);
    }
  };

  return (
    <Dialog
      open={open}
      onClose={onClose}
      maxWidth="md"
      fullWidth
    >
      <DialogTitle>
        {availability
          ? "Update Faculty Availability"
          : "Add Faculty Availability"}
      </DialogTitle>

      <DialogContent dividers>
        {message && (
          <Alert severity="error" sx={{ mb: 2 }}>
            {message}
          </Alert>
        )}

        {loading ? (
          <Grid
            container
            justifyContent="center"
            sx={{ py: 5 }}
          >
            <CircularProgress />
          </Grid>
        ) : (
          <Grid container spacing={3} sx={{ mt: 0.5 }}>
            <Grid item xs={12} md={6}>
              <TextField
                select
                fullWidth
                label="Faculty"
                name="facultyId"
                value={formData.facultyId}
                onChange={handleChange}
                error={!!errors.facultyId}
                helperText={errors.facultyId}
              >
                {facultyList.map((faculty) => (
                  <MenuItem
                    key={faculty.facultyId}
                    value={faculty.facultyId}
                  >
                    {faculty.facultyCode} - {faculty.facultyName}
                  </MenuItem>
                ))}
              </TextField>
            </Grid>

            <Grid item xs={12} md={6}>
              <TextField
                select
                fullWidth
                label="Day"
                name="dayOfWeek"
                value={formData.dayOfWeek}
                onChange={handleChange}
                error={!!errors.dayOfWeek}
                helperText={errors.dayOfWeek}
              >
                {DAYS.map((day) => (
                  <MenuItem key={day} value={day}>
                    {day}
                  </MenuItem>
                ))}
              </TextField>
            </Grid>
                        <Grid item xs={12}>
              <TextField
                select
                fullWidth
                label="Time Slot"
                name="timeSlotId"
                value={formData.timeSlotId}
                onChange={handleChange}
                error={!!errors.timeSlotId}
                helperText={errors.timeSlotId}
              >
                {timeSlotList.map((slot) => (
                  <MenuItem
                    key={slot.timeSlotId}
                    value={slot.timeSlotId}
                  >
                    {slot.startTime} - {slot.endTime}
                  </MenuItem>
                ))}
              </TextField>
            </Grid>

            <Grid item xs={12}>
              <FormControlLabel
                control={
                  <Switch
                    checked={formData.available}
                    onChange={handleChange}
                    name="available"
                  />
                }
                label="Faculty Available"
              />
            </Grid>
          </Grid>
        )}
      </DialogContent>

      <DialogActions>
        <Button
          onClick={onClose}
          disabled={saving}
        >
          Cancel
        </Button>

        <Button
          variant="contained"
          onClick={handleSave}
          disabled={saving}
        >
          {saving
            ? "Saving..."
            : availability
            ? "Update"
            : "Save"}
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default FacultyAvailabilityForm;