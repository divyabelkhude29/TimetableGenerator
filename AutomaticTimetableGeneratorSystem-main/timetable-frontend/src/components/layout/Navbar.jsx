import {
  AppBar,
  Toolbar,
  Typography,
  IconButton,
  Badge,
  Avatar,
  Box,
  Menu,
  MenuItem,
  Divider,
  ListItemIcon
} from "@mui/material";

import MenuIcon from "@mui/icons-material/Menu";
import NotificationsIcon from "@mui/icons-material/Notifications";
import LogoutIcon from "@mui/icons-material/Logout";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";

import { useState } from "react";
import { useNavigate } from "react-router-dom";

import useAuth from "../../hooks/useAuth";

const Navbar = ({ onMenuClick }) => {

  const navigate = useNavigate();

  const { user, logout } = useAuth();

  const [anchorEl, setAnchorEl] = useState(null);

  const open = Boolean(anchorEl);

  const handleMenuOpen = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  const handleLogout = () => {

    handleMenuClose();

    logout();

    navigate("/");

  };

  return (

    <AppBar
      position="fixed"
      elevation={1}
      sx={{
        backgroundColor: "#1976d2",
        zIndex: 1300
      }}
    >

      <Toolbar>

        <IconButton
          color="inherit"
          edge="start"
          sx={{ mr: 2 }}
          onClick={onMenuClick}
        >
          <MenuIcon />
        </IconButton>

        <Typography
          variant="h6"
          sx={{
            flexGrow: 1,
            fontWeight: 600
          }}
        >
          College Timetable Management System
        </Typography>

        <IconButton color="inherit">

          <Badge
            badgeContent={4}
            color="error"
          >

            <NotificationsIcon />

          </Badge>

        </IconButton>

        <Box
          sx={{
            display: "flex",
            alignItems: "center",
            ml: 2,
            cursor: "pointer"
          }}
          onClick={handleMenuOpen}
        >

          <Avatar
            sx={{
              bgcolor: "#fff",
              color: "#1976d2",
              mr: 1
            }}
          >

            {user?.username?.charAt(0)?.toUpperCase() || "A"}

          </Avatar>

          <Typography>

            {user?.username || "Admin"}

          </Typography>

        </Box>

        <Menu
          anchorEl={anchorEl}
          open={open}
          onClose={handleMenuClose}
        >

          <MenuItem>

            <ListItemIcon>

              <AccountCircleIcon fontSize="small" />

            </ListItemIcon>

            {user?.username}

          </MenuItem>

          <Divider />

          <MenuItem onClick={handleLogout}>

            <ListItemIcon>

              <LogoutIcon fontSize="small" />

            </ListItemIcon>

            Logout

          </MenuItem>

        </Menu>

      </Toolbar>

    </AppBar>

  );

};

export default Navbar;