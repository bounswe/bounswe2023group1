import * as React from 'react';
import {useEffect, useState} from 'react';
import {Map, Marker, ZoomControl} from 'pigeon-maps';
import {type_colors} from "../Colors";
import {AnnotationIcon, MarkerIcon} from "./MapIcons";

const MAPBOX_TOKEN = "pk.eyJ1IjoiaWxnYXplciIsImEiOiJjbG80Nzg4Z3gwMjZ4MmtxcTR3bGI5enR3In0.QdNftxZYpJ79K0M0DfYHUw"
const MAPBOX_STYLE = "mapbox/streets-v12"

function mapboxProvider(x, y, z, dpr) {
    return `https://api.mapbox.com/styles/v1/${
        MAPBOX_STYLE
    }/tiles/512/${z}/${x}/${y}${dpr >= 2 ? '@2x' : ''}?access_token=${
        MAPBOX_TOKEN
    }`;
}


export default function DisasterMap({onPointSelected, markers = [], center}) {
    const [zoom, setZoom] = useState(6.5);
    const [mapCenter, setMapCenter] = useState([39, 34.5])

    useEffect(() => {
        center && setMapCenter(center);
    }, [center])

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
                {marker.icon ? <AnnotationIcon icon={marker.icon}/> : <MarkerIcon color={type_colors[marker.type]}/>}
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
                    center={mapCenter}
                    zoom={zoom}
                    onClick={({event}) => {
                        onPointSelected(null);
                        event.preventDefault()
                    }}
                    onBoundsChanged={({center, zoom}) => {
                        setMapCenter(center)
                        setZoom(zoom)
                    }}>
                    <ZoomControl/>
                    {markers.map(renderMarker)}
                </Map>
            </div>
        </div>
    );
}


