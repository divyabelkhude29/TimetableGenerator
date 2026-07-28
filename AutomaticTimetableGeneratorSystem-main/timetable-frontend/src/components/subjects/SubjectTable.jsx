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

const SubjectTable = ({
  subjects = [],
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
              <b>Subject Name</b>
            </TableCell>

            <TableCell align="center">
              <b>Credits</b>
            </TableCell>

            <TableCell align="center">
              <b>Hours / Week</b>
            </TableCell>

            <TableCell>
              <b>Type</b>
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
              <b>Faculty</b>
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

          {
            subjects.length === 0 ? (

              <TableRow>

                <TableCell
                  colSpan={11}
                  align="center"
                >

                  No Subjects Available

                </TableCell>

              </TableRow>

            ) : (

              subjects.map((subject) => (

                <TableRow
                  key={subject.subjectId}
                  hover
                >

                  <TableCell>

                    {subject.subjectCode}

                  </TableCell>

                  <TableCell>

                    {subject.subjectName}

                  </TableCell>

                  <TableCell align="center">

                    {subject.credits}

                  </TableCell>

                  <TableCell align="center">

                    {subject.hoursPerWeek}

                  </TableCell>

                  <TableCell>

                    {subject.subjectType}

                  </TableCell>

                  <TableCell>

                    {subject.departmentName}

                  </TableCell>

                  <TableCell>

                    {subject.courseName}

                  </TableCell>

                  <TableCell>

                    {subject.semesterName}

                  </TableCell>

                  <TableCell>

                    {subject.facultyName || "-"}

                  </TableCell>

                  <TableCell align="center">

                    <Chip
                      label={
                        subject.active
                          ? "Active"
                          : "Inactive"
                      }
                      color={
                        subject.active
                          ? "success"
                          : "error"
                      }
                      size="small"
                    />

                  </TableCell>

                  <TableCell align="center">

                    <IconButton
                      color="primary"
                      onClick={() => onEdit(subject)}
                    >

                      <EditIcon />

                    </IconButton>

                    <IconButton
                      color="error"
                      onClick={() => onDelete(subject.subjectId)}
                    >

                      <DeleteIcon />

                    </IconButton>

                  </TableCell>

                </TableRow>

              ))

            )
          }

        </TableBody>

      </Table>

    </TableContainer>

  );

};

export default SubjectTable;