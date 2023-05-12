package com.a1.disasterresponse.service;

import com.a1.disasterresponse.model.ImdbResponse;
import com.a1.disasterresponse.model.Movie;
import com.a1.disasterresponse.model.WatchedItem;
import com.a1.disasterresponse.repository.WatchedItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Service
public class MovieService {
    String BASE_URL = "https://imdb8.p.rapidapi.com/title/v2/find?title=";

    String API_KEY = "8720851bd4msh5b9408dcf528f49p16c2cfjsna4066bc59d74";

    private static final OkHttpClient client = new OkHttpClient();

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private final WatchedItemRepository watchedItemRepository;

    public MovieService(WatchedItemRepository watchedItemRepository) {
        this.watchedItemRepository = watchedItemRepository;
    }


    public List<Movie> getMovies(String query, int limit) throws IOException {

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
    public Long saveWatchedItem(WatchedItem movie) {
        WatchedItem savedWatchedItem = watchedItemRepository.save(movie);
        return savedWatchedItem.getId();
    }



    public Double getAverageRating(String title){
        Double averageRating = watchedItemRepository.getAverageRating(title);
        if (averageRating == null) {
            return 0.0;
        }
        return averageRating;
    }

    public List<WatchedItem> getAllWatchedItems() {
        return watchedItemRepository.findAll();
    }





}

