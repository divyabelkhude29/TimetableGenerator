import { useEffect, useState } from "react";

import {
    Grid,
    TextField,
    MenuItem,
    Button,
    Paper,
    Typography,
    FormControlLabel,
    Switch
} from "@mui/material";

import facultyService from "../../services/facultyService";
import subjectService from "../../services/subjectService";
import sectionService from "../../services/sectionService";
import semesterService from "../../services/semesterService";

const initialState = {
    facultyId: "",
    subjectId: "",
    sectionId: "",
    semesterId: "",
    academicYear: "",
    active: true
};

const AllocationForm = ({
    open,
    onClose,
    onSave,
    allocation
}) => {

    const [formData, setFormData] = useState(initialState);

    const [faculties, setFaculties] = useState([]);
    const [subjects, setSubjects] = useState([]);
    const [sections, setSections] = useState([]);
    const [semesters, setSemesters] = useState([]);

    useEffect(() => {

        loadDropdowns();

    }, []);

    useEffect(() => {

        if (allocation) {

            setFormData({
                facultyId: allocation.facultyId,
                subjectId: allocation.subjectId,
                sectionId: allocation.sectionId,
                semesterId: allocation.semesterId,
                academicYear: allocation.academicYear,
                active: allocation.active
            });

        } else {

            setFormData(initialState);

        }

    }, [allocation]);

    const loadDropdowns = async () => {

        try {

            const [
                facultyRes,
                subjectRes,
                sectionRes,
                semesterRes
            ] = await Promise.all([
                facultyService.getAllFaculties(),
                subjectService.getAllSubjects(),
                sectionService.getAllSections(),
                semesterService.getAllSemesters()
            ]);

            setFaculties(facultyRes.data || []);
            setSubjects(subjectRes.data || []);
            setSections(sectionRes.data || []);
            setSemesters(semesterRes.data || []);

        } catch (error) {

            console.error(error);

        }

    };

    const handleChange = (e) => {

        const { name, value } = e.target;

        setFormData(prev => ({
            ...prev,
            [name]: value
        }));

    };

    const handleSwitch = (e) => {

        setFormData(prev => ({
            ...prev,
            active: e.target.checked
        }));

    };

    const handleSubmit = (e) => {

        e.preventDefault();

        onSave(formData);

    };

    if (!open) return null;

    return (

        <Paper
            elevation={5}
            sx={{
                p: 3,
                mb: 3,
                borderRadius: 3
            }}
        >

            <Typography
                variant="h6"
                mb={3}
            >
                {allocation
                    ? "Update Faculty Allocation"
                    : "New Faculty Allocation"}
            </Typography>

            <form onSubmit={handleSubmit}>

                <Grid container spacing={2}>

                    <Grid item xs={12} md={6}>

                        <TextField
                            select
                            fullWidth
                            label="Faculty"
                            name="facultyId"
                            value={formData.facultyId}
                            onChange={handleChange}
                            required
                        >
                            {
                                faculties.map(faculty => (

                                    <MenuItem
                                        key={faculty.facultyId}
                                        value={faculty.facultyId}
                                    >
                                        {faculty.firstName} {faculty.lastName}
                                    </MenuItem>

                                ))
                            }
                        </TextField>

                    </Grid>

                    <Grid item xs={12} md={6}>

                        <TextField
                            select
                            fullWidth
                            label="Subject"
                            name="subjectId"
                            value={formData.subjectId}
                            onChange={handleChange}
                            required
                        >
                            {
                                subjects.map(subject => (

                                    <MenuItem
                                        key={subject.subjectId}
                                        value={subject.subjectId}
                                    >
                                        {subject.subjectName}
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
                            required
                        >
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

                    <Grid item xs={12} md={6}>

                        <TextField
                            select
                            fullWidth
                            label="Semester"
                            name="semesterId"
                            value={formData.semesterId}
                            onChange={handleChange}
                            required
                        >
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

                    <Grid item xs={12} md={6}>

                        <TextField
                            fullWidth
                            label="Academic Year"
                            name="academicYear"
                            value={formData.academicYear}
                            onChange={handleChange}
                            placeholder="2026-2027"
                            required
                        />

                    </Grid>

                    <Grid item xs={12} md={6}>

                        <FormControlLabel
                            control={
                                <Switch
                                    checked={formData.active}
                                    onChange={handleSwitch}
                                />
                            }
                            label="Active"
                        />

                    </Grid>

                    <Grid
                        item
                        xs={12}
                        display="flex"
                        justifyContent="flex-end"
                        gap={2}
                    >

                        <Button
                            variant="outlined"
                            color="inherit"
                            onClick={onClose}
                        >
                            Cancel
                        </Button>

                        <Button
                            type="submit"
                            variant="contained"
                        >
                            {allocation ? "Update" : "Save"}
                        </Button>

                    </Grid>

                </Grid>

            </form>

        </Paper>

    );

};

export default AllocationForm;