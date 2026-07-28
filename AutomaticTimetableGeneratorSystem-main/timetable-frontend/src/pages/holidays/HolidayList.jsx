import { useEffect, useMemo, useState } from "react";

import {
    Box,
    Typography,
    CircularProgress,
    Alert,
    TextField,
    InputAdornment
} from "@mui/material";

import SearchIcon from "@mui/icons-material/Search";

import DashboardLayout from "../../components/layout/DashboardLayout";
import HolidayTable from "../../components/holidays/HolidayTable";
import HolidayForm from "../../components/holidays/HolidayForm";

import holidayService from "../../services/holidayService";

const HolidayList = () => {

    const [holidays, setHolidays] = useState([]);

    const [loading, setLoading] = useState(true);

    const [error, setError] = useState("");

    const [open, setOpen] = useState(false);

    const [selectedHoliday, setSelectedHoliday] = useState(null);

    const [search, setSearch] = useState("");

    const [formData, setFormData] = useState({
        holidayName: "",
        holidayDate: "",
        holidayType: "",
        description: "",
        active: true
    });

    const loadHolidays = async () => {

        try {

            setLoading(true);

            const response =
                await holidayService.getAllHolidays();

            setHolidays(response);

        } catch (error) {

            console.error(error);

            setError("Unable to load holidays.");

        } finally {

            setLoading(false);

        }

    };

    useEffect(() => {

        loadHolidays();

    }, []);

    const filteredHolidays = useMemo(() => {

        const keyword = search.toLowerCase();

        return holidays.filter((holiday) =>

            holiday.holidayName?.toLowerCase().includes(keyword) ||

            holiday.holidayType?.toLowerCase().includes(keyword) ||

            holiday.description?.toLowerCase().includes(keyword)

        );

    }, [holidays, search]);

    const handleAdd = () => {

        setSelectedHoliday(null);

        setFormData({

            holidayName: "",
            holidayDate: "",
            holidayType: "",
            description: "",
            active: true

        });

        setOpen(true);

    };

    const handleEdit = (holiday) => {

        setSelectedHoliday(holiday);

        setFormData({

            holidayName: holiday.holidayName,
            holidayDate: holiday.holidayDate,
            holidayType: holiday.holidayType,
            description: holiday.description,
            active: holiday.active

        });

        setOpen(true);

    };

    const handleClose = () => {

        setOpen(false);

        setSelectedHoliday(null);

    };

    const handleSave = async () => {

        try {

            if (selectedHoliday) {

                await holidayService.updateHoliday(

                    selectedHoliday.holidayId,

                    formData

                );

            } else {

                await holidayService.createHoliday(

                    formData

                );

            }

            handleClose();

            loadHolidays();

        } catch (error) {

            console.error(error);

        }

    };

    return (

        <DashboardLayout>

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

            </Box>

            <TextField

                fullWidth

                placeholder="Search Holiday..."

                value={search}

                onChange={(e) =>
                    setSearch(e.target.value)
                }

                sx={{ mb: 3 }}

                InputProps={{

                    startAdornment: (

                        <InputAdornment position="start">

                            <SearchIcon />

                        </InputAdornment>

                    )

                }}

            />

            {

                error && (

                    <Alert

                        severity="error"

                        sx={{ mb: 2 }}

                    >

                        {error}

                    </Alert>

                )

            }

            {

                loading ? (

                    <Box

                        display="flex"

                        justifyContent="center"

                        mt={5}

                    >

                        <CircularProgress />

                    </Box>

                ) : (

                    <HolidayTable

                        holidays={filteredHolidays}

                        onEdit={handleEdit}

                        reload={loadHolidays}

                    />

                )

            }

            <HolidayForm

                open={open}

                editing={Boolean(selectedHoliday)}

                formData={formData}

                setFormData={setFormData}

                onSave={handleSave}

                onClose={handleClose}

            />

        </DashboardLayout>

    );

};

export default HolidayList;