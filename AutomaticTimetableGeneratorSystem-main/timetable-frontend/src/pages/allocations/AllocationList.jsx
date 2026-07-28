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
import AllocationTable from "../../components/allocations/AllocationTable";
import AllocationForm from "../../components/allocations/AllocationForm";
import allocationService from "../../services/allocationService";

const AllocationList = () => {

  const [allocationList, setAllocationList] = useState([]);

  const [loading, setLoading] = useState(true);

  const [error, setError] = useState("");

  const [open, setOpen] = useState(false);

  const [selectedAllocation, setSelectedAllocation] = useState(null);

  const loadAllocations = async () => {

    try {

      setLoading(true);

      setError("");

      const response =
        await allocationService.getAllAllocations();

      console.log("Allocation Response :", response);

      if (Array.isArray(response)) {

        setAllocationList(response);

      } else if (Array.isArray(response.data)) {

        setAllocationList(response.data);

      } else {

        setAllocationList([]);

      }

    } catch (err) {

      console.error(err);

      setAllocationList([]);

      setError("Unable to load allocations.");

    } finally {

      setLoading(false);

    }

  };

  useEffect(() => {

    loadAllocations();

  }, []);

  const handleAdd = () => {

    setSelectedAllocation(null);

    setOpen(true);

  };

  const handleEdit = (allocation) => {

    setSelectedAllocation(allocation);

    setOpen(true);

  };

  const handleDelete = async (allocationId) => {

    if (!window.confirm("Delete this allocation?")) {

      return;

    }

    try {

      await allocationService.deleteAllocation(allocationId);

      loadAllocations();

    } catch (err) {

      console.error(err);

      alert("Unable to delete allocation.");

    }

  };

  const handleClose = () => {

    setOpen(false);

    setSelectedAllocation(null);

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

          Faculty Subject Allocation

        </Typography>

        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={handleAdd}
        >

          Add Allocation

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

        <AllocationTable
          allocations={allocationList}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />

      )}

      <AllocationForm
        open={open}
        onClose={handleClose}
        allocation={selectedAllocation}
        reload={loadAllocations}
      />

    </DashboardLayout>

  );

};

export default AllocationList;