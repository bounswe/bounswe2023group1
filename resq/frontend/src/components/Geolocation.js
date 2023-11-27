// geocode.js
import axios from 'axios';

const GOOGLE_API_KEY = "YOUR_GOOGLE_API_KEY"; // Replace with your actual API key

// Function to perform reverse geocoding
export const reverseGeocode = async (latitude, longitude) => {
    try {
        const response = await axios.get(`https://maps.googleapis.com/maps/api/geocode/json?latlng=${latitude},${longitude}&key=AIzaSyCehlfJwJ-V_xOWZ9JK3s0rcjkV2ga0DVg`);
        const cityName = response.data.results[0]?.formatted_address || 'Unknown Location';
        return cityName;
    } catch (error) {
        console.error('Error fetching location name:', error);
        return 'Unknown Location';
    }
};
