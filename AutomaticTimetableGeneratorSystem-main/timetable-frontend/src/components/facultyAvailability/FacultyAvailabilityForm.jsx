import {
  useEffect,
  useState
} from "react";

import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  Grid,
  TextField,
  MenuItem,
  FormControlLabel,
  Switch,
  CircularProgress,
  Alert,
  Box
} from "@mui/material";

import facultyService
  from "../../services/facultyService";

import timeSlotService
  from "../../services/timeSlotService";

import facultyAvailabilityService
  from "../../services/facultyAvailabilityService";


const DAYS = [

  "MONDAY",

  "TUESDAY",

  "WEDNESDAY",

  "THURSDAY",

  "FRIDAY",

  "SATURDAY",

  "SUNDAY"

];


const initialState = {

  facultyId: "",

  dayOfWeek: "",

  timeSlotId: "",

  available: true

};


const FacultyAvailabilityForm = ({

  open,

  onClose,

  availability,

  reload

}) => {


  // =====================================================
  // STATE
  // =====================================================

  const [formData, setFormData] =
    useState({
      ...initialState
    });


  const [facultyList, setFacultyList] =
    useState([]);


  const [timeSlotList, setTimeSlotList] =
    useState([]);


  const [errors, setErrors] =
    useState({});


  const [loading, setLoading] =
    useState(false);


  const [saving, setSaving] =
    useState(false);


  const [message, setMessage] =
    useState("");


  // =====================================================
  // LOAD DROPDOWNS WHEN FORM OPENS
  // =====================================================

  useEffect(() => {

    if (open) {

      loadDropdowns();

    }

  }, [open]);


  // =====================================================
  // SET ADD / EDIT DATA
  // =====================================================

  useEffect(() => {

    if (availability) {

      console.log(
        "Editing Availability:",
        availability
      );


      setFormData({

        facultyId:
          availability.facultyId ??
          availability.faculty?.facultyId ??
          "",

        dayOfWeek:
          availability.dayOfWeek ??
          "",

        timeSlotId:
          availability.timeSlotId ??
          availability.timeSlot?.timeSlotId ??
          "",

        available:
          availability.available === undefined
            ? true
            : availability.available

      });

    } else {

      setFormData({
        ...initialState
      });

    }


    setErrors({});

    setMessage("");


  }, [

    availability,

    open

  ]);


  // =====================================================
  // LOAD FACULTY AND TIME SLOTS
  // =====================================================

  const loadDropdowns = async () => {

    try {

      setLoading(true);

      setMessage("");


      console.log(
        "Loading Faculty and Time Slots..."
      );


      /*
       * IMPORTANT:
       *
       * Your facultyService contains:
       *
       * getAllFaculty()
       *
       * NOT:
       *
       * getAllFaculties()
       *
       */

      const [

        facultyResponse,

        timeSlotResponse

      ] = await Promise.all([

        facultyService.getAllFaculty(),

        timeSlotService.getAllTimeSlots()

      ]);


      console.log(
        "Faculty Response:",
        facultyResponse
      );


      console.log(
        "Time Slot Response:",
        timeSlotResponse
      );


      // =================================================
      // NORMALIZE FACULTY RESPONSE
      // =================================================

      let facultyData = [];


      if (
        Array.isArray(
          facultyResponse
        )
      ) {

        facultyData =
          facultyResponse;

      } else if (
        Array.isArray(
          facultyResponse?.data
        )
      ) {

        facultyData =
          facultyResponse.data;

      } else if (
        Array.isArray(
          facultyResponse?.content
        )
      ) {

        facultyData =
          facultyResponse.content;

      }


      // =================================================
      // NORMALIZE TIME SLOT RESPONSE
      // =================================================

      let timeSlotData = [];


      if (
        Array.isArray(
          timeSlotResponse
        )
      ) {

        timeSlotData =
          timeSlotResponse;

      } else if (
        Array.isArray(
          timeSlotResponse?.data
        )
      ) {

        timeSlotData =
          timeSlotResponse.data;

      } else if (
        Array.isArray(
          timeSlotResponse?.content
        )
      ) {

        timeSlotData =
          timeSlotResponse.content;

      }


      console.log(
        "Final Faculty List:",
        facultyData
      );


      console.log(
        "Final Time Slot List:",
        timeSlotData
      );


      setFacultyList(
        facultyData
      );


      setTimeSlotList(
        timeSlotData
      );


    } catch (error) {

      console.error(
        "Dropdown loading error:",
        error
      );


      console.error(
        "Server response:",
        error?.response?.data
      );


      setFacultyList([]);

      setTimeSlotList([]);


      setMessage(

        error?.response?.data?.message ||

        error?.response?.data?.error ||

        error?.message ||

        "Unable to load Faculty or Time Slots."

      );


    } finally {

      setLoading(false);

    }

  };


  // =====================================================
  // HANDLE INPUT CHANGE
  // =====================================================

  const handleChange = (
    event
  ) => {

    const {

      name,

      value,

      checked,

      type

    } = event.target;


    setFormData(
      previous => ({

        ...previous,

        [name]:
          type === "checkbox"
            ? checked
            : value

      })
    );


    setErrors(
      previous => ({

        ...previous,

        [name]: ""

      })
    );

  };


  // =====================================================
  // VALIDATION
  // =====================================================

  const validate = () => {

    const temp = {};


    if (
      !formData.facultyId
    ) {

      temp.facultyId =
        "Faculty is required.";

    }


    if (
      !formData.dayOfWeek
    ) {

      temp.dayOfWeek =
        "Day is required.";

    }


    if (
      !formData.timeSlotId
    ) {

      temp.timeSlotId =
        "Time Slot is required.";

    }


    setErrors(
      temp
    );


    return (
      Object.keys(temp).length === 0
    );

  };


  // =====================================================
  // SAVE
  // =====================================================

  const handleSave = async () => {

    if (
      !validate()
    ) {

      return;

    }


    try {

      setSaving(true);

      setMessage("");


      /*
       * Convert IDs to numbers.
       *
       * This avoids sending:
       *
       * "1"
       *
       * instead of:
       *
       * 1
       */

      const payload = {

        facultyId:
          Number(
            formData.facultyId
          ),

        dayOfWeek:
          formData.dayOfWeek,

        timeSlotId:
          Number(
            formData.timeSlotId
          ),

        available:
          formData.available

      };


      console.log(
        "Faculty Availability Payload:",
        payload
      );


      // =================================================
      // UPDATE
      // =================================================

      if (
        availability
      ) {

        await facultyAvailabilityService
          .updateAvailability(

            availability.availabilityId,

            payload

          );

      }

      // =================================================
      // CREATE
      // =================================================

      else {

        await facultyAvailabilityService
          .createAvailability(
            payload
          );

      }


      // Reload table

      await reload();


      // Reset form

      setFormData({
        ...initialState
      });


      // Close dialog

      onClose();


    } catch (error) {

      console.error(
        "Save Availability Error:",
        error
      );


      console.error(
        "Server Response:",
        error?.response?.data
      );


      setMessage(

        error?.response?.data?.message ||

        error?.response?.data?.error ||

        error?.message ||

        "Unable to save Faculty Availability."

      );


    } finally {

      setSaving(false);

    }

  };


  // =====================================================
  // RENDER
  // =====================================================

  return (

    <Dialog

      open={
        open
      }

      onClose={
        saving
          ? undefined
          : onClose
      }

      maxWidth="md"

      fullWidth

    >

      <DialogTitle>

        {availability

          ? "Update Faculty Availability"

          : "Add Faculty Availability"

        }

      </DialogTitle>


      <DialogContent
        dividers
      >


        {/* ERROR MESSAGE */}

        {message && (

          <Alert

            severity="error"

            sx={{
              mb: 2
            }}

            onClose={() =>
              setMessage("")
            }

          >

            {message}

          </Alert>

        )}


        {/* LOADING */}

        {loading ? (

          <Box

            sx={{

              display:
                "flex",

              justifyContent:
                "center",

              alignItems:
                "center",

              minHeight:
                200

            }}

          >

            <CircularProgress />

          </Box>

        ) : (

          <Grid

            container

            spacing={3}

            sx={{
              mt: 0.5
            }}

          >


            {/* =========================================
                FACULTY
            ========================================= */}

            <Grid
              item
              xs={12}
              md={6}
            >

              <TextField

                select

                fullWidth

                label="Faculty"

                name="facultyId"

                value={
                  formData.facultyId
                }

                onChange={
                  handleChange
                }

                error={
                  !!errors.facultyId
                }

                helperText={
                  errors.facultyId
                }

                required

              >

                <MenuItem value="">

                  Select Faculty

                </MenuItem>


                {facultyList.map(
                  (faculty) => (

                    <MenuItem

                      key={
                        faculty.facultyId
                      }

                      value={
                        faculty.facultyId
                      }

                    >

                      {faculty.facultyCode
                        ? `${faculty.facultyCode} - `
                        : ""
                      }

                      {faculty.facultyName
                        ||
                        `${faculty.firstName || ""} ${faculty.lastName || ""}`
                      }

                    </MenuItem>

                  )
                )}

              </TextField>

            </Grid>


            {/* =========================================
                DAY
            ========================================= */}

            <Grid
              item
              xs={12}
              md={6}
            >

              <TextField

                select

                fullWidth

                label="Day"

                name="dayOfWeek"

                value={
                  formData.dayOfWeek
                }

                onChange={
                  handleChange
                }

                error={
                  !!errors.dayOfWeek
                }

                helperText={
                  errors.dayOfWeek
                }

                required

              >

                <MenuItem value="">

                  Select Day

                </MenuItem>


                {DAYS.map(
                  (day) => (

                    <MenuItem

                      key={day}

                      value={day}

                    >

                      {day}

                    </MenuItem>

                  )
                )}

              </TextField>

            </Grid>


            {/* =========================================
                TIME SLOT
            ========================================= */}

            <Grid
              item
              xs={12}
            >

              <TextField

                select

                fullWidth

                label="Time Slot"

                name="timeSlotId"

                value={
                  formData.timeSlotId
                }

                onChange={
                  handleChange
                }

                error={
                  !!errors.timeSlotId
                }

                helperText={
                  errors.timeSlotId
                }

                required

              >

                <MenuItem value="">

                  Select Time Slot

                </MenuItem>


                {timeSlotList.map(
                  (slot) => (

                    <MenuItem

                      key={
                        slot.timeSlotId
                      }

                      value={
                        slot.timeSlotId
                      }

                    >

                      {slot.startTime}

                      {" - "}

                      {slot.endTime}

                    </MenuItem>

                  )
                )}

              </TextField>

            </Grid>


            {/* =========================================
                AVAILABLE
            ========================================= */}

            <Grid
              item
              xs={12}
            >

              <FormControlLabel

                control={

                  <Switch

                    checked={
                      formData.available
                    }

                    onChange={
                      handleChange
                    }

                    name="available"

                  />

                }

                label="Faculty Available"

              />

            </Grid>


          </Grid>

        )}

      </DialogContent>


      {/* ===============================================
          ACTION BUTTONS
      =============================================== */}

      <DialogActions>

        <Button

          onClick={
            onClose
          }

          disabled={
            saving
          }

        >

          Cancel

        </Button>


        <Button

          variant="contained"

          onClick={
            handleSave
          }

          disabled={
            saving ||
            loading
          }

        >

          {saving

            ? "Saving..."

            : availability

              ? "Update"

              : "Save"

          }

        </Button>

      </DialogActions>


    </Dialog>

  );

};


export default FacultyAvailabilityForm;