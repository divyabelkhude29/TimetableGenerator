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

const SemesterTable = ({
  semesters,
  onEdit,
  onDelete,
}) => {
  return (
    <TableContainer component={Paper}>
      <Table>

        <TableHead>

          <TableRow>

            <TableCell>
              <b>No</b>
            </TableCell>

            <TableCell>
              <b>Name</b>
            </TableCell>

            <TableCell>
              <b>Academic Year</b>
            </TableCell>

            <TableCell>
              <b>Course</b>
            </TableCell>

            <TableCell>
              <b>Duration</b>
            </TableCell>

            <TableCell>
              <b>Status</b>
            </TableCell>

            <TableCell align="center">
              <b>Actions</b>
            </TableCell>

          </TableRow>

        </TableHead>

        <TableBody>

          {semesters.map((semester) => (

            <TableRow
              hover
              key={semester.semesterId}
            >

              <TableCell>
                {semester.semesterNumber}
              </TableCell>

              <TableCell>
                {semester.semesterName}
              </TableCell>

              <TableCell>
                {semester.academicYear}
              </TableCell>

              <TableCell>
                {semester.courseName}
              </TableCell>

              <TableCell>
                {semester.startDate}
                <br />
                {semester.endDate}
              </TableCell>

              <TableCell>

                <Chip
                  label={
                    semester.active
                      ? "Active"
                      : "Inactive"
                  }
                  color={
                    semester.active
                      ? "success"
                      : "error"
                  }
                />

              </TableCell>

              <TableCell align="center">

                <IconButton
                  color="primary"
                  onClick={() => onEdit(semester)}
                >
                  <EditIcon />
                </IconButton>

                <IconButton
                  color="error"
                  onClick={() =>
                    onDelete(semester.semesterId)
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

export default SemesterTable;