import { useEffect, useState } from "react";

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
    Alert
} from "@mui/material";


const HOLIDAY_TYPES = [

    "NATIONAL",

    "STATE",

    "COLLEGE",

    "FESTIVAL",

    "OTHER"

];


const HolidayForm = ({

    open,

    editing,

    formData,

    setFormData,

    onSave,

    onClose,

    saving = false

}) => {


    const [errors, setErrors] =
        useState({});


    /*
     * Clear validation
     * when dialog closes
     */
    useEffect(() => {

        if (!open) {

            setErrors({});

        }

    }, [
        open
    ]);


    /*
     * Handle input changes
     */
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


        /*
         * Remove field error
         */
        setErrors(

            previous => ({

                ...previous,

                [name]: ""

            })

        );

    };


    /*
     * Validate form
     */
    const validate = () => {

        const temp = {};


        /*
         * Holiday name
         */
        if (
            !formData.holidayName?.trim()
        ) {

            temp.holidayName =
                "Holiday Name is required.";

        }


        /*
         * Holiday date
         */
        if (
            !formData.holidayDate
        ) {

            temp.holidayDate =
                "Holiday Date is required.";

        }


        /*
         * Holiday type
         */
        if (
            !formData.holidayType
        ) {

            temp.holidayType =
                "Holiday Type is required.";

        }


        setErrors(
            temp
        );


        return (
            Object.keys(temp).length === 0
        );

    };


    /*
     * Submit
     */
    const handleSubmit = () => {

        if (saving) {

            return;

        }


        if (
            validate()
        ) {

            onSave();

        }

    };


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

            <DialogTitle>

                {

                    editing

                        ? "Update Holiday"

                        : "Add Holiday"

                }

            </DialogTitle>


            <DialogContent>


                {/* ================= ERROR ================= */}

                {Object.keys(errors).length > 0 && (

                    <Alert

                        severity="error"

                        sx={{
                            mt: 2,
                            mb: 1
                        }}

                    >

                        Please fill in all required fields.

                    </Alert>

                )}


                <Grid

                    container

                    spacing={3}

                    sx={{
                        mt: 1
                    }}

                >


                    {/* ================= HOLIDAY NAME ================= */}

                    <Grid

                        item

                        xs={12}

                        md={6}

                    >

                        <TextField

                            fullWidth

                            label="Holiday Name"

                            name="holidayName"

                            value={
                                formData.holidayName
                            }

                            onChange={
                                handleChange
                            }

                            error={
                                !!errors.holidayName
                            }

                            helperText={
                                errors.holidayName
                            }

                            required

                        />

                    </Grid>


                    {/* ================= HOLIDAY DATE ================= */}

                    <Grid

                        item

                        xs={12}

                        md={6}

                    >

                        <TextField

                            fullWidth

                            type="date"

                            label="Holiday Date"

                            name="holidayDate"

                            value={
                                formData.holidayDate
                            }

                            onChange={
                                handleChange
                            }

                            error={
                                !!errors.holidayDate
                            }

                            helperText={
                                errors.holidayDate
                            }

                            InputLabelProps={{
                                shrink: true
                            }}

                            required

                        />

                    </Grid>


                    {/* ================= HOLIDAY TYPE ================= */}

                    <Grid

                        item

                        xs={12}

                        md={6}

                    >

                        <TextField

                            select

                            fullWidth

                            label="Holiday Type"

                            name="holidayType"

                            value={
                                formData.holidayType
                            }

                            onChange={
                                handleChange
                            }

                            error={
                                !!errors.holidayType
                            }

                            helperText={
                                errors.holidayType
                            }

                            required

                        >

                            <MenuItem value="">

                                Select Holiday Type

                            </MenuItem>


                            {

                                HOLIDAY_TYPES.map(

                                    (type) => (

                                        <MenuItem

                                            key={
                                                type
                                            }

                                            value={
                                                type
                                            }

                                        >

                                            {type}

                                        </MenuItem>

                                    )

                                )

                            }

                        </TextField>

                    </Grid>


                    {/* ================= ACTIVE ================= */}

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
                                        handleChange
                                    }

                                    name="active"

                                />

                            }

                            label="Active"

                        />

                    </Grid>


                    {/* ================= DESCRIPTION ================= */}

                    <Grid

                        item

                        xs={12}

                    >

                        <TextField

                            fullWidth

                            multiline

                            rows={4}

                            label="Description"

                            name="description"

                            value={
                                formData.description
                            }

                            onChange={
                                handleChange
                            }

                            placeholder="Enter holiday description"

                        />

                    </Grid>


                </Grid>

            </DialogContent>


            {/* ================= BUTTONS ================= */}

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
                        handleSubmit
                    }

                    disabled={
                        saving
                    }

                >

                    {

                        saving

                            ? "Saving..."

                            : editing

                                ? "Update"

                                : "Save"

                    }

                </Button>

            </DialogActions>

        </Dialog>

    );

};


export default HolidayForm;