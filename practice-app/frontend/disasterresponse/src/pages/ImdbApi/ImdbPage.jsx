import React from 'react'
import { useState } from 'react'
import axios from 'axios'

export default function ImdbPage() {
    const [query , setQuery] = useState("");
    const [limit , setLimit] = useState("");
    const [movies, setMovies] = useState([]);

    

    const getMovies = () => {
        axios.get(`/movie/getMovies?query=${encodeURIComponent(query)}&limit=${encodeURIComponent(limit)}`).then(res => {
            setMovies(res.data)
            console.log(res)
            console.log(res.data)
        }).catch(error =>{
            console.log(error)
        })
    }
    
    return (
        <div className="movieSearch">
            <h2>Movie Search</h2>
            <form onSubmit={(e) => {e.preventDefault(); getMovies()}}>
                <input type="text" placeholder="Enter movie query" onChange={e => setQuery(e.target.value)} />
            <button type="submit">Search</button>
            </form>
            <div className="movieResults">
            {movies.map(movie => (
                <div className="movieCard" key={movie.id}>
                <h3>{movie.title}</h3>
                </div>
            ))}
            </div>
        </div>
        );

}
        
    


