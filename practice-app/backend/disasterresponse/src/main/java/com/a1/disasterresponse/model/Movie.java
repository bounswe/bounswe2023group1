package com.a1.disasterresponse.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class Movie {
    private String id;
    private Image image;
    private int runningTimeInMinutes;
    private String nextEpisode;
    private int numberOfEpisodes;
    private int seriesEndYear;
    private int seriesStartYear;
    private String title;
    private String titleType;
    private int year;
    private List<Principal> principals;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Role {
    private String character;
    private String characterId;

}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Principal {

    private String id;

    private String legacyNameText;

    private String name;

    private String category;

    private List<String> characters;

    private int endYear;

    private int episodeCount;

    private List<Role> roles;

    private int startYear;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Image {
    private int height;
    private String id;
    private String url;
    private int width;

}
