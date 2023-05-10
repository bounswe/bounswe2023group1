package com.a1.disasterresponse.service;

import com.a1.disasterresponse.model.WeatherData;
import com.a1.disasterresponse.model.WikidataEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WikidataService {

    private static final String WIKIDATA_BASE_URL = "https://www.wikidata.org/w/api.php";

    public static final String WIKIDATA_SEARCH_ENTITY = "?action=wbsearchentities&format=json&language=en&type=item&continue=0&search=";
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    public String getWikidataEntityId(String name) throws IOException {
        String url = WIKIDATA_BASE_URL + WIKIDATA_SEARCH_ENTITY + name;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("Unexpected code " + response);
            }

            String body = response.body().string();
            JsonNode rootNode = mapper.readTree(body);

            return rootNode.get("search").get(0).get("id").asText();

        }
    }

    private record WikidataSearchResult(String id, WikidataSearchResultDisplay display,) {
    }

    private record WikidataSearchResultDisplay(WikidataSearchResultDisplayItem label,
                                               WikidataSearchResultDisplayItem description) {

    }

    private record WikidataSearchResultDisplayItem(String value, String language) {
    }

}
