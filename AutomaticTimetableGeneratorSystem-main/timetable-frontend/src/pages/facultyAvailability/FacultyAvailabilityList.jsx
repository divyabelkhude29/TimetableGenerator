import { useEffect, useMemo, useState } from "react";

import {
  Box,
  Typography,
  CircularProgress,
  Alert,
  TextField,
  InputAdornment,
  Button,
} from "@mui/material";

import AddIcon from "@mui/icons-material/Add";
import SearchIcon from "@mui/icons-material/Search";

import DashboardLayout from "../../components/layout/DashboardLayout";

import FacultyAvailabilityTable
  from "../../components/facultyAvailability/FacultyAvailabilityTable";

import FacultyAvailabilityForm
  from "../../components/facultyAvailability/FacultyAvailabilityForm";

import facultyAvailabilityService
  from "../../services/facultyAvailabilityService";


const FacultyAvailabilityList = () => {

  // =====================================================
  // STATE
  // =====================================================

  const [availabilityList, setAvailabilityList] =
    useState([]);

  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState("");

  const [open, setOpen] =
    useState(false);

  const [selectedAvailability, setSelectedAvailability] =
    useState(null);

  const [search, setSearch] =
    useState("");


  // =====================================================
  // LOAD FACULTY AVAILABILITY
  // =====================================================

  const loadAvailability = async () => {

    try {

      setLoading(true);

      setError("");

      const response =
        await facultyAvailabilityService
          .getAllAvailability();

      console.log(
        "Faculty Availability Response:",
        response
      );

      /*
       * Supports:
       *
       * [
       *   {...}
       * ]
       *
       * OR
       *
       * {
       *   data: [...]
       * }
       *
       * OR
       *
       * {
       *   content: [...]
       * }
       */

      let data = [];

      if (Array.isArray(response)) {

        data = response;

      } else if (
        Array.isArray(response?.data)
      ) {

        data = response.data;

      } else if (
        Array.isArray(response?.content)
      ) {

        data = response.content;

      }

      setAvailabilityList(data);

    } catch (error) {

      console.error(
        "Load Faculty Availability Error:",
        error
      );

      setAvailabilityList([]);

      setError(
        error?.response?.data?.message ||
        error?.response?.data?.error ||
        "Unable to load Faculty Availability."
      );

    } finally {

      setLoading(false);

    }

  };


  // =====================================================
  // INITIAL LOAD
  // =====================================================

  useEffect(() => {

    loadAvailability();

  }, []);


  // =====================================================
  // SEARCH FILTER
  // =====================================================

  const filteredAvailability = useMemo(() => {

    const keyword =
      search.trim().toLowerCase();

    if (!keyword) {

      return availabilityList;

    }

    return availabilityList.filter(
      (item) => {

        const facultyCode =
          String(
            item.facultyCode ||
            item.faculty?.facultyCode ||
            ""
          ).toLowerCase();

        const facultyName =
          String(
            item.facultyName ||
            (
              item.faculty
                ? `${item.faculty.firstName || ""} ${item.faculty.lastName || ""}`
                : ""
            ) ||
            ""
          ).toLowerCase();

        const dayOfWeek =
          String(
            item.dayOfWeek ||
            ""
          ).toLowerCase();

        const startTime =
          String(
            item.startTime ||
            item.timeSlot?.startTime ||
            ""
          ).toLowerCase();

        const endTime =
          String(
            item.endTime ||
            item.timeSlot?.endTime ||
            ""
          ).toLowerCase();


        return (

          facultyCode.includes(keyword) ||

          facultyName.includes(keyword) ||

          dayOfWeek.includes(keyword) ||

          startTime.includes(keyword) ||

          endTime.includes(keyword)

        );

      }
    );

  }, [
    availabilityList,
    search
  ]);


  // =====================================================
  // ADD
  // =====================================================

  const handleAdd = () => {

    console.log(
      "Opening Add Faculty Availability Form"
    );

    setSelectedAvailability(null);

    setOpen(true);

  };


  // =====================================================
  // EDIT
  // =====================================================

  const handleEdit = (
    availability
  ) => {

    console.log(
      "Editing Faculty Availability:",
      availability
    );

    setSelectedAvailability(
      availability
    );

    setOpen(true);

  };


  // =====================================================
  // CLOSE FORM
  // =====================================================

  const handleClose = () => {

    setOpen(false);

    setSelectedAvailability(null);

  };


  // =====================================================
  // RENDER
  // =====================================================

  return (

    <DashboardLayout>

      {/* =================================================
          PAGE HEADER
      ================================================= */}

      <Box
        display="flex"
        justifyContent="space-between"
        alignItems="center"
        mb={3}
        gap={2}
        flexWrap="wrap"
      >

        <Typography
          variant="h4"
          fontWeight="bold"
        >

          Faculty Availability Management

        </Typography>


        {/* =================================================
            ADD BUTTON
        ================================================= */}

        <Button
          variant="contained"
          color="primary"
          startIcon={<AddIcon />}
          onClick={handleAdd}
          sx={{
            minWidth: 180,
            height: 45,
            borderRadius: 2,
            fontWeight: "bold",
            textTransform: "none",
          }}
        >

          Add Availability

        </Button>

      </Box>


      {/* =================================================
          SEARCH
      ================================================= */}

      <TextField
        fullWidth
        placeholder="Search by Faculty Code, Faculty Name, Day or Time Slot..."
        value={search}
        onChange={(e) =>
          setSearch(e.target.value)
        }
        sx={{
          mb: 3
        }}
        InputProps={{
          startAdornment: (

            <InputAdornment
              position="start"
            >

              <SearchIcon />

            </InputAdornment>

          ),
        }}
      />


      {/* =================================================
          ERROR
      ================================================= */}

      {error && (

        <Alert
          severity="error"
          sx={{
            mb: 2
          }}
          onClose={() =>
            setError("")
          }
        >

          {error}

        </Alert>

      )}


      {/* =================================================
          LOADING
      ================================================= */}

      {loading ? (

        <Box
          display="flex"
          justifyContent="center"
          alignItems="center"
          mt={5}
          minHeight={200}
        >

          <CircularProgress />

        </Box>

      ) : (

        /* =================================================
           TABLE
        ================================================= */

        <FacultyAvailabilityTable

          availabilityList={
            filteredAvailability
          }

          onEdit={
            handleEdit
          }

          reload={
            loadAvailability
          }

        />

      )}


      {/* =================================================
          ADD / EDIT FORM
      ================================================= */}

      <FacultyAvailabilityForm

        open={
          open
        }

        onClose={
          handleClose
        }

        availability={
          selectedAvailability
        }

        reload={
          loadAvailability
        }

      />

    </DashboardLayout>

  );

};


export default FacultyAvailabilityList;