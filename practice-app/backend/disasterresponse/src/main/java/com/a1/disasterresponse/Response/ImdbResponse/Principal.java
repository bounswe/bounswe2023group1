package com.a1.disasterresponse.Response.ImdbResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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