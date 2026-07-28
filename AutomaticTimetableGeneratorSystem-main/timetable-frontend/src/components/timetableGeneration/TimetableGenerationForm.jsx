import { useEffect, useState } from "react";

import {
    Grid,
    TextField,
    MenuItem,
    Paper,
    Typography,
    FormControlLabel,
    Switch,
    Button,
    Stack
} from "@mui/material";

import courseService from "../../services/courseService";
import semesterService from "../../services/semesterService";
import sectionService from "../../services/sectionService";

export default function TimetableGenerationForm({

    formData,
    setFormData,
    onGenerate,
    onPreview,
    onValidate,
    onRegenerate,
    onDelete,
    loading

}) {

    const [courses, setCourses] = useState([]);

    const [semesters, setSemesters] = useState([]);

    const [sections, setSections] = useState([]);

    const [errors, setErrors] = useState({});

    useEffect(() => {

        loadCourses();

        loadSemesters();

        loadSections();

    }, []);

    const loadCourses = async () => {

        try {

            const response =
                await courseService.getAllCourses();

            setCourses(response);

        } catch (error) {

            console.error(error);

        }

    };

    const loadSemesters = async () => {

        try {

            const response =
                await semesterService.getAllSemesters();

            setSemesters(response);

        } catch (error) {

            console.error(error);

        }

    };

    const loadSections = async () => {

        try {

            const response =
                await sectionService.getAllSections();

            setSections(response);

        } catch (error) {

            console.error(error);

        }

    };

    const handleChange = (event) => {

        const {

            name,
            value,
            checked,
            type

        } = event.target;

        setFormData({

            ...formData,

            [name]:

                type === "checkbox"

                    ? checked

                    : value

        });

    };

    const validate = () => {

        let temp = {};

        if (!formData.academicYear)

            temp.academicYear =
                "Academic Year is required.";

        if (!formData.courseId)

            temp.courseId =
                "Course is required.";

        if (!formData.semesterId)

            temp.semesterId =
                "Semester is required.";

        if (!formData.sectionId)

            temp.sectionId =
                "Section is required.";

        setErrors(temp);

        return Object.keys(temp).length === 0;

    };

    const submit = (callback) => {

        if (validate()) {

            callback();

        }

    };

    return (

        <Paper sx={{ p: 3 }}>

            <Typography
                variant="h6"
                mb={3}
                fontWeight="bold"
            >

                Timetable Generation

            </Typography>

            <Grid container spacing={3}>

                <Grid item xs={12} md={6}>

                    <TextField

                        fullWidth

                        label="Academic Year"

                        name="academicYear"

                        placeholder="2025-2026"

                        value={formData.academicYear}

                        onChange={handleChange}

                        error={!!errors.academicYear}

                        helperText={errors.academicYear}

                    />

                </Grid>

                <Grid item xs={12} md={6}>

                    <TextField

                        select

                        fullWidth

                        label="Course"

                        name="courseId"

                        value={formData.courseId}

                        onChange={handleChange}

                        error={!!errors.courseId}

                        helperText={errors.courseId}

                    >

                        {

                            courses.map((course) => (

                                <MenuItem

                                    key={course.courseId}

                                    value={course.courseId}

                                >

                                    {course.courseCode}
                                    {" - "}
                                    {course.courseName}

                                </MenuItem>

                            ))

                        }

                    </TextField>

                </Grid>

                <Grid item xs={12} md={6}>

                    <TextField

                        select

                        fullWidth

                        label="Semester"

                        name="semesterId"

                        value={formData.semesterId}

                        onChange={handleChange}

                        error={!!errors.semesterId}

                        helperText={errors.semesterId}

                    >

                        {

                            semesters.map((semester) => (

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

                <Grid item xs={12} md={6}>

                    <TextField

                        select

                        fullWidth

                        label="Section"

                        name="sectionId"

                        value={formData.sectionId}

                        onChange={handleChange}

                        error={!!errors.sectionId}

                        helperText={errors.sectionId}

                    >

                        {

                            sections.map((section) => (

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

                <Grid item xs={12}>

                    <FormControlLabel

                        control={

                            <Switch

                                checked={formData.overwriteExisting}

                                onChange={handleChange}

                                name="overwriteExisting"

                            />

                        }

                        label="Overwrite Existing Timetable"

                    />

                </Grid>

                <Grid item xs={12}>

                    <Stack
                        direction="row"
                        spacing={2}
                        flexWrap="wrap"
                    >

                        <Button
                            variant="contained"
                            onClick={() => submit(onValidate)}
                            disabled={loading}
                        >
                            Validate
                        </Button>

                        <Button
                            variant="outlined"
                            onClick={() => submit(onPreview)}
                            disabled={loading}
                        >
                            Preview
                        </Button>

                        <Button
                            variant="contained"
                            color="success"
                            onClick={() => submit(onGenerate)}
                            disabled={loading}
                        >
                            Generate
                        </Button>

                        <Button
                            variant="contained"
                            color="warning"
                            onClick={() => submit(onRegenerate)}
                            disabled={loading}
                        >
                            Regenerate
                        </Button>

                        <Button
                            variant="contained"
                            color="error"
                            onClick={() => submit(onDelete)}
                            disabled={loading}
                        >
                            Delete
                        </Button>

                    </Stack>

                </Grid>

            </Grid>

        </Paper>

    );

}