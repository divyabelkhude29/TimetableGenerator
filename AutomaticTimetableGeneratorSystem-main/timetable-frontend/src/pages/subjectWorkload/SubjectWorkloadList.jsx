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
import SubjectWorkloadTable from "../../components/subjectWorkload/SubjectWorkloadTable";
import SubjectWorkloadForm from "../../components/subjectWorkload/SubjectWorkloadForm";
import subjectWorkloadService from "../../services/subjectWorkloadService";

const SubjectWorkloadList = () => {

  const [workloads, setWorkloads] = useState([]);

  const [loading, setLoading] = useState(true);

  const [error, setError] = useState("");

  const [open, setOpen] = useState(false);

  const [selectedWorkload, setSelectedWorkload] = useState(null);

  const [search, setSearch] = useState("");

  const loadWorkloads = async () => {

    try {

      setLoading(true);

      const response =
        await subjectWorkloadService.getAllSubjectWorkloads();

      setWorkloads(response);

    } catch (error) {

      console.error(error);

      setError("Unable to load Subject Workloads.");

    } finally {

      setLoading(false);

    }

  };

  useEffect(() => {

    loadWorkloads();

  }, []);

  const filteredWorkloads = useMemo(() => {

    const keyword = search.toLowerCase();

    return workloads.filter((item) =>

      item.facultyCode?.toLowerCase().includes(keyword) ||

      item.facultyName?.toLowerCase().includes(keyword) ||

      item.subjectCode?.toLowerCase().includes(keyword) ||

      item.subjectName?.toLowerCase().includes(keyword) ||

      item.sectionName?.toLowerCase().includes(keyword)

    );

  }, [workloads, search]);

  const handleAdd = () => {

    setSelectedWorkload(null);

    setOpen(true);

  };

  const handleEdit = (workload) => {

    setSelectedWorkload(workload);

    setOpen(true);

  };

  const handleClose = () => {

    setOpen(false);

    setSelectedWorkload(null);

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
          Subject Workload Management
        </Typography>

      </Box>

      <TextField
        fullWidth
        placeholder="Search by Faculty, Subject or Section..."
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
      ) : (        <SubjectWorkloadTable
          workloads={filteredWorkloads}
          onEdit={handleEdit}
          reload={loadWorkloads}
        />
      )}

      <SubjectWorkloadForm
        open={open}
        onClose={handleClose}
        workload={selectedWorkload}
        reload={loadWorkloads}
      />

    </DashboardLayout>

  );

};

export default SubjectWorkloadList;