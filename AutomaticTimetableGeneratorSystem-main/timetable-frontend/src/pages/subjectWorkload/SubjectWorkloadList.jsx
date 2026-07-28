import {
    useEffect,
    useMemo,
    useState
} from "react";

import {
    Box,
    Typography,
    CircularProgress,
    Alert,
    TextField,
    InputAdornment,
    Button
} from "@mui/material";

import AddIcon from "@mui/icons-material/Add";

import SearchIcon from "@mui/icons-material/Search";

import DashboardLayout
    from "../../components/layout/DashboardLayout";

import SubjectWorkloadTable
    from "../../components/subjectWorkload/SubjectWorkloadTable";

import SubjectWorkloadForm
    from "../../components/subjectWorkload/SubjectWorkloadForm";

import subjectWorkloadService
    from "../../services/subjectWorkloadService";


const SubjectWorkloadList = () => {

    // ==========================================
    // STATE
    // ==========================================

    const [workloads, setWorkloads] =
        useState([]);

    const [loading, setLoading] =
        useState(true);

    const [error, setError] =
        useState("");

    const [open, setOpen] =
        useState(false);

    const [selectedWorkload, setSelectedWorkload] =
        useState(null);

    const [search, setSearch] =
        useState("");


    // ==========================================
    // LOAD SUBJECT WORKLOADS
    // ==========================================

    const loadWorkloads = async () => {

        try {

            setLoading(true);

            setError("");

            const response =
                await subjectWorkloadService
                    .getAllSubjectWorkloads();


            // Support:
            // []
            //
            // OR
            //
            // { content: [] }

            const data =
                Array.isArray(response)
                    ? response
                    : response?.content || [];


            setWorkloads(data);


        } catch (error) {

            console.error(
                "Subject workload loading error:",
                error
            );


            console.error(
                "Server response:",
                error?.response?.data
            );


            setError(

                error?.response?.data?.message ||

                error?.response?.data?.error ||

                error?.message ||

                "Unable to load Subject Workloads."

            );

        } finally {

            setLoading(false);

        }

    };


    // ==========================================
    // LOAD ON PAGE OPEN
    // ==========================================

    useEffect(() => {

        loadWorkloads();

    }, []);


    // ==========================================
    // SEARCH FILTER
    // ==========================================

    const filteredWorkloads = useMemo(() => {

        const keyword =
            search
                .trim()
                .toLowerCase();


        if (!keyword) {

            return workloads;

        }


        return workloads.filter(
            (item) =>

                item.facultyCode
                    ?.toLowerCase()
                    .includes(keyword)

                ||

                item.facultyName
                    ?.toLowerCase()
                    .includes(keyword)

                ||

                item.subjectCode
                    ?.toLowerCase()
                    .includes(keyword)

                ||

                item.subjectName
                    ?.toLowerCase()
                    .includes(keyword)

                ||

                item.sectionName
                    ?.toLowerCase()
                    .includes(keyword)

        );

    }, [
        workloads,
        search
    ]);


    // ==========================================
    // ADD BUTTON
    // ==========================================

    const handleAdd = () => {

        // Clear previous selected record

        setSelectedWorkload(null);

        // Open Add form

        setOpen(true);

    };


    // ==========================================
    // EDIT BUTTON
    // ==========================================

    const handleEdit = (
        workload
    ) => {

        setSelectedWorkload(
            workload
        );

        setOpen(true);

    };


    // ==========================================
    // CLOSE FORM
    // ==========================================

    const handleClose = () => {

        setOpen(false);

        setSelectedWorkload(null);

    };


    // ==========================================
    // RENDER
    // ==========================================

    return (

        <DashboardLayout>

            {/* ==================================
                PAGE HEADER
            ================================== */}

            <Box
                display="flex"
                justifyContent="space-between"
                alignItems="center"
                mb={3}
                gap={2}
                flexWrap="wrap"
            >

                <Typography
                    variant="h4"
                    fontWeight="bold"
                >

                    Subject Workload Management

                </Typography>


                {/* ==================================
                    ADD BUTTON
                ================================== */}

                <Button
                    variant="contained"
                    color="primary"
                    startIcon={
                        <AddIcon />
                    }
                    onClick={
                        handleAdd
                    }
                    sx={{
                        minWidth: 220,
                        height: 45,
                        fontWeight: "bold"
                    }}
                >

                    Add Subject Workload

                </Button>

            </Box>


            {/* ==================================
                SEARCH
            ================================== */}

            <TextField

                fullWidth

                placeholder=
                    "Search by Faculty, Subject or Section..."

                value={
                    search
                }

                onChange={
                    (event) =>
                        setSearch(
                            event.target.value
                        )
                }

                sx={{
                    mb: 3
                }}

                InputProps={{

                    startAdornment: (

                        <InputAdornment
                            position="start"
                        >

                            <SearchIcon />

                        </InputAdornment>

                    )

                }}

            />


            {/* ==================================
                ERROR
            ================================== */}

            {error && (

                <Alert
                    severity="error"
                    sx={{
                        mb: 2
                    }}
                    onClose={() =>
                        setError("")
                    }
                >

                    {error}

                </Alert>

            )}


            {/* ==================================
                LOADING
            ================================== */}

            {loading ? (

                <Box

                    display="flex"

                    justifyContent="center"

                    alignItems="center"

                    mt={5}

                >

                    <CircularProgress />

                </Box>

            ) : (

                /* ==================================
                   TABLE
                ================================== */

                <SubjectWorkloadTable

                    workloads={
                        filteredWorkloads
                    }

                    onEdit={
                        handleEdit
                    }

                    reload={
                        loadWorkloads
                    }

                />

            )}


            {/* ==================================
                ADD / EDIT FORM
            ================================== */}

            <SubjectWorkloadForm

                open={
                    open
                }

                onClose={
                    handleClose
                }

                workload={
                    selectedWorkload
                }

                reload={
                    loadWorkloads
                }

            />

        </DashboardLayout>

    );

};


export default SubjectWorkloadList;