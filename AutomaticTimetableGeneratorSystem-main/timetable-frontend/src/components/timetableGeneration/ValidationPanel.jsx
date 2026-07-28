import {
    Alert,
    AlertTitle,
    Paper,
    Typography,
    List,
    ListItem,
    ListItemIcon,
    ListItemText
} from "@mui/material";

import ErrorOutlineIcon from "@mui/icons-material/ErrorOutline";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";

export default function ValidationPanel({

    validations = []

}) {

    if (!validations) {

        return null;

    }

    return (

        <Paper sx={{ p: 3, mt: 3 }}>

            <Typography
                variant="h6"
                fontWeight="bold"
                gutterBottom
            >

                Timetable Validation

            </Typography>

            {

                validations.length === 0 ? (

                    <Alert severity="success">

                        <AlertTitle>

                            Validation Successful

                        </AlertTitle>

                        No timetable conflicts found.

                        All constraints are satisfied.

                    </Alert>

                ) : (

                    <Alert severity="warning">

                        <AlertTitle>

                            Validation Failed

                        </AlertTitle>

                        The following conflicts were detected.

                    </Alert>

                )

            }

            {

                validations.length > 0 && (

                    <List sx={{ mt: 2 }}>

                        {

                            validations.map((item, index) => (

                                <ListItem
                                    key={index}
                                    divider
                                >

                                    <ListItemIcon>

                                        <ErrorOutlineIcon
                                            color="error"
                                        />

                                    </ListItemIcon>

                                    <ListItemText

                                        primary={item}

                                    />

                                </ListItem>

                            ))

                        }

                    </List>

                )

            }

            {

                validations.length === 0 && (

                    <List sx={{ mt: 2 }}>

                        <ListItem>

                            <ListItemIcon>

                                <CheckCircleIcon
                                    color="success"
                                />

                            </ListItemIcon>

                            <ListItemText
                                primary="Faculty availability verified."
                            />

                        </ListItem>

                        <ListItem>

                            <ListItemIcon>

                                <CheckCircleIcon
                                    color="success"
                                />

                            </ListItemIcon>

                            <ListItemText
                                primary="Subject workload verified."
                            />

                        </ListItem>

                        <ListItem>

                            <ListItemIcon>

                                <CheckCircleIcon
                                    color="success"
                                />

                            </ListItemIcon>

                            <ListItemText
                                primary="Classroom availability verified."
                            />

                        </ListItem>

                        <ListItem>

                            <ListItemIcon>

                                <CheckCircleIcon
                                    color="success"
                                />

                            </ListItemIcon>

                            <ListItemText
                                primary="Holiday constraints verified."
                            />

                        </ListItem>

                        <ListItem>

                            <ListItemIcon>

                                <CheckCircleIcon
                                    color="success"
                                />

                            </ListItemIcon>

                            <ListItemText
                                primary="No faculty or section conflicts detected."
                            />

                        </ListItem>

                    </List>

                )

            }

        </Paper>

    );

}