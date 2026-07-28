import {
    useEffect,
    useState
} from "react";

import {
    Box,
    Typography,
    Button,
    CircularProgress,
    Alert
} from "@mui/material";

import AddIcon
    from "@mui/icons-material/Add";

import DashboardLayout
    from "../../components/layout/DashboardLayout";

import AllocationTable
    from "../../components/allocations/AllocationTable";

import AllocationForm
    from "../../components/allocations/AllocationForm";

import allocationService
    from "../../services/allocationService";


const AllocationList = () => {


    const [
        allocationList,
        setAllocationList
    ] = useState([]);


    const [
        loading,
        setLoading
    ] = useState(true);


    const [
        error,
        setError
    ] = useState("");


    const [
        open,
        setOpen
    ] = useState(false);


    const [
        selectedAllocation,
        setSelectedAllocation
    ] = useState(null);


    /*
     * Load allocations
     */
    const loadAllocations = async () => {

        try {

            setLoading(true);

            setError("");


            const response =
                await allocationService
                    .getAllAllocations();


            console.log(
                "Allocation Response:",
                response
            );


            /*
             * Your service already returns
             * response.data.
             */
            const data =

                Array.isArray(response)

                    ? response

                    : Array.isArray(
                        response?.content
                    )

                        ? response.content

                        : [];


            setAllocationList(
                data
            );


        } catch (err) {

            console.error(
                "Error loading allocations:",
                err
            );


            setAllocationList([]);


            setError(

                err?.response?.data?.message ||

                err?.response?.data?.error ||

                "Unable to load allocations."

            );

        } finally {

            setLoading(false);

        }

    };


    /*
     * Initial load
     */
    useEffect(() => {

        loadAllocations();

    }, []);


    /*
     * Add Allocation
     */
    const handleAdd = () => {

        setSelectedAllocation(
            null
        );

        setOpen(true);

    };


    /*
     * Edit Allocation
     */
    const handleEdit = (
        allocation
    ) => {

        setSelectedAllocation(
            allocation
        );

        setOpen(true);

    };


    /*
     * Save Allocation
     */
    const handleSave = async (
        formData
    ) => {

        try {

            setError("");


            console.log(
                "Allocation Save Payload:",
                formData
            );


            /*
             * UPDATE
             */
            if (
                selectedAllocation
            ) {

                const allocationId =

                    selectedAllocation
                        .allocationId ??

                    selectedAllocation
                        .id;


                if (!allocationId) {

                    throw new Error(
                        "Allocation ID not found."
                    );

                }


                await allocationService
                    .updateAllocation(

                        allocationId,

                        formData

                    );

            }

            /*
             * CREATE
             */
            else {

                await allocationService
                    .createAllocation(
                        formData
                    );

            }


            /*
             * Close form
             */
            setOpen(false);


            setSelectedAllocation(
                null
            );


            /*
             * Reload table
             */
            await loadAllocations();


        } catch (err) {

            console.error(
                "Save allocation failed:",
                err
            );


            setError(

                err?.response?.data?.message ||

                err?.response?.data?.error ||

                err?.message ||

                "Unable to save allocation."

            );


            throw err;

        }

    };


    /*
     * Delete Allocation
     */
    const handleDelete = async (
        allocationId
    ) => {


        if (!allocationId) {

            alert(
                "Invalid allocation ID."
            );

            return;

        }


        const confirmed =
            window.confirm(
                "Delete this allocation?"
            );


        if (!confirmed) {

            return;

        }


        try {

            await allocationService
                .deleteAllocation(
                    allocationId
                );


            await loadAllocations();


        } catch (err) {

            console.error(
                "Delete allocation failed:",
                err
            );


            setError(

                err?.response?.data?.message ||

                err?.response?.data?.error ||

                "Unable to delete allocation."

            );

        }

    };


    /*
     * Close Form
     */
    const handleClose = () => {

        setOpen(false);

        setSelectedAllocation(
            null
        );

    };


    return (

        <DashboardLayout>

            <Box
                sx={{
                    width: "100%"
                }}
            >


                {/* HEADER */}

                <Box

                    sx={{

                        display:
                            "flex",

                        justifyContent:
                            "space-between",

                        alignItems:
                            "center",

                        mb: 3

                    }}

                >

                    <Typography

                        variant="h4"

                        fontWeight="bold"

                    >

                        Faculty Subject Allocation

                    </Typography>


                    <Button

                        variant="contained"

                        startIcon={
                            <AddIcon />
                        }

                        onClick={
                            handleAdd
                        }

                    >

                        Add Allocation

                    </Button>

                </Box>


                {/* ERROR */}

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


                {/* FORM */}

                <AllocationForm

                    open={
                        open
                    }

                    onClose={
                        handleClose
                    }

                    onSave={
                        handleSave
                    }

                    allocation={
                        selectedAllocation
                    }

                />


                {/* TABLE */}

                {loading ? (

                    <Box

                        sx={{

                            display:
                                "flex",

                            justifyContent:
                                "center",

                            mt: 5

                        }}

                    >

                        <CircularProgress />

                    </Box>

                ) : (

                    <AllocationTable

                        allocations={
                            allocationList
                        }

                        onEdit={
                            handleEdit
                        }

                        onDelete={
                            handleDelete
                        }

                    />

                )}

            </Box>

        </DashboardLayout>

    );

};


export default AllocationList;