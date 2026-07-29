import {
  AppBar,
  Toolbar,
  Typography,
  IconButton,
  Avatar,
  Box,
  Menu,
  MenuItem,
  Divider,
  ListItemIcon,
  useMediaQuery,
  useTheme,
} from "@mui/material";

import MenuIcon from "@mui/icons-material/Menu";
import LogoutIcon from "@mui/icons-material/Logout";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";

import { useState } from "react";
import { useNavigate } from "react-router-dom";

import useAuth from "../../hooks/useAuth";

const Navbar = ({
  drawerWidth,
  collapsedWidth,
  sidebarOpen,
  onMenuClick,
  onMobileMenuClick,
}) => {
  const navigate = useNavigate();

  const { user, logout } = useAuth();

  const [anchorEl, setAnchorEl] = useState(null);

  const theme = useTheme();

  const isMobile = useMediaQuery(theme.breakpoints.down("md"));

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
        zIndex: (theme) => theme.zIndex.drawer + 1,

        backgroundColor: "#1976d2",

        transition: "all .3s ease",

        width: {
          xs: "100%",
          md: `calc(100% - ${sidebarOpen ? drawerWidth : collapsedWidth}px)`,
        },

        ml: {
          xs: 0,
          md: `${sidebarOpen ? drawerWidth : collapsedWidth}px`,
        },
      }}
    >
      <Toolbar>
        {/* <IconButton
          color="inherit"
          edge="start"
          onClick={isMobile ? onMobileMenuClick : onMenuClick}
          sx={{ mr: 2 }}
        >
          <MenuIcon />
        </IconButton> */}
        <IconButton
          color="inherit"
          edge="start"
          onClick={onMobileMenuClick}
          sx={{
            mr: 2,
            display: {
              xs: "flex",
              md: "none",
            },
          }}
        >
          <MenuIcon />
        </IconButton>

        <Typography
          variant="h6"
          sx={{
            flexGrow: 1,
            fontWeight: 600,
            whiteSpace: "nowrap",
            overflow: "hidden",
            textOverflow: "ellipsis",
          }}
        >
          Constraint Based Timetable Management System
        </Typography>

        <Box
          sx={{
            display: "flex",
            alignItems: "center",
            cursor: "pointer",
          }}
          onClick={handleMenuOpen}
        >
          <Avatar
            sx={{
              bgcolor: "#fff",
              color: "#1976d2",
              mr: 1,
            }}
          >
            {user?.username?.charAt(0)?.toUpperCase() || "A"}
          </Avatar>

          <Typography
            sx={{
              display: {
                xs: "none",
                sm: "block",
              },
            }}
          >
            {user?.username || "Admin"}
          </Typography>
        </Box>

        <Menu anchorEl={anchorEl} open={open} onClose={handleMenuClose}>
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
