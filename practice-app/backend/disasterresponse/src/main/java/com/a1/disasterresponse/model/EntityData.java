package com.a1.disasterresponse.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Map;


public record EntityData(String id, Map<String, String> labels,
                         List<String> categories) {

    public EntityRecord toRecord() {
        return new EntityRecord(id, labels.getOrDefault("en", id));
    }

    @Entity
    public static class EntityRecord {
        @Id
        public String id;

        public String name;

        public EntityRecord() {
        }

        public EntityRecord(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}