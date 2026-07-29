import { useState } from "react";

import {
    Box,
    Paper,
    Typography,
    Snackbar,
    Alert,
    CircularProgress,
    Button,
    Stack
} from "@mui/material";

import VisibilityIcon from "@mui/icons-material/Visibility";
import RefreshIcon from "@mui/icons-material/Refresh";
import DeleteIcon from "@mui/icons-material/Delete";
import PictureAsPdfIcon from "@mui/icons-material/PictureAsPdf";
import TableViewIcon from "@mui/icons-material/TableView";
import PrintIcon from "@mui/icons-material/Print";

import DashboardLayout from "../../components/layout/DashboardLayout";

import TimetableFilter from "../../components/timetable/TimetableFilter";
import TimetableGrid from "../../components/timetable/TimetableGrid";
import TimetableLegend from "../../components/timetable/TimetableLegend";

import timetableService from "../../services/timetableService";


const TimetableView = () => {

    /*
     * Filter values
     */
    const [filters, setFilters] = useState({
        academicYear: "",
        courseId: "",
        semesterId: "",
        sectionId: ""
    });

    /*
     * Timetable data
     */
    const [timetable, setTimetable] = useState([]);

    /*
     * Loading state
     */
    const [loading, setLoading] = useState(false);

    /*
     * Snackbar
     */
    const [message, setMessage] = useState("");

    const [severity, setSeverity] = useState("success");

    const [openSnackbar, setOpenSnackbar] = useState(false);


    /*
     * Show message
     */
    const showMessage = (
        msg,
        type = "success"
    ) => {

        setMessage(msg);

        setSeverity(type);

        setOpenSnackbar(true);

    };


    /*
     * Validate filters
     */
    const validateFilters = () => {

        /*
         * NOTE: Academic Year is informational only - the
         * backend identifies a timetable purely by
         * sectionId, so it is not required to load/export.
         */

        if (!filters.courseId) {

            showMessage(
                "Please select Course.",
                "warning"
            );

            return false;

        }

        if (!filters.semesterId) {

            showMessage(
                "Please select Semester.",
                "warning"
            );

            return false;

        }

        if (!filters.sectionId) {

            showMessage(
                "Please select Section.",
                "warning"
            );

            return false;

        }

        return true;

    };


    /*
     * Load / View Timetable
     */
    const handleLoad = async () => {

        if (!validateFilters()) {

            return;

        }

        try {

            setLoading(true);

            const data =
                await timetableService.getTimetable(

                    filters.academicYear,

                    filters.courseId,

                    filters.semesterId,

                    filters.sectionId

                );


            /*
             * Make sure timetable is always an array
             */
            if (Array.isArray(data)) {

                setTimetable(data);

            } else if (data?.data && Array.isArray(data.data)) {

                setTimetable(data.data);

            } else {

                setTimetable([]);

            }


            showMessage(
                "Timetable loaded successfully."
            );

        } catch (error) {

            console.error(
                "Error loading timetable:",
                error
            );

            setTimetable([]);

            showMessage(

                error.response?.data?.message ||

                "Unable to load timetable.",

                "error"

            );

        } finally {

            setLoading(false);

        }

    };


    /*
     * Refresh timetable
     */
    const handleRefresh = async () => {

        if (!validateFilters()) {

            return;

        }

        await handleLoad();

    };


    /*
     * Delete timetable
     */
    const handleDelete = async () => {

        if (!validateFilters()) {

            return;

        }

        /*
         * The delete endpoint (DELETE /timetable-generation)
         * requires Academic Year specifically, unlike
         * view/PDF/Excel which only need the Section.
         */
        if (!filters.academicYear) {

            showMessage(
                "Please enter Academic Year before deleting.",
                "warning"
            );

            return;

        }

        const confirmed =
            window.confirm(

                "Are you sure you want to delete the generated timetable?"

            );


        if (!confirmed) {

            return;

        }


        try {

            setLoading(true);

            await timetableService.deleteTimetable({

                academicYear:
                    filters.academicYear,

                courseId:
                    filters.courseId,

                semesterId:
                    filters.semesterId,

                sectionId:
                    filters.sectionId

            });


            setTimetable([]);


            showMessage(
                "Timetable deleted successfully."
            );

        } catch (error) {

            console.error(
                "Error deleting timetable:",
                error
            );

            showMessage(

                error.response?.data?.message ||

                "Unable to delete timetable.",

                "error"

            );

        } finally {

            setLoading(false);

        }

    };


    /*
     * Export PDF
     */
    const handlePdf = async () => {

        if (!validateFilters()) {

            return;

        }

        try {

            setLoading(true);

            const blob =
                await timetableService.exportPdf(

                    filters.academicYear,

                    filters.courseId,

                    filters.semesterId,

                    filters.sectionId

                );


            const url =
                window.URL.createObjectURL(blob);


            const link =
                document.createElement("a");


            link.href = url;

            link.download =
                `Timetable_Section${filters.sectionId}.pdf`;


            document.body.appendChild(link);

            link.click();

            document.body.removeChild(link);


            window.URL.revokeObjectURL(url);


            showMessage(
                "PDF downloaded successfully."
            );

        } catch (error) {

            console.error(
                "PDF export error:",
                error
            );

            showMessage(
                "Unable to export PDF.",
                "error"
            );

        } finally {

            setLoading(false);

        }

    };


    /*
     * Export Excel
     */
    const handleExcel = async () => {

        if (!validateFilters()) {

            return;

        }

        try {

            setLoading(true);

            const blob =
                await timetableService.exportExcel(

                    filters.academicYear,

                    filters.courseId,

                    filters.semesterId,

                    filters.sectionId

                );


            const url =
                window.URL.createObjectURL(blob);


            const link =
                document.createElement("a");


            link.href = url;

            link.download =
                `Timetable_Section${filters.sectionId}.xlsx`;


            document.body.appendChild(link);

            link.click();

            document.body.removeChild(link);


            window.URL.revokeObjectURL(url);


            showMessage(
                "Excel file downloaded successfully."
            );

        } catch (error) {

            console.error(
                "Excel export error:",
                error
            );

            showMessage(
                "Unable to export Excel file.",
                "error"
            );

        } finally {

            setLoading(false);

        }

    };


    /*
     * Print timetable
     */
    const handlePrint = () => {

        if (timetable.length === 0) {

            showMessage(

                "Please load a timetable before printing.",

                "warning"

            );

            return;

        }

        window.print();

    };


    return (

        <DashboardLayout>

            <Box
                sx={{
                    p: {
                        xs: 1,
                        sm: 2,
                        md: 3
                    }
                }}
            >

                {/* PAGE TITLE */}

                <Typography
                    variant="h4"
                    fontWeight="bold"
                    mb={3}
                >

                    Timetable View

                </Typography>


                {/* FILTER SECTION */}

                <Paper
                    elevation={3}
                    sx={{
                        p: 3,
                        mb: 3
                    }}
                >

                    <Typography
                        variant="h6"
                        fontWeight="bold"
                        mb={3}
                    >

                        View Generated Timetable

                    </Typography>


                    <TimetableFilter

                        filters={filters}

                        setFilters={setFilters}

                        onLoad={handleLoad}

                        onGenerate={null}

                        onRefresh={handleRefresh}

                    />

                </Paper>


                {/* TOOLBAR */}

                <Paper
                    elevation={3}
                    sx={{
                        p: 2,
                        mb: 3
                    }}
                >

                    <Stack
                        direction={{
                            xs: "column",
                            sm: "row"
                        }}
                        spacing={2}
                        flexWrap="wrap"
                    >

                        {/* VIEW */}

                        <Button
                            variant="contained"
                            startIcon={
                                <VisibilityIcon />
                            }
                            onClick={handleLoad}
                            disabled={loading}
                        >

                            View Timetable

                        </Button>


                        {/* REFRESH */}

                        <Button
                            variant="outlined"
                            startIcon={
                                <RefreshIcon />
                            }
                            onClick={handleRefresh}
                            disabled={loading}
                        >

                            Refresh

                        </Button>


                        {/* DELETE */}

                        <Button
                            variant="contained"
                            color="error"
                            startIcon={
                                <DeleteIcon />
                            }
                            onClick={handleDelete}
                            disabled={
                                loading ||
                                timetable.length === 0
                            }
                        >

                            Delete

                        </Button>


                        {/* PDF */}

                        <Button
                            variant="contained"
                            color="error"
                            startIcon={
                                <PictureAsPdfIcon />
                            }
                            onClick={handlePdf}
                            disabled={
                                loading ||
                                timetable.length === 0
                            }
                        >

                            PDF

                        </Button>


                        {/* EXCEL */}

                        <Button
                            variant="contained"
                            color="success"
                            startIcon={
                                <TableViewIcon />
                            }
                            onClick={handleExcel}
                            disabled={
                                loading ||
                                timetable.length === 0
                            }
                        >

                            Excel

                        </Button>


                        {/* PRINT */}

                        <Button
                            variant="outlined"
                            startIcon={
                                <PrintIcon />
                            }
                            onClick={handlePrint}
                            disabled={
                                loading ||
                                timetable.length === 0
                            }
                        >

                            Print

                        </Button>

                    </Stack>

                </Paper>


                {/* LOADING */}

                {loading && (

                    <Box
                        display="flex"
                        justifyContent="center"
                        alignItems="center"
                        py={5}
                    >

                        <CircularProgress />

                    </Box>

                )}


                {/* TIMETABLE */}

                {!loading &&
                    timetable.length > 0 && (

                        <>

                            <Paper
                                elevation={3}
                                sx={{
                                    p: 2,
                                    mb: 3,
                                    overflow: "auto"
                                }}
                            >

                                <TimetableGrid
                                    timetable={timetable}
                                />

                            </Paper>


                            <Paper
                                elevation={2}
                                sx={{
                                    p: 2,
                                    mb: 3
                                }}
                            >

                                <TimetableLegend />

                            </Paper>

                        </>

                    )}


                {/* EMPTY STATE */}

                {!loading &&
                    timetable.length === 0 && (

                        <Paper
                            elevation={2}
                            sx={{
                                p: 5,
                                textAlign: "center"
                            }}
                        >

                            <Typography
                                variant="h6"
                                color="text.secondary"
                            >

                                No Timetable Found

                            </Typography>

                            <Typography
                                variant="body2"
                                color="text.secondary"
                                mt={1}
                            >

                                Select Academic Year,
                                Course, Semester and
                                Section, then click
                                "View Timetable".

                            </Typography>

                        </Paper>

                    )}


                {/* SNACKBAR */}

                <Snackbar
                    open={openSnackbar}
                    autoHideDuration={4000}
                    onClose={() =>
                        setOpenSnackbar(false)
                    }
                    anchorOrigin={{
                        vertical: "bottom",
                        horizontal: "center"
                    }}
                >

                    <Alert
                        severity={severity}
                        onClose={() =>
                            setOpenSnackbar(false)
                        }
                        variant="filled"
                        sx={{
                            width: "100%"
                        }}
                    >

                        {message}

                    </Alert>

                </Snackbar>

            </Box>

        </DashboardLayout>

    );

};

export default TimetableView;