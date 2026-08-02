import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  Avatar,
  Box,
  Button,
  CircularProgress,
  Container,
  Divider,
  Grid,
  Paper,
  Snackbar,
  Alert,
  TextField,
  Typography,
  Stack,
  IconButton,
  InputAdornment,
} from "@mui/material";
import { ArrowBack, Visibility, VisibilityOff } from "@mui/icons-material";

import profileService from "../../services/profileService";
import "../../styles/Profile.css";

const Profile = () => {
  const navigate = useNavigate();
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  const [loading, setLoading] = useState(true);

  const [profile, setProfile] = useState({
    username: "",
    firstName: "",
    lastName: "",
    role: "",
    email: "",
    mobile: "",
  });

  const [formData, setFormData] = useState({
    email: "",
    mobile: "",
    password: "",
    confirmPassword: "",
  });

  const [errors, setErrors] = useState({});

  const [snackbar, setSnackbar] = useState({
    open: false,
    severity: "success",
    message: "",
  });

  useEffect(() => {
    loadProfile();
  }, []);

  const loadProfile = async () => {
    try {
      setLoading(true);

      const data = await profileService.getProfile();

      setProfile(data);

      setFormData({
        email: data.email || "",
        mobile: data.mobile || "",
        password: "",
        confirmPassword: "",
      });
    } catch (error) {
      setSnackbar({
        open: true,
        severity: "error",
        message: "Failed to load profile.",
      });
    } finally {
      setLoading(false);
    }
  };

  const handleBack = () => {
    switch (profile.role) {
      case "ROLE_ADMIN":
        navigate("/admin");
        break;

      case "ROLE_FACULTY":
        navigate("/faculty");
        break;

      case "ROLE_STUDENT":
        navigate("/student");
        break;

      default:
        navigate("/");
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;

    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const validate = () => {
    const newErrors = {};

    if (!formData.email.trim()) {
      newErrors.email = "Email is required";
    } else if (
      !/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i.test(formData.email)
    ) {
      newErrors.email = "Invalid email";
    }

    if (!/^\d{10}$/.test(formData.mobile)) {
      newErrors.mobile = "Enter valid mobile number";
    }

    if (formData.password) {
      if (formData.password.length < 8) {
        newErrors.password = "Minimum 8 characters";
      }

      if (formData.password !== formData.confirmPassword) {
        newErrors.confirmPassword = "Passwords do not match";
      }
    }

    setErrors(newErrors);

    return Object.keys(newErrors).length === 0;
  };

  const handleUpdate = async () => {
    if (!validate()) return;

    try {
      const payload = {
        email: formData.email,
        mobile: formData.mobile,
      };

      if (formData.password) {
        payload.password = formData.password;
      }

      await profileService.updateProfile(payload);

      setSnackbar({
        open: true,
        severity: "success",
        message: "Profile updated successfully.",
      });

      loadProfile();

      setFormData((prev) => ({
        ...prev,
        password: "",
        confirmPassword: "",
      }));
    } catch (error) {
      setSnackbar({
        open: true,
        severity: "error",
        message: error?.response?.data?.message || "Failed to update profile.",
      });
    }
  };

  if (loading) {
    return (
      <Box
        display="flex"
        justifyContent="center"
        alignItems="center"
        minHeight="70vh"
      >
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Container maxWidth="md" sx={{ py: 4 }}>
      <Paper elevation={4} sx={{ p: 4, borderRadius: 3 }}>
        {/* Header */}
        <Stack
          direction="row"
          justifyContent="space-between"
          alignItems="center"
          mb={3}
        >
          <Button
            startIcon={<ArrowBack />}
            variant="outlined"
            onClick={handleBack}
          >
            Back to Dashboard
          </Button>

          <Avatar
            sx={{
              width: 70,
              height: 70,
              bgcolor: "primary.main",
              fontSize: 30,
            }}
          >
            {profile.username?.charAt(0).toUpperCase()}
          </Avatar>

          <Box width={170} />
        </Stack>

        <Typography
          variant="h4"
          align="center"
          fontWeight="bold"
          color="primary"
        >
          My Profile
        </Typography>

        <Typography align="center" color="text.secondary" mb={4}>
          Manage your account information
        </Typography>

        <Divider sx={{ mb: 4 }} />

        <Typography variant="h6" color="primary" gutterBottom>
          Personal Information
        </Typography>

        <Grid container spacing={3}>
          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              label="Username"
              value={profile.username}
              disabled
            />
          </Grid>

          <Grid item xs={12} md={6}>
            <TextField fullWidth label="Role" value={profile.role} disabled />
          </Grid>

          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              label="First Name"
              value={profile.firstName}
              disabled
            />
          </Grid>

          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              label="Last Name"
              value={profile.lastName}
              disabled
            />
          </Grid>
        </Grid>

        <Divider sx={{ my: 4 }} />

        <Typography variant="h6" color="primary" gutterBottom>
          Contact Information
        </Typography>

        <Grid container spacing={3}>
          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              label="Email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              error={Boolean(errors.email)}
              helperText={errors.email}
            />
          </Grid>

          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              label="Mobile Number"
              name="mobile"
              value={formData.mobile}
              onChange={handleChange}
              error={Boolean(errors.mobile)}
              helperText={errors.mobile}
            />
          </Grid>
        </Grid>

        <Divider sx={{ my: 4 }} />

        <Typography variant="h6" color="primary" gutterBottom>
          Change Password
        </Typography>

        <Grid container spacing={3}>
          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              type={showPassword ? "text" : "password"}
              label="New Password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              error={Boolean(errors.password)}
              helperText={errors.password}
              InputProps={{
                endAdornment: (
                  <InputAdornment position="end">
                    <IconButton
                      onClick={() => setShowPassword(!showPassword)}
                      edge="end"
                    >
                      {showPassword ? <VisibilityOff /> : <Visibility />}
                    </IconButton>
                  </InputAdornment>
                ),
              }}
            />
          </Grid>

          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              type={showConfirmPassword ? "text" : "password"}
              label="Confirm Password"
              name="confirmPassword"
              value={formData.confirmPassword}
              onChange={handleChange}
              error={Boolean(errors.confirmPassword)}
              helperText={errors.confirmPassword}
              InputProps={{
                endAdornment: (
                  <InputAdornment position="end">
                    <IconButton
                      onClick={() =>
                        setShowConfirmPassword(!showConfirmPassword)
                      }
                      edge="end"
                    >
                      {showConfirmPassword ? <VisibilityOff /> : <Visibility />}
                    </IconButton>
                  </InputAdornment>
                ),
              }}
            />
          </Grid>
        </Grid>

        <Box display="flex" justifyContent="center" mt={5}>
          <Button
            variant="contained"
            size="large"
            onClick={handleUpdate}
            sx={{
              px: 6,
              py: 1.5,
            }}
          >
            Update Profile
          </Button>
        </Box>
      </Paper>

      <Snackbar
        open={snackbar.open}
        autoHideDuration={4000}
        onClose={() =>
          setSnackbar((prev) => ({
            ...prev,
            open: false,
          }))
        }
      >
        <Alert
          severity={snackbar.severity}
          variant="filled"
          onClose={() =>
            setSnackbar((prev) => ({
              ...prev,
              open: false,
            }))
          }
        >
          {snackbar.message}
        </Alert>
      </Snackbar>
    </Container>
  );
};

export default Profile;
