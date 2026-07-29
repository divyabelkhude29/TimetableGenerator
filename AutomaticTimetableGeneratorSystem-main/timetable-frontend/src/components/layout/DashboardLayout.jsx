import { Box } from "@mui/material";
import { useState } from "react";

import Navbar from "./Navbar";
import Sidebar from "./Sidebar";
import Footer from "./Footer";

const drawerWidth = 260;
const collapsedWidth = 72;

const DashboardLayout = ({ children }) => {

  const [sidebarOpen, setSidebarOpen] = useState(true);

  const [mobileOpen, setMobileOpen] = useState(false);

  const handleSidebarToggle = () => {
    setSidebarOpen((prev) => !prev);
  };

  const handleMobileDrawerToggle = () => {
    setMobileOpen((prev) => !prev);
  };

  return (
    <Box sx={{ display: "flex", minHeight: "100vh", bgcolor: "#f5f7fb" }}>

      <Navbar
        drawerWidth={drawerWidth}
        collapsedWidth={collapsedWidth}
        sidebarOpen={sidebarOpen}
        onMenuClick={handleSidebarToggle}
        onMobileMenuClick={handleMobileDrawerToggle}
      />

      <Sidebar
        drawerWidth={drawerWidth}
        collapsedWidth={collapsedWidth}
        sidebarOpen={sidebarOpen}
        mobileOpen={mobileOpen}
        onMobileClose={handleMobileDrawerToggle}
      />

      <Box
        component="main"
        sx={{
          flexGrow: 1,
          display: "flex",
          flexDirection: "column",
          minHeight: "100vh",
          transition: "all .3s ease",

          width: {
            md: `calc(100% - ${
              sidebarOpen ? drawerWidth : collapsedWidth
            }px)`
          },

          ml: {
            md: `${sidebarOpen ? drawerWidth : collapsedWidth}px`
          },

          mt: "64px"
        }}
      >

        <Box
          sx={{
            flex: 1,
            p: {
              xs: 2,
              md: 4
            }
          }}
        >
          {children}
        </Box>

        <Footer />

      </Box>

    </Box>
  );
};

export default DashboardLayout;