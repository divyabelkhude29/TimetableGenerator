import { Routes, Route } from "react-router-dom";
import ProtectedRoute from "./ProtectedRoute";

/* Authentication */
import Login from "../pages/auth/Login";

/* Dashboards */
import AdminDashboard from "../pages/admin/AdminDashboard";
import FacultyDashboard from "../pages/faculty/FacultyDashboard";
import StudentDashboard from "../pages/student/StudentDashboard";

/* Master Modules */
import DepartmentList from "../pages/departments/DepartmentList";
import CourseList from "../pages/courses/CourseList";
import SemesterList from "../pages/semesters/SemesterList";
import SectionList from "../pages/sections/SectionList";
import FacultyList from "../pages/faculty/FacultyList";
import StudentList from "../pages/students/StudentList";
import SubjectList from "../pages/subjects/SubjectList";
import ClassroomList from "../pages/classrooms/ClassroomList";
import TimeSlotList from "../pages/timeslots/TimeSlotList";
import FacultyAvailabilityList from "../pages/facultyAvailability/FacultyAvailabilityList";/* Faculty Allocation */
import AllocationList from "../pages/allocations/AllocationList";
import SubjectWorkloadList from "../pages/subjectWorkload/SubjectWorkloadList";
import HolidayList from "../pages/holidays/HolidayList";
import ConstraintList from "../pages/constraints/ConstraintList";
import TimetableGenerationPage from "../pages/timetableGeneration/TimetableGenerationPage";
import TimetableView from "../pages/timetable/TimetableView";

const AppRoutes = () => {

    return (

        <Routes>

            {/* Login */}

            <Route
                path="/"
                element={<Login />}
            />

            {/* ================= ADMIN ================= */}

            <Route
                path="/admin"
                element={
                    <ProtectedRoute role="ROLE_ADMIN">
                        <AdminDashboard />
                    </ProtectedRoute>
                }
            />

            {/* ================= FACULTY ================= */}

            <Route
                path="/faculty"
                element={
                    <ProtectedRoute role="ROLE_FACULTY">
                        <FacultyDashboard />
                    </ProtectedRoute>
                }
            />

            {/* ================= STUDENT ================= */}

            <Route
                path="/student"
                element={
                    <ProtectedRoute role="ROLE_STUDENT">
                        <StudentDashboard />
                    </ProtectedRoute>
                }
            />

            {/* ================= MASTER MODULES ================= */}

            <Route
                path="/departments"
                element={
                    <ProtectedRoute role="ROLE_ADMIN">
                        <DepartmentList />
                    </ProtectedRoute>
                }
            />

            <Route
                path="/courses"
                element={
                    <ProtectedRoute role="ROLE_ADMIN">
                        <CourseList />
                    </ProtectedRoute>
                }
            />

            <Route
                path="/semesters"
                element={
                    <ProtectedRoute role="ROLE_ADMIN">
                        <SemesterList />
                    </ProtectedRoute>
                }
            />

            <Route
                path="/sections"
                element={
                    <ProtectedRoute role="ROLE_ADMIN">
                        <SectionList />
                    </ProtectedRoute>
                }
            />

            <Route
                path="/faculties"
                element={
                    <ProtectedRoute role="ROLE_ADMIN">
                        <FacultyList />
                    </ProtectedRoute>
                }
            />

            <Route
                path="/students"
                element={
                    <ProtectedRoute role="ROLE_ADMIN">
                        <StudentList />
                    </ProtectedRoute>
                }
            />

            <Route
                path="/subjects"
                element={
                    <ProtectedRoute role="ROLE_ADMIN">
                        <SubjectList />
                    </ProtectedRoute>
                }
            />

            <Route
                path="/classrooms"
                element={
                    <ProtectedRoute role="ROLE_ADMIN">
                        <ClassroomList />
                    </ProtectedRoute>
                }
            />

            <Route
                path="/timeslots"
                element={
                    <ProtectedRoute role="ROLE_ADMIN">
                        <TimeSlotList />
                    </ProtectedRoute>
                }
            />

            {/* ================= FACULTY ALLOCATION ================= */}

            <Route
                path="/allocations"
                element={
                    <ProtectedRoute role="ROLE_ADMIN">
                        <AllocationList />
                    </ProtectedRoute>
                }
            />

            {/* ================= TIMETABLE GENERATION ================= */}
{/* 
            <Route
                path="/timetable-generation"
                element={
                    <ProtectedRoute role="ROLE_ADMIN">
                        <GenerateTimetable />
                    </ProtectedRoute>
                }
            /> */}

            <Route
                path="/faculty-availability"
                element={
                    <ProtectedRoute role="ROLE_ADMIN">
                        <FacultyAvailabilityList />
                    </ProtectedRoute>
                }
            />

            <Route
                path="/subject-workloads"
                element={
                    <ProtectedRoute role="ROLE_ADMIN">
                        <SubjectWorkloadList />
                    </ProtectedRoute>
                }
            />

            <Route
                path="/holidays"
                element={
                    <ProtectedRoute role="ROLE_ADMIN">
                        <HolidayList />
                    </ProtectedRoute>
                }
            />

            <Route
                path="/constraints"
                element={
                    <ProtectedRoute role="ROLE_ADMIN">
                        <ConstraintList />
                    </ProtectedRoute>
                }
            />

            <Route
                path="/timetable-generation"
                element={
                    <ProtectedRoute role="ROLE_ADMIN">
                        <TimetableGenerationPage />
                    </ProtectedRoute>
                }
            />

            <Route
                path="/timetable-view"
                element={
                    <ProtectedRoute role="ROLE_ADMIN">
                        <TimetableView />
                    </ProtectedRoute>
                }
            />

        </Routes>

    );

};

export default AppRoutes;