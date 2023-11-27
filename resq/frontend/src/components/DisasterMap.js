import * as React from 'react';
import {useState} from 'react';
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

const marker_order = ["Annotation", "Request", "Resource"]

export default function DisasterMap({onPointSelected, markers = [], mapCenter, setMapCenter, onBoundsChanged}) {
    const [zoom, setZoom] = useState(6.5);

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
                {marker.type === "Annotation" ? <AnnotationIcon icon={marker.category}/> :
                    <MarkerIcon color={type_colors[marker.type]}/>}
            </Marker>
        );
    };

    // noinspection JSValidateTypes
    return (
        <div style={{display: "flex", height: "100%"}}>
            <div style={{flexGrow: 100, height: "100%"}}>
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
                    onBoundsChanged={({center, zoom, bounds}) => {
                        setMapCenter(center)
                        setZoom(zoom)
                        onBoundsChanged(bounds)
                    }}>
                    <ZoomControl/>
                    {markers
                        .sort(({type}) => -marker_order.indexOf(type))
                        .map(renderMarker)}
                </Map>
            </div>
        </div>
    );
}


