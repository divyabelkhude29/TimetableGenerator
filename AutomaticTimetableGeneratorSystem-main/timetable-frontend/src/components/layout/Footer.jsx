import { Box, Typography } from "@mui/material";

const Footer = () => {

  return (

    <Box
      component="footer"
      sx={{
        width: "100%",
        py: 2,
        px: 3,
        borderTop: "1px solid #e0e0e0",
        bgcolor: "#ffffff",
        textAlign: "center",
        mt: "auto"
      }}
    >

      <Typography
        variant="body2"
        color="text.secondary"
      >
        © 2026 College Timetable Management System | All Rights Reserved
      </Typography>

    </Box>

  );

};

export default Footer;