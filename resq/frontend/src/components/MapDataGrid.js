import * as React from 'react';
import Box from '@mui/material/Box';
import { DataGrid, GridToolbar } from '@mui/x-data-grid';
import { useEffect, useState } from 'react';
import { viewAllNeeds } from '../AppService';


const columns = [
  { field: 'id', headerName: 'ID', width: 2, flex: 0.5 },
  {
    field: 'Name',
    headerName: <strong>{'Name'}</strong>,
    width: 100,
    align: 'left',
    flex: 1,
  },
  {
    field: 'Type',
    headerName: <strong>{'Type'}</strong>,
    width: 100,
    align: 'left',
    flex: 1,
  },
  {
    field: 'Quantity',
    headerName: <strong>{'Quantity'}</strong>,
    type: 'number',
    width: 80,
    align: 'left',
    flex: 0.5,
  },
  {
    field: 'Location',
    headerName: <strong>{'Location'}</strong>,
    align: 'left',
    flex: 0.5,
  },
];


export default function MapDataGrid() {

  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await viewAllNeeds();
        setData(response.data.map(need => ({
          id: need.id,
          Name: need.name,
          Type: need.type,
          Quantity: need.quantity,
          Location: need.location,
        })));
      } catch (error) {
        console.error("Error fetching data: ", error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  return (
    <Box sx={{ height: 400, width: "100%" }}>
      <DataGrid
        rows={data}
        columns={columns}
        components={{ Toolbar: GridToolbar }}
        sx={{
          '& .MuiDataGrid-toolbarContainer': {
            justifyContent: 'flex-end',
            align: 'right',
            marginBottom: '16px',
          }
        }}
        initialState={{
          pagination: {
            paginationModel: { page: 0, pageSize: 10 },
          },
        }}
        loading={loading}
        pageSizeOptions={[5, 10, 15, 20, 25]}
        autoHeight
        checkboxSelection
        disableRowSelectionOnClick
      />
    </Box>
  );
}

