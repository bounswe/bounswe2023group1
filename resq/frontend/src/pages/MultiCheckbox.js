import {Chip, FormControl, InputLabel, MenuItem, OutlinedInput, Select, useTheme} from "@mui/material";
import * as React from "react";
import {useEffect, useId} from "react";
import Box from "@mui/material/Box";

function getDropDownStyles(name, personName, theme) {
    return {
        fontWeight:
            personName.indexOf(name) === -1
                ? theme.typography.fontWeightRegular
                : theme.typography.fontWeightMedium,
    };
}

export const MultiCheckbox = ({name, choices, onChosenChanged}) => {
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