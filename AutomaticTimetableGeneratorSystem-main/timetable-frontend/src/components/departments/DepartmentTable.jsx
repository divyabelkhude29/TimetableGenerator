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
  Tooltip,
  Typography,
  Box,
} from "@mui/material";

import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

import departmentService from "../../services/departmentService";

const DepartmentTable = ({
  departments,
  onEdit,
  reload,
}) => {

  const handleDelete = async (departmentId) => {

    const confirmDelete = window.confirm(
      "Are you sure you want to delete this department?"
    );

    if (!confirmDelete) return;

    try {

      await departmentService.deleteDepartment(departmentId);

      alert("Department deleted successfully.");

      reload();

    } catch (error) {

      console.error(error);

      alert("Unable to delete department.");

    }

  };

  return (

    <Paper
      elevation={3}
      sx={{
        width: "100%",
        borderRadius: 3,
        overflow: "hidden"
      }}
    >

      <TableContainer
        sx={{
          maxHeight: "70vh",
          overflowX: "auto"
        }}
      >

        <Table stickyHeader>

          <TableHead>

            <TableRow>

              <TableCell
                sx={{
                  fontWeight: "bold",
                  bgcolor: "#1976d2",
                  color: "#fff"
                }}
              >
                Department Code
              </TableCell>

              <TableCell
                sx={{
                  fontWeight: "bold",
                  bgcolor: "#1976d2",
                  color: "#fff"
                }}
              >
                Department Name
              </TableCell>

              <TableCell
                sx={{
                  fontWeight: "bold",
                  bgcolor: "#1976d2",
                  color: "#fff"
                }}
              >
                HOD
              </TableCell>

              <TableCell
                sx={{
                  fontWeight: "bold",
                  bgcolor: "#1976d2",
                  color: "#fff"
                }}
              >
                Status
              </TableCell>

              <TableCell
                align="center"
                sx={{
                  fontWeight: "bold",
                  bgcolor: "#1976d2",
                  color: "#fff"
                }}
              >
                Actions
              </TableCell>

            </TableRow>

          </TableHead>

          <TableBody>

            {departments.length > 0 ? (

              departments.map((department) => (

                <TableRow
                  key={department.departmentId}
                  hover
                >

                  <TableCell>
                    {department.departmentCode}
                  </TableCell>

                  <TableCell>
                    {department.departmentName}
                  </TableCell>

                  <TableCell>
                    {department.hodName || "-"}
                  </TableCell>

                  <TableCell>

                    <Chip
                      label={department.active ? "Active" : "Inactive"}
                      color={department.active ? "success" : "error"}
                      size="small"
                    />

                  </TableCell>

                  <TableCell align="center">

                    <Tooltip title="Edit">

                      <IconButton
                        color="primary"
                        onClick={() => onEdit(department)}
                      >

                        <EditIcon />

                      </IconButton>

                    </Tooltip>

                    <Tooltip title="Delete">

                      <IconButton
                        color="error"
                        onClick={() =>
                          handleDelete(department.departmentId)
                        }
                      >

                        <DeleteIcon />

                      </IconButton>

                    </Tooltip>

                  </TableCell>

                </TableRow>

              ))

            ) : (

              <TableRow>

                <TableCell
                  colSpan={5}
                  align="center"
                  sx={{ py: 5 }}
                >

                  <Box>

                    <Typography
                      variant="h6"
                      color="text.secondary"
                    >
                      No Departments Found
                    </Typography>

                  </Box>

                </TableCell>

              </TableRow>

            )}

          </TableBody>

        </Table>

      </TableContainer>

    </Paper>

  );

};

export default DepartmentTable;