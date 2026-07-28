import { useEffect, useState } from "react";

import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Grid,
  TextField,
  Button,
  FormControlLabel,
  Switch,
  CircularProgress,
  Alert,
} from "@mui/material";

import departmentService from "../../services/departmentService";

const initialState = {
  departmentCode: "",
  departmentName: "",
  hodName: "",
  description: "",
  active: true,
};

const DepartmentForm = ({
  open,
  onClose,
  department,
  reload,
}) => {
  const [formData, setFormData] = useState(initialState);

  const [loading, setLoading] = useState(false);

  const [error, setError] = useState("");

  useEffect(() => {
    if (department) {
      setFormData({
        departmentCode: department.departmentCode || "",
        departmentName: department.departmentName || "",
        hodName: department.hodName || "",
        description: department.description || "",
        active: department.active,
      });
    } else {
      setFormData(initialState);
    }

    setError("");
  }, [department, open]);

  const handleChange = (e) => {
    const { name, value, checked, type } = e.target;

    setFormData((prev) => ({
      ...prev,
      [name]:
        type === "checkbox"
          ? checked
          : value,
    }));
  };

  const validate = () => {
    if (
      formData.departmentCode.trim().length < 2 ||
      formData.departmentCode.trim().length > 10
    ) {
      setError(
        "Department Code must be between 2 and 10 characters."
      );
      return false;
    }

    if (
      formData.departmentName.trim().length < 3 ||
      formData.departmentName.trim().length > 100
    ) {
      setError(
        "Department Name must be between 3 and 100 characters."
      );
      return false;
    }

    return true;
  };

  const handleSubmit = async () => {
    if (!validate()) return;

    try {
      setLoading(true);

      if (department) {
        await departmentService.updateDepartment(
          department.departmentId,
          formData
        );
      } else {
        await departmentService.createDepartment(
          formData
        );
      }

      reload();

      onClose();
    } catch (err) {
      console.error(err);

      setError(
        err.response?.data?.message ||
          "Unable to save department."
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <Dialog
      open={open}
      fullWidth
      maxWidth="md"
    >
      <DialogTitle>
        {department
          ? "Update Department"
          : "Add Department"}
      </DialogTitle>

      <DialogContent>

        {error && (
          <Alert
            severity="error"
            sx={{ mb: 2 }}
          >
            {error}
          </Alert>
        )}

        <Grid
          container
          spacing={2}
          mt={1}
        >
          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              label="Department Code"
              name="departmentCode"
              value={formData.departmentCode}
              onChange={handleChange}
            />
          </Grid>

          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              label="Department Name"
              name="departmentName"
              value={formData.departmentName}
              onChange={handleChange}
            />
          </Grid>

          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              label="HOD Name"
              name="hodName"
              value={formData.hodName}
              onChange={handleChange}
            />
          </Grid>

          <Grid item xs={12}>
            <TextField
              fullWidth
              multiline
              rows={4}
              label="Description"
              name="description"
              value={formData.description}
              onChange={handleChange}
            />
          </Grid>

          <Grid item xs={12}>
            <FormControlLabel
              control={
                <Switch
                  checked={formData.active}
                  onChange={handleChange}
                  name="active"
                />
              }
              label="Active Department"
            />
          </Grid>
        </Grid>
      </DialogContent>

      <DialogActions>
        <Button
          color="inherit"
          onClick={onClose}
        >
          Cancel
        </Button>

        <Button
          variant="contained"
          onClick={handleSubmit}
          disabled={loading}
        >
          {loading ? (
            <CircularProgress
              size={22}
              color="inherit"
            />
          ) : department ? (
            "Update"
          ) : (
            "Save"
          )}
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default DepartmentForm;