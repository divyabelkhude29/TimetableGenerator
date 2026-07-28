import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Chip,
  IconButton,
} from "@mui/material";

import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

const CourseTable = ({
  courses,
  onEdit,
  onDelete,
}) => {
  return (
    <TableContainer component={Paper}>

      <Table>

        <TableHead>

          <TableRow>

            <TableCell>
              <b>Code</b>
            </TableCell>

            <TableCell>
              <b>Course</b>
            </TableCell>

            <TableCell>
              <b>Department</b>
            </TableCell>

            <TableCell align="center">
              <b>Duration</b>
            </TableCell>

            <TableCell align="center">
              <b>Semesters</b>
            </TableCell>

            <TableCell align="center">
              <b>Status</b>
            </TableCell>

            <TableCell align="center">
              <b>Actions</b>
            </TableCell>

          </TableRow>

        </TableHead>

        <TableBody>

          {courses.map((course) => (

            <TableRow
              hover
              key={course.courseId}
            >

              <TableCell>
                {course.courseCode}
              </TableCell>

              <TableCell>
                {course.courseName}
              </TableCell>

              <TableCell>
                {course.departmentName}
              </TableCell>

              <TableCell align="center">
                {course.durationYears} Years
              </TableCell>

              <TableCell align="center">
                {course.totalSemesters}
              </TableCell>

              <TableCell align="center">

                <Chip
                  label={
                    course.active
                      ? "Active"
                      : "Inactive"
                  }
                  color={
                    course.active
                      ? "success"
                      : "error"
                  }
                />

              </TableCell>

              <TableCell align="center">

                <IconButton
                  color="primary"
                  onClick={() => onEdit(course)}
                >
                  <EditIcon />
                </IconButton>

                <IconButton
                  color="error"
                  onClick={() =>
                    onDelete(course.courseId)
                  }
                >
                  <DeleteIcon />
                </IconButton>

              </TableCell>

            </TableRow>

          ))}

        </TableBody>

      </Table>

    </TableContainer>
  );
};

export default CourseTable;