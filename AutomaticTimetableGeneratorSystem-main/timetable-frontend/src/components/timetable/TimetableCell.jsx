import {
    Paper,
    Typography,
    Box,
    Chip
} from "@mui/material";

const TimetableCell = ({ slot }) => {

    if (!slot) {
        return (
            <Paper
                elevation={1}
                sx={{
                    minHeight: 110,
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                    backgroundColor: "#fafafa"
                }}
            >
                <Typography
                    variant="body2"
                    color="text.secondary"
                >
                    --
                </Typography>
            </Paper>
        );
    }

    let chipColor = "primary";
    let chipLabel = "Theory";

    if (slot.laboratory) {
        chipColor = "success";
        chipLabel = "Lab";
    }

    if (slot.elective) {
        chipColor = "warning";
        chipLabel = "Elective";
    }

    return (

        <Paper
            elevation={3}
            sx={{
                minHeight: 110,
                p: 1,
                borderRadius: 2
            }}
        >

            <Typography
                variant="subtitle2"
                fontWeight="bold"
                align="center"
            >
                {slot.subjectName}
            </Typography>

            <Typography
                variant="body2"
                align="center"
            >
                {slot.subjectCode}
            </Typography>

            <Typography
                variant="body2"
                align="center"
            >
                {slot.facultyName}
            </Typography>

            <Typography
                variant="caption"
                display="block"
                align="center"
            >
                {slot.startTime} - {slot.endTime}
            </Typography>

            <Box
                sx={{
                    display: "flex",
                    justifyContent: "center",
                    mt: 1
                }}
            >
                <Chip
                    size="small"
                    color={chipColor}
                    label={chipLabel}
                />
            </Box>

        </Paper>

    );

};

export default TimetableCell;