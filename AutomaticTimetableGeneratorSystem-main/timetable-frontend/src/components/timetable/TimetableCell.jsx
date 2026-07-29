import {
    Box,
    Typography
} from "@mui/material";

const TimetableCell = ({ slot }) => {

    if (!slot) {

        return (

            <Box
                sx={{
                    minHeight: 90
                }}
            />

        );

    }

    return (

        <Box
            sx={{
                p: 1,
                borderRadius: 2,
                bgcolor: "#E3F2FD",
                border: "1px solid #90CAF9",
                minHeight: 90
            }}
        >

            <Typography
                variant="subtitle2"
                fontWeight="bold"
            >
                {slot.subjectName}
            </Typography>

            <Typography
                variant="body2"
            >
                {slot.facultyName}
            </Typography>

            <Typography
                variant="body2"
            >
                {slot.classroomName}
            </Typography>

            <Typography
                variant="caption"
            >
                {slot.startTime} - {slot.endTime}
            </Typography>

        </Box>

    );

};

export default TimetableCell;