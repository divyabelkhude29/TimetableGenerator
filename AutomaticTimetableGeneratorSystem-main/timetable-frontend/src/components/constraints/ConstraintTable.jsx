import { useMemo, useState } from "react";

import {
    Paper,
    Box,
    Typography,
    TextField,
    InputAdornment,
    Table,
    TableHead,
    TableBody,
    TableRow,
    TableCell,
    TableContainer,
    TablePagination,
    IconButton,
    Tooltip,
    Chip,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Button
} from "@mui/material";

import SearchIcon from "@mui/icons-material/Search";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

import constraintService from "../../services/constraintService";

const ConstraintTable = ({

    constraints = [],
    onEdit,
    reload

}) => {

    const [page, setPage] = useState(0);

    const [rowsPerPage, setRowsPerPage] = useState(10);

    const [search, setSearch] = useState("");

    const [deleteOpen, setDeleteOpen] = useState(false);

    const [selectedId, setSelectedId] = useState(null);

    const filteredData = useMemo(() => {

        const keyword = search.toLowerCase();

        return constraints.filter((constraint) =>

            constraint.constraintName?.toLowerCase().includes(keyword) ||

            constraint.constraintKey?.toLowerCase().includes(keyword) ||

            constraint.constraintValue?.toLowerCase().includes(keyword) ||

            constraint.description?.toLowerCase().includes(keyword)

        );

    }, [constraints, search]);

    const openDeleteDialog = (id) => {

        setSelectedId(id);

        setDeleteOpen(true);

    };

    const handleDelete = async () => {

        try {

            await constraintService.deleteConstraint(selectedId);

            reload();

        } catch (error) {

            console.error(error);

        }

        setDeleteOpen(false);

    };

    return (

        <Paper sx={{ mt: 2 }}>

            <Box

                display="flex"

                justifyContent="space-between"

                alignItems="center"

                p={2}

            >

                <Typography

                    variant="h6"

                    fontWeight="bold"

                >

                    Timetable Constraints

                </Typography>

                <TextField

                    size="small"

                    placeholder="Search Constraint..."

                    value={search}

                    onChange={(e) => setSearch(e.target.value)}

                    InputProps={{

                        startAdornment: (

                            <InputAdornment position="start">

                                <SearchIcon />

                            </InputAdornment>

                        )

                    }}

                />

            </Box>

            <TableContainer>

                <Table>

                    <TableHead>

                        <TableRow>

                            <TableCell><b>ID</b></TableCell>

                            <TableCell><b>Name</b></TableCell>

                            <TableCell><b>Key</b></TableCell>

                            <TableCell><b>Value</b></TableCell>

                            <TableCell><b>Description</b></TableCell>

                            <TableCell align="center"><b>Status</b></TableCell>

                            <TableCell align="center"><b>Actions</b></TableCell>

                        </TableRow>

                    </TableHead>

                    <TableBody>

                        {

                            filteredData

                                .slice(

                                    page * rowsPerPage,

                                    page * rowsPerPage + rowsPerPage

                                )

                                .map((constraint) => (

                                    <TableRow

                                        key={constraint.constraintId}

                                        hover

                                    >

                                        <TableCell>

                                            {constraint.constraintId}

                                        </TableCell>

                                        <TableCell>

                                            {constraint.constraintName}

                                        </TableCell>

                                        <TableCell>

                                            {constraint.constraintKey}

                                        </TableCell>

                                        <TableCell>

                                            {constraint.constraintValue}

                                        </TableCell>

                                        <TableCell>

                                            {constraint.description}

                                        </TableCell>

                                        <TableCell align="center">

                                            {

                                                constraint.active ? (

                                                    <Chip

                                                        label="Active"

                                                        color="success"

                                                        size="small"

                                                    />

                                                ) : (

                                                    <Chip

                                                        label="Inactive"

                                                        color="error"

                                                        size="small"

                                                    />

                                                )

                                            }

                                        </TableCell>

                                        <TableCell align="center">

                                            <Tooltip title="Edit">

                                                <IconButton

                                                    color="primary"

                                                    onClick={() =>

                                                        onEdit(constraint)

                                                    }

                                                >

                                                    <EditIcon />

                                                </IconButton>

                                            </Tooltip>

                                            <Tooltip title="Delete">

                                                <IconButton

                                                    color="error"

                                                    onClick={() =>

                                                        openDeleteDialog(

                                                            constraint.constraintId

                                                        )

                                                    }

                                                >

                                                    <DeleteIcon />

                                                </IconButton>

                                            </Tooltip>

                                        </TableCell>

                                    </TableRow>

                                ))

                        }

                        {

                            filteredData.length === 0 && (

                                <TableRow>

                                    <TableCell

                                        colSpan={7}

                                        align="center"

                                    >

                                        No Constraints Found

                                    </TableCell>

                                </TableRow>

                            )

                        }

                    </TableBody>

                </Table>

            </TableContainer>

            <TablePagination

                component="div"

                count={filteredData.length}

                page={page}

                rowsPerPage={rowsPerPage}

                rowsPerPageOptions={[5, 10, 25, 50]}

                onPageChange={(event, newPage) =>

                    setPage(newPage)

                }

                onRowsPerPageChange={(event) => {

                    setRowsPerPage(

                        parseInt(event.target.value, 10)

                    );

                    setPage(0);

                }}

            />

            <Dialog

                open={deleteOpen}

                onClose={() => setDeleteOpen(false)}

            >

                <DialogTitle>

                    Delete Constraint

                </DialogTitle>

                <DialogContent>

                    Are you sure you want to delete this timetable constraint?

                </DialogContent>

                <DialogActions>

                    <Button

                        onClick={() =>

                            setDeleteOpen(false)

                        }

                    >

                        Cancel

                    </Button>

                    <Button

                        variant="contained"

                        color="error"

                        onClick={handleDelete}

                    >

                        Delete

                    </Button>

                </DialogActions>

            </Dialog>

        </Paper>

    );

};

export default ConstraintTable;