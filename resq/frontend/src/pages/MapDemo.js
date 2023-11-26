import * as React from 'react';
import {useEffect, useState} from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import Container from '@mui/material/Container';
import DisasterMap from "../components/DisasterMap";
import {cards} from "./ListCards";
import {MultiCheckbox} from "./MultiCheckbox";


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
        category: "health",
        title: "First Aid Clinic",
        short_description: "First aid clinic and emergency wound care. Open 24 hours.",
        long_description: "Welcome to our First Aid Clinic, a dedicated facility committed to providing immediate and " +
            "compassionate healthcare 24 hours a day. Our experienced team of healthcare professionals specializes in " +
            "emergency wound care and first aid assistance, ensuring you receive prompt attention when you need it most. " +
            "From minor cuts to more serious injuries, our clinic is equipped to handle a range of medical concerns, " +
            "promoting healing and preventing complications."
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
        status: "READY"
    },
]

function getAllCategories(item) {
    switch (item.type) {
        case "Annotation":
            return [item?.category]
        case "Resource":
            return item.resources.map(resource => resource?.category)
        case "Request":
            return item.needs.map(need => need?.category)
        default:
            return []
    }
}

const makeFilterByCategory = categories => {
    if (categories.length === 0)
        return () => true
    return item => {
        switch (item.type) {
            case "Annotation":
                return categories.indexOf(item?.category) !== -1
            case "Resource":
                return !item.resources.every(resource => categories.indexOf(resource?.category) === -1)
            case "Request":
                return !item.needs.every(need => categories.indexOf(need?.category) === -1)
            default:
                return false
        }
    }
};

const makeFilterByType = (typeFilter) => item => typeFilter.length === 0 || typeFilter.indexOf(item.type) !== -1

export default function MapDemo() {
    const [allMarkers, setAllMarkers] = useState(mock_markers)
    const [shownMarkers, setShownMarkers] = useState(allMarkers)
    const [selectedPoint, setSelectedPoint] = useState(null)

    const [typeFilter, setTypeFilter] = useState([])
    const [timeFilter, setTimeFilter] = useState([])
    const [amountFilter, setAmountFilter] = useState([])
    const [categoryFilter, setCategoryFilter] = useState([])
    const [mapBounds, setMapBounds] = useState([])

    useEffect(() => setShownMarkers(
        allMarkers
            .filter(makeFilterByCategory(categoryFilter))
            .filter(makeFilterByType(typeFilter))
    ), [allMarkers, categoryFilter, typeFilter])

    // noinspection JSValidateTypes
    return (
        <ThemeProvider theme={customTheme}>
            <Container maxWidth="100%" style={{height: "100%", display: "flex", flexDirection: "column"}}>
                <CssBaseline/>
                <Box sx={{display: "flex", flexDirection: "row", flexWrap: 'nowrap', margin: "12px", width: "100%"}}>
                    <MultiCheckbox name={"Type"} choices={["Annotation", "Resource", "Request"]}
                                   onChosenChanged={setTypeFilter}/>
                    <MultiCheckbox name={"Category"}
                                   choices={[
                                       ...allMarkers
                                           .map(getAllCategories)
                                           .flat(),
                                       ...categoryFilter
                                   ].filter((v, i, array) => v && array.indexOf(v) === i)}
                                   onChosenChanged={setCategoryFilter}/>
                    <MultiCheckbox name={"Type"}
                                   choices={["Annotation", "Resource", "Request"]}
                                   onChosenChanged={setTypeFilter}/>
                    <MultiCheckbox name={"Type"} choices={["Annotation", "Resource", "Request"]}
                                   onChosenChanged={setTypeFilter}/>
                </Box>
                <Box sx={{
                    display: "flex",
                    flexDirection: "row",
                    flexWrap: 'nowrap',
                    margin: "12px",
                    height: "100%",
                    flexGrow: 100
                }}>
                    <Box sx={{
                        flexBasis: "33%",
                        flexShrink: 0,
                        display: "flex",
                        flexDirection: "column",
                        rowGap: "16px"
                    }}>
                        {shownMarkers.map((marker) => {
                            const SelectedCard = cards[marker.type]
                            return < SelectedCard item={marker} onClick={() => setSelectedPoint(marker)}/>
                        })}
                    </Box>
                    <Box sx={{width: "36px"}}/>
                    <Box sx={{flexGrow: 100}}>
                        <DisasterMap markers={shownMarkers}
                                     center={selectedPoint && [selectedPoint.latitude, selectedPoint.longitude]}
                                     onPointSelected={setSelectedPoint}/>
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
    );
}



