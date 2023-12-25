import MapPage from "./MapPage";
import { useQuery } from "@tanstack/react-query";
import { viewAllNeeds } from "../AppService";
import { mock_markers } from "./Mock_markers";
import { Fab } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { Add } from "@mui/icons-material";
import MapPagewDataList from "./MapPagewDataList";


export default function FacilitatorMapPage() {

    //const queryClient = useQueryClient()

    const navigate = useNavigate()
    const needs = useQuery({ queryKey: ['viewAllNeeds'], queryFn: viewAllNeeds })

    const needMarkers = (needs.data?.data || []).map(a => ({ ...a, type: "Needs" }))
    const allMarkers = [...mock_markers, ...needMarkers]
    /*/ Mutations
    const mutation = useMutation({
        mutationFn: postTodo,
        onSuccess: () => {
            // Invalidate and refetch
            queryClient.invalidateQueries({queryKey: ['todos']})
        },
    })*/

    return (
        <>
            <MapPagewDataList allMarkers={allMarkers} />
            <Fab color="primary" variant="extended" onClick={() => navigate("/account")} sx={{
                position: 'absolute',
                bottom: 32,
                right: 32,
            }}>
                <Add sx={{ mr: 1 }} />
                Create Request
            </Fab>
        </>

    )
}