package managers.http.kv;

import exceptions.IntersectionException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private final String API_TOKEN;
    private String url;
    private HttpClient client;

    public KVTaskClient(String url) {
        this.API_TOKEN = null;
        this.url = url;
        this.client = HttpClient.newHttpClient();
    }

    /*private String register() {
        URI uri = URI.create(url.concat("/register"));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | IntersectionException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    } */

    //void put(String key, String json)

}
