package API;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class GoogleBooksAPI {
    private static final String API_KEY = "AIzaSyCt-87DOoWOn3QZWIwPXTxF5nnchYwIkL4";
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes";

    public static String searchBooks(String query) throws Exception {

        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        // Tạo URL truy vấn với API Key
        String url = BASE_URL + "?q=" + encodedQuery + "&key=" + API_KEY;


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        if (response.statusCode() != 200) {
            throw new Exception("Failed to fetch data from Google Books API. Status code: " + response.statusCode());
        }

        return response.body();
    }

    public static String getBookCover(String query) throws Exception {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        String url = BASE_URL + "?q=" + encodedQuery + "&key=" + API_KEY;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
        JsonArray items = json.getAsJsonArray("items");

        if (items != null && items.size() > 0) {
            JsonObject volumeInfo = items.get(0).getAsJsonObject().getAsJsonObject("volumeInfo");
            if (volumeInfo.has("imageLinks")) {
                JsonObject imageLinks = volumeInfo.getAsJsonObject("imageLinks");
                if (imageLinks.has("thumbnail")) {
                    return imageLinks.get("thumbnail").getAsString();
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        try {

            String result = searchBooks("Summertime Render");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
