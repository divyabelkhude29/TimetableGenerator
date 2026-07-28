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

import SectionTable from "../../components/sections/SectionTable";
import SectionForm from "../../components/sections/SectionForm";

import sectionService from "../../services/sectionService";

const SectionList = () => {

  const [sections, setSections] = useState([]);

  const [loading, setLoading] = useState(true);

  const [error, setError] = useState("");

  const [open, setOpen] = useState(false);

  const [selectedSection, setSelectedSection] = useState(null);

  const loadSections = async () => {

    try {

      setLoading(true);

      const response = await sectionService.getAllSections();

      setSections(response);

    } catch (error) {

      console.error(error);

      setError("Unable to load sections.");

    } finally {

      setLoading(false);

    }

  };

  useEffect(() => {

    loadSections();

  }, []);

  const handleAdd = () => {

    setSelectedSection(null);

    setOpen(true);

  };

  const handleEdit = (section) => {

    setSelectedSection(section);

    setOpen(true);

  };

  const handleDelete = async (id) => {

    if (!window.confirm("Delete this section?")) {

      return;

    }

    try {

      await sectionService.deleteSection(id);

      loadSections();

    } catch (error) {

      console.error(error);

      alert("Unable to delete section.");

    }

  };

  const handleClose = () => {

    setOpen(false);

    setSelectedSection(null);

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

          Section Management

        </Typography>

        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={handleAdd}
        >

          Add Section

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

        <SectionTable
          sections={sections}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />

      )}

      <SectionForm
        open={open}
        onClose={handleClose}
        section={selectedSection}
        reload={loadSections}
      />

    </DashboardLayout>

  );

};

export default SectionList;