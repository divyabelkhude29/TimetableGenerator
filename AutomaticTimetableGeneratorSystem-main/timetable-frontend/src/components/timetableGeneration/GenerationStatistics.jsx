import {
    Grid,
    Paper,
    Typography,
    Box
} from "@mui/material";

import SchoolIcon from "@mui/icons-material/School";
import AccessTimeIcon from "@mui/icons-material/AccessTime";
import EventAvailableIcon from "@mui/icons-material/EventAvailable";
import SpeedIcon from "@mui/icons-material/Speed";

export default function GenerationStatistics({

    statistics

}) {

    if (!statistics || !statistics.success) {

        return null;

    }

    return (

        <Grid
            container
            spacing={3}
            sx={{ mt: 2 }}
        >

            <Grid
                item
                xs={12}
                sm={6}
                md={3}
            >

                <Paper sx={{ p: 3 }}>

                    <Box
                        display="flex"
                        alignItems="center"
                        gap={2}
                    >

                        <SchoolIcon
                            color="primary"
                            fontSize="large"
                        />

                        <Box>

                            <Typography
                                variant="body2"
                                color="text.secondary"
                            >

                                Classes Generated

                            </Typography>

                            <Typography
                                variant="h5"
                                fontWeight="bold"
                            >

                                {statistics.totalClassesGenerated ?? 0}

                            </Typography>

                        </Box>

                    </Box>

                </Paper>

            </Grid>

            <Grid
                item
                xs={12}
                sm={6}
                md={3}
            >

                <Paper sx={{ p: 3 }}>

                    <Box
                        display="flex"
                        alignItems="center"
                        gap={2}
                    >

                        <AccessTimeIcon
                            color="success"
                            fontSize="large"
                        />

                        <Box>

                            <Typography
                                variant="body2"
                                color="text.secondary"
                            >

                                Hours Assigned

                            </Typography>

                            <Typography
                                variant="h5"
                                fontWeight="bold"
                            >

                                {statistics.totalHoursAssigned ?? 0}

                            </Typography>

                        </Box>

                    </Box>

                </Paper>

            </Grid>

            <Grid
                item
                xs={12}
                sm={6}
                md={3}
            >

                <Paper sx={{ p: 3 }}>

                    <Box
                        display="flex"
                        alignItems="center"
                        gap={2}
                    >

                        <SpeedIcon
                            color="warning"
                            fontSize="large"
                        />

                        <Box>

                            <Typography
                                variant="body2"
                                color="text.secondary"
                            >

                                Execution Time

                            </Typography>

                            <Typography
                                variant="h5"
                                fontWeight="bold"
                            >

                                {statistics.executionTimeInMilliseconds ?? 0}
                                {" ms"}

                            </Typography>

                        </Box>

                    </Box>

                </Paper>

            </Grid>

            <Grid
                item
                xs={12}
                sm={6}
                md={3}
            >

                <Paper sx={{ p: 3 }}>

                    <Box
                        display="flex"
                        alignItems="center"
                        gap={2}
                    >

                        <EventAvailableIcon
                            color="secondary"
                            fontSize="large"
                        />

                        <Box>

                            <Typography
                                variant="body2"
                                color="text.secondary"
                            >

                                Generated At

                            </Typography>

                            <Typography
                                variant="body1"
                                fontWeight="bold"
                            >

                                {

                                    statistics.generatedAt

                                        ? new Date(
                                            statistics.generatedAt
                                        ).toLocaleString()

                                        : "-"

                                }

                            </Typography>

                        </Box>

                    </Box>

                </Paper>

            </Grid>

        </Grid>

    );

}