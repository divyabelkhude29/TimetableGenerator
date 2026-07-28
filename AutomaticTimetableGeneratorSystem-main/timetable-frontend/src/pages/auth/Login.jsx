import {
  Box,
  Paper,
  Typography,
  TextField,
  Button,
  Alert,
  CircularProgress,
} from "@mui/material";

import { useState } from "react";

import { useNavigate } from "react-router-dom";

import useAuth from "../../hooks/useAuth";

import authService from "../../services/authService";


const Login = () => {

  const navigate = useNavigate();

  const { login } = useAuth();


  const [username, setUsername] =
    useState("");

  const [password, setPassword] =
    useState("");

  const [loading, setLoading] =
    useState(false);

  const [error, setError] =
    useState("");


  const handleSubmit = async (e) => {

    e.preventDefault();

    setError("");


    /*
     * Validate fields
     */
    if (
      !username.trim() ||
      !password.trim()
    ) {

      setError(
        "Username and Password are required."
      );

      return;
    }


    setLoading(true);


    try {

      /*
       * Call backend login
       */
      const response =
        await authService.login(
          username.trim(),
          password
        );


      console.log(
        "Login Response:",
        response
      );


      /*
       * Check JWT
       */
      if (!response?.token) {

        setError(
          "Login successful but JWT token was not received."
        );

        return;
      }


      /*
       * Save JWT and user information
       */
      login(

        response.token,

        {
          username:
            response.username,

          role:
            response.role,
        }

      );


      /*
       * Navigate according to role
       */
      switch (
        response.role
      ) {

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

          console.error(
            "Unknown Role:",
            response.role
          );

          setError(
            "Unknown user role received from server."
          );

      }


    } catch (err) {

      console.error(
        "Login Error:",
        err
      );


      if (err.response) {

        console.error(
          "Response:",
          err.response.data
        );


        console.error(
          "Status:",
          err.response.status
        );


        setError(

          err.response.data?.message ||

          "Invalid username or password."

        );

      } else {

        setError(
          "Unable to connect to the server."
        );

      }


    } finally {

      setLoading(false);

    }

  };


  return (

    <Box
      sx={{
        height: "100vh",

        display: "flex",

        justifyContent: "center",

        alignItems: "center",

        bgcolor: "#f5f5f5",
      }}
    >

      <Paper
        elevation={6}
        sx={{
          width: 420,

          p: 5,
        }}
      >

        <Typography
          variant="h4"
          align="center"
          fontWeight="bold"
          mb={4}
        >
          College Timetable
        </Typography>


        {error && (

          <Alert
            severity="error"
            sx={{ mb: 2 }}
          >

            {error}

          </Alert>

        )}


        <Box
          component="form"
          onSubmit={handleSubmit}
        >

          <TextField
            fullWidth
            label="Username"
            margin="normal"
            value={username}
            onChange={(e) =>
              setUsername(e.target.value)
            }
            disabled={loading}
          />


          <TextField
            fullWidth
            label="Password"
            type="password"
            margin="normal"
            value={password}
            onChange={(e) =>
              setPassword(e.target.value)
            }
            disabled={loading}
          />


          <Button
            fullWidth
            variant="contained"
            type="submit"
            disabled={loading}
            sx={{
              mt: 3,
              py: 1.5,
            }}
          >

            {loading ? (

              <CircularProgress
                size={24}
                color="inherit"
              />

            ) : (

              "Login"

            )}

          </Button>

        </Box>

      </Paper>

    </Box>

  );

};


export default Login;