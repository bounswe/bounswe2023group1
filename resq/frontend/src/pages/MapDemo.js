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
import reverseGeocode from "../components/Geolocation";

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
        latitude: 41.085,
        longitude: 29.056,
        category: "Shelter",
        title: "Temporary Shelter Camp",
        short_description: "Temporary shelter camp for displaced individuals and families.",
        long_description: "Our Temporary Shelter Camp offers a safe haven for individuals and families displaced by the disaster. We provide temporary accommodation, basic necessities, and support services. Our goal is to ensure the well-being and comfort of those affected by the disaster while they await further assistance.",
        date: "27/11/2023"
    },
    {
        type: "Annotation",
        latitude: 41.099,
        longitude: 29.047,
        category: "Food",
        title: "Food Distribution Center",
        short_description: "Food distribution center providing meals to disaster survivors.",
        long_description: "The Food Distribution Center is committed to providing nutritious meals to disaster survivors. We offer a variety of food options to meet the dietary needs of individuals and families affected by the disaster. Our team works tirelessly to ensure that no one goes hungry during these challenging times.",
        date: "28/11/2023"
    },
    {
        type: "Annotation",
        latitude: 41.088,
        longitude: 29.552,
        category: "Rescue",
        title: "Search and Rescue Team",
        short_description: "Dedicated search and rescue team for disaster response.",
        long_description: "Our Search and Rescue Team is on standby to locate and rescue individuals who may be trapped or in distress due to the disaster. We are equipped with specialized tools and trained personnel to conduct safe and efficient rescue operations. Saving lives is our top priority.",
        date: "28/11/2023"
    },
    {
        type: "Annotation",
        latitude: 40.092,
        longitude: 29.008,
        category: "Health",
        title: "Emergency Medical Center",
        short_description: "Emergency medical center with specialized trauma care.",
        long_description: "Our Emergency Medical Center is equipped with state-of-the-art facilities to provide specialized trauma care in the aftermath of a disaster. Our medical team is trained to handle critical injuries, perform life-saving procedures, and ensure the well-being of patients. We are available 24/7 to respond to emergencies and provide immediate medical assistance.",
        date: "27/11/2023"
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

    const [typeFilter, setTypeFilter] = useState([])
    const [dateFromFilter, setDateFromFilter] = useState(null)
    const [dateToFilter, setDateToFilter] = useState(null)
    const [amountFilter, setAmountFilter] = useState([])
    const [categoryFilter, setCategoryFilter] = useState([])
    const [mapBounds, setMapBounds] = useState({ ne: [0, 0], sw: [0, 0] })

    const [locationNames, setLocationNames] = useState({});

    useEffect(() => {
        const fetchLocationNames = async () => {
            const names = {};
            for (const marker of mock_markers) {
                const key = `${marker.latitude},${marker.longitude}`;
                if (!names[key]) {
                    const locationName = await reverseGeocode(marker.latitude, marker.longitude);
                    names[key] = locationName;
                }
            }
            setLocationNames(names);
        };
        fetchLocationNames();
    }, []);


    const [selectedAnnotation, setSelectedAnnotation] = useState(null);

    const handlePointSelect = (point) => {
        setSelectedPoint(point);
        if (point && point.type === 'Annotation') {
            setSelectedAnnotation(point);
        }
    };

    useEffect(() => {
        if (selectedPoint) {
            setMapCenter([selectedPoint.latitude, selectedPoint.longitude]);
        }
    }, [selectedPoint]);


    useEffect(() => setShownMarkers(
        allMarkers
            .filter(makeFilterByCategory(categoryFilter))
            .filter(makeFilterByType(typeFilter))
            .filter(makeFilterByAmount(amountFilter))
            .filter(makeFilterByDateFrom(dateFromFilter))
            .filter(makeFilterByDateTo(dateToFilter))
            .filter(makeFilterByBounds(mapBounds))
    ), [allMarkers, amountFilter, categoryFilter, dateFromFilter, dateToFilter, mapBounds, typeFilter])

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
                        {shownMarkers.filter(marker => marker.type === 'Annotation').map((annotation, index) => (
                            <AnnotationCard
                                annotation={{ ...annotation, locationName: locationNames[`${annotation.latitude},${annotation.longitude}`] || 'Loading...' }}
                                key={`annotation-${index}`}
                                isSelected={selectedAnnotation && annotation.id === selectedAnnotation.id}
                                onSelect={() => handlePointSelect(annotation)}
                            />
                        ))}


                        {/* Other Markers Section (Request and Resource) */}
                        {shownMarkers.filter(marker => marker.type !== 'Annotation').map((marker, index) => {
                            const SelectedCard = cards[marker.type];
                            const locationName = locationNames[`${marker.latitude},${marker.longitude}`] || 'Loading...';
                            return ( // Add this return statement
                                <SelectedCard
                                    item={{ ...marker, locationName }}
                                    onClick={() => setSelectedPoint(marker)}
                                    key={`${marker.type}-${index}`}
                                />
                            );
                        })}

                    </Box>
                    <Box sx={{ width: "36px" }} />
                    <Box sx={{ flexGrow: 100 }}>
                        <DisasterMap markers={shownMarkers}
                            mapCenter={mapCenter}
                            setMapCenter={setMapCenter}
                            onPointSelected={setSelectedPoint}
                            onBoundsChanged={setMapBounds}
                        />
                    </Box>
                </Box>
            </Container>
        </ThemeProvider >
    );
}



