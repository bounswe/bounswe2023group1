import React from 'react'
import { useState } from 'react'
import axios from 'axios'
import './ImdbPage.css'

const ImdbPage =() =>  {
    const [query , setQuery] = useState("");
    const [movies, setMovies] = useState([]);



    
    const getMovies = () => {
        axios.get(`api/movie/getMovies?query=${encodeURIComponent(query)}&limit=5`).then(res => {
            setMovies(res.data)
        }).catch(error =>{
            console.log(error)
        })
    };
    const submitRating = (movieTitle, rating) => {
        axios
          .post(`api/movie/rateMovie`, {
            title: movieTitle,
            rating: rating,
          })
          .catch((error) => {
            console.log(error);
          });
        };
        
    return (
            <div className="movieSearch">
              <h2>Movie Search</h2>

              <form onSubmit={(e) => { e.preventDefault(); getMovies() }}>
                <input
                  type="text"
                  placeholder="Enter movie query"
                  onChange={(e) => setQuery(e.target.value)}
                />
                <button type="submit">Search</button>
              </form>

              <div className="movieResults">
                {movies.map((movie) => (
                  <div className="movieCard" key={movie.id}>
                    <h3>{movie.title}</h3>
                    <img src={movie.image.url} alt={movie.title} />
                    <form
                      onSubmit={(e) => {
                        e.preventDefault();
                        submitRating(movie.title, e.target.rating.value);
                        e.target.reset();
                      }}
                    >
                      <input type="number" name="rating" min="1" max="5" />
                      <button type="submit">Submit Rating</button>
                    </form>
            
                  </div>
                ))}
              </div>
            </div>
          );
    
        
};
export default ImdbPage;
    


