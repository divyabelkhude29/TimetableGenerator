import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  IconButton,
  Chip,
} from "@mui/material";

import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

const StudentTable = ({
  students = [],
  onEdit,
  onDelete,
}) => {
  return (
    <TableContainer component={Paper}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>
              <b>Roll No</b>
            </TableCell>

            <TableCell>
              <b>Register No</b>
            </TableCell>

            <TableCell>
              <b>Student Name</b>
            </TableCell>

            <TableCell>
              <b>Gender</b>
            </TableCell>

            <TableCell>
              <b>Email</b>
            </TableCell>

            <TableCell>
              <b>Phone</b>
            </TableCell>

            <TableCell>
              <b>Department</b>
            </TableCell>

            <TableCell>
              <b>Course</b>
            </TableCell>

            <TableCell>
              <b>Semester</b>
            </TableCell>

            <TableCell>
              <b>Admission</b>
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
          {students.length === 0 ? (
            <TableRow>
              <TableCell
                colSpan={12}
                align="center"
              >
                No Students Available
              </TableCell>
            </TableRow>
          ) : (
            students.map((student) => (
              <TableRow
                key={student.studentId}
                hover
              >
                <TableCell>
                  {student.rollNumber}
                </TableCell>

                <TableCell>
                  {student.registerNumber}
                </TableCell>

                <TableCell>
                  {student.firstName} {student.lastName}
                </TableCell>

                <TableCell>
                  {student.gender}
                </TableCell>

                <TableCell>
                  {student.email}
                </TableCell>

                <TableCell>
                  {student.phone}
                </TableCell>

                <TableCell>
                  {student.departmentName}
                </TableCell>

                <TableCell>
                  {student.courseName}
                </TableCell>

                <TableCell>
                  {student.semesterName}
                </TableCell>

                <TableCell>
                  {student.admissionYear}
                </TableCell>

                <TableCell align="center">
                  <Chip
                    label={
                      student.active
                        ? "Active"
                        : "Inactive"
                    }
                    color={
                      student.active
                        ? "success"
                        : "error"
                    }
                    size="small"
                  />
                </TableCell>

                <TableCell align="center">
                  <IconButton
                    color="primary"
                    onClick={() => onEdit(student)}
                  >
                    <EditIcon />
                  </IconButton>

                  <IconButton
                    color="error"
                    onClick={() =>
                      onDelete(student.studentId)
                    }
                  >
                    <DeleteIcon />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))
          )}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default StudentTable;