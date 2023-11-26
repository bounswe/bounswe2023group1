import * as React from 'react';
import {useEffect, useId, useState} from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import Container from '@mui/material/Container';
import DisasterMap from "../components/DisasterMap";
import {cards} from "./ListCards";
import {Chip, FormControl, InputLabel, MenuItem, OutlinedInput, Select, useTheme} from "@mui/material";


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
        icon: "health",
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
                description: "1.5 liters bottles",
                quantity: 300,
            },
        ],
        status: "READY"
    },
]

function getDropDownStyles(name, personName, theme) {
    return {
        fontWeight:
            personName.indexOf(name) === -1
                ? theme.typography.fontWeightRegular
                : theme.typography.fontWeightMedium,
    };
}

const MultiCheckbox = ({name, choices, onChosenChanged}) => {
    const theme = useTheme();

    const ITEM_HEIGHT = 48;
    const ITEM_PADDING_TOP = 8;
    const MenuProps = {
        PaperProps: {
            style: {
                maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
                width: 250,
            },
        },
    };

    const [currentChoices, setCurrentChoices] = React.useState([]);
    const label_id = useId();
    const select_id = useId();
    const input_id = useId();


    const handleChange = (event) => {
        const {
            target: {value},
        } = event;
        setCurrentChoices(
            typeof value === 'string' ? value.split(',') : value,
        );
    };

    useEffect(() => {
        onChosenChanged && onChosenChanged(currentChoices)
    }, [onChosenChanged, currentChoices])

    return <FormControl sx={{m: 1}}>
        <InputLabel id={label_id}>{name}</InputLabel>
        <Select
            sx={{minWidth: "100px"}}
            labelId={label_id}
            multiple
            id={select_id}
            value={currentChoices}
            onChange={handleChange}
            input={<OutlinedInput id={input_id} label={name}/>}
            renderValue={(selected) => (
                <Box sx={{display: 'flex', flexWrap: 'wrap', gap: 0.5}}>
                    {selected.map((value) => (
                        <Chip key={value} label={value}/>
                    ))}
                </Box>
            )}
            MenuProps={MenuProps}
        >
            {choices.map((name) => (
                <MenuItem
                    key={name}
                    value={name}
                    style={getDropDownStyles(name, currentChoices, theme)}
                >
                    {name}
                </MenuItem>
            ))}
        </Select>
    </FormControl>
}

export default function MapDemo() {

    const [selectedPoint, setSelectedPoint] = useState(null)

    // noinspection JSValidateTypes
    return (
        <ThemeProvider theme={customTheme}>
            <Container component="main" maxWidth="100%">
                <CssBaseline/>
                <Box sx={{display: "flex", flexDirection: "row", flexWrap: 'nowrap', margin: "12px", width: "100%"}}>
                    <MultiCheckbox name={"Type"} choices={["Annotation", "Resource", "Request"]}/>
                    <MultiCheckbox name={"Test"} choices={["Annotation", "Resource", "Request"]}/>
                </Box>
                <Box sx={{display: "flex", flexDirection: "row", flexWrap: 'nowrap', margin: "12px"}}>
                    <Box sx={{
                        flexBasis: "33%",
                        flexShrink: 0,
                        display: "flex",
                        flexDirection: "column",
                        rowGap: "16px"
                    }}>
                        {mock_markers.map((marker) => {
                            const SelectedCard = cards[marker.type]
                            return < SelectedCard item={marker} onClick={() => setSelectedPoint(marker)}/>
                        })}
                    </Box>
                    <Box sx={{width: "36px"}}/>
                    <Box sx={{flexGrow: 100}}>
                        <DisasterMap markers={mock_markers}
                                     center={selectedPoint && [selectedPoint.latitude, selectedPoint.longitude]}
                                     onPointSelected={setSelectedPoint}/>
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
    );
}



