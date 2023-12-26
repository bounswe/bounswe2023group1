import React, {useState} from 'react';
import {GeoJson, GeoJsonFeature, Map, Marker, ZoomControl} from 'pigeon-maps';
import {type_colors} from "../Colors";
import {AnnotationIcon, MarkerIcon} from "./MapIcons";
import {GeoArrowFeature} from "./GeoArrowFeature";

const MAPBOX_TOKEN = "pk.eyJ1IjoiaWxnYXplciIsImEiOiJjbG80Nzg4Z3gwMjZ4MmtxcTR3bGI5enR3In0.QdNftxZYpJ79K0M0DfYHUw"
const MAPBOX_STYLE = "mapbox/streets-v12"

function mapboxProvider(x, y, z, dpr) {
    return `https://api.mapbox.com/styles/v1/${MAPBOX_STYLE
    }/tiles/512/${z}/${x}/${y}${dpr >= 2 ? '@2x' : ''}?access_token=${MAPBOX_TOKEN
    }`;
}

const marker_order = ["Facility", "Request", "Resource"]

const geoJson = (coordinates) => ({
    type: "Feature",
    geometry: {type: "LineString", coordinates},
});

export default function DisasterMap({
                                        onPointSelected,
                                        markers = [],
                                        paths = [],
                                        mapCenter,
                                        setMapCenter,
                                        onBoundsChanged
                                    }) {
    const [zoom, setZoom] = useState(6.5);

    const renderMarker = (marker) => {
        return (
            <Marker
                width={33}
                anchor={[marker.latitude, marker.longitude]}
                key={marker.type + marker.id}
                onClick={({ event }) => {
                    onPointSelected && onPointSelected(marker);
                    event.preventDefault()
                }}
            >
                {marker.type === "Facility" ?
                    <AnnotationIcon icon={marker.category} /> :
                    <MarkerIcon color={marker.color || type_colors[marker.type]}/>}
            </Marker>
        );
    };

    return (
        <div style={{display: "flex", height: "100%"}}>
            <div style={{flexGrow: 100, height: "100%"}}>
                <Map
                    provider={mapboxProvider}
                    dprs={[1, 2]}
                    center={mapCenter}
                    zoom={zoom}
                    onClick={({event}) => {
                        onPointSelected && onPointSelected(null);
                        event.preventDefault()
                    }}
                    onBoundsChanged={({center, zoom, bounds}) => {
                        setMapCenter && setMapCenter(center)
                        setZoom(zoom)
                        onBoundsChanged && onBoundsChanged(bounds)
                    }}>
                    <ZoomControl/>
                    <GeoJson>
                        {paths
                            .map(path => <GeoArrowFeature feature={geoJson(path.coordinates)}
                                                          svgAttributes={{
                                                              fill: path.color,
                                                              strokeWidth: "2",
                                                              stroke: path.color,
                                                              r: "2",
                                                          }}
                                                          onClick={({event}) => {
                                                              onPointSelected && onPointSelected(path);
                                                              event.preventDefault()
                                                          }}/>)}
                    </GeoJson>
                    {markers
                        .sort(({type}) => -marker_order.indexOf(type))
                        .map(renderMarker)}
                </Map>
            </div>
        </div>
    );
}
