import {useQuery} from "@tanstack/react-query";
import {getAddress} from "../components/Cards/ListCards";
import * as React from "react";

export const Location = ({latitude, longitude}) => {
    const location = useQuery({
        queryKey: ['location', latitude, longitude],
        queryFn: () => getAddress(latitude, longitude)
    })
    return <>{location?.data || "Loading"}</>
}