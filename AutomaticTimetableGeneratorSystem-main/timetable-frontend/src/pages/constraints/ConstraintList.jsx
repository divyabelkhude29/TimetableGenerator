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

import SearchIcon from "@mui/icons-material/Search";
import AddIcon from "@mui/icons-material/Add";

import DashboardLayout from "../../components/layout/DashboardLayout";
import ConstraintTable from "../../components/constraints/ConstraintTable";
import ConstraintForm from "../../components/constraints/ConstraintForm";

import constraintService from "../../services/constraintService";

const ConstraintList = () => {

    const [constraints, setConstraints] = useState([]);

    const [loading, setLoading] = useState(true);

    const [error, setError] = useState("");

    const [search, setSearch] = useState("");

    const [open, setOpen] = useState(false);

    const [editing, setEditing] = useState(false);

    const initialForm = {

        constraintId: null,

        constraintName: "",

        constraintKey: "",

        constraintValue: "",

        description: "",

        active: true

    };

    const [formData, setFormData] = useState(initialForm);

    const loadConstraints = async () => {

        try {

            setLoading(true);

            const response =
                await constraintService.getAllConstraints();

            setConstraints(response);

        }

        catch (error) {

            console.error(error);

            setError("Unable to load timetable constraints.");

        }

        finally {

            setLoading(false);

        }

    };

    useEffect(() => {

        loadConstraints();

    }, []);

    const filteredConstraints = useMemo(() => {

        const keyword = search.toLowerCase();

        return constraints.filter((constraint) =>

            constraint.constraintName
                ?.toLowerCase()
                .includes(keyword)

            ||

            constraint.constraintKey
                ?.toLowerCase()
                .includes(keyword)

            ||

            constraint.constraintValue
                ?.toLowerCase()
                .includes(keyword)

            ||

            constraint.description
                ?.toLowerCase()
                .includes(keyword)

        );

    }, [constraints, search]);

    const handleAdd = () => {

        setEditing(false);

        setFormData(initialForm);

        setOpen(true);

    };

    const handleEdit = (constraint) => {

        setEditing(true);

        setFormData({

            constraintId: constraint.constraintId,

            constraintName: constraint.constraintName,

            constraintKey: constraint.constraintKey,

            constraintValue: constraint.constraintValue,

            description: constraint.description,

            active: constraint.active

        });

        setOpen(true);

    };

    const handleClose = () => {

        setOpen(false);

        setEditing(false);

        setFormData(initialForm);

    };

    const handleSave = async () => {

        try {

            if (editing) {

                await constraintService.updateConstraint(

                    formData.constraintId,

                    formData

                );

            }

            else {

                await constraintService.createConstraint(

                    formData

                );

            }

            handleClose();

            loadConstraints();

        }

        catch (error) {

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

                    Timetable Constraints

                </Typography>

                <Button

                    variant="contained"

                    startIcon={<AddIcon />}

                    onClick={handleAdd}

                >

                    Add Constraint

                </Button>

            </Box>

            <TextField

                fullWidth

                placeholder="Search Constraint Name, Key or Value..."

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

                )

                :

                (

                    <ConstraintTable

                        constraints={filteredConstraints}

                        onEdit={handleEdit}

                        reload={loadConstraints}

                    />

                )

            }

            <ConstraintForm

                open={open}

                editing={editing}

                formData={formData}

                setFormData={setFormData}

                onSave={handleSave}

                onClose={handleClose}

            />

        </DashboardLayout>

    );

};

export default ConstraintList;