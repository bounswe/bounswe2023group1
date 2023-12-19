import MapPage from "./MapPage";
import { useQuery } from "@tanstack/react-query";
import { viewAllTasks } from "../AppService";
import { Fab } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { Add } from "@mui/icons-material";

export default function ResponderMapPage() {
    const navigate = useNavigate();

    const userId = parseInt(localStorage.getItem('userId'));

    const { data, isError, isLoading, error } = useQuery(
        ['viewAllTasks', userId],
        () => viewAllTasks(userId),
    );

    if (isLoading) {
        return <div>Loading tasks...</div>;
    }

    if (isError) {
        console.error('Error fetching tasks:', error);
        return <div>Error fetching tasks.</div>;
    }

    console.log('Tasks data:', data);

    const taskMarkers = data?.map(task => ({
        lat: task.startLatitude,
        lng: task.startLongitude,
        title: task.description,
        dueDate: task.dueDate,
        isCompleted: task.completed,
    }));

    return (
        <>
            <MapPage allMarkers={taskMarkers || []} />
            <Fab color="primary" variant="extended" onClick={() => navigate("/account")} sx={{
                position: 'absolute',
                bottom: 32,
                right: 32,
            }}>
                <Add sx={{ mr: 1 }} />
                My Tasks
            </Fab>
        </>
    );
}
