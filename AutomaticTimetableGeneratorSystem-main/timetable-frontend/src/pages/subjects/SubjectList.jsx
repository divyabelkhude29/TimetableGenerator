import { useEffect, useState } from "react";

import {
  Box,
  Typography,
  Button,
  CircularProgress,
  Alert,
  Snackbar,
} from "@mui/material";

import MuiAlert from "@mui/material/Alert";

import AddIcon from "@mui/icons-material/Add";

import DashboardLayout from "../../components/layout/DashboardLayout";
import SubjectTable from "../../components/subjects/SubjectTable";
import SubjectForm from "../../components/subjects/SubjectForm";

import subjectService from "../../services/subjectService";

const SubjectList = () => {

  const [subjects, setSubjects] = useState([]);

  const [loading, setLoading] = useState(true);

  const [error, setError] = useState("");

  const [open, setOpen] = useState(false);

  const [selectedSubject, setSelectedSubject] = useState(null);

  const [snackbar, setSnackbar] = useState({

    open: false,

    message: "",

    severity: "success",

  });

  const loadSubjects = async () => {

    try {

      setLoading(true);

      setError("");

      const response = await subjectService.getAllSubjects();

      console.log("Subjects :", response);

      if (Array.isArray(response)) {

        setSubjects(response);

      } else {

        setSubjects([]);

      }

    } catch (err) {

      console.error(err);

      setSubjects([]);

      setError("Unable to load subjects.");

    } finally {

      setLoading(false);

    }

  };

  useEffect(() => {

    loadSubjects();

  }, []);

  const handleAdd = () => {

    setSelectedSubject(null);

    setOpen(true);

  };

  const handleEdit = (subject) => {

    setSelectedSubject(subject);

    setOpen(true);

  };

  const handleDelete = async (subjectId) => {

    if (!window.confirm("Delete this subject?")) {

      return;

    }

    try {

      await subjectService.deleteSubject(subjectId);

      setSnackbar({

        open: true,

        message: "Subject deleted successfully.",

        severity: "success",

      });

      loadSubjects();

    } catch (err) {

      console.error(err);

      setSnackbar({

        open: true,

        message: err.response?.data?.message || "Unable to delete subject.",

        severity: "error",

      });

    }

  };

  const handleClose = () => {

    setOpen(false);

    setSelectedSubject(null);

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

          Subject Management

        </Typography>

        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={handleAdd}
        >

          Add Subject

        </Button>

      </Box>

      {

        error && (

          <Alert
            severity="error"
            sx={{ mb: 2 }}
          >

            {error}

          </Alert>

        )

      }

      {

        loading ?

        (

          <Box
            display="flex"
            justifyContent="center"
            mt={5}
          >

            <CircularProgress />

          </Box>

        )

        :

        (

          <SubjectTable

            subjects={subjects}

            onEdit={handleEdit}

            onDelete={handleDelete}

          />

        )

      }

      <SubjectForm

        open={open}

        onClose={handleClose}

        subject={selectedSubject}

        reload={loadSubjects}

      />

      <Snackbar

        open={snackbar.open}

        autoHideDuration={3000}

        onClose={() =>

          setSnackbar({

            ...snackbar,

            open: false,

          })

        }

      >

        <MuiAlert

          elevation={6}

          variant="filled"

          severity={snackbar.severity}

        >

          {snackbar.message}

        </MuiAlert>

      </Snackbar>

    </DashboardLayout>

  );

};

export default SubjectList;