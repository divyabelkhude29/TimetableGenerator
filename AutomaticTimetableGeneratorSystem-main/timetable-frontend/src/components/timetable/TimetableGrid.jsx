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

    if (!Array.isArray(timetable) || timetable.length === 0) {
        return (
            <Paper sx={{ p: 4 }}>
                <Typography align="center">
                    No timetable available.
                </Typography>
            </Paper>
        );
    }

    /*
     * Unique Time Slots
     */
    const timeSlots = [
        ...new Map(
            timetable.map(item => [
                item.timeSlotId,
                {
                    timeSlotId: item.timeSlotId,
                    startTime: item.startTime,
                    endTime: item.endTime
                }
            ])
        ).values()
    ].sort((a, b) => a.timeSlotId - b.timeSlotId);

    /*
     * Normalize day
     */
    const normalizeDay = (day) => {

        if (!day) return "";

        return day.toUpperCase().trim();

    };

    /*
     * Find slot
     */
    const findSlot = (day, timeSlotId) => {

        return timetable.find(item =>

            normalizeDay(item.dayOfWeek) === day &&
            item.timeSlotId === timeSlotId

        );

    };

    return (

        <TableContainer
            component={Paper}
            sx={{
                mt: 2,
                overflowX: "auto"
            }}
        >

            <Table>

                <TableHead>

                    <TableRow>

                        <TableCell
                            align="center"
                            sx={{
                                fontWeight: "bold",
                                minWidth: 120
                            }}
                        >
                            Day / Time
                        </TableCell>

                        {

                            timeSlots.map(slot => (

                                <TableCell
                                    key={slot.timeSlotId}
                                    align="center"
                                    sx={{
                                        fontWeight: "bold",
                                        minWidth: 180
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
                                                padding: 1
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