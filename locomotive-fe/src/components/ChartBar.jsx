/* eslint-disable no-unused-vars */
import React, { useEffect, useState } from 'react';
import { axisClasses, BarChart } from '@mui/x-charts';
import { Box } from '@mui/material';

const ChartBar = () => {
  const [chartData, setChartData] = useState([]);
  const [isDataEmpty, setIsDataEmpty] = useState(true);

  async function getChartData() {
    try {
      fetch('http://localhost:8081/status/all')
        .then((response) => response.json())
        .then((data) => {
          const sortedData = data.sort((a, b) => {
            const numA = parseInt(a.locomotiveName.replace('Lokomotif ', ''));
            const numB = parseInt(b.locomotiveName.replace('Lokomotif ', ''));
            return numA - numB;
          });
          console.log('Fetched data: ', data);
          setChartData(sortedData);

          if (data.length > 0) {
            setIsDataEmpty(false);
          } else {
            setIsDataEmpty(true);
          }
        });
    } catch (error) {
      console.error('Failed to fetch chart data: ', error);
    }
  }

  useEffect(() => {
    getChartData();
  }, []);

  const options = {
    yAxis: [
      {
        label: 'Locomotives',
      },
    ],
    width: 1000,
    height: 450,
    sx: {
      [`.${axisClasses.left} .${axisClasses.label}`]: {
        transform: 'translate(-10px, 0)',
      },
    },
  };

  return (
    <>
      <Box sx={{ maxWidth: 800, mx: 4, my: 4 }}>
        <BarChart 
          dataset={chartData}
          xAxis={[{ scaleType: 'band', dataKey: 'locomotiveName' }]}
          series={[
            {dataKey: 'active', label: 'Active'},
            {dataKey: 'nonActive', label: 'Inactive'},
            {dataKey: 'maintenance', label: 'Maintenance'},
          ]}
          {...options}
        />
      </Box>
    </>
  );
};

export default ChartBar;
