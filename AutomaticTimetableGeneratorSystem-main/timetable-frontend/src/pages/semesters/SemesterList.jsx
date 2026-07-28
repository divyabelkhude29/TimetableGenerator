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
import SemesterTable from "../../components/semesters/SemesterTable";
import SemesterForm from "../../components/semesters/SemesterForm";

import semesterService from "../../services/semesterService";

const SemesterList = () => {
  const [semesters, setSemesters] = useState([]);

  const [loading, setLoading] = useState(true);

  const [error, setError] = useState("");

  const [open, setOpen] = useState(false);

  const [selectedSemester, setSelectedSemester] = useState(null);

  const loadSemesters = async () => {
    try {
      setLoading(true);

      const response =
        await semesterService.getAllSemesters();

      setSemesters(response);

      setError("");
    } catch (err) {
      console.error(err);

      setError("Unable to load semesters.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadSemesters();
  }, []);

  const handleAdd = () => {
    setSelectedSemester(null);
    setOpen(true);
  };

  const handleEdit = (semester) => {
    setSelectedSemester(semester);
    setOpen(true);
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this semester?")) return;

    try {
      await semesterService.deleteSemester(id);

      loadSemesters();
    } catch (err) {
      console.error(err);

      alert("Unable to delete semester.");
    }
  };

  const handleClose = () => {
    setOpen(false);
    setSelectedSemester(null);
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
          Semester Management
        </Typography>

        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={handleAdd}
        >
          Add Semester
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
        <CircularProgress />
      ) : (
        <SemesterTable
          semesters={semesters}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />
      )}

      <SemesterForm
        open={open}
        onClose={handleClose}
        semester={selectedSemester}
        reload={loadSemesters}
      />

    </DashboardLayout>
  );
};

export default SemesterList;