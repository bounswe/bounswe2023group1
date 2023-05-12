import React, { useState } from 'react';

export default function Kubra() {
    const [inputText, setInputText] = useState('moon');
    const [responseText, setResponseText] = useState('');

    const handleInputChange = (event) => {
        setInputText(event.target.value);
    };

    const [text, setText] = useState('moon');

    const handleRequest = () => {
        fetch(`api/nasa/search?text=${inputText}`, {
            method: 'GET',

        })
            .then((response) => {
                console.log('Success:', response);
                return response.json();
            })
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
            <input
                type="text"
                placeholder="Input Text"
                value={inputText}
                onChange={handleInputChange}
            />
            <button onClick={handleRequest}>
                Send Request
            </button>
            {
                responseText?.collection?.items?.map((item) => {
                    return <div key={item.data[0].nasa_id}>
                        <div>{item.data[0].title}</div>
                        <img src={item.links[0].href} alt={item.data[0].title}></img>
                    </div>
                })
            }
        </div>
    );
};