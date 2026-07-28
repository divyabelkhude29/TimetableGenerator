import {
Paper,
Typography,
Table,
TableBody,
TableCell,
TableHead,
TableRow
} from "@mui/material";

const TodayTimetable=()=>{

const rows=[

["09:00","Java"],

["10:00","DBMS"],

["11:00","Break"],

["12:00","Networks"]

];

return(

<Paper sx={{mt:3,p:3}}>

<Typography
variant="h6"
mb={2}
>

Today's Timetable

</Typography>

<Table>

<TableHead>

<TableRow>

<TableCell>Time</TableCell>

<TableCell>Subject</TableCell>

</TableRow>

</TableHead>

<TableBody>

{

rows.map((row,index)=>(

<TableRow key={index}>

<TableCell>{row[0]}</TableCell>

<TableCell>{row[1]}</TableCell>

</TableRow>

))

}

</TableBody>

</Table>

</Paper>

);

};

export default TodayTimetable;