import React, { useState } from 'react'
import axios from 'axios';
import './DirectionsPage.css';
import { Button, Form, Modal } from 'react-bootstrap';

export default function DirectionsPage() {

    //Directions

    const [from, setFrom] = useState("");
    const [to, setTo] = useState("");

    const [directions, setDirections] = useState(null);

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => {
        getBookmarkedDestinations();
        setShow(true);
    }

    const [bookmarkedDestinations, setBookmarkedDestinations] = useState([]);

    const saveDestination = () => { 
        axios.post(`/saveDestination?destination=${to}`)
            .then((res) => {
                console.log(res.data)
            }).catch(e => {
                console.log(e)
            })
    }

    const getDirections = () => {
        axios.get(`/api/getDirection?from=${from}&to=${to}`)
            .then((res) => {
                console.log(res.data)
                setDirections(res.data);
            }).catch(e => {
                console.log(e)
            })
    }

    const getBookmarkedDestinations = () => {
        axios.get(`/getBookmarkedDestinations`)
            .then((res) => {
                console.log(res.data)
                setBookmarkedDestinations(res.data);
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
                {
                    to &&
                    <Button variant="success" onClick={saveDestination} style={{marginLeft: "10px"}}>
                        Save Destination
                    </Button>
                }
            </Form>
            <div className='directionsDiv'>
                {
                    directions &&
                    directions?.routes[0]?.legs[0].steps.map((step, i) => {
                        return <div key={i} dangerouslySetInnerHTML={{ __html: step.html_instructions }} />
                    })
                }
            </div>
            <>
                <Button variant="primary" onClick={handleShow}>
                    Bookmarked Destinations
                </Button>

                <Modal show={show} onHide={handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>Bookmarked Destinations</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {
                            bookmarkedDestinations &&
                            bookmarkedDestinations.map((destination, i) => {
                                return <div key={i}>
                                    <div>{destination.address}</div>
                                </div>
                            }
                            )
                        }
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={handleClose}>
                            Close
                        </Button>
                    </Modal.Footer>
                </Modal>
            </>
        </div>
    )
}
