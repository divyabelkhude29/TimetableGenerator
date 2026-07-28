import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
  IconButton,
  Chip,
  Tooltip,
} from "@mui/material";

import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

const TimeSlotTable = ({
  timeSlots = [],
  onEdit,
  onDelete,
}) => {

  return (

    <Paper sx={{ p: 2 }}>

      <Typography
        variant="h6"
        sx={{ mb: 2 }}
      >
        Time Slot List
      </Typography>

      <TableContainer>

        <Table>

          <TableHead>

            <TableRow>

              <TableCell>
                <b>Slot Name</b>
              </TableCell>

              <TableCell>
                <b>Day</b>
              </TableCell>

              <TableCell>
                <b>Start Time</b>
              </TableCell>

              <TableCell>
                <b>End Time</b>
              </TableCell>

              <TableCell>
                <b>Slot Order</b>
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

            {timeSlots.length === 0 ? (

              <TableRow>

                <TableCell
                  colSpan={7}
                  align="center"
                >
                  No Time Slots Available
                </TableCell>

              </TableRow>

            ) : (

              timeSlots.map((slot) => (

                <TableRow
                  hover
                  key={slot.timeSlotId}
                >

                  <TableCell>
                    {slot.slotName}
                  </TableCell>

                  <TableCell>
                    {slot.dayOfWeek}
                  </TableCell>

                  <TableCell>
                    {slot.startTime}
                  </TableCell>

                  <TableCell>
                    {slot.endTime}
                  </TableCell>

                  <TableCell>
                    {slot.slotOrder}
                  </TableCell>

                  <TableCell align="center">

                    <Chip
                      size="small"
                      label={
                        slot.active
                          ? "Active"
                          : "Inactive"
                      }
                      color={
                        slot.active
                          ? "success"
                          : "error"
                      }
                    />

                  </TableCell>

                  <TableCell align="center">

                    <Tooltip title="Edit Time Slot">

                      <IconButton
                        color="primary"
                        onClick={() => onEdit(slot)}
                      >
                        <EditIcon />
                      </IconButton>

                    </Tooltip>

                    <Tooltip title="Delete Time Slot">

                      <IconButton
                        color="error"
                        onClick={() =>
                          onDelete(slot.timeSlotId)
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

    </Paper>

  );

};

export default TimeSlotTable;