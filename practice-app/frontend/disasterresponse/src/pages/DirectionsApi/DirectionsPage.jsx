import React, { useState } from 'react'
import axios from 'axios';
import './DirectionsPage.css';

export default function DirectionsPage() {

    //Directions

    const [from, setFrom] = useState("");
    const [to, setTo] = useState("");

    const [directions, setDirections] = useState(null);

    const getDirections = () => {
        axios.get(`/api/getDirection?from=${from}&to=${to}`)
            .then((res) => {
                console.log(res.data)
                setDirections(res.data);
            }).catch(e => {
                console.log(e)
            })
    }

    return (
        <div className='directionsDiv'>
            <input type="text" value={from} onChange={(e) => setFrom(e.target.value)} />
            <input type="text" value={to} onChange={(e) => setTo(e.target.value)} />
            <button onClick={getDirections}>Get Directions</button>
            <div>
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
