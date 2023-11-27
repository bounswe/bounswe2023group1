// noinspection JSUnusedLocalSymbols

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
import {useQuery} from "@tanstack/react-query";
import {getCategoryTree} from "../AppService";
import AnnotationCard from "../components/AnnotationCard";
import reverseGeocode from "../components/Geolocation";

const customTheme = createTheme({
    palette: {
        primary: {
            main: '#FF0000',
        },
    },
});

const getAllCategories = categoryTree => {
    if (categoryTree) {
        return item => {
            switch (item.type) {
                case "Annotation":
                    return [{id: item?.category, data: item?.category}]
                default:
                    return categoryTree.findCategoryWithId(parseInt(item.categoryTreeId))?.getAllParentCategories()
            }
        };
    } else {
        return () => ([]);
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

    return item => {
        switch (item.type) {
            case "Annotation":
                return categories.map(a=>a.id).indexOf(item?.category) !== -1;
            default:
                return !categories.every(a => !a.findCategoryWithId || !(a.findCategoryWithId(parseInt(item.categoryTreeId))))
        }
    }
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

const makeFilterByBounds = ({ne: [ne_lat, ne_lng], sw: [sw_lat, sw_lng]}) =>
    function (item) {
        return item.latitude <= ne_lat &&
            item.longitude <= ne_lng &&
            item.latitude >= sw_lat &&
            item.longitude >= sw_lng;
    }


export default function MapPage({allMarkers}) {
    const [shownMarkers, setShownMarkers] = useState(allMarkers)
    const [selectedPoint, setSelectedPoint] = useState(null)
    const [mapCenter, setMapCenter] = useState([39, 34.5])

    const [typeFilter, setTypeFilter] = useState([])
    const [dateFromFilter, setDateFromFilter] = useState(null)
    const [dateToFilter, setDateToFilter] = useState(null)
    const [amountFilter, setAmountFilter] = useState([])
    const [categoryFilter, setCategoryFilter] = useState([])
    const [mapBounds, setMapBounds] = useState({ne: [0, 0], sw: [0, 0]})

    const categoryTree = useQuery({queryKey: ['categoryTree'], queryFn: getCategoryTree})


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

    const choices = new Map([
        ...allMarkers
            .map(getAllCategories(categoryTree?.data))
            .flat(),
        ...categoryFilter
    ]
        .filter(a=>a)
        .map(a=>[a?.id, a]))
    // noinspection JSValidateTypes
    return (
        <ThemeProvider theme={customTheme}>
            <Container maxWidth="100%" style={{height: "100%", display: "flex", flexDirection: "column"}}>
                <CssBaseline/>
                <Box sx={{
                    display: "flex", flexDirection: "row", flexWrap: 'nowrap', margin: "12px", width: "100%",
                    justifyContent: "center"
                }}>
                    <MultiCheckbox name={"Type"}
                                   choices={["Annotation", "Resource", "Request"].map(i => ({id: i, data: i}))}
                                   onChosenChanged={setTypeFilter}/>
                    <MultiCheckbox name={"Category"}
                                   choices={[...choices.values()]}
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
                        height: "100%",
                        overflow: "scroll"
                    }}>
                        <Box sx={{
                            display: "flex",
                            flexDirection: "column",
                            rowGap: "16px",
                            height: "fit-content"
                        }}>
                            {shownMarkers.map((marker) => {
                                const SelectedCard = cards[marker.type]
                                return < SelectedCard item={marker} onClick={() => setSelectedPoint(marker)}/>
                            })}
                        </Box>
                    </Box>
                    <Box sx={{width: "36px"}}/>
                    <Box sx={{flexGrow: 100}}>
                        <DisasterMap markers={shownMarkers}
                                     mapCenter={mapCenter}
                                     setMapCenter={setMapCenter}
                                     onPointSelected={setSelectedPoint}
                                     onBoundsChanged={setMapBounds}
                        />
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
    );
}



