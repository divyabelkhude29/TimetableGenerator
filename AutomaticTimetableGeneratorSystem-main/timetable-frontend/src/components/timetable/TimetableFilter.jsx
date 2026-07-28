import { useEffect, useState } from "react";
import {
    Grid,
    Paper,
    TextField,
    MenuItem,
    Button,
    Typography
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

    const loadCourses = async () => {

        try {

            const response =
                await courseService.getAllCourses();

            setCourses(response || []);

        } catch (error) {

            console.error(error);

        }

    };

    const loadSemesters = async (courseId) => {

        try {

            const response =
                await semesterService.getSemestersByCourse(courseId);

            setSemesters(response || []);

        } catch (error) {

            console.error(error);

        }

    };

    const loadSections = async (semesterId) => {

        try {

            const response =
                await sectionService.getSectionsBySemester(semesterId);

            setSections(response || []);

        } catch (error) {

            console.error(error);

        }

    };

    const handleChange = (event) => {

        const { name, value } = event.target;

        setFilters(prev => ({
            ...prev,
            [name]: value
        }));

    };

    return (

        <Paper sx={{ p: 3, mb: 3 }}>

            <Typography
                variant="h6"
                gutterBottom>

                Timetable Filters

            </Typography>

            <Grid
                container
                spacing={2}>

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
                    >

                        {
                            courses.map(course => (

                                <MenuItem
                                    key={course.courseId}
                                    value={course.courseId}>

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
                    >

                        {
                            semesters.map(semester => (

                                <MenuItem
                                    key={semester.semesterId}
                                    value={semester.semesterId}>

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
                    >

                        {
                            sections.map(section => (

                                <MenuItem
                                    key={section.sectionId}
                                    value={section.sectionId}>

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
                        gap: 2,
                        justifyContent: "flex-end"
                    }}>

                    <Button
                        variant="contained"
                        onClick={onLoad}>

                        Load Timetable

                    </Button>

                    <Button
                        variant="contained"
                        color="success"
                        onClick={onGenerate}>

                        Generate

                    </Button>

                    <Button
                        variant="outlined"
                        onClick={onRefresh}>

                        Refresh

                    </Button>

                </Grid>

            </Grid>

        </Paper>

    );

};

export default TimetableFilter;