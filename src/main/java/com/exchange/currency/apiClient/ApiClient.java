package com.exchange.currency.apiClient;

import com.exchange.currency.exceptions.ExchangeProviderException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class ApiClient {
    private final HttpClient client = HttpClient.newHttpClient();

    public String getResponse(String api) throws Exception {
        HttpResponse<String> response = apiRequest(api);

        return response.body();
    }

    private HttpResponse<String> apiRequest(String api) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(api))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        handleErrors(response);

        return response;
    }

    private void handleErrors(HttpResponse<String> response) {
        if (response.statusCode() != HttpStatus.OK.value()) {
            Gson gson = new GsonBuilder().create();
            JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
            String errorMessage = jsonResponse.get("error").getAsJsonObject().get("en").getAsString();

            throw new ExchangeProviderException("Data provider error. Initial error: " + errorMessage);
        }
    }
}
