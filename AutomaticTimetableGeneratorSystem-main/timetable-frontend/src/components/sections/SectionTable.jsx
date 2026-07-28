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
  Tooltip,
} from "@mui/material";

import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

const SectionTable = ({
  sections,
  onEdit,
  onDelete,
}) => {

  return (

    <TableContainer component={Paper} elevation={3}>

      <Table>

        <TableHead>

          <TableRow>

            <TableCell>
              <b>Code</b>
            </TableCell>

            <TableCell>
              <b>Section</b>
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
              <b>Academic Year</b>
            </TableCell>

            <TableCell align="center">
              <b>Strength</b>
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

          {sections.length === 0 ? (

            <TableRow>

              <TableCell
                colSpan={9}
                align="center"
              >

                No Sections Found

              </TableCell>

            </TableRow>

          ) : (

            sections.map((section) => (

              <TableRow
                key={section.sectionId}
                hover
              >

                <TableCell>

                  {section.sectionCode}

                </TableCell>

                <TableCell>

                  {section.sectionName}

                </TableCell>

                <TableCell>

                  {section.departmentName}

                </TableCell>

                <TableCell>

                  {section.courseName}

                </TableCell>

                <TableCell>

                  Semester {section.semesterNumber}

                </TableCell>

                <TableCell>

                  {section.academicYear}

                </TableCell>

                <TableCell align="center">

                  {section.strength}

                </TableCell>

                <TableCell align="center">

                  <Chip
                    label={
                      section.active
                        ? "Active"
                        : "Inactive"
                    }
                    color={
                      section.active
                        ? "success"
                        : "error"
                    }
                    size="small"
                  />

                </TableCell>

                <TableCell align="center">

                  <Tooltip title="Edit">

                    <IconButton
                      color="primary"
                      onClick={() => onEdit(section)}
                    >

                      <EditIcon />

                    </IconButton>

                  </Tooltip>

                  <Tooltip title="Delete">

                    <IconButton
                      color="error"
                      onClick={() =>
                        onDelete(section.sectionId)
                      }
                    >

                      <DeleteIcon />

                    </IconButton>

                  </Tooltip>

                </TableCell>

              </TableRow>

            ))

          )}

        </TableBody>

      </Table>

    </TableContainer>

  );

};

export default SectionTable;