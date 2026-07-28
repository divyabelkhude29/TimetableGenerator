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
import StudentTable from "../../components/students/StudentTable";
import StudentForm from "../../components/students/StudentForm";
import studentService from "../../services/studentService";

const StudentList = () => {

  const [studentList, setStudentList] = useState([]);

  const [loading, setLoading] = useState(true);

  const [error, setError] = useState("");

  const [open, setOpen] = useState(false);

  const [selectedStudent, setSelectedStudent] = useState(null);

  const loadStudents = async () => {

    try {

      setLoading(true);

      setError("");

      const response =
        await studentService.getAllStudents();

      console.log("Students Response :", response);

      if (Array.isArray(response)) {

        setStudentList(response);

      } else {

        setStudentList([]);

      }

    } catch (err) {

      console.error(err);

      setStudentList([]);

      setError("Unable to load students.");

    } finally {

      setLoading(false);

    }

  };

  useEffect(() => {

    loadStudents();

  }, []);

  const handleAdd = () => {

    setSelectedStudent(null);

    setOpen(true);

  };

  const handleEdit = (student) => {

    setSelectedStudent(student);

    setOpen(true);

  };

  const handleDelete = async (studentId) => {

    if (!window.confirm("Delete this student?")) {

      return;

    }

    try {

      await studentService.deleteStudent(studentId);

      loadStudents();

    } catch (err) {

      console.error(err);

      alert("Unable to delete student.");

    }

  };

  const handleClose = () => {

    setOpen(false);

    setSelectedStudent(null);

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

          Student Management

        </Typography>

        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={handleAdd}
        >

          Add Student

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

        <StudentTable
          students={studentList}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />

      )}

      <StudentForm
        open={open}
        onClose={handleClose}
        student={selectedStudent}
        reload={loadStudents}
      />

    </DashboardLayout>

  );

};

export default StudentList;