/* eslint-disable no-unused-vars */
import { Box, Grid, Typography } from '@mui/material';
import React from 'react';
import TableData from './components/TableData';

const App = () => {
  return (
    <Box sx={{ width: '100vw', height: '100vh', display: 'flex', flexDirection: 'column' }}>
      {/* Container for the full-screen table */}
      <Grid container direction={'row'} sx={{ flexGrow: 1 }}>
        <TableData />
      </Grid>
    </Box>
  );
};

export default App;
