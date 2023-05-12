package com.a1.disasterresponse.model;

import java.util.List;
import java.util.Map;

public record EntityData(String id, Map<String, String> labels, List<String> categories) {
}