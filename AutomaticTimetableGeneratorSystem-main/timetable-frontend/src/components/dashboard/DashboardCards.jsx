import { Grid, Paper, Typography, Box } from "@mui/material";

import BusinessIcon from "@mui/icons-material/Business";
import SchoolIcon from "@mui/icons-material/School";
import GroupsIcon from "@mui/icons-material/Groups";
import PersonIcon from "@mui/icons-material/Person";
import MenuBookIcon from "@mui/icons-material/MenuBook";
import MeetingRoomIcon from "@mui/icons-material/MeetingRoom";

const DashboardCards = ({ counts }) => {

    const cards = [

        {
            title: "Departments",
            value: counts?.departments || 0,
            icon: <BusinessIcon fontSize="large" />,
            color: "#1976d2"
        },

        {
            title: "Courses",
            value: counts?.courses || 0,
            icon: <SchoolIcon fontSize="large" />,
            color: "#2e7d32"
        },

        {
            title: "Faculty",
            value: counts?.faculties || 0,
            icon: <GroupsIcon fontSize="large" />,
            color: "#ed6c02"
        },

        {
            title: "Students",
            value: counts?.students || 0,
            icon: <PersonIcon fontSize="large" />,
            color: "#9c27b0"
        },

        {
            title: "Subjects",
            value: counts?.subjects || 0,
            icon: <MenuBookIcon fontSize="large" />,
            color: "#0288d1"
        },

        {
            title: "Classrooms",
            value: counts?.classrooms || 0,
            icon: <MeetingRoomIcon fontSize="large" />,
            color: "#d32f2f"
        }

    ];

    return (

        <Grid container spacing={3}>

            {

                cards.map((card, index) => (

                    <Grid
                        item
                        xs={12}
                        sm={6}
                        md={4}
                        key={index}
                    >

                        <Paper
                            elevation={4}
                            sx={{
                                p: 3,
                                borderLeft: `6px solid ${card.color}`,
                                borderRadius: 3,
                                transition: "0.3s",
                                "&:hover": {
                                    transform: "translateY(-5px)",
                                    boxShadow: 8
                                }
                            }}
                        >

                            <Box
                                display="flex"
                                justifyContent="space-between"
                                alignItems="center"
                            >

                                <Box>

                                    <Typography
                                        variant="body2"
                                        color="text.secondary"
                                    >

                                        {card.title}

                                    </Typography>

                                    <Typography
                                        variant="h4"
                                        fontWeight="bold"
                                        mt={1}
                                    >

                                        {card.value}

                                    </Typography>

                                </Box>

                                <Box
                                    sx={{
                                        color: card.color
                                    }}
                                >

                                    {card.icon}

                                </Box>

                            </Box>

                        </Paper>

                    </Grid>

                ))

            }

        </Grid>

    );

};

export default DashboardCards;