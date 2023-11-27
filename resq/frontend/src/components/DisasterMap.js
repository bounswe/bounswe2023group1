import * as React from 'react';
import {useState} from 'react';
import {Map, Marker, ZoomControl} from 'pigeon-maps';
import {type_colors} from "../Colors";

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


export default function DisasterMap({onPointSelected, markers = []}) {
    const [zoom, setZoom] = useState(6.5);
    const [center, setCenter] = useState([39, 34.5])

    const renderMarker = (marker) => {
        return (
            <Marker
                width={33}
                anchor={[marker.latitude, marker.longitude]}
                key={marker.id}
                onClick={({event}) => {
                    onPointSelected(marker);
                    event.preventDefault()
                }}
            >
                {<MarkerIcon color={type_colors[marker.type]}/>}
            </Marker>
        );
    };

    // noinspection JSValidateTypes
    return (
        <div style={{display: "flex"}}>
            <div style={{flexGrow: 100, height: "calc(100vh - 56px)"}}>
                <Map
                    provider={mapboxProvider}
                    dprs={[1, 2]}
                    //height={window.innerHeight - (ref?.y || 48)}
                    //width={ref?.offsetWidth || 100}
                    center={center}
                    zoom={zoom}
                    onClick={({event}) => {
                        onPointSelected(null);
                        event.preventDefault()
                    }}
                    onBoundsChanged={({center, zoom}) => {
                        setCenter(center)
                        setZoom(zoom)
                    }}>
                    <ZoomControl/>
                    {markers.map(renderMarker)}
                </Map>
            </div>
        </div>
    );
}


