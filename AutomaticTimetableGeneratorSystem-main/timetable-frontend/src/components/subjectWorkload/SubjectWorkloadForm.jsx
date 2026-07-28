import { useEffect, useState } from "react";

import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  Grid,
  TextField,
  MenuItem,
  FormControlLabel,
  Switch,
} from "@mui/material";

import allocationService from "../../services/allocationService";
import subjectWorkloadService from "../../services/subjectWorkloadService";

const SubjectWorkloadForm = ({
  open,
  onClose,
  workload,
  reload,
}) => {

  const [allocationList, setAllocationList] = useState([]);

  const [saving, setSaving] = useState(false);

  const [formData, setFormData] = useState({
    allocationId: "",
    weeklyHours: "",
    theoryHours: "",
    practicalHours: "",
    active: true,
  });

  const [errors, setErrors] = useState({});

  useEffect(() => {

    if (open) {

      loadAllocations();

      if (workload) {

        setFormData({

          allocationId: workload.allocationId,

          weeklyHours: workload.weeklyHours,

          theoryHours: workload.theoryHours,

          practicalHours: workload.practicalHours,

          active: workload.active,

        });

      } else {

        setFormData({

          allocationId: "",

          weeklyHours: "",

          theoryHours: "",

          practicalHours: "",

          active: true,

        });

      }

    }

  }, [open, workload]);

  const loadAllocations = async () => {

    try {

      const data =
        await allocationService.getAllAllocations();

      setAllocationList(data);

    } catch (error) {

      console.error(error);

    }

  };

  const handleChange = (event) => {

    const { name, value, checked, type } = event.target;

    setFormData({

      ...formData,

      [name]:
        type === "checkbox"
          ? checked
          : value,

    });

  };

  const validate = () => {

    let temp = {};

    if (!formData.allocationId)
      temp.allocationId = "Allocation is required";

    if (!formData.weeklyHours)
      temp.weeklyHours = "Weekly Hours required";

    if (!formData.theoryHours)
      temp.theoryHours = "Theory Hours required";

    if (!formData.practicalHours)
      temp.practicalHours = "Practical Hours required";

    setErrors(temp);

    return Object.keys(temp).length === 0;

  };

  const handleSave = async () => {

    if (!validate()) return;

    try {

      setSaving(true);

      if (workload) {

        await subjectWorkloadService.updateSubjectWorkload(
          workload.workloadId,
          formData
        );

      } else {

        await subjectWorkloadService.saveSubjectWorkload(
          formData
        );

      }

      reload();

      onClose();

    } catch (error) {

      console.error(error);

    } finally {

      setSaving(false);

    }

  };

  return (

    <Dialog
      open={open}
      onClose={onClose}
      fullWidth
      maxWidth="md"
    >

      <DialogTitle>

        {workload
          ? "Update Subject Workload"
          : "Add Subject Workload"}

      </DialogTitle>

      <DialogContent>

        <Grid
          container
          spacing={3}
          sx={{ mt: 1 }}
        >

          <Grid item xs={12}>

            <TextField
              select
              fullWidth
              label="Faculty Allocation"
              name="allocationId"
              value={formData.allocationId}
              onChange={handleChange}
              error={!!errors.allocationId}
              helperText={errors.allocationId}
            >

              {allocationList.map((item) => (

                <MenuItem
                  key={item.allocationId}
                  value={item.allocationId}
                >
                  {item.facultyCode} - {item.subjectCode}
                </MenuItem>

              ))}

            </TextField>

          </Grid>
                    <Grid item xs={12} md={4}>
            <TextField
              fullWidth
              type="number"
              label="Weekly Hours"
              name="weeklyHours"
              value={formData.weeklyHours}
              onChange={handleChange}
              error={!!errors.weeklyHours}
              helperText={errors.weeklyHours}
              inputProps={{
                min: 1,
              }}
            />
          </Grid>

          <Grid item xs={12} md={4}>
            <TextField
              fullWidth
              type="number"
              label="Theory Hours"
              name="theoryHours"
              value={formData.theoryHours}
              onChange={handleChange}
              error={!!errors.theoryHours}
              helperText={errors.theoryHours}
              inputProps={{
                min: 0,
              }}
            />
          </Grid>

          <Grid item xs={12} md={4}>
            <TextField
              fullWidth
              type="number"
              label="Practical Hours"
              name="practicalHours"
              value={formData.practicalHours}
              onChange={handleChange}
              error={!!errors.practicalHours}
              helperText={errors.practicalHours}
              inputProps={{
                min: 0,
              }}
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
              label="Active"
            />
          </Grid>

        </Grid>

      </DialogContent>

      <DialogActions>

        <Button
          onClick={onClose}
          disabled={saving}
        >
          Cancel
        </Button>

        <Button
          variant="contained"
          onClick={handleSave}
          disabled={saving}
        >
          {saving
            ? "Saving..."
            : workload
            ? "Update"
            : "Save"}
        </Button>

      </DialogActions>

    </Dialog>

  );

};

export default SubjectWorkloadForm;