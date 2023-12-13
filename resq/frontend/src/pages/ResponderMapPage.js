import MapPage from "./MapPage";
import {useQuery} from "@tanstack/react-query";
import {getAllResources} from "../AppService";
import {mock_markers} from "./Mock_markers";
import {Fab} from "@mui/material";
import {useNavigate} from "react-router-dom";
import {Add} from "@mui/icons-material";


export default function ResponderMapPage() {

    
//const queryClient = useQueryClient()

const navigate = useNavigate()
const requests = useQuery({queryKey: ['viewAllNeeds'], queryFn: viewAllNeeds})

const requestMarkers = (requests.data?.data || []).map(a => ({...a, type: "Requests"}))
const allMarkers = [...mock_markers, ...requestMarkers]
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
        <MapPage allMarkers={allMarkers}/>
        <Fab color="primary" variant="extended" onClick={() => navigate("/requestcreation")} sx={{
            position: 'absolute',
            bottom: 32,
            right: 32,
        }}>
            <Add sx={{mr: 1}}/>
            Add Resource
        </Fab>
    </>

)

}