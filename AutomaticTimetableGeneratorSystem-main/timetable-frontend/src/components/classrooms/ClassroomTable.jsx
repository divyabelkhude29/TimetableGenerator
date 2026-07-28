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

const ClassroomTable = ({
  classrooms = [],
  onEdit,
  onDelete,
}) => {

  return (

    <TableContainer component={Paper}>

      <Table>

        <TableHead>

          <TableRow>

            <TableCell><b>Room No</b></TableCell>

            <TableCell><b>Building</b></TableCell>

            <TableCell><b>Floor</b></TableCell>

            <TableCell><b>Capacity</b></TableCell>

            <TableCell><b>Room Type</b></TableCell>

            <TableCell align="center">
              <b>Status</b>
            </TableCell>

            <TableCell align="center">
              <b>Actions</b>
            </TableCell>

          </TableRow>

        </TableHead>

        <TableBody>

          {classrooms.length === 0 ? (

            <TableRow>

              <TableCell
                colSpan={7}
                align="center"
              >

                No Classrooms Available

              </TableCell>

            </TableRow>

          ) : (

            classrooms.map((room) => (

              <TableRow
                hover
                key={room.classroomId}
              >

                <TableCell>
                  {room.roomNumber}
                </TableCell>

                <TableCell>
                  {room.buildingName}
                </TableCell>

                <TableCell>
                  {room.floorNumber}
                </TableCell>

                <TableCell>
                  {room.capacity}
                </TableCell>

                <TableCell>
                  {room.roomType}
                </TableCell>

                <TableCell align="center">

                  <Chip
                    size="small"
                    color={
                      room.active
                        ? "success"
                        : "error"
                    }
                    label={
                      room.active
                        ? "Active"
                        : "Inactive"
                    }
                  />

                </TableCell>

                <TableCell align="center">

                  <IconButton
                    color="primary"
                    onClick={() =>
                      onEdit(room)
                    }
                  >

                    <EditIcon />

                  </IconButton>

                  <IconButton
                    color="error"
                    onClick={() =>
                      onDelete(room.classroomId)
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

export default ClassroomTable;