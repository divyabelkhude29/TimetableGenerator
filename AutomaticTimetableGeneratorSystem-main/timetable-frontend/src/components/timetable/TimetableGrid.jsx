import {
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Typography
} from "@mui/material";

import TimetableCell from "./TimetableCell";

const DAYS = [
    "MONDAY",
    "TUESDAY",
    "WEDNESDAY",
    "THURSDAY",
    "FRIDAY",
    "SATURDAY"
];

const TimetableGrid = ({ timetable = [] }) => {

    if (!timetable.length) {
        return (
            <Paper sx={{ p: 4 }}>
                <Typography align="center">
                    No timetable available.
                </Typography>
            </Paper>
        );
    }

    /*
     * Get unique time slots
     */
    const timeSlots = [...new Map(
        timetable.map(slot => [
            slot.timeSlotId,
            {
                timeSlotId: slot.timeSlotId,
                startTime: slot.startTime,
                endTime: slot.endTime
            }
        ])
    ).values()]
        .sort((a, b) => a.timeSlotId - b.timeSlotId);

    /*
     * Find one slot
     */
    const findSlot = (day, timeSlotId) => {

        return timetable.find(
            slot =>
                slot.dayOfWeek === day &&
                slot.timeSlotId === timeSlotId
        );

    };

    return (

        <TableContainer
            component={Paper}
            sx={{ mt: 3 }}
        >

            <Table>

                <TableHead>

                    <TableRow>

                        <TableCell
                            sx={{
                                fontWeight: "bold",
                                width: 130
                            }}
                        >
                            Day
                        </TableCell>

                        {
                            timeSlots.map(slot => (

                                <TableCell
                                    key={slot.timeSlotId}
                                    align="center"
                                    sx={{
                                        fontWeight: "bold",
                                        minWidth: 170
                                    }}
                                >

                                    {slot.startTime}
                                    <br />
                                    -
                                    <br />
                                    {slot.endTime}

                                </TableCell>

                            ))
                        }

                    </TableRow>

                </TableHead>

                <TableBody>

                    {
                        DAYS.map(day => (

                            <TableRow key={day}>

                                <TableCell
                                    sx={{
                                        fontWeight: "bold"
                                    }}
                                >
                                    {day}
                                </TableCell>

                                {
                                    timeSlots.map(slot => (

                                        <TableCell
                                            key={`${day}-${slot.timeSlotId}`}
                                            sx={{
                                                verticalAlign: "top",
                                                p: 1
                                            }}
                                        >

                                            <TimetableCell
                                                slot={
                                                    findSlot(
                                                        day,
                                                        slot.timeSlotId
                                                    )
                                                }
                                            />

                                        </TableCell>

                                    ))
                                }

                            </TableRow>

                        ))
                    }

                </TableBody>

            </Table>

        </TableContainer>

    );

};

export default TimetableGrid;