// noinspection JSUnusedLocalSymbols

import * as React from 'react';
import { useEffect, useState } from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Container from '@mui/material/Container';
import DisasterMap from "../components/DisasterMap";
import { cards } from "../components/ListCards";
import { AmountSelector, MultiCheckbox } from "../components/MultiCheckbox";
import { DatePicker } from "@mui/x-date-pickers";
import dayjs from "dayjs";
import AnnotationCard from "../components/AnnotationCard";


const customTheme = createTheme({
    palette: {
        primary: {
            main: '#FF0000',
        },
    },
});


const mock_markers = [
    {
        type: "Annotation",
        latitude: 41.089,
        longitude: 29.053,
        category: "Health",
        title: "First Aid Clinic",
        short_description: "First aid clinic and emergency wound care. Open 24 hours.",
        long_description: "Welcome to our First Aid Clinic, a dedicated facility committed to providing immediate and " +
            "compassionate healthcare 24 hours a day. Our experienced team of healthcare professionals specializes in " +
            "emergency wound care and first aid assistance, ensuring you receive prompt attention when you need it most. " +
            "From minor cuts to more serious injuries, our clinic is equipped to handle a range of medical concerns, " +
            "promoting healing and preventing complications.",
        date: "26/11/2023"
    },

    {
        type: "Annotation",
        latitude: 40.089,
        longitude: 29.053,
        category: "Health",
        title: "First Aid Clinic",
        short_description: "Emergency wound care available 24/7.",
        long_description: "Dedicated to prompt, compassionate care for all emergency first aid needs...",
        date: "26/11/2023",
        additionalMetadata: {
            "Staff Expertise": "Trauma specialists for children",
            "Contact Number": "+90 555 555 5555",
        }
    },

    {
        type: "Annotation",
        latitude: 40.789,
        longitude: 29.053,
        category: "Medical Facility",
        title: "Emergency Response Clinic",
        short_description: "Urgent medical care for disaster victims.",
        long_description: "Providing immediate medical attention, trauma care, and emergency services...",
        date: "01/10/2023",
        additionalMetadata: {
            "Services Offered": "Trauma care, Emergency surgery, First aid",
            "Contact Info": "emergency.clinic@response.org"
        }
    },


    {
        type: "Request",
        latitude: 37.08,
        longitude: 31.05,
        requester: {
            name: "Müslüm",
            surname: "Ertürk"
        },
        urgency: "HIGH",
        needs: [
            {
                name: "Canned food",
                description: "Preferably a variety of different foodstuffs.",
                quantity: 3
            },
            {
                name: "Diapers",
                description: "Preferably individually packed.",
                quantity: 2
            }
        ],
        status: "TODO"
    },
    {
        type: "Request",
        latitude: 41.1,
        longitude: 29.15,
        requester: {
            name: "Ali",
            surname: "Er"
        },
        urgency: "LOW",
        needs: [
            {
                name: "Power banks",
                category: "Other",
                description: "Power banks for the staff, preferably with cables included.",
                quantity: 30
            },
        ],
        status: "IN_PROGRESS"
    },
    {
        type: "Resource",
        latitude: 41.1,
        longitude: 29.04,
        owner: {
            name: "Te",
            surname: "St"
        },
        urgency: "HIGH",
        resources: [
            {
                name: "Bottled Water",
                category: "Water",
                description: "1.5 liters bottles",
                quantity: 300,
            },
            {
                name: "Canned Beans",
                category: "Food",
                description: "400 gram cans",
                quantity: 500,
            },
        ],
    },
    {
        type: "Annotation",
        latitude: 40.7128,
        longitude: 74.0060,
        category: "Relief Center",
        title: "Local Disaster Relief Hub",
        short_description: "Central point for relief operations and support.",
        long_description: "Coordinating relief efforts, providing shelter, food, water, and basic necessities...",
        date: "02/10/2023",
        additionalMetadata: {
            "Aid Services": "Shelter, Food supplies, Clothing",
            "Volunteer Coordination": "volunteer@reliefcenter.org"
        }
    },

    {
        type: "Annotation",
        latitude: 51.5074,
        longitude: -0.1278,
        category: "Communication Hub",
        title: "Emergency Communication Center",
        short_description: "Communication hub for disaster response coordination.",
        long_description: "Facilitating communication between various response teams, authorities, and the public...",
        date: "03/10/2023",
        additionalMetadata: {
            "Communication Channels": "Radio, Satellite, Internet",
            "Operational Hours": "24/7 during emergency"
        }
    },

    {
        type: "Annotation",
        latitude: 48.8566,
        longitude: 2.3522,
        category: "Supply Point",
        title: "Disaster Supply Depot",
        short_description: "Distribution center for essential supplies and equipment.",
        long_description: "Stocked with food, water, medical supplies, and emergency equipment for distribution...",
        date: "04/10/2023",
        additionalMetadata: {
            "Available Supplies": "Water, Non-perishable food, Medical kits",
            "Distribution Hours": "8am - 8pm"
        }
    },

    {
        type: "Annotation",
        latitude: 41.9028,
        longitude: 12.4964,
        category: "Evacuation Zone",
        title: "Safe Evacuation Area",
        short_description: "Designated safe area for emergency evacuation.",
        long_description: "A secured zone for the community to gather during evacuation, with access to emergency services...",
        date: "05/10/2023",
        additionalMetadata: {
            "Capacity": "Up to 5000 individuals",
            "Facilities": "Temporary shelters, Medical aid, Sanitation"
        }
    },

    ...[...Array(20).keys()].map(i =>
        [...Array(20).keys()].map(j => (
            {
                type: "Request",
                latitude: 37 + 0.5 * i,
                longitude: 31 + 0.5 * j,
                requester: {
                    name: "Müslüm",
                    surname: "Ertürk"
                },
                urgency: "HIGH",
                needs: []
            }))).flat()
]

function getAllCategories(item) {
    // Extract categories based on the type of item
    switch (item.type) {
        case "Annotation":
            // Annotations may have a single category
            return [item?.category];
        case "Resource":
            // Resources may have multiple categories
            return item.resources.map(resource => resource?.category);
        case "Request":
            // Requests may have multiple needs, each with its own category
            return item.needs.map(need => need?.category);
        default:
            return [];
    }
}


const applyFilterTo = (predicate) =>
    item => {
        switch (item.type) {
            case "Annotation":
                return predicate(item)
            case "Resource":
                return !item.resources.every(resource => !predicate(resource))
            case "Request":
                return !item.needs.every(need => !predicate(need))
            default:
                return false
        }
    }

const makeFilterByCategory = categories => {
    if (categories.length === 0)
        return () => true
    return applyFilterTo(
        function (item) {
            return categories.indexOf(item?.category) !== -1;
        }
    )
};

const makeFilterByType = (typeFilter) => item => typeFilter.length === 0 || typeFilter.indexOf(item.type) !== -1

const makeFilterByAmount = ([amount]) => {
    if (typeof amount !== "string" || amount.indexOf("-") === -1)
        return () => true
    const [min, max] = amount.split("-").map(i => parseInt(i))
    return applyFilterTo(function (item) {
        return item.quantity && item.quantity >= min && max >= item.quantity;
    })
};

const makeFilterByDateFrom = (dateFrom) => item => dateFrom === null || !dateFrom.isValid() || !(dateFrom > dayjs(item.date))
const makeFilterByDateTo = (dateTo) => item => dateTo === null || !dateTo.isValid() || !(dateTo < dayjs(item.date))

const makeFilterByBounds = ({ ne: [ne_lat, ne_lng], sw: [sw_lat, sw_lng] }) =>
    function (item) {
        return item.latitude <= ne_lat &&
            item.longitude <= ne_lng &&
            item.latitude >= sw_lat &&
            item.longitude >= sw_lng;
    }


export default function MapDemo() {
    // eslint-disable-next-line no-unused-vars
    const [allMarkers, setAllMarkers] = useState(mock_markers)
    const [shownMarkers, setShownMarkers] = useState(allMarkers)
    const [selectedPoint, setSelectedPoint] = useState(null)
    const [mapCenter, setMapCenter] = useState([39, 34.5])

    const [selectedAnnotation, setSelectedAnnotation] = useState(null);

    const handleAnnotationSelect = (annotation) => {
        setSelectedAnnotation(annotation);
    };

    const [typeFilter, setTypeFilter] = useState([])
    const [dateFromFilter, setDateFromFilter] = useState(null)
    const [dateToFilter, setDateToFilter] = useState(null)
    const [amountFilter, setAmountFilter] = useState([])
    const [categoryFilter, setCategoryFilter] = useState([])
    const [mapBounds, setMapBounds] = useState({ ne: [0, 0], sw: [0, 0] })

    useEffect(() => {
        if (selectedPoint)
            setMapCenter([selectedPoint.latitude, selectedPoint.longitude])
    }, [selectedPoint])

    useEffect(() => setShownMarkers(
        allMarkers
            .filter(makeFilterByCategory(categoryFilter))
            .filter(makeFilterByType(typeFilter))
            .filter(makeFilterByAmount(amountFilter))
            .filter(makeFilterByDateFrom(dateFromFilter))
            .filter(makeFilterByDateTo(dateToFilter))
            .filter(makeFilterByBounds(mapBounds))
    ), [allMarkers, amountFilter, categoryFilter, dateFromFilter, dateToFilter, mapBounds, typeFilter])

    const fetchLocationName = async (latitude, longitude) => {
        try {
            const locationName = await reverseGeocode(latitude, longitude);
            return locationName;
        } catch (error) {
            console.error('Error fetching location name:', error);
            return 'Unknown Location';
        }
    };

    // noinspection JSValidateTypes
    return (
        <ThemeProvider theme={customTheme}>
            <Container maxWidth="100%" style={{ height: "100%", display: "flex", flexDirection: "column" }}>
                <CssBaseline />
                {/* Top Filter UI */}
                <Box sx={{ display: "flex", flexDirection: "row", flexWrap: 'nowrap', margin: "12px", width: "100%", justifyContent: "center" }}>
                    {/* Filter Components */}
                    <MultiCheckbox name={"Type"} choices={["Annotation", "Resource", "Request"]}
                        onChosenChanged={setTypeFilter} />
                    <MultiCheckbox name={"Category"}
                        choices={[
                            ...allMarkers
                                .map(getAllCategories)
                                .flat(),
                            ...categoryFilter
                        ].filter((v, i, array) => v && array.indexOf(v) === i)}
                        onChosenChanged={setCategoryFilter} />
                    <AmountSelector name={"Amount"}
                        onChosenChanged={setAmountFilter} />
                    <DatePicker
                        sx={{ m: 1 }}
                        label="From"
                        format="DD/MM/YYYY"
                        value={dateFromFilter}
                        onChange={e => setDateFromFilter(e)}
                    />
                    <DatePicker
                        sx={{ m: 1 }}
                        label="To"
                        format="DD/MM/YYYY"
                        value={dateToFilter}
                        onChange={e => setDateToFilter(e)}
                    />
                </Box>
                <Box sx={{
                    display: "flex", flexDirection: "row", flexWrap: 'nowrap', margin: "12px", height: "100%", flexGrow: 100
                }}>
                    <Box sx={{ flexBasis: "33%", flexShrink: 0, height: "100%", overflow: "scroll" }}>
                        {/* Annotations Section */}
                        {shownMarkers.filter(marker => marker.type === 'Annotation').map(async (annotation, index) => {
                            const locationName = await fetchLocationName(annotation.latitude, annotation.longitude);
                            return (
                                <AnnotationCard
                                    annotation={{ ...annotation, locationName }} // Include locationName
                                    key={index}
                                    onSelect={handleAnnotationSelect}
                                />
                            );
                        })}
                        <Box sx={{ display: "flex", flexDirection: "column", rowGap: "16px", height: "fit-content" }}>
                            {/* Cards Section */}
                            {shownMarkers.filter(marker => marker.type !== 'Annotation').map((marker, index) => {
                                const SelectedCard = cards[marker.type];
                                const key = marker.id || `${marker.type}-${index}`;
                                return <SelectedCard item={marker} onClick={() => setSelectedPoint(marker)} key={key} />;
                            })}
                        </Box>
                    </Box>

                    <Box sx={{ width: "36px" }} />
                    <Box sx={{ flexGrow: 100 }}>
                        <DisasterMap markers={shownMarkers}
                            mapCenter={mapCenter}
                            setMapCenter={setMapCenter}
                            onPointSelected={setSelectedPoint}
                            onBoundsChanged={setMapBounds}
                        />
                        {/* AnnotationCard Component */}
                        {selectedAnnotation && selectedAnnotation.type === 'Annotation' && (
                            <AnnotationCard annotation={selectedAnnotation} />
                        )}
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
    );
}



