import React, { useEffect, useState } from 'react';
import { Box, Typography, Button, Chip } from '@mui/material';
import reverseGeocode from './Geolocation';

const AnnotationCard = ({ annotation }) => {
    const [locationName, setLocationName] = useState('Unknown Location');

    useEffect(() => {
        if (annotation.latitude && annotation.longitude) {
            reverseGeocode(annotation.latitude, annotation.longitude)
                .then((name) => setLocationName(name))
                .catch((error) => console.error('Error fetching location name:', error));
        }
    }, [annotation.latitude, annotation.longitude]);

    const handleViewMore = (annotationData) => {
    };

    return (
        <Box padding={2} border={1} borderColor="grey.300" borderRadius={4} marginY={2}>
            <Typography variant="h6">{annotation.title}</Typography>
            <Typography variant="subtitle1" color="textSecondary">{annotation.category}</Typography>
            <Typography variant="body1">{annotation.short_description}</Typography>

            {/* Date and Location Data */}
            {annotation.date && (
                <Typography variant="body2">Date: {new Date(annotation.date).toLocaleDateString()}</Typography>
            )}
            {locationName !== 'Unknown Location' && (
                <Typography variant="body2">Location: {locationName}</Typography>
            )}

            {/* Additional Metadata */}
            {annotation.additionalMetadata && Object.entries(annotation.additionalMetadata).map(([key, value]) => (
                <Chip key={key} label={`${key}: ${value}`} variant="outlined" size="small" />
            ))}

            <Button color="primary" onClick={() => handleViewMore(annotation)}>View More</Button>
        </Box>
    );
};

export default AnnotationCard;
