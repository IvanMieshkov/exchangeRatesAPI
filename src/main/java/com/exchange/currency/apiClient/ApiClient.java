package com.exchange.currency.apiClient;

import com.exchange.currency.exceptions.ExchangeProviderException;
import com.exchange.currency.model.Exchange;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class ApiClient {

    public List<Exchange> loadData(final String api, final String provider) throws Exception {
        Gson gson = new GsonBuilder().create();
        String response = apiRequest(api);
        List<Exchange> rates = gson.fromJson(response, new TypeToken<List<Exchange>>(){}.getType());
        assignProvider(provider, rates);
        return rates;
    }

    private String apiRequest(String api) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(api))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return handleErrors(response);
    }

    private String handleErrors(HttpResponse<String> response) {
        if (response.statusCode() == HttpStatus.OK.value()) {
            return response.body();
        } else {
            Gson gson = new GsonBuilder().create();
            JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
            String errorMessage = jsonResponse.get("error").getAsJsonObject().get("en").getAsString();

            throw new ExchangeProviderException("Data provider error. Initial error: " + errorMessage);
        }
    }

    private void assignProvider(final String provider, final List<Exchange> rates) {
        for (Exchange rate : rates) {
            rate.setProvider(provider);
        }
    }
}
