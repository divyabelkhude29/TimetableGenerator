import {
  Grid,
  Paper,
  Button,
  Typography
} from "@mui/material";

const actions = [

    "Add Department",

    "Add Faculty",

    "Add Student",

    "Generate Timetable"

];

const QuickActions = () => {

    return(

        <Paper
            sx={{
                mt:3,
                p:3
            }}
        >

            <Typography
                variant="h6"
                mb={2}
            >

                Quick Actions

            </Typography>

            <Grid
                container
                spacing={2}
            >

                {

                    actions.map((action,index)=>(

                        <Grid
                            item
                            key={index}
                        >

                            <Button
                                variant="contained"
                            >

                                {action}

                            </Button>

                        </Grid>

                    ))

                }

            </Grid>

        </Paper>

    );

};

export default QuickActions;