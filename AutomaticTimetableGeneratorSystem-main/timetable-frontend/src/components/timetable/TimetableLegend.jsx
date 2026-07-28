import {
    Paper,
    Typography,
    Box,
    Chip
} from "@mui/material";

const TimetableLegend = () => {

    return (

        <Paper
            elevation={2}
            sx={{
                mt: 3,
                p: 2
            }}
        >

            <Typography
                variant="h6"
                gutterBottom
            >
                Timetable Legend
            </Typography>

            <Box
                sx={{
                    display: "flex",
                    flexWrap: "wrap",
                    gap: 2,
                    mt: 1
                }}
            >

                <Chip
                    label="Theory"
                    color="primary"
                />

                <Chip
                    label="Laboratory"
                    color="success"
                />

                <Chip
                    label="Elective"
                    color="warning"
                />

                <Chip
                    label="Free Period"
                    variant="outlined"
                />

            </Box>

            <Typography
                variant="body2"
                color="text.secondary"
                sx={{ mt: 2 }}
            >
                • Blue indicates regular theory classes.
            </Typography>

            <Typography
                variant="body2"
                color="text.secondary"
            >
                • Green indicates laboratory sessions.
            </Typography>

            <Typography
                variant="body2"
                color="text.secondary"
            >
                • Orange indicates elective subjects.
            </Typography>

            <Typography
                variant="body2"
                color="text.secondary"
            >
                • Empty cells indicate no class is scheduled.
            </Typography>

        </Paper>

    );

};

export default TimetableLegend;