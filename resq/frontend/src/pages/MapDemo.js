import * as React from 'react';
import {useEffect, useState} from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import Container from '@mui/material/Container';
import DisasterMap from "../components/DisasterMap";
import {cards} from "../components/ListCards";
import {AmountSelector, MultiCheckbox} from "../components/MultiCheckbox";
import {DatePicker} from "@mui/x-date-pickers";
import dayjs from "dayjs";


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
const makeFilterByDateTo = (dateTo) => item => !(dateTo < dayjs(item.date))

export default function MapDemo() {
    // eslint-disable-next-line no-unused-vars
    const [allMarkers, setAllMarkers] = useState(mock_markers)
    const [shownMarkers, setShownMarkers] = useState(allMarkers)
    const [selectedPoint, setSelectedPoint] = useState(null)

    const [typeFilter, setTypeFilter] = useState([])
    const [dateFromFilter, setDateFromFilter] = useState(null)
    const [dateToFilter, setDateToFilter] = useState(null)
    const [amountFilter, setAmountFilter] = useState([])
    const [categoryFilter, setCategoryFilter] = useState([])
    const [mapBounds, setMapBounds] = useState([])

    useEffect(() => setShownMarkers(
        allMarkers
            .filter(makeFilterByCategory(categoryFilter))
            .filter(makeFilterByType(typeFilter))
            .filter(makeFilterByAmount(amountFilter))
            .filter(makeFilterByDateFrom(dateFromFilter))
            .filter(makeFilterByDateTo(dateToFilter))
    ), [allMarkers, amountFilter, categoryFilter, dateFromFilter, dateToFilter, typeFilter])

    // noinspection JSValidateTypes
    return (
        <ThemeProvider theme={customTheme}>
            <Container maxWidth="100%" style={{height: "100%", display: "flex", flexDirection: "column"}}>
                <CssBaseline/>
                <Box sx={{
                    display: "flex", flexDirection: "row", flexWrap: 'nowrap', margin: "12px", width: "100%",
                    justifyContent: "center"
                }}>
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
                    <AmountSelector name={"Amount"}
                                    onChosenChanged={setAmountFilter}/>
                    <DatePicker
                        sx={{m: 1}}
                        label="From"
                        format="DD/MM/YYYY"
                        value={dateFromFilter}
                        onChange={e => setDateFromFilter(e)}
                    />
                    <DatePicker
                        sx={{m: 1}}
                        label="To"
                        format="DD/MM/YYYY"
                        value={dateToFilter}
                        onChange={e => setDateToFilter(e)}
                    />
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



