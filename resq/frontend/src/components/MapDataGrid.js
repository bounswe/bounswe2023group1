import * as React from 'react';
import Box from '@mui/material/Box';
import {
  DataGridPremium,
  GridToolbar,
  useGridApiRef,
  useKeepGroupedColumnsHidden,
} from '@mui/x-data-grid-premium';
import { XGrid } from '@mui/x-data-grid-premium';
import { useDemoData } from '@mui/x-data-grid-generator';
import { DataGrid } from '@mui/x-data-grid';

const columns = [
  { field: 'id', headerName: 'ID', width: 2, flex:0.5 },
  {
    field: 'Name',
    headerName: <strong>{'Name'}</strong>,
    width: 100,
    align: 'left',
    flex: 1, 
  },
  {
    field: 'Type',
    headerName:  <strong>{'Type'}</strong>,
    width: 100,
    align: 'left',
    flex: 1, 
  },
  {
    field: 'Quantity',
    headerName:  <strong>{'Quantity'}</strong>,
    type: 'number',
    width: 80,
    align: 'left',
    flex: 0.5, 
  },
  {
    field: 'Location',
    headerName:  <strong>{'Location'}</strong>,
    align: 'left',
    flex: 0.5, 
  },
];

const rows = [
  { id: 1, Name: 'Baby Food', Type:  ['Baby', 'Food'], Quantity: '30 pck', Location: 'Bebek' },
  { id: 2, Name: 'Baby Food', Type:  ['Baby', 'Food'], Quantity: '160 jar', Location: 'Sariyer' },
  { id: 3, Name: 'Bottled Water', Type:  'Water', Quantity: '200 bttl', Location: 'Etiler' },
  { id: 4, Name: 'Bread', Type:  'Food', Quantity: '90 pcs', Location: 'Etiler' },
  { id: 5, Name: 'Canned Food', Type:  'Food', Quantity: '130 can', Location: 'Sisli' },
  { id: 6, Name: 'Canned Food', Type:  'Food', Quantity: '70 can', Location: 'Levent' },
  { id: 7, Name: 'Child Clothing', Type:  'Cloth', Quantity: '40 item', Location: 'Sariyer' },
  { id: 8, Name: 'Diapers', Type:  'Baby', Quantity: '450 pieces', Location: 'Gayrettepe' },
  { id: 9, Name:  'Doctor',  Type:  'Health', Quantity: '8 person', Location: 'Taksim' },
  { id: 10, Name:  'Heater',  Type:  'Heath', Quantity: '20 big', Location: 'Kadiköy' },
  { id: 11, Name:  'Heater',  Type:  'Heath', Quantity: '32 small', Location: 'Besiktas' },
  { id: 12, Name:  'Medicine',  Type:  'Health', Quantity: '60 pck', Location: 'Kadikoy' },
  { id: 13, Name:  'Medicine',  Type:  'Health', Quantity: '100 serum', Location: 'Osmanbey' },
  { id: 14, Name:  'Translator',  Type:  'Lang', Quantity: '12 person', Location: 'Karakoy' },
];


export default function MapDataGrid() {
  return (
    <Box sx={{ height: 400, width: "33%"}}>
      <DataGrid
        rows={rows}
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
        pageSizeOptions={[5, 10, 15, 20, 25]}
        autoHeight
        checkboxSelection
        disableRowSelectionOnClick
      />
    </Box>
  );
}

