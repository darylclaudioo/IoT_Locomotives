/* eslint-disable no-unused-vars */
import React, { useEffect, useState } from 'react';
import {
  Box,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Typography,
  TablePagination
} from '@mui/material';

const LocomotiveTable = () => {
  const [tableData, setTableData] = useState([]);
  const [isDataEmpty, setIsDataEmpty] = useState(true);
  const [page, setPage] = useState(0); // Current page
  const [rowsPerPage, setRowsPerPage] = useState(5); // Rows per page

  // Fetch data from the API
  async function getTableData() {
    try {
      fetch('http://localhost:8081/locomotives/all')
        .then((response) => response.json())
        .then((data) => {
          console.log('Fetched data: ', data);
          setTableData(data);

          if (data.length > 0) {
            setIsDataEmpty(false);
          } else {
            setIsDataEmpty(true);
          }
        });
    } catch (error) {
      console.error('Failed to fetch table data: ', error);
    }
  }

  useEffect(() => {
    getTableData();
  }, []);

  // Handle page change
  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  // Handle rows per page change
  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0); // Reset to the first page when rows per page changes
  };

  // Calculate the subset of data to display based on the current page and rows per page
  const paginatedData = tableData.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage);

  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', height: '100vh', maxWidth: '100%', mx: 'auto', my: 4 }}>
      <Typography variant="h6" align="center" gutterBottom>
        Locomotive List
      </Typography>

      {/* Conditionally render the table or a 'No Data' message */}
      {isDataEmpty ? (
        <Typography variant="h6" align="center" gutterBottom>
          No Data Available
        </Typography>
      ) : (
        <>
          <TableContainer component={Paper} sx={{ flexGrow: 1, overflow: 'auto' }}>
            <Table stickyHeader>
              <TableHead>
                <TableRow>
                  <TableCell align="left"><strong>Name</strong></TableCell>
                  <TableCell align="center"><strong>Dimension</strong></TableCell>
                  <TableCell align="center"><strong>Status</strong></TableCell>
                  <TableCell align="center"><strong>Date</strong></TableCell>
                  <TableCell align="center"><strong>Time</strong></TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {paginatedData.map((row) => (
                  <TableRow key={row.kodeLoco}>
                    <TableCell align="left">{row.namaLoco}</TableCell>
                    <TableCell align="center">{row.dimensiLoco}</TableCell>
                    <TableCell align="center">{row.status}</TableCell>
                    <TableCell align="center">{row.tanggal}</TableCell>
                    <TableCell align="center">{row.jam}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>

          {/* Pagination controls */}
          <TablePagination
            component="div"
            count={tableData.length} // Total number of rows
            page={page}
            onPageChange={handleChangePage}
            rowsPerPage={rowsPerPage}
            onRowsPerPageChange={handleChangeRowsPerPage}
            rowsPerPageOptions={[5, 10, 25]} // Options for rows per page
          />
        </>
      )}
    </Box>
  );
};

export default LocomotiveTable;
