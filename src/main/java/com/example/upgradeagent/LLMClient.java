package com.example.upgradeagent;

import okhttp3.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.*;

public class LLMClient {
    private static final String API_KEY = "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";
    private static final String ENDPOINT = "https://api.openai.com/v1/chat/completions";
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String queryLLM(String prompt) throws IOException {
        Map<String, Object> body = Map.of(
            "model", "gpt-4",
            "messages", List.of(Map.of("role", "user", "content", prompt)),
            "temperature", 0.7,
            "max_tokens", 2000
        );
        String json = mapper.writeValueAsString(body);

        Request request = new Request.Builder()
            .url(ENDPOINT)
            .addHeader("Authorization", "Bearer " + API_KEY)
            .addHeader("Content-Type", "application/json")
            .post(RequestBody.create(json, MediaType.get("application/json")))
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
             JsonNode jsonResponse = mapper.readTree(response.body().string());
          return jsonResponse.path("choices").get(0).path("message").path("content").asText();
        }
    }
}
