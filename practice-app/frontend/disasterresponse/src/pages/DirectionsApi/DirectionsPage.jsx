import React, { useState } from 'react'
import axios from 'axios';
import './DirectionsPage.css';
import { Button, Form } from 'react-bootstrap';

export default function DirectionsPage() {

    //Directions

    const [from, setFrom] = useState("");
    const [to, setTo] = useState("");

    const [directions, setDirections] = useState(null);

    const getDirections = () => {
        axios.get(`/getDirection?from=${from}&to=${to}`)
            .then((res) => {
                console.log(res.data)
                setDirections(res.data);
            }).catch(e => {
                console.log(e)
            })
    }

    return (
        <div className='directionsDiv'>
            <Form className="formDiv">
                <Form.Group className="mb-3" controlId="from">
                    <Form.Label>From</Form.Label>
                    <Form.Control type="text" placeholder="Enter address" onChange={(e) => setFrom(e.target.value)} />
                </Form.Group>

                <Form.Group className="mb-3" controlId="destination">
                    <Form.Label>Destination</Form.Label>
                    <Form.Control type="text" placeholder="Enter destination" onChange={(e) => setTo(e.target.value)} />
                </Form.Group>
                <Button variant="primary" onClick={getDirections}>
                    Get Directions
                </Button>
            </Form>
            <div className='directionsDiv'>
                {
                    directions &&
                    directions?.routes[0]?.legs[0].steps.map((step, i) => {
                        return <div key={i} dangerouslySetInnerHTML={{ __html: step.html_instructions }} />
                    })
                }
            </div>
        </div>
    )
}
