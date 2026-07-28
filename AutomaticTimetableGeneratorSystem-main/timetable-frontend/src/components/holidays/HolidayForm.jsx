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
    Switch
} from "@mui/material";

const HOLIDAY_TYPES = [
    "NATIONAL",
    "STATE",
    "COLLEGE",
    "FESTIVAL",
    "OTHER"
];

export default function HolidayForm({

    open,
    editing,
    formData,
    setFormData,
    onSave,
    onClose

}) {

    const [errors, setErrors] = useState({});

    useEffect(() => {

        if (!open) {

            setErrors({});

        }

    }, [open]);

    const handleChange = (event) => {

        const { name, value, checked, type } = event.target;

        setFormData({

            ...formData,

            [name]:
                type === "checkbox"
                    ? checked
                    : value

        });

    };

    const validate = () => {

        let temp = {};

        if (!formData.holidayName?.trim()) {

            temp.holidayName = "Holiday Name is required.";

        }

        if (!formData.holidayDate) {

            temp.holidayDate = "Holiday Date is required.";

        }

        if (!formData.holidayType) {

            temp.holidayType = "Holiday Type is required.";

        }

        setErrors(temp);

        return Object.keys(temp).length === 0;

    };

    const handleSubmit = () => {

        if (validate()) {

            onSave();

        }

    };

    return (

        <Dialog
            open={open}
            onClose={onClose}
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

                <Grid
                    container
                    spacing={3}
                    sx={{ mt: 1 }}
                >

                    <Grid
                        item
                        xs={12}
                        md={6}
                    >

                        <TextField

                            fullWidth

                            label="Holiday Name"

                            name="holidayName"

                            value={formData.holidayName}

                            onChange={handleChange}

                            error={!!errors.holidayName}

                            helperText={errors.holidayName}

                        />

                    </Grid>

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

                            value={formData.holidayDate}

                            onChange={handleChange}

                            error={!!errors.holidayDate}

                            helperText={errors.holidayDate}

                            InputLabelProps={{
                                shrink: true
                            }}

                        />

                    </Grid>

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

                            value={formData.holidayType}

                            onChange={handleChange}

                            error={!!errors.holidayType}

                            helperText={errors.holidayType}

                        >

                            {

                                HOLIDAY_TYPES.map((type) => (

                                    <MenuItem
                                        key={type}
                                        value={type}
                                    >

                                        {type}

                                    </MenuItem>

                                ))

                            }

                        </TextField>

                    </Grid>

                    <Grid
                        item
                        xs={12}
                        md={6}
                    >

                        <FormControlLabel

                            control={

                                <Switch

                                    checked={formData.active}

                                    onChange={handleChange}

                                    name="active"

                                />

                            }

                            label="Active"

                        />

                    </Grid>

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

                            value={formData.description}

                            onChange={handleChange}

                        />

                    </Grid>

                </Grid>

            </DialogContent>

            <DialogActions>

                <Button
                    onClick={onClose}
                >

                    Cancel

                </Button>

                <Button

                    variant="contained"

                    onClick={handleSubmit}

                >

                    {

                        editing

                            ? "Update"

                            : "Save"

                    }

                </Button>

            </DialogActions>

        </Dialog>

    );

}