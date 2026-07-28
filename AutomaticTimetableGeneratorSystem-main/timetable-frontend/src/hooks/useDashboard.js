import { useEffect, useState } from "react";

import dashboardService from "../services/dashboardService";

const useDashboard = () => {

    const [counts, setCounts] = useState({

        departments: 0,

        courses: 0,

        faculties: 0,

        students: 0,

        subjects: 0,

        classrooms: 0

    });

    const [loading, setLoading] = useState(true);

    useEffect(() => {

        loadDashboard();

    }, []);

    const loadDashboard = async () => {

        try {

            const response = await dashboardService.getDashboardCounts();

            setCounts(response);

        }

        catch (error) {

            console.error(error);

        }

        finally {

            setLoading(false);

        }

    };

    return {

        counts,

        loading,

        reload: loadDashboard

    };

};

export default useDashboard;