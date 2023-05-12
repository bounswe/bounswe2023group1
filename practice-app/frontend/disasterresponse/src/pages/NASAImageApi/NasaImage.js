import React, { useState } from 'react';

export default function Kubra() {
    const [inputText, setInputText] = useState('moon');
    const [responseText, setResponseText] = useState('');

    const handleInputChange = (event) => {
        setInputText(event.target.value);
    };

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
                console.log('Success:', data);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    };

    const handleSave = () => {
        fetch('api/nasa/save', {
            method: 'POST',
            body: JSON.stringify({ inputText }),
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then((response) => {
                console.log('Success:', response);
                alert('Data saved successfully!');
                return response.json();
            })
            .then((data) => {
                console.log('Success:', data);
                alert('Data saved successfully!');
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    };

    return (
        <div
            style={{
                padding: '20px',
                fontFamily: 'Arial, sans-serif',
            }}
        >
            <input
                type="text"
                placeholder="Input Text"
                value={inputText}
                onChange={handleInputChange}
                style={{
                    padding: '10px',
                    marginBottom: '10px',
                    width: '300px',
                }}
            />
            <div
                style={{
                    display: 'flex',
                    gap: '10px',
                }}
            >
                <button
                    style={{
                        padding: '10px 20px',
                        backgroundColor: '#007bff',
                        color: 'white',
                        border: 'none',
                        cursor: 'pointer',
                    }}
                    onClick={handleRequest}
                >
                    Send Request
                </button>
                <button
                    style={{
                        padding: '10px 20px',
                        backgroundColor: '#007bff',
                        color: 'white',
                        border: 'none',
                        cursor: 'pointer',
                    }}
                    onClick={handleSave}
                >
                    Save
                </button>
            </div>
            <div
                style={{
                    display: 'grid',
                    gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))',
                    gridGap: '20px',
                    marginTop: '20px',
                }}
            >
                {responseText?.collection?.items?.map((item) => (
                    <div
                        key={item.data[0].nasa_id}
                        style={{
                            padding: '10px',
                            border: '1px solid #ccc',
                        }}
                    >
                        <div
                            style={{
                                fontWeight: 'bold',
                                marginBottom: '10px',
                            }}
                        >
                            {item.data[0].title}
                        </div>
                        <img
                            src={item.links[0].href}
                            alt={item.data[0].title}
                            style={{
                                width: '100%',
                                height: 'auto',
                            }}
                        />
                    </div>
                ))}
            </div>
        </div>
    );
}


