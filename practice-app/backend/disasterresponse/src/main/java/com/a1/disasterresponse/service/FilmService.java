package com.a1.disasterresponse.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class FilmService {
    String BASE_URL = "https://imdb8.p.rapidapi.com/title";

    String API_KEY = "8720851bd4msh5b9408dcf528f49p16c2cfjsna4066bc59d74";

    private static final OkHttpClient client = new OkHttpClient();

    private static final ObjectMapper mapper = new ObjectMapper();


    public String getFilmId(String query) throws IOException {
        String url = String.format("%s/v2/find?title=%s", BASE_URL, query);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("X-RapidAPI-Key", API_KEY)
                .addHeader("X-RapidAPI-Host", "imdb8.p.rapidapi.com")
                .build();


        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String body = response.body().string();
            JsonNode rootNode = mapper.readTree(body);

            JsonNode current = rootNode.get("current");
            if (current == null) return null;
            List<>

            String id = current.get("")
        }

    }
}
