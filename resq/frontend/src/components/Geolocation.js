// geocode.js
import axios from 'axios';

const GOOGLE_API_KEY = "AIzaSyAQxkir-6QWOzrdH3MflAd8h_q3G8v2Uqs";

// Function to perform reverse geocoding
const reverseGeocode = async (latitude, longitude) => {
    try {
        const response = await axios.get(`https://maps.googleapis.com/maps/api/geocode/json?latlng=${latitude},${longitude}&key=${GOOGLE_API_KEY}`,
            {
                headers: {
                    Authorization: null,
                },
            });
        return response.data.results[0]?.formatted_address || 'Unknown Location';
    } catch (error) {
        console.error('Error fetching location name:', error);
        return 'Unknown Location';
    }
};

export default reverseGeocode;