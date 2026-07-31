import {
  Drawer,
  Toolbar,
  List,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Typography,
  Divider,
  Box,
} from "@mui/material";

import useAuth from "../../hooks/useAuth";
import MenuIcon from "@mui/icons-material/Menu";
import IconButton from "@mui/material/IconButton";

import { NavLink } from "react-router-dom";

import menuItems from "../../config/menuItems";
import studentMenuItems from "../../config/studentMenuItems";
import facultyMenuItems from "../../config/facultyMenuItems";

// const Sidebar = ({
//   drawerWidth,
//   collapsedWidth,
//   sidebarOpen,
//   mobileOpen,
//   onMobileClose,
// }) => {

const Sidebar = ({
  drawerWidth,
  collapsedWidth,
  sidebarOpen,
  mobileOpen,
  onMobileClose,
  onDesktopToggle,
}) => {
  const { user } = useAuth();
  let currentMenu = menuItems;

  if (user?.role === "ROLE_STUDENT") {
    currentMenu = studentMenuItems;
  } else if (user?.role === "ROLE_FACULTY") {
    currentMenu = facultyMenuItems;
  }

  const panelTitle =
    user?.role === "ROLE_STUDENT"
      ? "Student Panel"
      : user?.role === "ROLE_FACULTY"
        ? "Faculty Panel"
        : "Admin Panel";

  const drawerContent = (
    <>
      <Toolbar
        sx={{
          display: "flex",
          alignItems: "center",
          justifyContent: sidebarOpen ? "space-between" : "center",
          px: 2,
          minHeight: "64px",
        }}
      >
        {sidebarOpen ? (
          <>
            <IconButton
              onClick={onDesktopToggle}
              size="small"
              sx={{
                color: "#1976d2",
                display: {
                  xs: "none",
                  md: "flex",
                },
              }}
            >
              <MenuIcon />
            </IconButton>
            {/* <Typography
              variant="h6"
              fontWeight="bold"
              sx={{
                whiteSpace: "nowrap",
              }}
            >
              Admin Panel
            </Typography> */}
            <Typography
              variant="h6"
              fontWeight="bold"
              sx={{ whiteSpace: "nowrap" }}
            >
              {panelTitle}
            </Typography>
          </>
        ) : (
          <IconButton
            onClick={onDesktopToggle}
            sx={{
              color: "#1976d2",
            }}
          >
            <MenuIcon />
          </IconButton>
        )}
      </Toolbar>

      <Divider />

      <List sx={{ mt: 1 }}>
        {currentMenu.map((item, index) => {
          if (item.header) {
            return (
              <Typography
                key={index}
                sx={{
                  px: sidebarOpen ? 2 : 0,
                  py: 1,
                  textAlign: sidebarOpen ? "left" : "center",
                  color: "gray",
                  fontWeight: "bold",
                  fontSize: 12,
                  display: sidebarOpen ? "block" : "none",
                }}
              >
                {item.header}
              </Typography>
            );
          }

          return (
            <ListItemButton
              key={index}
              component={NavLink}
              to={item.path}
              sx={{
                minHeight: 50,

                px: sidebarOpen ? 2 : 1.5,

                justifyContent: sidebarOpen ? "initial" : "center",

                borderRadius: 2,

                mx: 1,

                mb: 0.5,

                "&.active": {
                  bgcolor: "#1976d2",

                  color: "#fff",

                  "& .MuiListItemIcon-root": {
                    color: "#fff",
                  },
                },
              }}
            >
              <ListItemIcon
                sx={{
                  minWidth: 0,

                  mr: sidebarOpen ? 2 : "auto",

                  justifyContent: "center",
                }}
              >
                {item.icon}
              </ListItemIcon>

              <ListItemText
                primary={item.text}
                sx={{
                  opacity: sidebarOpen ? 1 : 0,

                  transition: ".2s",

                  whiteSpace: "nowrap",
                }}
              />
            </ListItemButton>
          );
        })}
      </List>
    </>
  );

  return (
    <>
      {/* Mobile Drawer */}

      <Drawer
        variant="temporary"
        open={mobileOpen}
        onClose={onMobileClose}
        ModalProps={{
          keepMounted: true,
        }}
        sx={{
          display: {
            xs: "block",

            md: "none",
          },

          "& .MuiDrawer-paper": {
            width: drawerWidth,

            boxSizing: "border-box",
          },
        }}
      >
        {drawerContent}
      </Drawer>

      {/* Desktop Drawer */}

      <Drawer
        variant="permanent"
        sx={{
          display: {
            xs: "none",

            md: "block",
          },

          width: sidebarOpen ? drawerWidth : collapsedWidth,

          flexShrink: 0,

          "& .MuiDrawer-paper": {
            width: sidebarOpen ? drawerWidth : collapsedWidth,

            transition: "width .3s ease",

            overflowX: "hidden",

            whiteSpace: "nowrap",

            boxSizing: "border-box",

            borderRight: "1px solid #ddd",

            bgcolor: "#fff",
          },
        }}
      >
        {drawerContent}
      </Drawer>
    </>
  );
};

export default Sidebar;
