import {
Paper,
Typography
} from "@mui/material";

const StatisticsChart=()=>{

return(

<Paper
sx={{
mt:3,
p:3,
height:300
}}
>

<Typography
variant="h6"
>

Statistics Chart

</Typography>

<Typography
color="text.secondary"
mt={10}
align="center"
>

Chart will be connected after backend integration.

</Typography>

</Paper>

);

};

export default StatisticsChart;