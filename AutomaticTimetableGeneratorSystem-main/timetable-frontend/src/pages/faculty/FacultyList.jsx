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
import FacultyTable from "../../components/faculty/FacultyTable";
import FacultyForm from "../../components/faculty/FacultyForm";
import facultyService from "../../services/facultyService";

const FacultyList = () => {

  const [facultyList, setFacultyList] = useState([]);

  const [loading, setLoading] = useState(true);

  const [error, setError] = useState("");

  const [open, setOpen] = useState(false);

  const [selectedFaculty, setSelectedFaculty] = useState(null);

  const loadFaculty = async () => {

    try {

      setLoading(true);

      setError("");

      const response = await facultyService.getAllFaculty();

      console.log("Faculty Response :", response);

      if (Array.isArray(response)) {

        setFacultyList(response);

      } else {

        setFacultyList([]);

      }

    } catch (err) {

      console.error(err);

      setFacultyList([]);

      setError("Unable to load faculty.");

    } finally {

      setLoading(false);

    }

  };

  useEffect(() => {

    loadFaculty();

  }, []);

  const handleAdd = () => {

    setSelectedFaculty(null);

    setOpen(true);

  };

  const handleEdit = (faculty) => {

    setSelectedFaculty(faculty);

    setOpen(true);

  };

  const handleDelete = async (facultyId) => {

    if (!window.confirm("Delete this faculty?")) {

      return;

    }

    try {

      await facultyService.deleteFaculty(facultyId);

      loadFaculty();

    } catch (err) {

      console.error(err);

      alert("Unable to delete faculty.");

    }

  };

  const handleClose = () => {

    setOpen(false);

    setSelectedFaculty(null);

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

          Faculty Management

        </Typography>

        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={handleAdd}
        >

          Add Faculty

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

        <FacultyTable
          faculty={facultyList}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />

      )}

      <FacultyForm
        open={open}
        onClose={handleClose}
        faculty={selectedFaculty}
        reload={loadFaculty}
      />

    </DashboardLayout>

  );

};

export default FacultyList;