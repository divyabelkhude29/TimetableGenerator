import {
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    IconButton,
    Chip,
    Tooltip,
    Typography
} from "@mui/material";

import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

const AllocationTable = ({
    allocations = [],
    onEdit,
    onDelete
}) => {

    return (

        <TableContainer
            component={Paper}
            elevation={3}
            sx={{ borderRadius: 3 }}
        >

            <Table>

                <TableHead>

                    <TableRow>

                        <TableCell>
                            <strong>Faculty</strong>
                        </TableCell>

                        <TableCell>
                            <strong>Subject</strong>
                        </TableCell>

                        <TableCell>
                            <strong>Section</strong>
                        </TableCell>

                        <TableCell>
                            <strong>Semester</strong>
                        </TableCell>

                        <TableCell>
                            <strong>Academic Year</strong>
                        </TableCell>

                        <TableCell align="center">
                            <strong>Status</strong>
                        </TableCell>

                        <TableCell align="center">
                            <strong>Actions</strong>
                        </TableCell>

                    </TableRow>

                </TableHead>

                <TableBody>

                    {
                        allocations.length === 0 ? (

                            <TableRow>

                                <TableCell
                                    colSpan={7}
                                    align="center"
                                >

                                    <Typography
                                        color="text.secondary"
                                        py={2}
                                    >
                                        No Faculty Subject Allocations Found
                                    </Typography>

                                </TableCell>

                            </TableRow>

                        ) : (

                            allocations.map((allocation) => (

                                <TableRow
                                    hover
                                    key={allocation.allocationId}
                                >

                                    <TableCell>
                                        {allocation.facultyName}
                                    </TableCell>

                                    <TableCell>
                                        {allocation.subjectName}
                                    </TableCell>

                                    <TableCell>
                                        {allocation.sectionName}
                                    </TableCell>

                                    <TableCell>
                                        Semester {allocation.semesterNumber}
                                    </TableCell>

                                    <TableCell>
                                        {allocation.academicYear}
                                    </TableCell>

                                    <TableCell align="center">

                                        <Chip
                                            label={
                                                allocation.active
                                                    ? "Active"
                                                    : "Inactive"
                                            }
                                            color={
                                                allocation.active
                                                    ? "success"
                                                    : "error"
                                            }
                                            size="small"
                                        />

                                    </TableCell>

                                    <TableCell align="center">

                                        <Tooltip title="Edit">

                                            <IconButton
                                                color="primary"
                                                onClick={() =>
                                                    onEdit(allocation)
                                                }
                                            >
                                                <EditIcon />
                                            </IconButton>

                                        </Tooltip>

                                        <Tooltip title="Delete">

                                            <IconButton
                                                color="error"
                                                onClick={() =>
                                                    onDelete(
                                                        allocation
                                                            .allocationId
                                                    )
                                                }
                                            >
                                                <DeleteIcon />
                                            </IconButton>

                                        </Tooltip>

                                    </TableCell>

                                </TableRow>

                            ))

                        )

                    }

                </TableBody>

            </Table>

        </TableContainer>

    );

};

export default AllocationTable;