import { useEffect, useMemo, useState } from "react";

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

import DashboardLayout from "../../components/layout/DashboardLayout";
import HolidayTable from "../../components/holidays/HolidayTable";
import HolidayForm from "../../components/holidays/HolidayForm";

import holidayService from "../../services/holidayService";


const initialFormData = {
    holidayName: "",
    holidayDate: "",
    holidayType: "",
    description: "",
    active: true
};


const HolidayList = () => {

    const [holidays, setHolidays] = useState([]);

    const [loading, setLoading] = useState(true);

    const [error, setError] = useState("");

    const [open, setOpen] = useState(false);

    const [selectedHoliday, setSelectedHoliday] = useState(null);

    const [search, setSearch] = useState("");

    const [formData, setFormData] = useState({
        ...initialFormData
    });

    const [saving, setSaving] = useState(false);


    /*
     * Load all holidays
     */
    const loadHolidays = async () => {

        try {

            setLoading(true);

            setError("");

            const response =
                await holidayService.getAllHolidays();

            /*
             * Make sure response is an array.
             */
            const holidayList =
                Array.isArray(response)
                    ? response
                    : response?.content || [];

            setHolidays(holidayList);

        } catch (error) {

            console.error(
                "Unable to load holidays:",
                error
            );

            setError(
                error?.response?.data?.message ||
                error?.response?.data?.error ||
                "Unable to load holidays."
            );

        } finally {

            setLoading(false);

        }

    };


    /*
     * Load holidays when page opens
     */
    useEffect(() => {

        loadHolidays();

    }, []);


    /*
     * Search filter
     */
    const filteredHolidays = useMemo(() => {

        const keyword =
            search.trim().toLowerCase();

        if (!keyword) {

            return holidays;

        }

        return holidays.filter(
            (holiday) =>

                holiday.holidayName
                    ?.toLowerCase()
                    .includes(keyword) ||

                holiday.holidayType
                    ?.toLowerCase()
                    .includes(keyword) ||

                holiday.description
                    ?.toLowerCase()
                    .includes(keyword) ||

                holiday.holidayDate
                    ?.toString()
                    .toLowerCase()
                    .includes(keyword)

        );

    }, [
        holidays,
        search
    ]);


    /*
     * ADD HOLIDAY
     */
    const handleAdd = () => {

        setSelectedHoliday(null);

        setFormData({
            ...initialFormData
        });

        setError("");

        setOpen(true);

    };


    /*
     * EDIT HOLIDAY
     */
    const handleEdit = (holiday) => {

        setSelectedHoliday(holiday);

        setFormData({

            holidayName:
                holiday.holidayName || "",

            holidayDate:
                holiday.holidayDate || "",

            holidayType:
                holiday.holidayType || "",

            description:
                holiday.description || "",

            active:
                holiday.active ??
                true

        });

        setError("");

        setOpen(true);

    };


    /*
     * CLOSE FORM
     */
    const handleClose = () => {

        if (saving) {

            return;

        }

        setOpen(false);

        setSelectedHoliday(null);

        setFormData({
            ...initialFormData
        });

    };


    /*
     * SAVE / UPDATE HOLIDAY
     */
    const handleSave = async () => {

        try {

            setSaving(true);

            setError("");


            /*
             * Prepare payload
             */
            const payload = {

                holidayName:
                    formData.holidayName.trim(),

                holidayDate:
                    formData.holidayDate,

                holidayType:
                    formData.holidayType,

                description:
                    formData.description?.trim() || "",

                active:
                    formData.active

            };


            /*
             * UPDATE
             */
            if (selectedHoliday) {

                await holidayService.updateHoliday(

                    selectedHoliday.holidayId,

                    payload

                );

            }

            /*
             * CREATE / ADD
             */
            else {

                await holidayService.createHoliday(

                    payload

                );

            }


            /*
             * Close dialog
             */
            setOpen(false);

            setSelectedHoliday(null);

            setFormData({
                ...initialFormData
            });


            /*
             * Reload table
             */
            await loadHolidays();


        } catch (error) {

            console.error(
                "Holiday save error:",
                error
            );

            setError(

                error?.response?.data?.message ||

                error?.response?.data?.error ||

                error?.message ||

                "Unable to save holiday."

            );

        } finally {

            setSaving(false);

        }

    };


    return (

        <DashboardLayout>

            {/* ================= HEADER ================= */}

            <Box

                display="flex"

                justifyContent="space-between"

                alignItems="center"

                mb={3}

            >

                <Typography

                    variant="h4"

                    fontWeight="bold"

                >

                    Holiday Management

                </Typography>


                {/* ================= ADD BUTTON ================= */}

                <Button

                    variant="contained"

                    color="primary"

                    startIcon={
                        <AddIcon />
                    }

                    onClick={
                        handleAdd
                    }

                >

                    Add Holiday

                </Button>

            </Box>


            {/* ================= SEARCH ================= */}

            <TextField

                fullWidth

                placeholder="Search Holiday..."

                value={search}

                onChange={(e) =>
                    setSearch(
                        e.target.value
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


            {/* ================= ERROR ================= */}

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


            {/* ================= TABLE ================= */}

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

                <HolidayTable

                    holidays={
                        filteredHolidays
                    }

                    onEdit={
                        handleEdit
                    }

                    reload={
                        loadHolidays
                    }

                />

            )}


            {/* ================= FORM ================= */}

            <HolidayForm

                open={
                    open
                }

                editing={
                    Boolean(
                        selectedHoliday
                    )
                }

                formData={
                    formData
                }

                setFormData={
                    setFormData
                }

                onSave={
                    handleSave
                }

                onClose={
                    handleClose
                }

                saving={
                    saving
                }

            />

        </DashboardLayout>

    );

};


export default HolidayList;