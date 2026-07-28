import {
    useEffect,
    useState
} from "react";

import {
    Grid,
    TextField,
    MenuItem,
    Button,
    Paper,
    Typography,
    FormControlLabel,
    Switch,
    Box,
    CircularProgress,
    Alert
} from "@mui/material";

import facultyService
    from "../../services/facultyService";

import subjectService
    from "../../services/subjectService";

import sectionService
    from "../../services/sectionService";

import semesterService
    from "../../services/semesterService";


/*
 * Initial form state
 */
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


    /*
     * ============================
     * FORM STATE
     * ============================
     */

    const [
        formData,
        setFormData
    ] = useState({
        ...initialState
    });


    /*
     * ============================
     * DROPDOWN STATES
     * ============================
     */

    const [
        faculties,
        setFaculties
    ] = useState([]);


    const [
        subjects,
        setSubjects
    ] = useState([]);


    const [
        sections,
        setSections
    ] = useState([]);


    const [
        semesters,
        setSemesters
    ] = useState([]);


    /*
     * ============================
     * LOADING / ERROR STATES
     * ============================
     */

    const [
        loading,
        setLoading
    ] = useState(false);


    const [
        saving,
        setSaving
    ] = useState(false);


    const [
        error,
        setError
    ] = useState("");


    /*
     * ============================
     * LOAD DROPDOWNS WHEN FORM OPENS
     * ============================
     */

    useEffect(() => {

        if (open) {

            loadDropdowns();

        }

    }, [open]);


    /*
     * ============================
     * SET ADD / EDIT FORM DATA
     * ============================
     */

    useEffect(() => {

        if (!open) {

            return;

        }


        if (allocation) {

            console.log(
                "Editing Allocation:",
                allocation
            );


            setFormData({

                facultyId:
                    allocation.facultyId ??
                    allocation.faculty?.facultyId ??
                    "",

                subjectId:
                    allocation.subjectId ??
                    allocation.subject?.subjectId ??
                    "",

                sectionId:
                    allocation.sectionId ??
                    allocation.section?.sectionId ??
                    "",

                semesterId:
                    allocation.semesterId ??
                    allocation.semester?.semesterId ??
                    "",

                academicYear:
                    allocation.academicYear ??
                    "",

                active:
                    allocation.active ??
                    true

            });

        } else {

            /*
             * New allocation
             */

            setFormData({
                ...initialState
            });

        }


        setError("");


    }, [
        allocation,
        open
    ]);


    /*
     * ============================
     * HELPER
     * Convert API response to array
     * ============================
     *
     * Your services already return:
     *
     * response.data
     *
     * So normally response is already
     * an array.
     *
     * This also supports:
     *
     * {
     *     content: []
     * }
     *
     * or:
     *
     * {
     *     data: []
     * }
     */

    const getArrayData = (
        response
    ) => {

        if (
            Array.isArray(response)
        ) {

            return response;

        }


        if (
            Array.isArray(
                response?.content
            )
        ) {

            return response.content;

        }


        if (
            Array.isArray(
                response?.data
            )
        ) {

            return response.data;

        }


        return [];

    };


    /*
     * ============================
     * LOAD ALL DROPDOWNS
     * ============================
     */

    const loadDropdowns = async () => {

        try {

            setLoading(true);

            setError("");


            console.log(
                "================================"
            );

            console.log(
                "Loading Allocation Dropdowns..."
            );

            console.log(
                "================================"
            );


            /*
             * IMPORTANT FIX:
             *
             * Your facultyService has:
             *
             * getAllFaculty()
             *
             * NOT:
             *
             * getAllFaculties()
             */

            const [

                facultyResponse,

                subjectResponse,

                sectionResponse,

                semesterResponse

            ] = await Promise.all([

                facultyService
                    .getAllFaculty(),

                subjectService
                    .getAllSubjects(),

                sectionService
                    .getAllSections(),

                semesterService
                    .getAllSemesters()

            ]);


            /*
             * Convert API responses
             * into arrays
             */

            const facultyList =
                getArrayData(
                    facultyResponse
                );


            const subjectList =
                getArrayData(
                    subjectResponse
                );


            const sectionList =
                getArrayData(
                    sectionResponse
                );


            const semesterList =
                getArrayData(
                    semesterResponse
                );


            /*
             * Console debugging
             */

            console.log(
                "Faculty List:",
                facultyList
            );


            console.log(
                "Subject List:",
                subjectList
            );


            console.log(
                "Section List:",
                sectionList
            );


            console.log(
                "Semester List:",
                semesterList
            );


            /*
             * Update states
             */

            setFaculties(
                facultyList
            );


            setSubjects(
                subjectList
            );


            setSections(
                sectionList
            );


            setSemesters(
                semesterList
            );


        } catch (err) {

            console.error(
                "Dropdown loading error:",
                err
            );


            console.error(
                "Server response:",
                err?.response?.data
            );


            setError(

                err?.response?.data?.message ||

                err?.response?.data?.error ||

                err?.message ||

                "Unable to load faculty, subject, section and semester data."

            );

        } finally {

            setLoading(false);

        }

    };


    /*
     * ============================
     * HANDLE TEXT / SELECT CHANGE
     * ============================
     */

    const handleChange = (
        event
    ) => {

        const {

            name,

            value

        } = event.target;


        setFormData(

            previous => ({

                ...previous,

                [name]: value

            })

        );

    };


    /*
     * ============================
     * HANDLE ACTIVE SWITCH
     * ============================
     */

    const handleSwitch = (
        event
    ) => {

        setFormData(

            previous => ({

                ...previous,

                active:
                    event.target.checked

            })

        );

    };


    /*
     * ============================
     * HANDLE FORM SUBMIT
     * ============================
     */

    const handleSubmit = async (
        event
    ) => {

        event.preventDefault();


        setError("");


        /*
         * ============================
         * VALIDATION
         * ============================
         */

        if (
            !formData.facultyId
        ) {

            setError(
                "Please select a faculty."
            );

            return;

        }


        if (
            !formData.subjectId
        ) {

            setError(
                "Please select a subject."
            );

            return;

        }


        if (
            !formData.sectionId
        ) {

            setError(
                "Please select a section."
            );

            return;

        }


        if (
            !formData.semesterId
        ) {

            setError(
                "Please select a semester."
            );

            return;

        }


        if (
            !formData.academicYear.trim()
        ) {

            setError(
                "Please enter academic year."
            );

            return;

        }


        /*
         * ============================
         * ACADEMIC YEAR VALIDATION
         * ============================
         *
         * Valid:
         *
         * 2026-2027
         *
         * Invalid:
         *
         * 2026
         * 26-27
         * 2026/2027
         */

        const yearRegex =
            /^\d{4}-\d{4}$/;


        if (
            !yearRegex.test(
                formData.academicYear.trim()
            )
        ) {

            setError(
                "Academic year must be in format 2026-2027."
            );

            return;

        }


        try {

            setSaving(true);


            /*
             * ============================
             * CREATE FINAL PAYLOAD
             * ============================
             *
             * Convert IDs from strings
             * to numbers.
             */

            const payload = {

                facultyId:
                    Number(
                        formData.facultyId
                    ),

                subjectId:
                    Number(
                        formData.subjectId
                    ),

                sectionId:
                    Number(
                        formData.sectionId
                    ),

                semesterId:
                    Number(
                        formData.semesterId
                    ),

                academicYear:
                    formData
                        .academicYear
                        .trim(),

                active:
                    Boolean(
                        formData.active
                    )

            };


            console.log(
                "================================"
            );

            console.log(
                "FINAL ALLOCATION PAYLOAD:"
            );

            console.log(
                payload
            );

            console.log(
                "================================"
            );


            /*
             * Send payload to parent
             *
             * AllocationList.jsx
             * will call:
             *
             * createAllocation()
             *
             * or:
             *
             * updateAllocation()
             */

            if (
                typeof onSave ===
                "function"
            ) {

                await onSave(
                    payload
                );

            } else {

                console.error(
                    "onSave function is missing."
                );

                setError(
                    "Save function is not connected. Check AllocationList.jsx."
                );

                return;

            }


            /*
             * Reset form after success
             */

            setFormData({
                ...initialState
            });


        } catch (err) {

            console.error(
                "Allocation save error:",
                err
            );


            console.error(
                "Server response:",
                err?.response?.data
            );


            setError(

                err?.response?.data?.message ||

                err?.response?.data?.error ||

                err?.message ||

                "Unable to save allocation."

            );

        } finally {

            setSaving(false);

        }

    };


    /*
     * ============================
     * FORM CLOSED
     * ============================
     */

    if (!open) {

        return null;

    }


    /*
     * ============================
     * RENDER
     * ============================
     */

    return (

        <Paper

            elevation={5}

            sx={{

                p: 3,

                mb: 3,

                borderRadius: 3,

                width: "100%",

                boxSizing: "border-box"

            }}

        >

            {/* ============================ */}
            {/* TITLE */}
            {/* ============================ */}

            <Typography

                variant="h6"

                fontWeight="bold"

                mb={3}

            >

                {allocation

                    ? "Update Faculty Allocation"

                    : "New Faculty Allocation"

                }

            </Typography>


            {/* ============================ */}
            {/* ERROR */}
            {/* ============================ */}

            {error && (

                <Alert

                    severity="error"

                    sx={{
                        mb: 3
                    }}

                    onClose={() =>
                        setError("")
                    }

                >

                    {error}

                </Alert>

            )}


            {/* ============================ */}
            {/* LOADING */}
            {/* ============================ */}

            {loading ? (

                <Box

                    sx={{

                        display:
                            "flex",

                        justifyContent:
                            "center",

                        alignItems:
                            "center",

                        minHeight:
                            150

                    }}

                >

                    <CircularProgress />

                </Box>

            ) : (

                <form

                    onSubmit={
                        handleSubmit
                    }

                >

                    <Grid

                        container

                        spacing={2}

                    >


                        {/* ============================ */}
                        {/* FACULTY */}
                        {/* ============================ */}

                        <Grid

                            item

                            xs={12}

                            md={6}

                        >

                            <TextField

                                select

                                fullWidth

                                label="Faculty"

                                name="facultyId"

                                value={
                                    formData.facultyId
                                }

                                onChange={
                                    handleChange
                                }

                                required

                            >

                                <MenuItem
                                    value=""
                                >

                                    Select Faculty

                                </MenuItem>


                                {faculties.map(

                                    (
                                        faculty
                                    ) => (

                                        <MenuItem

                                            key={
                                                faculty.facultyId
                                            }

                                            value={
                                                faculty.facultyId
                                            }

                                        >

                                            {
                                                faculty.firstName
                                            }{" "}

                                            {
                                                faculty.lastName
                                            }

                                        </MenuItem>

                                    )

                                )}

                            </TextField>

                        </Grid>


                        {/* ============================ */}
                        {/* SUBJECT */}
                        {/* ============================ */}

                        <Grid

                            item

                            xs={12}

                            md={6}

                        >

                            <TextField

                                select

                                fullWidth

                                label="Subject"

                                name="subjectId"

                                value={
                                    formData.subjectId
                                }

                                onChange={
                                    handleChange
                                }

                                required

                            >

                                <MenuItem
                                    value=""
                                >

                                    Select Subject

                                </MenuItem>


                                {subjects.map(

                                    (
                                        subject
                                    ) => (

                                        <MenuItem

                                            key={
                                                subject.subjectId
                                            }

                                            value={
                                                subject.subjectId
                                            }

                                        >

                                            {
                                                subject.subjectName
                                            }

                                        </MenuItem>

                                    )

                                )}

                            </TextField>

                        </Grid>


                        {/* ============================ */}
                        {/* SECTION */}
                        {/* ============================ */}

                        <Grid

                            item

                            xs={12}

                            md={6}

                        >

                            <TextField

                                select

                                fullWidth

                                label="Section"

                                name="sectionId"

                                value={
                                    formData.sectionId
                                }

                                onChange={
                                    handleChange
                                }

                                required

                            >

                                <MenuItem
                                    value=""
                                >

                                    Select Section

                                </MenuItem>


                                {sections.map(

                                    (
                                        section
                                    ) => (

                                        <MenuItem

                                            key={
                                                section.sectionId
                                            }

                                            value={
                                                section.sectionId
                                            }

                                        >

                                            {
                                                section.sectionName
                                            }

                                        </MenuItem>

                                    )

                                )}

                            </TextField>

                        </Grid>


                        {/* ============================ */}
                        {/* SEMESTER */}
                        {/* ============================ */}

                        <Grid

                            item

                            xs={12}

                            md={6}

                        >

                            <TextField

                                select

                                fullWidth

                                label="Semester"

                                name="semesterId"

                                value={
                                    formData.semesterId
                                }

                                onChange={
                                    handleChange
                                }

                                required

                            >

                                <MenuItem
                                    value=""
                                >

                                    Select Semester

                                </MenuItem>


                                {semesters.map(

                                    (
                                        semester
                                    ) => (

                                        <MenuItem

                                            key={
                                                semester.semesterId
                                            }

                                            value={
                                                semester.semesterId
                                            }

                                        >

                                            Semester{" "}

                                            {
                                                semester.semesterNumber
                                            }

                                        </MenuItem>

                                    )

                                )}

                            </TextField>

                        </Grid>


                        {/* ============================ */}
                        {/* ACADEMIC YEAR */}
                        {/* ============================ */}

                        <Grid

                            item

                            xs={12}

                            md={6}

                        >

                            <TextField

                                fullWidth

                                label="Academic Year"

                                name="academicYear"

                                value={
                                    formData.academicYear
                                }

                                onChange={
                                    handleChange
                                }

                                placeholder="2026-2027"

                                required

                            />

                        </Grid>


                        {/* ============================ */}
                        {/* ACTIVE */}
                        {/* ============================ */}

                        <Grid

                            item

                            xs={12}

                            md={6}

                        >

                            <FormControlLabel

                                control={

                                    <Switch

                                        checked={
                                            formData.active
                                        }

                                        onChange={
                                            handleSwitch
                                        }

                                    />

                                }

                                label="Active"

                            />

                        </Grid>


                        {/* ============================ */}
                        {/* BUTTONS */}
                        {/* ============================ */}

                        <Grid

                            item

                            xs={12}

                        >

                            <Box

                                sx={{

                                    display:
                                        "flex",

                                    justifyContent:
                                        "flex-end",

                                    gap: 2,

                                    mt: 2

                                }}

                            >

                                <Button

                                    variant="outlined"

                                    color="inherit"

                                    onClick={
                                        onClose
                                    }

                                    disabled={
                                        saving
                                    }

                                >

                                    Cancel

                                </Button>


                                <Button

                                    type="submit"

                                    variant="contained"

                                    disabled={
                                        saving
                                    }

                                >

                                    {saving

                                        ? "Saving..."

                                        : allocation

                                            ? "Update"

                                            : "Save"

                                    }

                                </Button>

                            </Box>

                        </Grid>


                    </Grid>

                </form>

            )}

        </Paper>

    );

};


export default AllocationForm;