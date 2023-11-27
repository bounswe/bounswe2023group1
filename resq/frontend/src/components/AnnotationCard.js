import React, { useEffect, useState } from 'react';
import { Box, Typography, Button, Chip, Dialog, DialogTitle, DialogContent, DialogContentText } from '@mui/material';
import reverseGeocode from './Geolocation';

const AnnotationCard = ({ annotation }) => {
    const [locationName, setLocationName] = useState('Unknown Location');
    const [open, setOpen] = useState(false); // Added for Dialog control
    const [longDescription, setLongDescription] = useState(''); // State to hold long description

    useEffect(() => {
        if (annotation.latitude && annotation.longitude) {
            reverseGeocode(annotation.latitude, annotation.longitude)
                .then((name) => setLocationName(name))
                .catch((error) => console.error('Error fetching location name:', error));
        }
    }, [annotation.latitude, annotation.longitude]);

    const handleViewMore = () => {
        setLongDescription(annotation.long_description || 'Long description not available.');
        setOpen(true); // Open the dialog
    };


    const handleClose = () => {
        setOpen(false); // Close the dialog
    };

    // Dialog component to show long description
    const LongDescriptionDialog = () => (
        <Dialog open={open} onClose={handleClose}>
            <DialogTitle>{annotation.title}</DialogTitle>
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
            marginY={2}
            display="flex"
            flexDirection="column"
        >
            <Typography variant="h6">{annotation.title}</Typography>
            <Typography variant="subtitle1" color="textSecondary">
                {annotation.category}
            </Typography>
            <Typography variant="body1">{annotation.short_description}</Typography>

            {/* Date and Location Data */}
            {annotation.date && (
                <Typography variant="body2">Date: {new Date(annotation.date).toLocaleDateString()}</Typography>
            )}
            {locationName !== 'Unknown Location' && (
                <Typography variant="body2">Location: {locationName}</Typography>
            )}

            {/* Additional Metadata */}
            {annotation.additionalMetadata && (
                <Box marginTop={1}>
                    {Object.entries(annotation.additionalMetadata).map(([key, value]) => (
                        <Chip key={key} label={`${key}: ${value}`} variant="outlined" size="small" />
                    ))}
                </Box>
            )}

            <Button color="primary" onClick={() => handleViewMore(annotation)}>
                View More
            </Button>

            {/* Long Description Dialog */}
            <LongDescriptionDialog />
        </Box>
    );
};

export default AnnotationCard;
