import DashboardIcon from "@mui/icons-material/Dashboard";
import BusinessIcon from "@mui/icons-material/Business";
import SchoolIcon from "@mui/icons-material/School";
import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";
import ClassIcon from "@mui/icons-material/Class";
import MenuBookIcon from "@mui/icons-material/MenuBook";
import GroupsIcon from "@mui/icons-material/Groups";
import PersonIcon from "@mui/icons-material/Person";
import MeetingRoomIcon from "@mui/icons-material/MeetingRoom";
import AccessTimeIcon from "@mui/icons-material/AccessTime";
import AssignmentIndIcon from "@mui/icons-material/AssignmentInd";
import TableChartIcon from "@mui/icons-material/TableChart";
import LogoutIcon from "@mui/icons-material/Logout";
import ScheduleIcon from "@mui/icons-material/Schedule";
import WorkHistoryIcon from "@mui/icons-material/WorkHistory";
import EventBusyIcon from "@mui/icons-material/EventBusy";
import RuleIcon from "@mui/icons-material/Rule";
import AutoFixHighIcon from "@mui/icons-material/AutoFixHigh";

const menuItems = [

    {
        text: "Dashboard",
        icon: <DashboardIcon />,
        path: "/admin"
    },

    {
        header: "MASTER"
    },

    {
        text: "Departments",
        icon: <BusinessIcon />,
        path: "/departments"
    },

    {
        text: "Courses",
        icon: <SchoolIcon />,
        path: "/courses"
    },

    {
        text: "Semesters",
        icon: <CalendarMonthIcon />,
        path: "/semesters"
    },

    {
        text: "Sections",
        icon: <ClassIcon />,
        path: "/sections"
    },

    {
        text: "Subjects",
        icon: <MenuBookIcon />,
        path: "/subjects"
    },

    {
        text: "Faculty",
        icon: <GroupsIcon />,
        path: "/faculties"
    },

    {
        text: "Students",
        icon: <PersonIcon />,
        path: "/students"
    },

    {
        text: "Classrooms",
        icon: <MeetingRoomIcon />,
        path: "/classrooms"
    },

    {
        text: "Time Slots",
        icon: <AccessTimeIcon />,
        path: "/timeslots"
    },

    {
        header: "TIMETABLE"
    },

    {
        text: "Faculty Allocation",
        icon: <AssignmentIndIcon />,
        path: "/allocations"
    },

    {
        text: "Faculty Availability",
        icon: <ScheduleIcon />,
        path: "/faculty-availability"
    },

    {
    text: "Subject Workload",
    icon: <WorkHistoryIcon />,
    path: "/subject-workloads"
    },

    {
    text: "Holidays",
    icon: <EventBusyIcon />,
    path: "/holidays"
    },

    {
    text: "Timetable Constraints",
    icon: <RuleIcon />,
    path: "/constraints"
    },

    {
    text: "Generate Timetable",
    icon: <AutoFixHighIcon />,
    path: "/timetable-generation"
    },

    {
    text: "Timetable View",
    icon: <TableChartIcon />,
    path: "/timetable-view"
    },
    
    {
        header: "ACCOUNT"
    },

    {
        text: "Logout",
        icon: <LogoutIcon />,
        path: "/logout"
    }

];

export default menuItems;