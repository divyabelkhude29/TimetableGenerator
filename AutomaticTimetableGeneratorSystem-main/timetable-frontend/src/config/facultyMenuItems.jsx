import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";
import WorkHistoryIcon from "@mui/icons-material/WorkHistory";
import PersonIcon from "@mui/icons-material/Person";

const facultyMenuItems = [
  {
    text: "Profile",
    path: "/profile",
    icon: <PersonIcon />,
  },

  {
    text: "Timetable View",
    path: "/timetable-view",
    icon: <CalendarMonthIcon />,
  },

  {
    text: "Subject Workload",
    path: "/subject-workloads",
    icon: <WorkHistoryIcon />,
  },
];

export default facultyMenuItems;
