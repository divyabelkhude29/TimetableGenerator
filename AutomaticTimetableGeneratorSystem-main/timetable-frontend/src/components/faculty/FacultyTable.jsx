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

const FacultyTable = ({
  faculty = [],
  onEdit,
  onDelete,
}) => {

  return (

    <TableContainer component={Paper}>

      <Table>

        <TableHead>

          <TableRow>

            <TableCell>
              <b>Faculty Code</b>
            </TableCell>

            <TableCell>
              <b>Faculty Name</b>
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
              <b>Designation</b>
            </TableCell>

            <TableCell>
              <b>Qualification</b>
            </TableCell>

            <TableCell>
              <b>Experience</b>
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

            faculty.length === 0 ?

            (

              <TableRow>

                <TableCell
                  align="center"
                  colSpan={10}
                >

                  No Faculty Available

                </TableCell>

              </TableRow>

            )

            :

            (

              faculty.map((item) => (

                <TableRow
                  key={item.facultyId}
                  hover
                >

                  <TableCell>

                    {item.facultyCode}

                  </TableCell>

                  <TableCell>

                    {item.firstName} {item.lastName}

                  </TableCell>

                  <TableCell>

                    {item.email}

                  </TableCell>

                  <TableCell>

                    {item.phone}

                  </TableCell>

                  <TableCell>

                    {item.departmentName}

                  </TableCell>

                  <TableCell>

                    {item.designation}

                  </TableCell>

                  <TableCell>

                    {item.qualification}

                  </TableCell>

                  <TableCell>

                    {item.experienceYears} Years

                  </TableCell>

                  <TableCell align="center">

                    <Chip
                      label={
                        item.active
                          ? "Active"
                          : "Inactive"
                      }
                      color={
                        item.active
                          ? "success"
                          : "error"
                      }
                      size="small"
                    />

                  </TableCell>

                  <TableCell align="center">

                    <IconButton
                      color="primary"
                      onClick={() => onEdit(item)}
                    >

                      <EditIcon />

                    </IconButton>

                    <IconButton
                      color="error"
                      onClick={() => onDelete(item.facultyId)}
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

export default FacultyTable;