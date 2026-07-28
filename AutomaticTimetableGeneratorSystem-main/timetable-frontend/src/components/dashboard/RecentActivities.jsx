import {
Paper,
Typography,
List,
ListItem,
ListItemText
} from "@mui/material";

const RecentActivities=()=>{

const activities=[

"Department Created",

"Faculty Added",

"Subject Updated",

"Timetable Generated"

];

return(

<Paper sx={{mt:3,p:3}}>

<Typography
variant="h6"
mb={2}
>

Recent Activities

</Typography>

<List>

{

activities.map((item,index)=>(

<ListItem key={index}>

<ListItemText primary={item}/>

</ListItem>

))

}

</List>

</Paper>

);

};

export default RecentActivities;