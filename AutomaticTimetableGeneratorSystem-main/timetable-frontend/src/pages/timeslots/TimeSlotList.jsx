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
import TimeSlotTable from "../../components/timeslots/TimeSlotTable";
import TimeSlotForm from "../../components/timeslots/TimeSlotForm";
import timeSlotService from "../../services/timeSlotService";

const TimeSlotList = () => {

  const [timeSlots, setTimeSlots] = useState([]);

  const [loading, setLoading] = useState(true);

  const [error, setError] = useState("");

  const [open, setOpen] = useState(false);

  const [selectedTimeSlot, setSelectedTimeSlot] =
    useState(null);

  const loadTimeSlots = async () => {

    try {

      setLoading(true);

      setError("");

      const response =
        await timeSlotService.getAllTimeSlots();

      console.log("Time Slots :", response);

      if (Array.isArray(response)) {

        setTimeSlots(response);

      } else {

        setTimeSlots([]);

      }

    } catch (err) {

      console.error(err);

      setTimeSlots([]);

      setError("Unable to load time slots.");

    } finally {

      setLoading(false);

    }

  };

  useEffect(() => {

    loadTimeSlots();

  }, []);

  const handleAdd = () => {

    setSelectedTimeSlot(null);

    setOpen(true);

  };

  const handleEdit = (timeSlot) => {

    setSelectedTimeSlot(timeSlot);

    setOpen(true);

  };

  const handleDelete = async (timeSlotId) => {

    if (!window.confirm("Delete this time slot?")) {

      return;

    }

    try {

      await timeSlotService.deleteTimeSlot(timeSlotId);

      loadTimeSlots();

    } catch (err) {

      console.error(err);

      alert("Unable to delete time slot.");

    }

  };

  const handleClose = () => {

    setOpen(false);

    setSelectedTimeSlot(null);

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

          Time Slot Management

        </Typography>

        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={handleAdd}
        >

          Add Time Slot

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

        <Box
          display="flex"
          justifyContent="center"
          mt={5}
        >

          <CircularProgress />

        </Box>

      ) : (

        <TimeSlotTable
          timeSlots={timeSlots}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />

      )}

      <TimeSlotForm
        open={open}
        onClose={handleClose}
        timeSlot={selectedTimeSlot}
        reload={loadTimeSlots}
      />

    </DashboardLayout>

  );

};

export default TimeSlotList;