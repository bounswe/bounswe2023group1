import MapPage from "./MapPage";
import {useQuery} from "@tanstack/react-query";
import {getAllResources} from "../AppService";
import {mock_markers} from "./Mock_markers";

export default function VictimMapPage() {

    //const queryClient = useQueryClient()

    const resources = useQuery({queryKey: ['getAllResources'], queryFn: getAllResources})

    const resourceMarkers = (resources.data?.data || []).map(a=>({...a, type: "Resource"}))
    const allMarkers = [...resourceMarkers, ...mock_markers]
    /*/ Mutations
    const mutation = useMutation({
        mutationFn: postTodo,
        onSuccess: () => {
            // Invalidate and refetch
            queryClient.invalidateQueries({queryKey: ['todos']})
        },
    })*/

    return (
        <MapPage allMarkers={allMarkers}/>
    )
}