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

import subjectWorkloadService from "../../services/subjectWorkloadService";

const SubjectWorkloadTable = ({
  workloads = [],
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

    return workloads.filter((item) =>

      item.facultyCode?.toLowerCase().includes(keyword) ||

      item.facultyName?.toLowerCase().includes(keyword) ||

      item.subjectCode?.toLowerCase().includes(keyword) ||

      item.subjectName?.toLowerCase().includes(keyword) ||

      item.sectionName?.toLowerCase().includes(keyword)

    );

  }, [workloads, search]);

  const handleDeleteClick = (id) => {

    setSelectedId(id);

    setDeleteOpen(true);

  };

  const handleDelete = async () => {

    try {

      await subjectWorkloadService.deleteSubjectWorkload(
        selectedId
      );

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

        <Typography
          variant="h6"
          fontWeight="bold"
        >
          Subject Workload List
        </Typography>

        <TextField
          size="small"
          placeholder="Search..."
          value={search}
          onChange={(e) =>
            setSearch(e.target.value)
          }
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
                <b>Faculty</b>
              </TableCell>

              <TableCell>
                <b>Subject</b>
              </TableCell>

              <TableCell>
                <b>Section</b>
              </TableCell>

              <TableCell align="center">
                <b>Weekly</b>
              </TableCell>

              <TableCell align="center">
                <b>Theory</b>
              </TableCell>

              <TableCell align="center">
                <b>Practical</b>
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
                  key={row.workloadId}
                  hover
                >

                  <TableCell>
                    {row.workloadId}
                  </TableCell>

                  <TableCell>
                    {row.facultyCode}
                    <br />
                    {row.facultyName}
                  </TableCell>

                  <TableCell>
                    {row.subjectCode}
                    <br />
                    {row.subjectName}
                  </TableCell>

                  <TableCell>
                    {row.sectionName}
                  </TableCell>

                  <TableCell align="center">
                    {row.weeklyHours}
                  </TableCell>

                  <TableCell align="center">
                    {row.theoryHours}
                  </TableCell>

                  <TableCell align="center">
                    {row.practicalHours}
                  </TableCell>

                  <TableCell align="center">

                    {row.active ? (
                      <Chip
                        label="Active"
                        color="success"
                        size="small"
                      />
                    ) : (
                      <Chip
                        label="Inactive"
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
                          handleDeleteClick(
                            row.workloadId
                          )
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
                  colSpan={9}
                  align="center"
                >
                  No Subject Workloads Found
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
          Delete Subject Workload
        </DialogTitle>

        <DialogContent>
          Are you sure you want to delete this Subject
          Workload?
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

export default SubjectWorkloadTable;