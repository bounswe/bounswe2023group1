import React, { useState } from 'react';
import { TextField, Button } from '@mui/material';

export default function Nasa() {
    const [inputText, setInputText] = useState('moon');
    const [responseText, setResponseText] = useState('');

    const handleInputChange = (event) => {
        setInputText(event.target.value);
    };

    const [text, setText] = useState('moon');

    const handleRequest = () => {
        // Make API call to your Java Spring backend here
        fetch(`http://localhost:8081/api/nasa/search/${inputText}`, {
            method: 'GET',

        })
            .then((response) => {        console.log('Success:', response);
                return response.json() })
            .then((data) => {
                setResponseText(data);
                console.log('Success: sadfasd', data);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    };

    return (
        <div>
            <TextField
                label="Input Text"
                value={inputText}
                onChange={handleInputChange}
            />
            <Button variant="contained" onClick={handleRequest}>
                Send Request
            </Button>
            {

                responseText?.collection?.items?.map((item) => {


                        return <div>
                            <div>{item.data[0].title}</div>
                            <img src={item.links[0].href}></img></div>

                    }
                )
            }
        </div>
    );
};