import DashboardLayout from "../../components/layout/DashboardLayout";
import DashboardCards from "../../components/dashboard/DashboardCards";
import StatisticsChart from "../../components/dashboard/StatisticsChart";
import QuickActions from "../../components/dashboard/QuickActions";
import RecentActivities from "../../components/dashboard/RecentActivities";
import TodayTimetable from "../../components/dashboard/TodayTimetable";

import useDashboard from "../../hooks/useDashboard";

const AdminDashboard = () => {

    const { counts, loading } = useDashboard();

    if (loading) {
        return <h2>Loading Dashboard...</h2>;
    }

    return (

        <DashboardLayout>

            <DashboardCards counts={counts} />

            <StatisticsChart />

            <QuickActions />

            <RecentActivities />

            <TodayTimetable />

        </DashboardLayout>

    );

};

export default AdminDashboard;