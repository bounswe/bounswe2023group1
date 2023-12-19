import MapPage from "./MapPage";
import { useQuery } from "@tanstack/react-query";
import { getAllResources } from "../AppService";
import { mock_markers } from "./Mock_markers";
import { Fab } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { Add } from "@mui/icons-material";


export default function ResponderMapPage() {

    //const queryClient = useQueryClient()

    const navigate = useNavigate()
    const resources = useQuery({ queryKey: ['viewAllTasks'], queryFn: viewAllTasks })

    const mytaskMarkers = (resources.data?.data || []).map(a => ({ ...a, type: "My Tasks" }))
    const allMarkers = [...mock_markers, ...mytaskMarkers]
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
            <MapPage allMarkers={allMarkers} />
            <Fab color="primary" variant="extended" onClick={() => navigate("/account")} sx={{
                position: 'absolute',
                bottom: 32,
                right: 32,
            }}>
                <Add sx={{ mr: 1 }} />
                My Tasks
            </Fab>
        </>

    )

}