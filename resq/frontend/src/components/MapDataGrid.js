import React, { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import { DataGrid, GridToolbar } from '@mui/x-data-grid';
import { viewAllNeeds } from '../AppService';
import { Node, RootNode } from '../CategoryTree';

const categoryData = [
  { id: 67, type: 'Food & Water' },
  { id: 66, type: 'Water Supply' },
  { id: 65, type: 'Bottled Water' },
  { id: 64, type: 'Non-perishable Food' },
  { id: 61, type: 'Energy Bars' },
  { id: 60, type: 'Canned Food' },
  { id: 62, type: 'Dried Fruits' },
  { id: 63, type: 'Baby Food' },
  { id: 39, type: 'Clothing' },
  { id: 35, type: 'Protective Gear' },
  { id: 33, type: 'Dust Mask' },
  { id: 34, type: 'Reflective Vest' },
  { id: 32, type: 'Safety Goggles' },
  { id: 31, type: 'Work Gloves' },
  { id: 30, type: 'Helmet' },
  { id: 38, type: 'Footwear' },
  { id: 36, type: 'Boots' },
  { id: 37, type: 'Sneakers' },
  { id: 24, type: 'General Clothing' },
  { id: 22, type: 'Scarf' },
  { id: 23, type: 'Hat' },
  { id: 19, type: 'Jacket' },
  { id: 20, type: 'Coat' },
  { id: 16, type: 'Jeans' },
  { id: 17, type: 'Sweater' },
  { id: 14, type: 'Long-sleeve t-shirt' },
  { id: 18, type: 'Sweatshirt' },
  { id: 21, type: 'Pajamas' },
  { id: 13, type: 'T-shirt' },
  { id: 15, type: 'Pants' },
  { id: 29, type: 'Weather-Specific Clothing' },
  { id: 27, type: 'Insulated Jacket' },
  { id: 25, type: 'Overcoat' },
  { id: 26, type: 'Raincoat' },
  { id: 28, type: 'Thermal Underwear' },
  { id: 91, type: 'Transportation' },
  { id: 87, type: 'Evacuation & Delivery Vehicles' },
  { id: 85, type: 'Van' },
  { id: 84, type: 'Bus' },
  { id: 86, type: 'Truck' },
  { id: 90, type: 'Fuel Supply' },
  { id: 88, type: 'Fuel Reserves' },
  { id: 89, type: 'Fuel Containers' },
  { id: 83, type: 'Emergency Vehicles' },
  { id: 82, type: 'Fire Truck' },
  { id: 80, type: 'Ambulance' },
  { id: 81, type: 'Rescue Vehicle' },
  { id: 48, type: 'Shelter & Sleeping' },
  { id: 47, type: 'Shelter Accessories' },
  { id: 44, type: 'Tent' },
  { id: 45, type: 'Tarp' },
  { id: 46, type: 'Heater' },
  { id: 43, type: 'Sleep Accessories' },
  { id: 41, type: 'Sleeping Pad' },
  { id: 40, type: 'Sleeping Bag' },
  { id: 42, type: 'Blanket' },
  { id: 59, type: 'Tools & Equipment' },
  { id: 52, type: 'Search and Rescue Tools' },
  { id: 49, type: 'Flashlights' },
  { id: 50, type: 'Batteries' },
  { id: 51, type: 'Shovels' },
  { id: 58, type: 'Communication Equipment' },
  { id: 55, type: 'Whistles' },
  { id: 53, type: 'Two-way radios' },
  { id: 57, type: 'Maps' },
  { id: 54, type: 'Megaphones' },
  { id: 56, type: 'GPS Devices' },
  { id: 79, type: 'Medical Supplies & Special Needs' },
  { id: 75, type: 'Baby Supplies' },
  { id: 74, type: 'Baby Wipes' },
  { id: 72, type: 'Diapers' },
  { id: 73, type: 'Formula' },
  { id: 71, type: 'First Aid Kit' },
  { id: 68, type: 'Bandages' },
  { id: 69, type: 'Antiseptic Wipes' },
  { id: 70, type: 'Pain Relievers' },
  { id: 78, type: 'Prescription Medications' },
  { id: 77, type: 'Allergy Medications' },
  { id: 76, type: 'Chronic Medications' },
  { id: 12, type: 'Human Resource' },
  { id: 11, type: 'Support Staff' },
  { id: 9, type: 'Trained First Aid Provider' },
  { id: 1, type: 'Doctor' },
  { id: 3, type: 'Search and Rescue Personnel' },
  { id: 2, type: 'Nurse' },
  { id: 8, type: 'Psychiatrist' },
  { id: 10, type: 'Translator' },
  { id: 7, type: 'Driver' },
  { id: 5, type: 'C License' },
  { id: 6, type: 'G License' },
  { id: 4, type: 'B License' },
];

const columns = [
  { field: 'id', headerName: 'ID', width: 90, flex: 0.5 },
  { field: 'description', headerName: 'Description', width: 200, flex: 1 },
  { field: 'categoryTreeId', headerName: 'Category', width: 150, flex: 1 },
  { field: 'quantity', headerName: 'Quantity', type: 'number', width: 100, flex: 0.5 },
  { field: 'location', headerName: 'Location', width: 160, flex: 1 },
];

export default function MapDataGrid() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [categoryTree, setCategoryTree] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await viewAllNeeds();
        setData(response.data.map(need => ({
          id: need.id,
          description: need.description,
          categoryTreeId: need.categoryTreeId,
          quantity: need.quantity,
          location: `${need.latitude}, ${need.longitude}`,
          createdDate: need.createdDate,
          status: need.status,
          size: need.size
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
    <Box sx={{ height: 400, width: '100%' }}>
      <DataGrid
        rows={data}
        columns={columns}
        components={{ Toolbar: GridToolbar }}
        sx={{ '& .MuiDataGrid-toolbarContainer': { justifyContent: 'flex-end', marginBottom: '16px' } }}
        initialState={{ pagination: { page: 0, pageSize: 10 } }}
        loading={loading}
        pageSizeOptions={[5, 10, 15, 20, 25]}
        autoHeight
        checkboxSelection
        disableRowSelectionOnClick
      />
    </Box>
  );
}
