import { useEffect, useState } from "react";

import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Button,
    Grid,
    TextField,
    FormControlLabel,
    Switch
} from "@mui/material";

const ConstraintForm = ({

    open,
    editing,
    formData,
    setFormData,
    onSave,
    onClose

}) => {

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

        if (!formData.constraintName?.trim()) {

            temp.constraintName = "Constraint Name is required.";

        }

        if (!formData.constraintKey?.trim()) {

            temp.constraintKey = "Constraint Key is required.";

        }

        if (!formData.constraintValue?.trim()) {

            temp.constraintValue = "Constraint Value is required.";

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

                        ? "Update Timetable Constraint"

                        : "Add Timetable Constraint"

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

                            label="Constraint Name"

                            name="constraintName"

                            value={formData.constraintName}

                            onChange={handleChange}

                            error={!!errors.constraintName}

                            helperText={errors.constraintName}

                        />

                    </Grid>

                    <Grid
                        item
                        xs={12}
                        md={6}
                    >

                        <TextField

                            fullWidth

                            label="Constraint Key"

                            name="constraintKey"

                            value={formData.constraintKey}

                            onChange={handleChange}

                            error={!!errors.constraintKey}

                            helperText={errors.constraintKey}

                        />

                    </Grid>

                    <Grid
                        item
                        xs={12}
                    >

                        <TextField

                            fullWidth

                            label="Constraint Value"

                            name="constraintValue"

                            value={formData.constraintValue}

                            onChange={handleChange}

                            error={!!errors.constraintValue}

                            helperText={errors.constraintValue}

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

                    <Grid
                        item
                        xs={12}
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

};

export default ConstraintForm;