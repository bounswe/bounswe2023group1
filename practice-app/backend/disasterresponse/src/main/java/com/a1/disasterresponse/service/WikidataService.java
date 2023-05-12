package com.a1.disasterresponse.service;

import com.a1.disasterresponse.model.EntityData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WikidataService {

    private static final String WIKIDATA_BASE_URL = "https://www.wikidata.org/w/api.php";

    public static final String WIKIDATA_SEARCH = "?action=wbsearchentities&format=json&language=en&type=item&continue=0&search=";
    public static final String WIKIDATA_GET_ENTITY = "?action=wbgetentities&format=json&languages=en|tr&props=labels|info|claims&ids=";
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    public String searchForWikidataEntity(String query) throws IOException {
        JsonNode rootNode = executeWikidataQuery(WIKIDATA_SEARCH + query);

        return rootNode.get("search").get(0).get("id").asText();
    }

    public EntityData getWikidataEntityFromId(String id) throws IOException {
        JsonNode entity = executeWikidataQuery(WIKIDATA_GET_ENTITY + id).get("entities").get(id);

        Map<String, String> labels = new HashMap<>();

        entity.get("labels")
                .fields()
                .forEachRemaining(e -> labels.put(
                        e.getValue().get("language").textValue(),
                        e.getValue().get("value").textValue()
                ));

        List<String> supercategories = new ArrayList<>();

        if (entity.get("claims").has("P279")) {
            entity.get("claims")
                    .get("P279")
                    .fields()
                    .forEachRemaining(e -> supercategories.add(e
                            .getValue()
                            .get("mainsnak")
                            .get("datavalue")
                            .get("value")
                            .get("id")
                            .asText()));
        }

        return new EntityData(id, labels, supercategories);
    }

    private JsonNode executeWikidataQuery(String query) throws IOException {
        Request request = new Request.Builder()
                .url(WIKIDATA_BASE_URL + query)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("Unexpected code " + response);
            }

            String body = response.body().string();

            return mapper.readTree(body);
        }
    }
}
