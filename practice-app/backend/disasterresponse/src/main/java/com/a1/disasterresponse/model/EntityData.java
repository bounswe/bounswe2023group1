package com.a1.disasterresponse.model;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public record EntityData(String id, Map<String, Info> labels, List<Claim> claims) {
    public record Info(String value, String langugage) {
    }

    @JsonDeserialize(using = Claim.ClaimDeserialiser.class)
    public record Claim(Snak mainsnak, String type, String id, String rank) {
        public static final String SUBCLASS_OF = "P279";

        public record Snak(String snaktype, String property, String hash, Datavalue datavalue) {
            public record Datavalue(Value value, String type) {
                public record Value(String entity_type, int numeric_id, String id) {

                }
            }
        }

        public static class ClaimDeserialiser extends JsonDeserializer<Claim> {
            @Override
            public Claim deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {

            }
        }
    }
}

]