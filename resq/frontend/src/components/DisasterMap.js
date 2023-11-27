import React, { useState, useEffect } from 'react';
import { Map, Marker, ZoomControl } from 'pigeon-maps';
import { type_colors } from "../Colors";
import { AnnotationIcon, MarkerIcon } from "./MapIcons";

const MAPBOX_TOKEN = "pk.eyJ1IjoiaWxnYXplciIsImEiOiJjbG80Nzg4Z3gwMjZ4MmtxcTR3bGI5enR3In0.QdNftxZYpJ79K0M0DfYHUw";
const MAPBOX_STYLE = "mapbox/streets-v12";

function mapboxProvider(x, y, z, dpr) {
    return `https://api.mapbox.com/styles/v1/${MAPBOX_STYLE}/tiles/512/${z}/${x}/${y}${dpr >= 2 ? '@2x' : ''}?access_token=${MAPBOX_TOKEN}`;
}

const marker_order = ["Annotation", "Request", "Resource"];

export default function DisasterMap({ onPointSelected, markers = [], mapCenter, setMapCenter }) {
    const [zoom, setZoom] = useState(6.5);

    useEffect(() => {
        // Calculate bounds to encompass all markers
        if (markers.length > 0) {
            const bounds = markers.reduce((acc, marker) => {
                return [
                    Math.min(acc[0], marker.latitude),
                    Math.min(acc[1], marker.longitude),
                    Math.max(acc[2], marker.latitude),
                    Math.max(acc[3], marker.longitude),
                ];
            }, [markers[0].latitude, markers[0].longitude, markers[0].latitude, markers[0].longitude]);

            // Calculate center and zoom level based on bounds
            const center = [(bounds[0] + bounds[2]) / 2, (bounds[1] + bounds[3]) / 2];
            const zoom = getZoomLevel(bounds);

            // Set map center and zoom
            setMapCenter(center);
            setZoom(zoom);
        }
    }, [markers]);

    const renderMarker = (marker) => {
        return (
            <Marker
                width={33}
                anchor={[marker.latitude, marker.longitude]}
                key={marker.id}
                onClick={({ event }) => {
                    onPointSelected(marker);
                    event.preventDefault();
                }}
            >
                {marker.type === "Annotation" ? <AnnotationIcon icon={marker.category} /> :
                    <MarkerIcon color={type_colors[marker.type]} />}
            </Marker>
        );
    };

    const getZoomLevel = (bounds) => {
        // Calculate zoom level based on the bounds and map size
        const WORLD_DIM = { height: 256, width: 256 };
        const ZOOM_MAX = 19;

        const latRad1 = (bounds[0] * Math.PI) / 180;
        const latRad2 = (bounds[2] * Math.PI) / 180;
        const lngRad1 = (bounds[1] * Math.PI) / 180;
        const lngRad2 = (bounds[3] * Math.PI) / 180;

        const x1 = lngRad1;
        const y1 = Math.log(Math.tan(Math.PI / 4 + latRad1 / 2));
        const x2 = lngRad2;
        const y2 = Math.log(Math.tan(Math.PI / 4 + latRad2 / 2));

        const scaleX = WORLD_DIM.width / (x2 - x1);
        const scaleY = WORLD_DIM.height / (y2 - y1);
        const scale = Math.min(scaleX, scaleY);
        const zoom = ZOOM_MAX - Math.log(scale) / Math.log(2);

        return zoom;
    };

    return (
        <div style={{ display: "flex", height: "100%" }}>
            <div style={{ flexGrow: 100, height: "100%" }}>
                <Map
                    provider={mapboxProvider}
                    dprs={[1, 2]}
                    center={mapCenter}
                    zoom={zoom}
                    onClick={({ event }) => {
                        onPointSelected(null);
                        event.preventDefault();
                    }}
                >
                    <ZoomControl />
                    {markers
                        .sort(({ type }) => -marker_order.indexOf(type))
                        .map(renderMarker)}
                </Map>
            </div>
        </div>
    );
}
