package com.a1.disasterresponse.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImdbResponse {
    @JsonProperty("type")
    private String type;
    private String query;
    private List<Result> results;
}



@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class Result {
    @JsonProperty("id")
    private String id;
    @JsonProperty("image")
    private Image image;
    @JsonProperty("runningTimeInMinutes")
    private int runningTimeInMinutes;
    @JsonProperty("nextEpisode")
    private String nextEpisode;
    @JsonProperty("numberOfEpisodes")
    private int numberOfEpisodes;
    @JsonProperty("seriesEndYear")
    private int seriesEndYear;
    @JsonProperty("seriesStartYear")
    private int seriesStartYear;
    @JsonProperty("title")
    private String title;
    @JsonProperty("titleType")
    private String titleType;
    @JsonProperty("year")
    private int year;
    @JsonProperty("principals")
    private List<Principal> principals;
}

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class Image {
    @JsonProperty("height")
    private int height;
    @JsonProperty("id")
    private String id;
    @JsonProperty("url")
    private String url;
    @JsonProperty("width")
    private int width;


}

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class Principal {
    @JsonProperty("id")
    private String id;
    @JsonProperty("legacyNameText")
    private String legacyNameText;
    @JsonProperty("name")
    private String name;
    @JsonProperty("category")
    private String category;
    @JsonProperty("characters")
    private List<String> characters;
    @JsonProperty("endYear")
    private int endYear;
    @JsonProperty("episodeCount")
    private int episodeCount;
    @JsonProperty("roles")
    private List<Role> roles;
    @JsonProperty("startYear")
    private int startYear;
}

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class Role {
    @JsonProperty("character")
    private String character;
    @JsonProperty("characterId")
    private String characterId;

}




