import {
    Box,
    Button,
    Stack
} from "@mui/material";

import RefreshIcon from "@mui/icons-material/Refresh";
import AutoFixHighIcon from "@mui/icons-material/AutoFixHigh";
import PictureAsPdfIcon from "@mui/icons-material/PictureAsPdf";
import TableViewIcon from "@mui/icons-material/TableView";
import PrintIcon from "@mui/icons-material/Print";
import DeleteIcon from "@mui/icons-material/Delete";

const TimetableToolbar = ({
    onGenerate,
    onRefresh,
    onExportPdf,
    onExportExcel,
    onPrint,
    onDelete,
    loading = false
}) => {

    return (

        <Box sx={{ mb: 3 }}>

            <Stack
                direction="row"
                spacing={2}
                flexWrap="wrap"
            >

                <Button
                    variant="contained"
                    color="primary"
                    startIcon={<AutoFixHighIcon />}
                    onClick={onGenerate}
                    disabled={loading}
                >
                    Generate
                </Button>

                <Button
                    variant="outlined"
                    startIcon={<RefreshIcon />}
                    onClick={onRefresh}
                    disabled={loading}
                >
                    Refresh
                </Button>

                <Button
                    variant="contained"
                    color="error"
                    startIcon={<DeleteIcon />}
                    onClick={onDelete}
                    disabled={loading}
                >
                    Delete
                </Button>

                <Button
                    variant="contained"
                    color="secondary"
                    startIcon={<PictureAsPdfIcon />}
                    onClick={onExportPdf}
                    disabled={loading}
                >
                    Export PDF
                </Button>

                <Button
                    variant="contained"
                    color="success"
                    startIcon={<TableViewIcon />}
                    onClick={onExportExcel}
                    disabled={loading}
                >
                    Export Excel
                </Button>

                <Button
                    variant="outlined"
                    startIcon={<PrintIcon />}
                    onClick={onPrint}
                >
                    Print
                </Button>

            </Stack>

        </Box>

    );

};

export default TimetableToolbar;