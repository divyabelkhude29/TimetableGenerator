import { Typography } from "@mui/material";

import DashboardLayout from "../../components/layout/DashboardLayout";

const Dashboard = () => {
  return (
    <DashboardLayout>
      <Typography variant="h4" fontWeight="bold">
        Dashboard
      </Typography>

      <Typography sx={{ mt: 2 }}>
        Welcome to the College Timetable Management System.
      </Typography>
    </DashboardLayout>
  );
};

export default Dashboard;