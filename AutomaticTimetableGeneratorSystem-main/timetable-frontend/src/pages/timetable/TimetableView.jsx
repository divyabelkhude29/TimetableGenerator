import { useState } from "react";
import {
    Box,
    Paper,
    Typography,
    Snackbar,
    Alert
} from "@mui/material";

import TimetableFilter from "../../components/timetable/TimetableFilter";
import TimetableToolbar from "../../components/timetable/TimetableToolbar";
import TimetableGrid from "../../components/timetable/TimetableGrid";
import TimetableLegend from "../../components/timetable/TimetableLegend";

import timetableService from "../../services/timetableService";

const TimetableView = () => {

    const [filters, setFilters] = useState({
        academicYear: "",
        courseId: "",
        semesterId: "",
        sectionId: ""
    });

    const [timetable, setTimetable] = useState([]);

    const [loading, setLoading] = useState(false);

    const [message, setMessage] = useState("");

    const [severity, setSeverity] = useState("success");

    const [open, setOpen] = useState(false);

    const showMessage = (msg, type = "success") => {

        setMessage(msg);

        setSeverity(type);

        setOpen(true);

    };

    /*
     * Load timetable
     */
    const handleLoad = async () => {

        try {

            setLoading(true);

            const data =
                await timetableService.getTimetable(
                    filters.academicYear,
                    filters.courseId,
                    filters.semesterId,
                    filters.sectionId
                );

            setTimetable(data);

            showMessage("Timetable loaded successfully.");

        } catch (error) {

            console.error(error);

            showMessage(
                "Unable to load timetable.",
                "error"
            );

        } finally {

            setLoading(false);

        }

    };

    /*
     * Generate timetable
     */
    const handleGenerate = async () => {

        try {

            setLoading(true);

            const response =
                await timetableService.generateTimetable(
                    {
                        academicYear:
                            filters.academicYear,

                        courseId:
                            filters.courseId,

                        semesterId:
                            filters.semesterId,

                        sectionId:
                            filters.sectionId,

                        overwriteExisting:
                            true
                    }
                );

            showMessage(response.message);

            await handleLoad();

        } catch (error) {

            console.error(error);

            showMessage(
                "Generation failed.",
                "error"
            );

        } finally {

            setLoading(false);

        }

    };

    /*
     * Refresh
     */
    const handleRefresh = () => {

        handleLoad();

    };

    /*
     * Delete timetable
     */
    const handleDelete = async () => {

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

            console.error(error);

            showMessage(
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

        try {

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

            link.download = "Timetable.pdf";

            link.click();

        } catch (error) {

            console.error(error);

        }

    };

    /*
     * Export Excel
     */
    const handleExcel = async () => {

        try {

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

            link.download = "Timetable.xlsx";

            link.click();

        } catch (error) {

            console.error(error);

        }

    };

    return (

        <Box p={3}>

            <Typography
                variant="h4"
                gutterBottom>

                Timetable Management

            </Typography>

            <Paper
                sx={{
                    p: 3,
                    mb: 3
                }}
            >

                <TimetableFilter
                    filters={filters}
                    setFilters={setFilters}
                    onLoad={handleLoad}
                    onGenerate={handleGenerate}
                    onRefresh={handleRefresh}
                />

            </Paper>

            <TimetableToolbar
                loading={loading}
                onGenerate={handleGenerate}
                onRefresh={handleRefresh}
                onDelete={handleDelete}
                onExportPdf={handlePdf}
                onExportExcel={handleExcel}
                onPrint={() => window.print()}
            />

            <TimetableGrid
                timetable={timetable}
            />

            <TimetableLegend />

            <Snackbar
                open={open}
                autoHideDuration={3000}
                onClose={() => setOpen(false)}
            >

                <Alert
                    severity={severity}
                    onClose={() => setOpen(false)}
                >

                    {message}

                </Alert>

            </Snackbar>

        </Box>

    );

};

export default TimetableView;