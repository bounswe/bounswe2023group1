package com.a1.disasterresponse.service;

import Response.ImdbResponse.ImdbResponse;
import Response.ImdbResponse.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Service
public class FilmService {
    String BASE_URL = "https://imdb8.p.rapidapi.com/title/v2/find?title=";

    String API_KEY = "8720851bd4msh5b9408dcf528f49p16c2cfjsna4066bc59d74";

    private static final OkHttpClient client = new OkHttpClient();

    private static final ObjectMapper mapper = new ObjectMapper();


    public List<Movie> getFilmId(String query, int limit) throws IOException {

        String encodedQueryParam = URLEncoder.encode(query, StandardCharsets.UTF_8).replaceAll("\\+", "%20");

        Request request = new Request.Builder()
                .url(BASE_URL + encodedQueryParam + "&limit="+ Integer.toString(limit)+"&sortArg=moviemeter%2Casc")
                .get()
                .addHeader("X-RapidAPI-Key", API_KEY)
                .addHeader("X-RapidAPI-Host", "imdb8.p.rapidapi.com")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String body = response.body().string();

            ImdbResponse imdbResponse = mapper.readValue(body, ImdbResponse.class);
            return imdbResponse.getMovies();
            }
        }




}

