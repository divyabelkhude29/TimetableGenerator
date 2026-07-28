import {
    useEffect,
    useState
} from "react";

import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Button,
    Grid,
    TextField,
    MenuItem,
    FormControlLabel,
    Switch,
    CircularProgress,
    Alert,
    Box,
    Typography
} from "@mui/material";

import allocationService
    from "../../services/allocationService";

import subjectWorkloadService
    from "../../services/subjectWorkloadService";


const initialState = {

    allocationId: "",

    weeklyHours: "",

    theoryHours: "",

    practicalHours: "",

    active: true

};


const SubjectWorkloadForm = ({

    open,

    onClose,

    workload,

    reload

}) => {


    // ==========================================
    // STATE
    // ==========================================

    const [allocationList, setAllocationList] =
        useState([]);


    const [loading, setLoading] =
        useState(false);


    const [saving, setSaving] =
        useState(false);


    const [message, setMessage] =
        useState("");


    const [errors, setErrors] =
        useState({});


    const [formData, setFormData] =
        useState({
            ...initialState
        });


    // ==========================================
    // LOAD DATA WHEN FORM OPENS
    // ==========================================

    useEffect(() => {

        if (!open) {

            return;

        }


        setMessage("");

        setErrors({});


        loadAllocations();


        // ======================================
        // EDIT MODE
        // ======================================

        if (workload) {

            setFormData({

                allocationId:

                    workload.allocationId ??

                    workload.allocation?.allocationId ??

                    "",


                weeklyHours:

                    workload.weeklyHours ??

                    "",


                theoryHours:

                    workload.theoryHours ??

                    "",


                practicalHours:

                    workload.practicalHours ??

                    "",


                active:

                    workload.active ??
                    true

            });

        }


        // ======================================
        // ADD MODE
        // ======================================

        else {

            setFormData({

                ...initialState

            });

        }

    }, [
        open,
        workload
    ]);


    // ==========================================
    // LOAD FACULTY ALLOCATIONS
    // ==========================================

    const loadAllocations = async () => {

        try {

            setLoading(true);

            setMessage("");


            const response =

                await allocationService
                    .getAllAllocations();


            // Support:
            //
            // []
            //
            // OR
            //
            // { content: [] }

            const data =

                Array.isArray(response)

                    ? response

                    : response?.content || [];


            setAllocationList(
                data
            );


        } catch (error) {

            console.error(
                "Allocation loading error:",
                error
            );


            console.error(
                "Server response:",
                error?.response?.data
            );


            setMessage(

                error?.response?.data?.message ||

                error?.response?.data?.error ||

                error?.message ||

                "Unable to load Faculty Allocations."

            );

        } finally {

            setLoading(false);

        }

    };


    // ==========================================
    // HANDLE INPUT CHANGE
    // ==========================================

    const handleChange = (
        event
    ) => {

        const {

            name,

            value,

            checked,

            type

        } = event.target;


        setFormData(
            previous => ({

                ...previous,

                [name]:

                    type === "checkbox"

                        ? checked

                        : value

            })
        );


        // Clear field error

        setErrors(
            previous => ({

                ...previous,

                [name]: ""

            })
        );


        setMessage("");

    };


    // ==========================================
    // VALIDATION
    // ==========================================

    const validate = () => {

        const temp = {};


        // Allocation

        if (!formData.allocationId) {

            temp.allocationId =
                "Faculty Allocation is required.";

        }


        // Weekly hours

        if (
            formData.weeklyHours === "" ||
            formData.weeklyHours === null
        ) {

            temp.weeklyHours =
                "Weekly Hours is required.";

        } else if (
            Number(formData.weeklyHours) < 1
        ) {

            temp.weeklyHours =
                "Weekly Hours must be at least 1.";

        }


        // Theory hours

        if (
            formData.theoryHours === "" ||
            formData.theoryHours === null
        ) {

            temp.theoryHours =
                "Theory Hours is required.";

        } else if (
            Number(formData.theoryHours) < 0
        ) {

            temp.theoryHours =
                "Theory Hours cannot be negative.";

        }


        // Practical hours

        if (
            formData.practicalHours === "" ||
            formData.practicalHours === null
        ) {

            temp.practicalHours =
                "Practical Hours is required.";

        } else if (
            Number(formData.practicalHours) < 0
        ) {

            temp.practicalHours =
                "Practical Hours cannot be negative.";

        }


        // ======================================
        // THEORY + PRACTICAL CHECK
        // ======================================

        const weekly =
            Number(
                formData.weeklyHours
            );


        const theory =
            Number(
                formData.theoryHours
            );


        const practical =
            Number(
                formData.practicalHours
            );


        if (
            !temp.weeklyHours &&

            !temp.theoryHours &&

            !temp.practicalHours &&

            theory + practical > weekly
        ) {

            temp.practicalHours =

                "Theory Hours + Practical Hours cannot exceed Weekly Hours.";

        }


        setErrors(
            temp
        );


        return (
            Object.keys(temp).length === 0
        );

    };


    // ==========================================
    // SAVE
    // ==========================================

    const handleSave = async () => {

        setMessage("");


        // Validate

        if (!validate()) {

            return;

        }


        try {

            setSaving(true);


            // ==================================
            // FINAL PAYLOAD
            // ==================================

            const payload = {

                allocationId:
                    Number(
                        formData.allocationId
                    ),


                weeklyHours:
                    Number(
                        formData.weeklyHours
                    ),


                theoryHours:
                    Number(
                        formData.theoryHours
                    ),


                practicalHours:
                    Number(
                        formData.practicalHours
                    ),


                active:
                    Boolean(
                        formData.active
                    )

            };


            console.log(
                "SUBJECT WORKLOAD PAYLOAD:",
                payload
            );


            // ==================================
            // UPDATE
            // ==================================

            if (workload) {

                await subjectWorkloadService
                    .updateSubjectWorkload(

                        workload.workloadId,

                        payload

                    );

            }


            // ==================================
            // CREATE
            // ==================================

            else {

                await subjectWorkloadService
                    .saveSubjectWorkload(

                        payload

                    );

            }


            // ==================================
            // RELOAD TABLE
            // ==================================

            await reload();


            // ==================================
            // RESET FORM
            // ==================================

            setFormData({

                ...initialState

            });


            // ==================================
            // CLOSE
            // ==================================

            onClose();


        } catch (error) {

            console.error(
                "Subject workload save error:",
                error
            );


            console.error(
                "Server response:",
                error?.response?.data
            );


            setMessage(

                error?.response?.data?.message ||

                error?.response?.data?.error ||

                error?.message ||

                "Unable to save Subject Workload."

            );

        } finally {

            setSaving(false);

        }

    };


    // ==========================================
    // FORM UI
    // ==========================================

    return (

        <Dialog

            open={
                open
            }

            onClose={

                saving

                    ? undefined

                    : onClose

            }

            fullWidth

            maxWidth="md"

        >


            {/* ==================================
                TITLE
            ================================== */}

            <DialogTitle>

                {workload

                    ? "Update Subject Workload"

                    : "Add Subject Workload"

                }

            </DialogTitle>


            {/* ==================================
                CONTENT
            ================================== */}

            <DialogContent
                dividers
            >


                {/* ERROR MESSAGE */}

                {message && (

                    <Alert

                        severity="error"

                        sx={{
                            mb: 2
                        }}

                        onClose={() =>
                            setMessage("")
                        }

                    >

                        {message}

                    </Alert>

                )}


                {/* LOADING */}

                {loading ? (

                    <Box

                        display="flex"

                        justifyContent="center"

                        alignItems="center"

                        sx={{
                            py: 6
                        }}

                    >

                        <CircularProgress />

                    </Box>

                ) : (


                    <Grid

                        container

                        spacing={3}

                        sx={{
                            mt: 0.5
                        }}

                    >


                        {/* ==================================
                            FACULTY ALLOCATION
                        ================================== */}

                        <Grid

                            item

                            xs={12}

                        >

                            <TextField

                                select

                                fullWidth

                                label="Faculty Allocation"

                                name="allocationId"

                                value={
                                    formData.allocationId
                                }

                                onChange={
                                    handleChange
                                }

                                error={
                                    !!errors.allocationId
                                }

                                helperText={
                                    errors.allocationId ||
                                    "Select the faculty allocation for this workload."
                                }

                                required

                            >


                                <MenuItem
                                    value=""
                                >

                                    Select Faculty Allocation

                                </MenuItem>


                                {allocationList.map(

                                    (item) => {


                                        // ==================================
                                        // SUPPORT NESTED OBJECTS
                                        // ==================================

                                        const facultyCode =

                                            item.facultyCode ??

                                            item.faculty
                                                ?.facultyCode ??

                                            "";


                                        const facultyName =

                                            item.facultyName ??

                                            item.faculty
                                                ?.facultyName ??

                                            (

                                                item.faculty
                                                    ?.firstName

                                                    ?

                                                    `${item.faculty.firstName} ${item.faculty.lastName || ""}`

                                                    :

                                                    ""

                                            );


                                        const subjectCode =

                                            item.subjectCode ??

                                            item.subject
                                                ?.subjectCode ??

                                            "";


                                        const subjectName =

                                            item.subjectName ??

                                            item.subject
                                                ?.subjectName ??

                                            "";


                                        const sectionName =

                                            item.sectionName ??

                                            item.section
                                                ?.sectionName ??

                                            "";


                                        return (

                                            <MenuItem

                                                key={
                                                    item.allocationId
                                                }

                                                value={
                                                    item.allocationId
                                                }

                                            >

                                                <Box>

                                                    <Typography
                                                        variant="body1"
                                                        fontWeight="bold"
                                                    >

                                                        {facultyCode}

                                                        {" - "}

                                                        {facultyName}

                                                    </Typography>


                                                    <Typography
                                                        variant="body2"
                                                        color="text.secondary"
                                                    >

                                                        {subjectCode}

                                                        {" - "}

                                                        {subjectName}


                                                        {sectionName && (

                                                            <>
                                                                {" | "}
                                                                {sectionName}
                                                            </>

                                                        )}

                                                    </Typography>

                                                </Box>

                                            </MenuItem>

                                        );

                                    }

                                )}

                            </TextField>

                        </Grid>


                        {/* ==================================
                            WEEKLY HOURS
                        ================================== */}

                        <Grid

                            item

                            xs={12}

                            md={4}

                        >

                            <TextField

                                fullWidth

                                type="number"

                                label="Weekly Hours"

                                name="weeklyHours"

                                value={
                                    formData.weeklyHours
                                }

                                onChange={
                                    handleChange
                                }

                                error={
                                    !!errors.weeklyHours
                                }

                                helperText={
                                    errors.weeklyHours
                                }

                                inputProps={{
                                    min: 1
                                }}

                                required

                            />

                        </Grid>


                        {/* ==================================
                            THEORY HOURS
                        ================================== */}

                        <Grid

                            item

                            xs={12}

                            md={4}

                        >

                            <TextField

                                fullWidth

                                type="number"

                                label="Theory Hours"

                                name="theoryHours"

                                value={
                                    formData.theoryHours
                                }

                                onChange={
                                    handleChange
                                }

                                error={
                                    !!errors.theoryHours
                                }

                                helperText={
                                    errors.theoryHours
                                }

                                inputProps={{
                                    min: 0
                                }}

                                required

                            />

                        </Grid>


                        {/* ==================================
                            PRACTICAL HOURS
                        ================================== */}

                        <Grid

                            item

                            xs={12}

                            md={4}

                        >

                            <TextField

                                fullWidth

                                type="number"

                                label="Practical Hours"

                                name="practicalHours"

                                value={
                                    formData.practicalHours
                                }

                                onChange={
                                    handleChange
                                }

                                error={
                                    !!errors.practicalHours
                                }

                                helperText={
                                    errors.practicalHours
                                }

                                inputProps={{
                                    min: 0
                                }}

                                required

                            />

                        </Grid>


                        {/* ==================================
                            ACTIVE
                        ================================== */}

                        <Grid

                            item

                            xs={12}

                        >

                            <FormControlLabel

                                control={

                                    <Switch

                                        checked={
                                            formData.active
                                        }

                                        onChange={
                                            handleChange
                                        }

                                        name="active"

                                    />

                                }

                                label="Active"

                            />

                        </Grid>


                    </Grid>

                )}

            </DialogContent>


            {/* ==================================
                ACTIONS
            ================================== */}

            <DialogActions>

                <Button

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

                    variant="contained"

                    onClick={
                        handleSave
                    }

                    disabled={
                        saving ||
                        loading
                    }

                >

                    {saving

                        ? "Saving..."

                        : workload

                            ? "Update"

                            : "Save"

                    }

                </Button>

            </DialogActions>


        </Dialog>

    );

};


export default SubjectWorkloadForm;