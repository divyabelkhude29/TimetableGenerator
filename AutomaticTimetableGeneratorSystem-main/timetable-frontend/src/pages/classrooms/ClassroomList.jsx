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
import ClassroomTable from "../../components/classrooms/ClassroomTable";
import ClassroomForm from "../../components/classrooms/ClassroomForm";

import classroomService from "../../services/classroomService";

const ClassroomList = () => {

  const [classrooms, setClassrooms] = useState([]);

  const [loading, setLoading] = useState(true);

  const [error, setError] = useState("");

  const [open, setOpen] = useState(false);

  const [selectedClassroom, setSelectedClassroom] = useState(null);

  const loadClassrooms = async () => {

    try {

      setLoading(true);

      setError("");

      const response =
        await classroomService.getAllClassrooms();

      if (Array.isArray(response)) {

        setClassrooms(response);

      } else {

        setClassrooms([]);

      }

    } catch (error) {

      console.error(error);

      setError("Unable to load classrooms.");

      setClassrooms([]);

    } finally {

      setLoading(false);

    }

  };

  useEffect(() => {

    loadClassrooms();

  }, []);

  const handleAdd = () => {

    setSelectedClassroom(null);

    setOpen(true);

  };

  const handleEdit = (classroom) => {

    setSelectedClassroom(classroom);

    setOpen(true);

  };

  const handleDelete = async (id) => {

    if (!window.confirm("Delete this classroom?")) {

      return;

    }

    try {

      await classroomService.deleteClassroom(id);

      loadClassrooms();

    } catch (error) {

      console.error(error);

      alert("Unable to delete classroom.");

    }

  };

  const handleClose = () => {

    setOpen(false);

    setSelectedClassroom(null);

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

          Classroom Management

        </Typography>

        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={handleAdd}
        >

          Add Classroom

        </Button>

      </Box>

      {error && (

        <Alert
          severity="error"
          sx={{ mb:2 }}
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

        <ClassroomTable
          classrooms={classrooms}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />

      )}

      <ClassroomForm
        open={open}
        onClose={handleClose}
        classroom={selectedClassroom}
        reload={loadClassrooms}
      />

    </DashboardLayout>

  );

};

export default ClassroomList;