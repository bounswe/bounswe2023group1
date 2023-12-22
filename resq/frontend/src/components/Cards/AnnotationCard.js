import React, { useEffect, useState } from 'react';
import { Box, Typography, Button, Chip, Dialog, DialogTitle, DialogContent, DialogContentText } from '@mui/material';
import reverseGeocode from '../Geolocation';

const AnnotationCard = ({ item }) => {
    const [locationName, setLocationName] = useState('Unknown Location');
    const [open, setOpen] = useState(false); // Added for Dialog control
    const [longDescription, setLongDescription] = useState(''); // State to hold long description

    useEffect(() => {
        if (item.latitude && item.longitude) {
            reverseGeocode(item.latitude, item.longitude)
                .then((name) => setLocationName(name))
                .catch((error) => console.error('Error fetching location name:', error));
        }
    }, [item.latitude, item.longitude]);

    const handleViewMore = () => {
        setLongDescription(item.long_description || 'Long description not available.');
        setOpen(true); // Open the dialog
    };


    const handleClose = () => {
        setOpen(false); // Close the dialog
    };

    // Dialog component to show long description
    const LongDescriptionDialog = () => (
        <Dialog open={open} onClose={handleClose}>
            <DialogTitle>{item.title}</DialogTitle>
            <DialogContent>
                <DialogContentText>
                    {longDescription || 'Long description not available.'}
                </DialogContentText>
            </DialogContent>
        </Dialog>
    );

    return (
        <Box
            padding={2}
            border={1}
            borderColor="grey.300"
            borderRadius={4}
            display="flex"
            flexDirection="column"
            key={item.type+item.id}
            id={item.type+item.id}
            className={"anno-root"}
        >
            <Typography variant="h6">{item.title}</Typography>
            <Typography variant="subtitle1" color="textSecondary">
                {item.category}
            </Typography>
            <Typography variant="body1">{item.short_description}</Typography>

            {/* Date and Location Data */}
            {item.date && (
                <Typography variant="body2">Date: {new Date(item.date).toLocaleDateString()}</Typography>
            )}
            {locationName !== 'Unknown Location' && (
                <Typography variant="body2">Location: {locationName}</Typography>
            )}

            {/* Additional Metadata */}
            {item.additionalMetadata && (
                <Box marginTop={1}>
                    {Object.entries(item.additionalMetadata).map(([key, value]) => (
                        <Chip key={key} label={`${key}: ${value}`} variant="outlined" size="small" />
                    ))}
                </Box>
            )}

            <Button color="primary" onClick={() => handleViewMore(item)}>
                View More
            </Button>

            {/* Long Description Dialog */}
            <LongDescriptionDialog />
        </Box>
    );
};

export default AnnotationCard;
