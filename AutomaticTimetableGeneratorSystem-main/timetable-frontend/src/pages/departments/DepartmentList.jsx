import { useEffect, useMemo, useState } from "react";

import {
  Box,
  Typography,
  Button,
  CircularProgress,
  Alert,
  TextField,
  InputAdornment,
} from "@mui/material";

import AddIcon from "@mui/icons-material/Add";
import SearchIcon from "@mui/icons-material/Search";

import DashboardLayout from "../../components/layout/DashboardLayout";
import DepartmentTable from "../../components/departments/DepartmentTable";
import DepartmentForm from "../../components/departments/DepartmentForm";
import departmentService from "../../services/departmentService";

const DepartmentList = () => {
  const [departments, setDepartments] = useState([]);

  const [loading, setLoading] = useState(true);

  const [error, setError] = useState("");

  const [open, setOpen] = useState(false);

  const [selectedDepartment, setSelectedDepartment] = useState(null);

  const [search, setSearch] = useState("");

  const loadDepartments = async () => {
    try {
      setLoading(true);

      const response = await departmentService.getAllDepartments();

      setDepartments(response);
    } catch (error) {
      console.error(error);

      setError("Unable to load departments.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadDepartments();
  }, []);

  const filteredDepartments = useMemo(() => {
    return departments.filter((department) => {
      const keyword = search.toLowerCase();

      return (
        department.departmentCode?.toLowerCase().includes(keyword) ||
        department.departmentName?.toLowerCase().includes(keyword) ||
        department.hodName?.toLowerCase().includes(keyword)
      );
    });
  }, [departments, search]);

  const handleAdd = () => {
    setSelectedDepartment(null);

    setOpen(true);
  };

  const handleEdit = (department) => {
    setSelectedDepartment(department);

    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);

    setSelectedDepartment(null);
  };

  return (
    <DashboardLayout>
      <Box
        display="flex"
        justifyContent="space-between"
        alignItems="center"
        mb={3}
      >
        <Typography variant="h4" fontWeight="bold">
          Department Management
        </Typography>

        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={handleAdd}
        >
          Add Department
        </Button>
      </Box>

      <TextField
        fullWidth
        placeholder="Search by Department Code, Name or HOD..."
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
        <Alert severity="error" sx={{ mb: 2 }}>
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
        <DepartmentTable
          departments={filteredDepartments}
          onEdit={handleEdit}
          reload={loadDepartments}
        />
      )}

      <DepartmentForm
        open={open}
        onClose={handleClose}
        department={selectedDepartment}
        reload={loadDepartments}
      />
    </DashboardLayout>
  );
};

export default DepartmentList;