import * as React from 'react';
import Box from '@mui/material/Box';
import {useEffect, useRef, useState} from "react";
import {Map, Marker, ZoomControl} from 'pigeon-maps';

const TILE_SIZE = 256;
const HEIGHT_RATIO = 0.75;

const MAPBOX_TOKEN = "pk.eyJ1IjoiaWxnYXplciIsImEiOiJjbG80Nzg4Z3gwMjZ4MmtxcTR3bGI5enR3In0.QdNftxZYpJ79K0M0DfYHUw"
const MAPBOX_STYLE = "mapbox/streets-v12"

function mapboxProvider(x, y, z, dpr) {
    return `https://api.mapbox.com/styles/v1/${
        MAPBOX_STYLE
    }/tiles/512/${z}/${x}/${y}${dpr >= 2 ? '@2x' : ''}?access_token=${
        MAPBOX_TOKEN
    }`;
}


const MarkerIcon = ({color}) => (
    <svg
        width={33}
        height="38.689655172413794"
        viewBox="0 0 61 71"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
    >
        <g style={{pointerEvents: "auto"}}>
            <path
                d="M52 31.5C52 36.8395 49.18 42.314 45.0107 47.6094C40.8672 52.872 35.619 57.678 31.1763 61.6922C30.7916 62.0398 30.2084 62.0398 29.8237 61.6922C25.381 57.678 20.1328 52.872 15.9893 47.6094C11.82 42.314 9 36.8395 9 31.5C9 18.5709 18.6801 9 30.5 9C42.3199 9 52 18.5709 52 31.5Z"
                fill={color}
                stroke="white"
                strokeWidth={4}
            />
            <circle cx="30.5" cy="30.5" r="8.5" fill="white" opacity="0.6"/>
        </g>
    </svg>
);

const marker_colors = {
    "Need": "#93C0D0",
    "Point": "#AA0000"
}

const mock_markers = [
    {
        type: "Need",
        coordinates: {
            latitude: 41.08,
            longitude: 29.05
        },
        id: 1
    },
    {
        type: "Need",
        coordinates: {
            latitude: 41.09,
            longitude: 29.04
        },
        id: 2
    },
    {
        type: "Point",
        coordinates: {
            latitude: 41.1,
            longitude: 29.04
        },
        id: 3
    }
]

const renderMarker = (marker) => {
    if (!marker?.coordinates) {
        return null;
    }

    return (
        <Marker
            width={33}
            anchor={[marker.coordinates.latitude, marker.coordinates.longitude]}
            key={marker.id}>
            {<MarkerIcon color={marker_colors[marker.type]}/>}
        </Marker>
    );
};

export default function DisasterMap({marks = []}) {
    const [zoom, setZoom] = useState(5);
    const [center, setCenter] = useState([39, 34.5])
    const [ref, setRef] = useState();

    return (
        <div style={{display: "flex"}}>
            <div style={{flexGrow: 100, height: "100%"}} ref={newRef => setRef(newRef)}>
                <Map
                    provider={mapboxProvider}
                    height={window.innerHeight - (ref?.y || 48)}
                    width={ref?.offsetWidth || 100}
                    center={center}
                    zoom={zoom}
                    onBoundsChanged={({center, zoom}) => {
                        setCenter(center)
                        setZoom(zoom)
                    }}>

                    <ZoomControl/>
                    {mock_markers.map(renderMarker)}

                </Map>
            </div>
        </div>
    );
}


