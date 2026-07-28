import {
    Alert,
    AlertTitle,
    Paper,
    Typography,
    Grid,
    Divider,
    List,
    ListItem,
    ListItemIcon,
    ListItemText,
    Chip
} from "@mui/material";

import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import ErrorIcon from "@mui/icons-material/Error";
import WarningAmberIcon from "@mui/icons-material/WarningAmber";

export default function GenerationResult({

    result

}) {

    if (!result) {

        return null;

    }

    return (

        <Paper sx={{ mt: 3, p: 3 }}>

            <Alert

                severity={
                    result.success
                        ? "success"
                        : "error"
                }

                sx={{ mb: 3 }}

            >

                <AlertTitle>

                    {

                        result.success

                            ? "Timetable Generated Successfully"

                            : "Generation Failed"

                    }

                </AlertTitle>

                {result.message}

            </Alert>

            <Typography
                variant="h6"
                fontWeight="bold"
                gutterBottom
            >

                Generation Summary

            </Typography>

            <Divider sx={{ mb: 2 }} />

            <Grid
                container
                spacing={2}
            >

                <Grid item xs={12} md={6}>

                    <Typography>

                        <strong>

                            Academic Year :

                        </strong>

                        {" "}

                        {result.academicYear}

                    </Typography>

                </Grid>

                <Grid item xs={12} md={6}>

                    <Typography>

                        <strong>

                            Course ID :

                        </strong>

                        {" "}

                        {result.courseId}

                    </Typography>

                </Grid>

                <Grid item xs={12} md={6}>

                    <Typography>

                        <strong>

                            Semester ID :

                        </strong>

                        {" "}

                        {result.semesterId}

                    </Typography>

                </Grid>

                <Grid item xs={12} md={6}>

                    <Typography>

                        <strong>

                            Section ID :

                        </strong>

                        {" "}

                        {result.sectionId}

                    </Typography>

                </Grid>

                <Grid item xs={12} md={6}>

                    <Typography>

                        <strong>

                            Classes Generated :

                        </strong>

                        {" "}

                        {result.totalClassesGenerated}

                    </Typography>

                </Grid>

                <Grid item xs={12} md={6}>

                    <Typography>

                        <strong>

                            Hours Assigned :

                        </strong>

                        {" "}

                        {result.totalHoursAssigned}

                    </Typography>

                </Grid>

                <Grid item xs={12} md={6}>

                    <Typography>

                        <strong>

                            Execution Time :

                        </strong>

                        {" "}

                        {result.executionTimeInMilliseconds}

                        {" ms"}

                    </Typography>

                </Grid>

                <Grid item xs={12} md={6}>

                    <Typography>

                        <strong>

                            Generated At :

                        </strong>

                        {" "}

                        {

                            result.generatedAt

                                ? new Date(
                                    result.generatedAt
                                ).toLocaleString()

                                : "-"

                        }

                    </Typography>

                </Grid>

            </Grid>

            <Divider sx={{ my: 3 }} />

            <Typography
                variant="h6"
                fontWeight="bold"
                gutterBottom
            >

                Constraint Conflicts

            </Typography>

            {

                result.conflicts &&
                result.conflicts.length > 0 ? (

                    <List>

                        {

                            result.conflicts.map(

                                (conflict, index) => (

                                    <ListItem
                                        key={index}
                                    >

                                        <ListItemIcon>

                                            <WarningAmberIcon
                                                color="warning"
                                            />

                                        </ListItemIcon>

                                        <ListItemText

                                            primary={
                                                conflict
                                            }

                                        />

                                    </ListItem>

                                )

                            )

                        }

                    </List>

                ) : (

                    <Chip

                        icon={
                            result.success

                                ? <CheckCircleIcon />

                                : <ErrorIcon />

                        }

                        color={
                            result.success

                                ? "success"

                                : "error"
                        }

                        label={

                            result.success

                                ? "No Conflicts Found"

                                : "Generation Failed"

                        }

                    />

                )

            }

        </Paper>

    );

}