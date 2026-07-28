import { useState } from "react";

import {
    Paper,
    Typography,
    Table,
    TableHead,
    TableBody,
    TableRow,
    TableCell,
    TableContainer,
    TablePagination,
    Chip
} from "@mui/material";

export default function PreviewTable({

    rows = []

}) {

    const [page, setPage] = useState(0);

    const [rowsPerPage, setRowsPerPage] = useState(10);

    return (

        <Paper sx={{ mt: 3 }}>

            <Typography
                variant="h6"
                fontWeight="bold"
                sx={{ p: 2 }}
            >

                Timetable Preview

            </Typography>

            <TableContainer>

                <Table>

                    <TableHead>

                        <TableRow>

                            <TableCell>
                                <b>Day</b>
                            </TableCell>

                            <TableCell>
                                <b>Time</b>
                            </TableCell>

                            <TableCell>
                                <b>Course</b>
                            </TableCell>

                            <TableCell>
                                <b>Semester</b>
                            </TableCell>

                            <TableCell>
                                <b>Section</b>
                            </TableCell>

                            <TableCell>
                                <b>Subject</b>
                            </TableCell>

                            <TableCell>
                                <b>Faculty</b>
                            </TableCell>

                            <TableCell align="center">
                                <b>Lab</b>
                            </TableCell>

                            <TableCell align="center">
                                <b>Elective</b>
                            </TableCell>

                            <TableCell align="center">
                                <b>Generated</b>
                            </TableCell>

                        </TableRow>

                    </TableHead>

                    <TableBody>

                        {

                            rows.length > 0 ? (

                                rows
                                    .slice(
                                        page * rowsPerPage,
                                        page * rowsPerPage + rowsPerPage
                                    )
                                    .map((slot, index) => (

                                        <TableRow
                                            key={index}
                                            hover
                                        >

                                            <TableCell>

                                                {slot.dayOfWeek}

                                            </TableCell>

                                            <TableCell>

                                                {slot.startTime}

                                                {" - "}

                                                {slot.endTime}

                                            </TableCell>

                                            <TableCell>

                                                {slot.courseName}

                                            </TableCell>

                                            <TableCell>

                                                {slot.semesterNumber}

                                            </TableCell>

                                            <TableCell>

                                                {slot.sectionName}

                                            </TableCell>

                                            <TableCell>

                                                <strong>

                                                    {slot.subjectCode}

                                                </strong>

                                                {" - "}

                                                {slot.subjectName}

                                            </TableCell>

                                            <TableCell>

                                                <strong>

                                                    {slot.facultyCode}

                                                </strong>

                                                {" - "}

                                                {slot.facultyName}

                                            </TableCell>

                                            <TableCell align="center">

                                                {

                                                    slot.laboratory ? (

                                                        <Chip

                                                            label="YES"

                                                            color="primary"

                                                            size="small"

                                                        />

                                                    ) : (

                                                        <Chip

                                                            label="NO"

                                                            size="small"

                                                        />

                                                    )

                                                }

                                            </TableCell>

                                            <TableCell align="center">

                                                {

                                                    slot.elective ? (

                                                        <Chip

                                                            label="YES"

                                                            color="secondary"

                                                            size="small"

                                                        />

                                                    ) : (

                                                        <Chip

                                                            label="NO"

                                                            size="small"

                                                        />

                                                    )

                                                }

                                            </TableCell>

                                            <TableCell align="center">

                                                {

                                                    slot.generatedAutomatically ? (

                                                        <Chip

                                                            label="AUTO"

                                                            color="success"

                                                            size="small"

                                                        />

                                                    ) : (

                                                        <Chip

                                                            label="MANUAL"

                                                            color="warning"

                                                            size="small"

                                                        />

                                                    )

                                                }

                                            </TableCell>

                                        </TableRow>

                                    ))

                            ) : (

                                <TableRow>

                                    <TableCell
                                        align="center"
                                        colSpan={10}
                                    >

                                        No Preview Available

                                    </TableCell>

                                </TableRow>

                            )

                        }

                    </TableBody>

                </Table>

            </TableContainer>

            <TablePagination

                component="div"

                count={rows.length}

                page={page}

                rowsPerPage={rowsPerPage}

                rowsPerPageOptions={[5, 10, 20, 50]}

                onPageChange={(event, newPage) =>
                    setPage(newPage)
                }

                onRowsPerPageChange={(event) => {

                    setRowsPerPage(
                        parseInt(
                            event.target.value,
                            10
                        )
                    );

                    setPage(0);

                }}

            />

        </Paper>

    );

}