import { useEffect, useMemo, useState } from "react";

import {
  Box,
  Typography,
  CircularProgress,
  Alert,
  TextField,
  InputAdornment,
} from "@mui/material";

import SearchIcon from "@mui/icons-material/Search";

import DashboardLayout from "../../components/layout/DashboardLayout";
import FacultyAvailabilityTable from "../../components/facultyAvailability/FacultyAvailabilityTable";
import FacultyAvailabilityForm from "../../components/facultyAvailability/FacultyAvailabilityForm";
import facultyAvailabilityService from "../../services/facultyAvailabilityService";

const FacultyAvailabilityList = () => {

  const [availabilityList, setAvailabilityList] = useState([]);

  const [loading, setLoading] = useState(true);

  const [error, setError] = useState("");

  const [open, setOpen] = useState(false);

  const [selectedAvailability, setSelectedAvailability] = useState(null);

  const [search, setSearch] = useState("");

  const loadAvailability = async () => {

    try {

      setLoading(true);

      const response =
        await facultyAvailabilityService.getAllAvailability();

      setAvailabilityList(response);

    } catch (error) {

      console.error(error);

      setError("Unable to load Faculty Availability.");

    } finally {

      setLoading(false);

    }

  };

  useEffect(() => {

    loadAvailability();

  }, []);

  const filteredAvailability = useMemo(() => {

    const keyword = search.toLowerCase();

    return availabilityList.filter((item) =>

      item.facultyCode?.toLowerCase().includes(keyword) ||

      item.facultyName?.toLowerCase().includes(keyword) ||

      item.dayOfWeek?.toLowerCase().includes(keyword) ||

      item.startTime?.toLowerCase().includes(keyword) ||

      item.endTime?.toLowerCase().includes(keyword)

    );

  }, [availabilityList, search]);

  const handleAdd = () => {

    setSelectedAvailability(null);

    setOpen(true);

  };

  const handleEdit = (availability) => {

    setSelectedAvailability(availability);

    setOpen(true);

  };

  const handleClose = () => {

    setOpen(false);

    setSelectedAvailability(null);

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
          Faculty Availability Management
        </Typography>

      </Box>

      <TextField
        fullWidth
        placeholder="Search by Faculty Code, Faculty Name, Day or Time Slot..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
        sx={{ mb: 3 }}
        InputProps={{
          startAdornment: (
            <InputAdornment position="start">
              <SearchIcon />
            </InputAdornment>
          ),
        }}
      />

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
      ) : (        <FacultyAvailabilityTable
          availabilityList={filteredAvailability}
          onEdit={handleEdit}
          reload={loadAvailability}
        />
      )}

      <FacultyAvailabilityForm
        open={open}
        onClose={handleClose}
        availability={selectedAvailability}
        reload={loadAvailability}
      />

    </DashboardLayout>

  );

};

export default FacultyAvailabilityList;