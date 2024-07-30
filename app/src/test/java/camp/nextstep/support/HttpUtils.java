package camp.nextstep.support;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

public abstract class HttpUtils {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(3))
            .build();

    public static HttpResponse<String> post(final String path, final Map<String, Object> params) {
        final HttpRequest request = build(path)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(FormUtils.getFormString(params)))
                .build();
        return send(request);
    }

    public static HttpResponse<String> get(final String path) {
        final HttpRequest request = build(path)
                .GET()
                .build();

        return send(request);
    }

    public static HttpResponse<String> get(final String path, final Map<String, Object> params) {
        return get(path + "?" + FormUtils.getFormString(params));
    }

    private static HttpRequest.Builder build(final String path) {
        return HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080" + path))
                .timeout(Duration.ofSeconds(3));
    }

    private static HttpResponse<String> send(final HttpRequest request) {
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
