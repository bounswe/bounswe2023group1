import React from 'react'
import { useState } from 'react'
import axios from 'axios'
import './ImdbPage.css'

export default function ImdbPage() {
    const [query , setQuery] = useState("");
    const [movies, setMovies] = useState([]);

    

    const getMovies = () => {
        axios.get(`/movie/getMovies?query=${encodeURIComponent(query)}&limit=5`).then(res => {
            setMovies(res.data)
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
                <img src={movie.image.url} alt={movie.title} />
                </div>
            ))}
            </div>
        </div>
        );

}
        
    


