import { useMemo, useState } from "react";

import {
  Paper,
  Table,
  TableHead,
  TableBody,
  TableRow,
  TableCell,
  TableContainer,
  TablePagination,
  IconButton,
  Chip,
  TextField,
  Typography,
  Box,
  Tooltip,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  InputAdornment,
} from "@mui/material";

import SearchIcon from "@mui/icons-material/Search";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

import facultyAvailabilityService from "../../services/facultyAvailabilityService";

const FacultyAvailabilityTable = ({
  availabilityList = [],
  onEdit,
  reload,
}) => {
  const [page, setPage] = useState(0);

  const [rowsPerPage, setRowsPerPage] = useState(10);

  const [search, setSearch] = useState("");

  const [deleteOpen, setDeleteOpen] = useState(false);

  const [selectedId, setSelectedId] = useState(null);

  const filteredData = useMemo(() => {
    const keyword = search.toLowerCase();

    return availabilityList.filter((item) => {
      return (
        item.facultyCode?.toLowerCase().includes(keyword) ||
        item.facultyName?.toLowerCase().includes(keyword) ||
        item.dayOfWeek?.toLowerCase().includes(keyword) ||
        item.startTime?.toLowerCase().includes(keyword) ||
        item.endTime?.toLowerCase().includes(keyword)
      );
    });
  }, [availabilityList, search]);

  const handleDeleteClick = (id) => {
    setSelectedId(id);
    setDeleteOpen(true);
  };

  const handleDelete = async () => {
    try {
      await facultyAvailabilityService.deleteAvailability(selectedId);

      reload();

      setDeleteOpen(false);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <Paper elevation={3}>

      <Box
        display="flex"
        justifyContent="space-between"
        alignItems="center"
        p={2}
      >
        <Typography variant="h6" fontWeight="bold">
          Faculty Availability List
        </Typography>

        <TextField
          size="small"
          placeholder="Search..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          InputProps={{
            startAdornment: (
              <InputAdornment position="start">
                <SearchIcon />
              </InputAdornment>
            ),
          }}
        />
      </Box>

      <TableContainer>

        <Table>

          <TableHead>

            <TableRow>

              <TableCell>
                <b>ID</b>
              </TableCell>

              <TableCell>
                <b>Faculty Code</b>
              </TableCell>

              <TableCell>
                <b>Faculty Name</b>
              </TableCell>

              <TableCell>
                <b>Day</b>
              </TableCell>

              <TableCell>
                <b>Time Slot</b>
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

            {filteredData
              .slice(
                page * rowsPerPage,
                page * rowsPerPage + rowsPerPage
              )
              .map((row) => (

                <TableRow
                  hover
                  key={row.availabilityId}
                >

                  <TableCell>
                    {row.availabilityId}
                  </TableCell>

                  <TableCell>
                    {row.facultyCode}
                  </TableCell>

                  <TableCell>
                    {row.facultyName}
                  </TableCell>

                  <TableCell>
                    {row.dayOfWeek}
                  </TableCell>

                  <TableCell>
                    {row.startTime} - {row.endTime}
                  </TableCell>

                  <TableCell align="center">

                    {row.available ? (

                      <Chip
                        label="Available"
                        color="success"
                        size="small"
                      />

                    ) : (

                      <Chip
                        label="Unavailable"
                        color="error"
                        size="small"
                      />

                    )}

                  </TableCell>

                  <TableCell align="center">

                    <Tooltip title="Edit">

                      <IconButton
                        color="primary"
                        onClick={() => onEdit(row)}
                      >
                        <EditIcon />
                      </IconButton>

                    </Tooltip>

                    <Tooltip title="Delete">

                      <IconButton
                        color="error"
                        onClick={() =>
                          handleDeleteClick(row.availabilityId)
                        }
                      >
                        <DeleteIcon />
                      </IconButton>

                    </Tooltip>

                  </TableCell>

                </TableRow>

              ))}
                          {filteredData.length === 0 && (
              <TableRow>
                <TableCell
                  colSpan={7}
                  align="center"
                >
                  No Faculty Availability Found
                </TableCell>
              </TableRow>
            )}

          </TableBody>

        </Table>

      </TableContainer>

      <TablePagination
        component="div"
        count={filteredData.length}
        page={page}
        rowsPerPage={rowsPerPage}
        rowsPerPageOptions={[5, 10, 25, 50]}
        onPageChange={(event, newPage) => {
          setPage(newPage);
        }}
        onRowsPerPageChange={(event) => {
          setRowsPerPage(
            parseInt(event.target.value, 10)
          );
          setPage(0);
        }}
      />

      <Dialog
        open={deleteOpen}
        onClose={() => setDeleteOpen(false)}
      >
        <DialogTitle>
          Delete Faculty Availability
        </DialogTitle>

        <DialogContent>
          Are you sure you want to delete this
          Faculty Availability record?
        </DialogContent>

        <DialogActions>

          <Button
            onClick={() => setDeleteOpen(false)}
          >
            Cancel
          </Button>

          <Button
            variant="contained"
            color="error"
            onClick={handleDelete}
          >
            Delete
          </Button>

        </DialogActions>

      </Dialog>

    </Paper>

  );

};

export default FacultyAvailabilityTable;