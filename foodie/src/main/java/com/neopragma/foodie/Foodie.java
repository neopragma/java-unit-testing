package com.neopragma.foodie;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class Foodie {

    private final String baseURLString = "https://world.openfoodfacts.org/api/v0/product/%s";
    private final HttpRequest request;

    public Foodie(String productCode) {
        this.request = HttpRequest.newBuilder()
                .uri(URI.create(
                        String.format(baseURLString,
                                URLEncoder.encode(productCode, StandardCharsets.UTF_8)
                        )))
                .GET()
                .build();
    }

    public String getProductInfo() {
        String body = null;
        try (HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build()) {
            HttpResponse<String> response = client.send(this.request,
                    HttpResponse.BodyHandlers.ofString());
            body = response.body();
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();

        }
        return body;
    }
}
