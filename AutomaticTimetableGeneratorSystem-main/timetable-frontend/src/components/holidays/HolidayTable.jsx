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

import holidayService from "../../services/holidayService";

const HolidayTable = ({

    holidays = [],
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

        return holidays.filter((holiday) =>

            holiday.holidayName?.toLowerCase().includes(keyword) ||

            holiday.holidayType?.toLowerCase().includes(keyword) ||

            holiday.description?.toLowerCase().includes(keyword)

        );

    }, [holidays, search]);

    const openDeleteDialog = (id) => {

        setSelectedId(id);

        setDeleteOpen(true);

    };

    const handleDelete = async () => {

        try {

            await holidayService.deleteHoliday(selectedId);

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

                    Holiday List

                </Typography>

                <TextField

                    size="small"

                    placeholder="Search Holiday..."

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

                            <TableCell>

                                <b>ID</b>

                            </TableCell>

                            <TableCell>

                                <b>Name</b>

                            </TableCell>

                            <TableCell>

                                <b>Date</b>

                            </TableCell>

                            <TableCell>

                                <b>Type</b>

                            </TableCell>

                            <TableCell>

                                <b>Description</b>

                            </TableCell>

                            <TableCell align="center">

                                <b>Status</b>

                            </TableCell>

                            <TableCell align="center">

                                <b>Actions</b>

                            </TableCell>

                        </TableRow>

                    </TableHead>

                    <TableBody>

                        {

                            filteredData

                                .slice(

                                    page * rowsPerPage,

                                    page * rowsPerPage + rowsPerPage

                                )

                                .map((holiday) => (

                                    <TableRow

                                        key={holiday.holidayId}

                                        hover

                                    >

                                        <TableCell>

                                            {holiday.holidayId}

                                        </TableCell>

                                        <TableCell>

                                            {holiday.holidayName}

                                        </TableCell>

                                        <TableCell>

                                            {holiday.holidayDate}

                                        </TableCell>

                                        <TableCell>

                                            {holiday.holidayType}

                                        </TableCell>

                                        <TableCell>

                                            {holiday.description}

                                        </TableCell>

                                        <TableCell align="center">

                                            {

                                                holiday.active ? (

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

                                                        onEdit(holiday)

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

                                                            holiday.holidayId

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

                                        No Holidays Found

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

                    Delete Holiday

                </DialogTitle>

                <DialogContent>

                    Are you sure you want to delete this holiday?

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

                        color="error"

                        variant="contained"

                        onClick={handleDelete}

                    >

                        Delete

                    </Button>

                </DialogActions>

            </Dialog>

        </Paper>

    );

};

export default HolidayTable;