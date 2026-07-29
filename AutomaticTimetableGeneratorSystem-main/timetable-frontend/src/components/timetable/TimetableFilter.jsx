import { useEffect, useState } from "react";

import {
    Grid,
    Paper,
    TextField,
    MenuItem,
    Button,
    Typography,
    CircularProgress
} from "@mui/material";

import courseService from "../../services/courseService";
import semesterService from "../../services/semesterService";
import sectionService from "../../services/sectionService";

const TimetableFilter = ({
    filters,
    setFilters,
    onLoad,
    onGenerate,
    onRefresh
}) => {

    const [courses, setCourses] = useState([]);
    const [semesters, setSemesters] = useState([]);
    const [sections, setSections] = useState([]);

    const [loadingCourses, setLoadingCourses] = useState(false);
    const [loadingSemesters, setLoadingSemesters] = useState(false);
    const [loadingSections, setLoadingSections] = useState(false);

    useEffect(() => {
        loadCourses();
    }, []);

    useEffect(() => {

        if (filters.courseId) {

            loadSemesters(filters.courseId);

        } else {

            setSemesters([]);
            setSections([]);

        }

    }, [filters.courseId]);

    useEffect(() => {

        if (filters.semesterId) {

            loadSections(filters.semesterId);

        } else {

            setSections([]);

        }

    }, [filters.semesterId]);

    const extractList = (response) => {

        if (Array.isArray(response)) {
            return response;
        }

        if (response?.data && Array.isArray(response.data)) {
            return response.data;
        }

        if (response?.content && Array.isArray(response.content)) {
            return response.content;
        }

        return [];
    };

    const loadCourses = async () => {

        try {

            setLoadingCourses(true);

            const response =
                await courseService.getAllCourses();

            setCourses(extractList(response));

        } catch (error) {

            console.error(error);

            setCourses([]);

        } finally {

            setLoadingCourses(false);

        }

    };

    const loadSemesters = async (courseId) => {

        try {

            setLoadingSemesters(true);

            const response =
                await semesterService.getSemestersByCourse(courseId);

            setSemesters(extractList(response));

        } catch (error) {

            console.error(error);

            setSemesters([]);

        } finally {

            setLoadingSemesters(false);

        }

    };

    const loadSections = async (semesterId) => {

        try {

            setLoadingSections(true);

            const response =
                await sectionService.getSectionsBySemester(semesterId);

            setSections(extractList(response));

        } catch (error) {

            console.error(error);

            setSections([]);

        } finally {

            setLoadingSections(false);

        }

    };

    const handleChange = (event) => {

        const { name, value } = event.target;

        if (name === "courseId") {

            setFilters(prev => ({
                ...prev,
                courseId: value,
                semesterId: "",
                sectionId: ""
            }));

            return;
        }

        if (name === "semesterId") {

            setFilters(prev => ({
                ...prev,
                semesterId: value,
                sectionId: ""
            }));

            return;
        }

        setFilters(prev => ({
            ...prev,
            [name]: value
        }));

    };

    return (

        <Paper sx={{ p: 3 }}>

            <Typography
                variant="h6"
                gutterBottom
            >
                Timetable Filters
            </Typography>

            <Grid container spacing={2}>

                <Grid item xs={12} md={3}>

                    <TextField
                        fullWidth
                        label="Academic Year"
                        name="academicYear"
                        value={filters.academicYear}
                        onChange={handleChange}
                    />

                </Grid>

                <Grid item xs={12} md={3}>

                    <TextField
                        fullWidth
                        select
                        label="Course"
                        name="courseId"
                        value={filters.courseId}
                        onChange={handleChange}
                        disabled={loadingCourses}
                    >

                        <MenuItem value="">
                            Select Course
                        </MenuItem>

                        {

                            courses.map(course => (

                                <MenuItem
                                    key={course.courseId}
                                    value={course.courseId}
                                >

                                    {course.courseName}

                                </MenuItem>

                            ))

                        }

                    </TextField>

                </Grid>

                <Grid item xs={12} md={3}>

                    <TextField
                        fullWidth
                        select
                        label="Semester"
                        name="semesterId"
                        value={filters.semesterId}
                        onChange={handleChange}
                        disabled={
                            !filters.courseId ||
                            loadingSemesters
                        }
                    >

                        <MenuItem value="">
                            Select Semester
                        </MenuItem>

                        {

                            semesters.map(semester => (

                                <MenuItem
                                    key={semester.semesterId}
                                    value={semester.semesterId}
                                >

                                    Semester {semester.semesterNumber}

                                </MenuItem>

                            ))

                        }

                    </TextField>

                </Grid>

                <Grid item xs={12} md={3}>

                    <TextField
                        fullWidth
                        select
                        label="Section"
                        name="sectionId"
                        value={filters.sectionId}
                        onChange={handleChange}
                        disabled={
                            !filters.semesterId ||
                            loadingSections
                        }
                    >

                        <MenuItem value="">
                            Select Section
                        </MenuItem>

                        {

                            sections.map(section => (

                                <MenuItem
                                    key={section.sectionId}
                                    value={section.sectionId}
                                >

                                    {section.sectionName}

                                </MenuItem>

                            ))

                        }

                    </TextField>

                </Grid>

                <Grid
                    item
                    xs={12}
                    sx={{
                        display: "flex",
                        justifyContent: "flex-end",
                        gap: 2,
                        flexWrap: "wrap"
                    }}
                >

                    <Button
                        variant="contained"
                        onClick={onLoad}
                    >
                        Load Timetable
                    </Button>

                    {

                        onGenerate && (

                            <Button
                                variant="contained"
                                color="success"
                                onClick={onGenerate}
                            >
                                Generate
                            </Button>

                        )

                    }

                    <Button
                        variant="outlined"
                        onClick={onRefresh}
                    >
                        Refresh
                    </Button>

                </Grid>

            </Grid>

        </Paper>

    );

};

export default TimetableFilter;