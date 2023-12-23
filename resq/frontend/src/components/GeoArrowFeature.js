import React, {useEffect, useState} from "react"
import matrix from "matrix-js";

const defaultSvgAttributes = {
    fill: "#93c0d099",
    strokeWidth: "2",
    stroke: "white",
    r: "30"
}

export function PointComponent(props) {
    const {latLngToPixel} = props
    const [y, x] = props.coordinates
    const [cx, cy] = latLngToPixel([x, y])
    return <circle cx={cx} cy={cy} {...props.svgAttributes} />
}

export function MultiPoint(props) {
    return (
        <>
            {props.coordinates.map((point, i) => (
                <PointComponent {...props} coordinates={point} key={i}/>
            ))}
        </>
    )
}

export function LineString(props) {
    const {latLngToPixel} = props
    const [x1, y1] = latLngToPixel(props.coordinates[0].toReversed())
    const [x2, y2] = latLngToPixel(props.coordinates[1].toReversed())
    const lx = x2 - x1;
    const ly = y2 - y1;
    const a = matrix([[lx, -ly], [ly, lx]])
    const b = matrix([[x1, x1, x1, x1, x1, x1, x1], [y1, y1, y1, y1, y1, y1, y1]])
    const s = 0.075

    const arrow = matrix([[0., 0.5 + s / 2, 0.5 - s / 2, 0.5 + s / 2, 0.5 - s / 2, 0.5 + s / 2, 1.], [0., 0., s, 0., -s, 0., 0.]])
    const result = matrix(matrix(a.prod(arrow)).add(b))

    let p = "M"
    for (let i = 0; i < 7; i++) {
        const [[x], [y]] = result([], i)
        p += " " + x + " " + y
    }

    return <path d={p} {...props.svgAttributes}/>
}

export function MultiLineString(props) {
    return (
        <>
            {props.coordinates.map((line, i) => (
                <LineString coordinates={line} key={i}/>
            ))}
        </>
    )
}

export function Polygon(props) {
    const {latLngToPixel} = props
    // GeoJson polygons is a collection of linear rings
    const p = props.coordinates.reduce(
        (a, part) =>
            a +
            " M" +
            part.reduce((a, [y, x]) => {
                const [v, w] = latLngToPixel([x, y])
                return a + " " + v + " " + w
            }, "") +
            "Z",
        ""
    )
    return <path d={p} {...props.svgAttributes} />
}

export function MultiPolygon(props) {
    return (
        <>
            {props.coordinates.map((polygon, i) => (
                <Polygon {...props} coordinates={polygon} key={i}/>
            ))}
        </>
    )
}

export function GeometryCollection(props) {
    const renderer = {
        Point: PointComponent,
        MultiPoint,
        LineString,
        MultiLineString,
        Polygon,
        MultiPolygon
    }

    const {type, coordinates, geometries} = props.geometry

    if (type === "GeometryCollection") {
        return (
            <>
                {geometries.map((geometry, i) => (
                    <GeometryCollection key={i} {...props} geometry={geometry}/>
                ))}
            </>
        )
    }

    const Component = renderer[type]

    if (Component === undefined) {
        console.warn(`The GeoJson Type ${type} is not known`)
        return null
    }
    return (
        <Component
            latLngToPixel={props.latLngToPixel}
            geometry={props.geometry}
            coordinates={coordinates}
            svgAttributes={props.svgAttributes}
        />
    )
}

export function GeoArrowFeature(props) {
    const [internalHover, setInternalHover] = useState(props.hover || false)
    const hover = props.hover !== undefined ? props.hover : internalHover
    const callbackSvgAttributes =
        props.styleCallback && props.styleCallback(props.feature, hover)
    const svgAttributes = callbackSvgAttributes
        ? props.svgAttributes
            ? {...props.svgAttributes, ...callbackSvgAttributes}
            : callbackSvgAttributes
        : props.svgAttributes
            ? props.svgAttributes
            : defaultSvgAttributes

    const eventParameters = event => ({
        event,
        anchor: props.anchor,
        payload: props.feature
    })

    return (
        <g
            clipRule="evenodd"
            style={{pointerEvents: "auto"}}
            onClick={
                props.onClick ? event => props.onClick(eventParameters(event)) : null
            }
            onContextMenu={
                props.onContextMenu
                    ? event => props.onContextMenu(eventParameters(event))
                    : null
            }
            onMouseOver={event => {
                props.onMouseOver && props.onMouseOver(eventParameters(event))
                setInternalHover(true)
            }}
            onMouseOut={event => {
                props.onMouseOut && props.onMouseOut(eventParameters(event))
                setInternalHover(false)
            }}
        >
            <GeometryCollection
                {...props}
                {...props.feature}
                svgAttributes={svgAttributes}
            />
        </g>
    )
}
