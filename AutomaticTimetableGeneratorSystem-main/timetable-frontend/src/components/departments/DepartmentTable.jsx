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

    <TableContainer component={Paper}>

      <Table>

        <TableHead>

          <TableRow>

            <TableCell>
              <b>Department Code</b>
            </TableCell>

            <TableCell>
              <b>Department Name</b>
            </TableCell>

            <TableCell>
              <b>HOD</b>
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
                    label={
                      department.active
                        ? "Active"
                        : "Inactive"
                    }
                    color={
                      department.active
                        ? "success"
                        : "error"
                    }
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
                        handleDelete(
                          department.departmentId
                        )
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
              >

                No Departments Found

              </TableCell>

            </TableRow>

          )}

        </TableBody>

      </Table>

    </TableContainer>

  );

};

export default DepartmentTable;