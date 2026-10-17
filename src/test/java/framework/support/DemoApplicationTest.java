package framework.support;

import org.junit.jupiter.api.Test;
import java.net.URI;
import java.net.http.*;
import static org.junit.jupiter.api.Assertions.*;

class DemoApplicationTest {
    @Test void malformedJsonIsRejectedAndServerRemainsUsable() throws Exception {
        try (var app = new DemoApplication()) {
            var client = HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder(URI.create(app.baseUrl() + "/users"))
                .POST(HttpRequest.BodyPublishers.ofString("{bad-json")).build();
            assertEquals(400, client.send(request, HttpResponse.BodyHandlers.ofString()).statusCode());
            var get = HttpRequest.newBuilder(URI.create(app.baseUrl() + "/users")).GET().build();
            assertEquals(405, client.send(get, HttpResponse.BodyHandlers.ofString()).statusCode());
        }
    }
}
