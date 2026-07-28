import { useState } from "react";
import { useNavigate } from "react-router-dom";

import {
    Box,
    CircularProgress,
    Alert,
    Typography
} from "@mui/material";

import DashboardLayout from "../../components/layout/DashboardLayout";

import TimetableGenerationForm from "../../components/timetableGeneration/TimetableGenerationForm";
import GenerationStatistics from "../../components/timetableGeneration/GenerationStatistics";
import ValidationPanel from "../../components/timetableGeneration/ValidationPanel";
import PreviewTable from "../../components/timetableGeneration/PreviewTable";
import GenerationResult from "../../components/timetableGeneration/GenerationResult";

import timetableGenerationService from "../../services/timetableGenerationService";

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
     * Generate Timetable
     */
    const handleGenerate = async () => {

        try {

            setLoading(true);
            setError("");

            const response =
                await timetableGenerationService.generateTimetable(formData);

            setGenerationResult(response);
            setStatistics(response);

            if (response.success) {

                setTimeout(() => {
                    navigate("/timetable-view");
                }, 1000);

            }

        }
        catch (error) {

            console.error(error);

            setError(
                error.response?.data?.message ||
                "Unable to generate timetable."
            );

        }
        finally {

            setLoading(false);

        }

    };

    /*
     * Preview Timetable
     */
    const handlePreview = async () => {

        try {

            setLoading(true);
            setError("");

            const response =
                await timetableGenerationService.previewTimetable(formData);

            setPreviewRows(response);

        }
        catch (error) {

            console.error(error);

            setError(
                error.response?.data?.message ||
                "Unable to preview timetable."
            );

        }
        finally {

            setLoading(false);

        }

    };

    /*
     * Validate Timetable
     */
    const handleValidate = async () => {

        try {

            setLoading(true);
            setError("");

            const response =
                await timetableGenerationService.validateGeneration(formData);

            setValidationMessages(response);

        }
        catch (error) {

            console.error(error);

            setError(
                error.response?.data?.message ||
                "Validation failed."
            );

        }
        finally {

            setLoading(false);

        }

    };

    /*
     * Regenerate Timetable
     */
    const handleRegenerate = async () => {

        try {

            setLoading(true);
            setError("");

            const response =
                await timetableGenerationService.regenerateTimetable(formData);

            setGenerationResult(response);
            setStatistics(response);

            if (response.success) {

                setTimeout(() => {
                    navigate("/timetable-view");
                }, 1000);

            }

        }
        catch (error) {

            console.error(error);

            setError(
                error.response?.data?.message ||
                "Unable to regenerate timetable."
            );

        }
        finally {

            setLoading(false);

        }

    };

    /*
     * Delete Generated Timetable
     */
    const handleDelete = async () => {

        try {

            setLoading(true);
            setError("");

            await timetableGenerationService.deleteGeneratedTimetable(formData);

            setStatistics(null);
            setGenerationResult(null);
            setPreviewRows([]);
            setValidationMessages([]);

        }
        catch (error) {

            console.error(error);

            setError(
                error.response?.data?.message ||
                "Unable to delete timetable."
            );

        }
        finally {

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
                        messages={validationMessages}
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