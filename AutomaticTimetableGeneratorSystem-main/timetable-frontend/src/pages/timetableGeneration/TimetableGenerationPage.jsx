import { useState } from "react";
import { useNavigate } from "react-router-dom";

import {
    Box,
    CircularProgress,
    Alert,
    Typography,
    Button
} from "@mui/material";

import VisibilityIcon from "@mui/icons-material/Visibility";

import DashboardLayout from "../../components/layout/DashboardLayout";

import TimetableGenerationForm
    from "../../components/timetableGeneration/TimetableGenerationForm";

import GenerationStatistics
    from "../../components/timetableGeneration/GenerationStatistics";

import ValidationPanel
    from "../../components/timetableGeneration/ValidationPanel";

import PreviewTable
    from "../../components/timetableGeneration/PreviewTable";

import GenerationResult
    from "../../components/timetableGeneration/GenerationResult";

import timetableGenerationService
    from "../../services/timetableGenerationService";


export default function TimetableGenerationPage() {

    const navigate = useNavigate();

    const [loading, setLoading] = useState(false);

    const [error, setError] = useState("");

    const [formData, setFormData] = useState({
        academicYear: "",
        courseId: "",
        semesterId: "",
        sectionId: "",
        overwriteExisting: false
    });

    const [statistics, setStatistics] = useState(null);

    const [previewRows, setPreviewRows] = useState([]);

    const [validationMessages, setValidationMessages] = useState([]);

    const [generationResult, setGenerationResult] = useState(null);


    /*
     * GENERATE
     * Generate timetable but DO NOT redirect automatically
     */
    const handleGenerate = async () => {

        try {

            setLoading(true);

            setError("");

            const response =
                await timetableGenerationService.generateTimetable(
                    formData
                );

            console.log(
                "Generate Response:",
                response
            );

            setGenerationResult(response);

            setStatistics(response);

        } catch (error) {

            console.error(
                "Generation Error:",
                error
            );

            setError(
                error.response?.data?.message ||
                "Unable to generate timetable."
            );

        } finally {

            setLoading(false);

        }

    };


    /*
     * VIEW TIMETABLE
     */
    const handleViewTimetable = () => {

        console.log(
            "Navigating to timetable view..."
        );

        navigate("/timetable-view");

    };


    /*
     * PREVIEW
     */
    const handlePreview = async () => {

        try {

            setLoading(true);

            setError("");

            const response =
                await timetableGenerationService.previewTimetable(
                    formData
                );

            setPreviewRows(
                Array.isArray(response)
                    ? response
                    : response?.data || []
            );

        } catch (error) {

            console.error(error);

            setError(
                error.response?.data?.message ||
                "Unable to preview timetable."
            );

        } finally {

            setLoading(false);

        }

    };


    /*
     * VALIDATE
     */
    const handleValidate = async () => {

        try {

            setLoading(true);

            setError("");

            const response =
                await timetableGenerationService.validateGeneration(
                    formData
                );

            setValidationMessages(
                Array.isArray(response)
                    ? response
                    : response?.data || []
            );

        } catch (error) {

            console.error(error);

            setError(
                error.response?.data?.message ||
                "Validation failed."
            );

        } finally {

            setLoading(false);

        }

    };


    /*
     * REGENERATE
     */
    const handleRegenerate = async () => {

        try {

            setLoading(true);

            setError("");

            const response =
                await timetableGenerationService.regenerateTimetable(
                    formData
                );

            console.log(
                "Regenerate Response:",
                response
            );

            setGenerationResult(response);

            setStatistics(response);

        } catch (error) {

            console.error(error);

            setError(
                error.response?.data?.message ||
                "Unable to regenerate timetable."
            );

        } finally {

            setLoading(false);

        }

    };


    /*
     * DELETE
     */
    const handleDelete = async () => {

        try {

            setLoading(true);

            setError("");

            await timetableGenerationService
                .deleteGeneratedTimetable(
                    formData
                );

            setStatistics(null);

            setGenerationResult(null);

            setPreviewRows([]);

            setValidationMessages([]);

        } catch (error) {

            console.error(error);

            setError(
                error.response?.data?.message ||
                "Unable to delete timetable."
            );

        } finally {

            setLoading(false);

        }

    };


    return (

        <DashboardLayout>

            <Box>

                <Typography
                    variant="h4"
                    fontWeight="bold"
                    mb={3}
                >
                    Automatic Timetable Generation
                </Typography>


                {error && (

                    <Alert
                        severity="error"
                        sx={{ mb: 3 }}
                    >
                        {error}
                    </Alert>

                )}


                <TimetableGenerationForm

                    formData={formData}

                    setFormData={setFormData}

                    onPreview={handlePreview}

                    onValidate={handleValidate}

                    onGenerate={handleGenerate}

                    onRegenerate={handleRegenerate}

                    onDelete={handleDelete}

                    loading={loading}

                />


                {/* VIEW TIMETABLE BUTTON */}

                <Box
                    display="flex"
                    justifyContent="flex-end"
                    mt={3}
                >

                    <Button
                        variant="contained"
                        color="info"
                        size="large"
                        startIcon={
                            <VisibilityIcon />
                        }
                        onClick={
                            handleViewTimetable
                        }
                    >
                        View Timetable
                    </Button>

                </Box>


                {loading && (

                    <Box
                        display="flex"
                        justifyContent="center"
                        my={4}
                    >

                        <CircularProgress />

                    </Box>

                )}


                {validationMessages.length > 0 && (

                    <ValidationPanel
                        messages={
                            validationMessages
                        }
                    />

                )}


                {previewRows.length > 0 && (

                    <PreviewTable
                        rows={previewRows}
                    />

                )}


                {statistics && (

                    <GenerationStatistics
                        statistics={statistics}
                    />

                )}


                {generationResult && (

                    <GenerationResult
                        result={generationResult}
                    />

                )}

            </Box>

        </DashboardLayout>

    );

}