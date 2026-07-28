import { Box, Toolbar } from "@mui/material";
import { useState } from "react";

import Navbar from "./Navbar";
import Sidebar from "./Sidebar";
import Footer from "./Footer";

const drawerWidth = 250;

const DashboardLayout = ({ children }) => {

    const [mobileOpen, setMobileOpen] = useState(false);

    const handleDrawerToggle = () => {

        setMobileOpen(!mobileOpen);

    };

    return (

        <Box sx={{ display: "flex" }}>

            <Navbar onMenuClick={handleDrawerToggle} />

            <Sidebar
                drawerWidth={drawerWidth}
                mobileOpen={mobileOpen}
                handleDrawerToggle={handleDrawerToggle}
            />

            <Box
                component="main"
                sx={{
                    flexGrow: 1,
                    ml: {
                        xs: 0,
                        md: `${drawerWidth}px`
                    },
                    bgcolor: "#f5f5f5",
                    minHeight: "100vh",
                    p: 3
                }}
            >

                <Toolbar />

                {children}

                <Footer />

            </Box>

        </Box>

    );

};

export default DashboardLayout;