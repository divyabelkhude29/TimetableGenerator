import { useEffect, useMemo, useState } from "react";

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
  Alert,
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

  // ==============================
  // Pagination
  // ==============================

  const [page, setPage] = useState(0);

  const [rowsPerPage, setRowsPerPage] = useState(10);


  // ==============================
  // Search
  // ==============================

  const [search, setSearch] = useState("");


  // ==============================
  // Delete Dialog
  // ==============================

  const [deleteOpen, setDeleteOpen] = useState(false);

  const [selectedId, setSelectedId] = useState(null);

  const [deleting, setDeleting] = useState(false);

  const [error, setError] = useState("");


  // ==============================
  // Reset pagination when data
  // or search changes
  // ==============================

  useEffect(() => {

    setPage(0);

  }, [search, workloads]);


  // ==============================
  // Get Faculty Code
  // Supports:
  // 1. item.facultyCode
  // 2. item.faculty.facultyCode
  // 3. item.allocation.faculty.facultyCode
  // ==============================

  const getFacultyCode = (item) => {

    return (

      item?.facultyCode ||

      item?.faculty?.facultyCode ||

      item?.allocation?.faculty?.facultyCode ||

      ""

    );

  };


  // ==============================
  // Get Faculty Name
  // ==============================

  const getFacultyName = (item) => {

    if (item?.facultyName) {

      return item.facultyName;

    }


    if (item?.faculty?.facultyName) {

      return item.faculty.facultyName;

    }


    if (item?.allocation?.faculty?.facultyName) {

      return item.allocation.faculty.facultyName;

    }


    // Support firstName + lastName

    if (item?.faculty) {

      return [

        item.faculty.firstName,

        item.faculty.lastName

      ]

        .filter(Boolean)

        .join(" ");

    }


    if (item?.allocation?.faculty) {

      return [

        item.allocation.faculty.firstName,

        item.allocation.faculty.lastName

      ]

        .filter(Boolean)

        .join(" ");

    }


    return "";

  };


  // ==============================
  // Get Subject Code
  // ==============================

  const getSubjectCode = (item) => {

    return (

      item?.subjectCode ||

      item?.subject?.subjectCode ||

      item?.allocation?.subject?.subjectCode ||

      ""

    );

  };


  // ==============================
  // Get Subject Name
  // ==============================

  const getSubjectName = (item) => {

    return (

      item?.subjectName ||

      item?.subject?.subjectName ||

      item?.allocation?.subject?.subjectName ||

      ""

    );

  };


  // ==============================
  // Get Section Name
  // ==============================

  const getSectionName = (item) => {

    return (

      item?.sectionName ||

      item?.section?.sectionName ||

      item?.allocation?.section?.sectionName ||

      ""

    );

  };


  // ==============================
  // Filter Data
  // ==============================

  const filteredData = useMemo(() => {

    const keyword = search
      .trim()
      .toLowerCase();


    if (!keyword) {

      return workloads;

    }


    return workloads.filter((item) => {

      const facultyCode =
        getFacultyCode(item)
          .toLowerCase();


      const facultyName =
        getFacultyName(item)
          .toLowerCase();


      const subjectCode =
        getSubjectCode(item)
          .toLowerCase();


      const subjectName =
        getSubjectName(item)
          .toLowerCase();


      const sectionName =
        getSectionName(item)
          .toLowerCase();


      return (

        facultyCode.includes(keyword) ||

        facultyName.includes(keyword) ||

        subjectCode.includes(keyword) ||

        subjectName.includes(keyword) ||

        sectionName.includes(keyword)

      );

    });

  }, [workloads, search]);


  // ==============================
  // Open Delete Dialog
  // ==============================

  const handleDeleteClick = (id) => {

    setSelectedId(id);

    setError("");

    setDeleteOpen(true);

  };


  // ==============================
  // Close Delete Dialog
  // ==============================

  const handleDeleteCancel = () => {

    if (deleting) {

      return;

    }

    setDeleteOpen(false);

    setSelectedId(null);

    setError("");

  };


  // ==============================
  // Delete Workload
  // ==============================

  const handleDelete = async () => {

    if (!selectedId) {

      return;

    }


    try {

      setDeleting(true);

      setError("");


      await subjectWorkloadService
        .deleteSubjectWorkload(
          selectedId
        );


      setDeleteOpen(false);

      setSelectedId(null);


      if (reload) {

        await reload();

      }


    } catch (error) {

      console.error(
        "Delete Subject Workload Error:",
        error
      );


      setError(

        error?.response?.data?.message ||

        error?.response?.data?.error ||

        error?.message ||

        "Unable to delete Subject Workload."

      );

    } finally {

      setDeleting(false);

    }

  };


  // ==============================
  // Handle Page Change
  // ==============================

  const handlePageChange = (
    event,
    newPage
  ) => {

    setPage(newPage);

  };


  // ==============================
  // Handle Rows Per Page
  // ==============================

  const handleRowsPerPageChange = (
    event
  ) => {

    const newRowsPerPage =
      parseInt(
        event.target.value,
        10
      );


    setRowsPerPage(
      newRowsPerPage
    );

    setPage(0);

  };


  // ==============================
  // Safe Pagination
  // ==============================

  const paginatedData =
    filteredData.slice(

      page * rowsPerPage,

      page * rowsPerPage +
        rowsPerPage

    );


  return (

    <Paper
      elevation={3}
      sx={{
        width: "100%",
        overflow: "hidden",
        borderRadius: 2,
      }}
    >

      {/* ========================= */}
      {/* HEADER */}
      {/* ========================= */}

      <Box

        display="flex"

        justifyContent="space-between"

        alignItems="center"

        gap={2}

        p={2}

        flexWrap="wrap"

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
            setSearch(
              e.target.value
            )
          }

          sx={{
            minWidth: 250,
          }}

          InputProps={{

            startAdornment: (

              <InputAdornment
                position="start"
              >

                <SearchIcon />

              </InputAdornment>

            ),

          }}

        />

      </Box>


      {/* ========================= */}
      {/* ERROR MESSAGE */}
      {/* ========================= */}

      {error && (

        <Alert

          severity="error"

          sx={{
            mx: 2,
            mb: 2,
          }}

          onClose={() =>
            setError("")
          }

        >

          {error}

        </Alert>

      )}


      {/* ========================= */}
      {/* TABLE */}
      {/* ========================= */}

      <TableContainer>

        <Table>

          {/* ===================== */}
          {/* TABLE HEAD */}
          {/* ===================== */}

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


          {/* ===================== */}
          {/* TABLE BODY */}
          {/* ===================== */}

          <TableBody>

            {paginatedData.map(
              (row) => (

                <TableRow

                  key={
                    row.workloadId
                  }

                  hover

                >

                  {/* ID */}

                  <TableCell>

                    {row.workloadId}

                  </TableCell>


                  {/* FACULTY */}

                  <TableCell>

                    <Typography
                      variant="body2"
                      fontWeight="bold"
                    >

                      {
                        getFacultyCode(
                          row
                        ) || "-"
                      }

                    </Typography>


                    <Typography
                      variant="body2"
                    >

                      {
                        getFacultyName(
                          row
                        ) || "-"
                      }

                    </Typography>

                  </TableCell>


                  {/* SUBJECT */}

                  <TableCell>

                    <Typography
                      variant="body2"
                      fontWeight="bold"
                    >

                      {
                        getSubjectCode(
                          row
                        ) || "-"
                      }

                    </Typography>


                    <Typography
                      variant="body2"
                    >

                      {
                        getSubjectName(
                          row
                        ) || "-"
                      }

                    </Typography>

                  </TableCell>


                  {/* SECTION */}

                  <TableCell>

                    {
                      getSectionName(
                        row
                      ) || "-"
                    }

                  </TableCell>


                  {/* WEEKLY HOURS */}

                  <TableCell align="center">

                    {
                      row.weeklyHours ??
                      0
                    }

                  </TableCell>


                  {/* THEORY HOURS */}

                  <TableCell align="center">

                    {
                      row.theoryHours ??
                      0
                    }

                  </TableCell>


                  {/* PRACTICAL HOURS */}

                  <TableCell align="center">

                    {
                      row.practicalHours ??
                      0
                    }

                  </TableCell>


                  {/* STATUS */}

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


                  {/* ACTIONS */}

                  <TableCell align="center">

                    <Tooltip
                      title="Edit"
                    >

                      <IconButton

                        color="primary"

                        onClick={() => {

                          if (onEdit) {

                            onEdit(row);

                          }

                        }}

                      >

                        <EditIcon />

                      </IconButton>

                    </Tooltip>


                    <Tooltip
                      title="Delete"
                    >

                      <IconButton

                        color="error"

                        onClick={() =>
                          handleDeleteClick(
                            row.workloadId
                          )
                        }

                        disabled={
                          deleting
                        }

                      >

                        <DeleteIcon />

                      </IconButton>

                    </Tooltip>

                  </TableCell>

                </TableRow>

              )

            )}


            {/* ===================== */}
            {/* EMPTY DATA */}
            {/* ===================== */}

            {paginatedData.length === 0 && (

              <TableRow>

                <TableCell

                  colSpan={9}

                  align="center"

                  sx={{
                    py: 5,
                  }}

                >

                  <Typography
                    color="text.secondary"
                  >

                    {search

                      ? "No Subject Workloads Found Matching Your Search"

                      : "No Subject Workloads Found"

                    }

                  </Typography>

                </TableCell>

              </TableRow>

            )}

          </TableBody>

        </Table>

      </TableContainer>


      {/* ========================= */}
      {/* PAGINATION */}
      {/* ========================= */}

      <TablePagination

        component="div"

        count={
          filteredData.length
        }

        page={

          page >=
            Math.ceil(
              filteredData.length /
                rowsPerPage
            ) &&

          filteredData.length > 0

            ? Math.max(

                0,

                Math.ceil(
                  filteredData.length /
                    rowsPerPage
                ) - 1

              )

            : page

        }

        rowsPerPage={
          rowsPerPage
        }

        rowsPerPageOptions={[
          5,
          10,
          25,
          50,
        ]}

        onPageChange={
          handlePageChange
        }

        onRowsPerPageChange={
          handleRowsPerPageChange
        }

      />


      {/* ========================= */}
      {/* DELETE CONFIRMATION */}
      {/* ========================= */}

      <Dialog

        open={deleteOpen}

        onClose={
          handleDeleteCancel
        }

        maxWidth="xs"

        fullWidth

      >

        <DialogTitle>

          Delete Subject Workload

        </DialogTitle>


        <DialogContent>

          <Typography>

            Are you sure you want to
            delete this Subject
            Workload?

          </Typography>

        </DialogContent>


        <DialogActions>

          <Button

            onClick={
              handleDeleteCancel
            }

            disabled={
              deleting
            }

          >

            Cancel

          </Button>


          <Button

            variant="contained"

            color="error"

            onClick={
              handleDelete
            }

            disabled={
              deleting
            }

          >

            {deleting

              ? "Deleting..."

              : "Delete"

            }

          </Button>

        </DialogActions>

      </Dialog>

    </Paper>

  );

};


export default SubjectWorkloadTable;