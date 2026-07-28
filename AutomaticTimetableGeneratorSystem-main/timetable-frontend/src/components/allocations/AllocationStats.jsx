import { Grid, Paper, Typography } from "@mui/material";
import AssignmentIndIcon from "@mui/icons-material/AssignmentInd";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import CancelIcon from "@mui/icons-material/Cancel";
import GroupsIcon from "@mui/icons-material/Groups";

const AllocationStats = ({ allocations = [] }) => {

    const total = allocations.length;

    const active = allocations.filter(
        allocation => allocation.active
    ).length;

    const inactive = total - active;

    const faculties = new Set(
        allocations.map(
            allocation => allocation.facultyId
        )
    ).size;

    const cards = [

        {
            title: "Total Allocations",
            value: total,
            icon: <AssignmentIndIcon fontSize="large" />
        },

        {
            title: "Active",
            value: active,
            icon: <CheckCircleIcon fontSize="large" />
        },

        {
            title: "Inactive",
            value: inactive,
            icon: <CancelIcon fontSize="large" />
        },

        {
            title: "Faculty",
            value: faculties,
            icon: <GroupsIcon fontSize="large" />
        }

    ];

    return (

        <Grid container spacing={3}>

            {

                cards.map((card, index) => (

                    <Grid
                        item
                        xs={12}
                        sm={6}
                        md={3}
                        key={index}
                    >

                        <Paper
                            elevation={3}
                            sx={{
                                p: 3,
                                display: "flex",
                                justifyContent: "space-between",
                                alignItems: "center",
                                borderRadius: 3
                            }}
                        >

                            <div>

                                <Typography
                                    variant="body2"
                                    color="text.secondary"
                                >
                                    {card.title}
                                </Typography>

                                <Typography
                                    variant="h4"
                                    fontWeight="bold"
                                >
                                    {card.value}
                                </Typography>

                            </div>

                            {card.icon}

                        </Paper>

                    </Grid>

                ))

            }

        </Grid>

    );

};

export default AllocationStats;