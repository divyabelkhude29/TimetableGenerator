import { Box, Typography } from "@mui/material";

const Footer = () => {

    return (

        <Box

            sx={{

                mt: 5,

                py: 2,

                borderTop: "1px solid #ddd",

                textAlign: "center"

            }}

        >

            <Typography
                variant="body2"
                color="text.secondary"
            >

                © 2026 College Timetable Management System

            </Typography>

        </Box>

    );

};

export default Footer;