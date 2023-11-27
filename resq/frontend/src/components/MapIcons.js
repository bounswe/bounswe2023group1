import {Cancel, LocalFireDepartment, LocalHospital} from "@mui/icons-material";
import * as React from "react";

export const AnnotationIcon = ({icon, color}) =>
    ({
        Fire: <LocalFireDepartment style={{pointerEvents: "auto", height: "38.6px"}}/>,
        Health: <LocalHospital style={{pointerEvents: "auto", height: "38.6px", color: color || "#e00000"}}/>,
        "Road Closure": <Cancel style={{pointerEvents: "auto", height: "38.6px"}}/>
    })[icon]
export const MarkerIcon = ({color}) => (
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