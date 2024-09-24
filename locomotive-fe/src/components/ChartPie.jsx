/* eslint-disable no-unused-vars */
import React, { useEffect, useState } from 'react';
import { PieChart } from '@mui/x-charts/PieChart';
import { Box } from '@mui/material';

const ChartPie = () => {
  const [chartData, setChartData] = useState([]);
  
  async function getChartData() {
    try {
      fetch('http://localhost:8081/summary/latest')
        .then((response) => response.json())
        .then((data) => {
          console.log('Fetched data: ', data);
          setChartData(data);
        });
    } catch (error) {
      console.error('Failed to fetch chart data: ', error);
    }
  }

  useEffect(() => {
    getChartData();
  }, []);

  return (
    <Box sx={{ maxWidth: 400, mx: 'auto', my: 4 }}>
      <PieChart 
        series={[
          {
            data: [
              { value: chartData.active, label: 'Active' },
              { value: chartData.nonActive, label: 'Inactive' },
              { value: chartData.maintenance, label: 'Maintenance' },
            ]
          }
        ]}
        width={400}
        height={200}
      />
    </Box>
  );
};

export default ChartPie;
