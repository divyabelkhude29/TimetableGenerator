import {

    Drawer,

    Toolbar,

    List,

    ListItemButton,

    ListItemIcon,

    ListItemText,

    Typography,

    Divider,

    Box

} from "@mui/material";

import { NavLink } from "react-router-dom";

import menuItems from "../../config/menuItems";

const Sidebar = ({

    drawerWidth,

    mobileOpen,

    handleDrawerToggle

}) => {

    const drawer = (

        <>

            <Toolbar>

                <Typography
                    variant="h6"
                    fontWeight="bold"
                >

                    Admin Panel

                </Typography>

            </Toolbar>

            <Divider />

            <List>

                {

                    menuItems.map((item, index) => {

                        if (item.header) {

                            return (

                                <Typography

                                    key={index}

                                    sx={{

                                        px: 2,

                                        py: 1,

                                        fontWeight: "bold",

                                        color: "gray",

                                        fontSize: 13

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

                                    "&.active": {

                                        backgroundColor: "#1976d2",

                                        color: "#fff",

                                        "& .MuiListItemIcon-root": {

                                            color: "#fff"

                                        }

                                    }

                                }}

                            >

                                <ListItemIcon>

                                    {item.icon}

                                </ListItemIcon>

                                <ListItemText

                                    primary={item.text}

                                />

                            </ListItemButton>

                        );

                    })

                }

            </List>

        </>

    );

    return (

        <Box
            component="nav"
            sx={{
                width: {
                    md: drawerWidth
                },
                flexShrink: {
                    md: 0
                }
            }}
        >

            <Drawer

                variant="temporary"

                open={mobileOpen}

                onClose={handleDrawerToggle}

                ModalProps={{

                    keepMounted: true

                }}

                sx={{

                    display: {

                        xs: "block",

                        md: "none"

                    },

                    "& .MuiDrawer-paper": {

                        width: drawerWidth

                    }

                }}

            >

                {drawer}

            </Drawer>

            <Drawer

                variant="persistent"

                open

                sx={{

                    display: {

                        xs: "none",

                        md: "block"

                    },

                    "& .MuiDrawer-paper": {

                        width: drawerWidth,

                        boxSizing: "border-box"

                    }

                }}

            >

                {drawer}

            </Drawer>

        </Box>

    );

};

export default Sidebar;