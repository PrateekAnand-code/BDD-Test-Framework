package framework.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

/** Deterministic example target, not a production authentication service. */
public final class DemoApplication implements AutoCloseable {
    private final HttpServer server;
    private final ObjectMapper json = new ObjectMapper();

    public DemoApplication() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/login", exchange -> send(exchange, 200, "text/html", """
            <!doctype html><html lang="en"><meta charset="utf-8"><title>Demo login</title>
            <h1>Sign in</h1><form id="login">
            <label>Username<input name="username" autocomplete="username"></label>
            <label>Password<input name="password" type="password" autocomplete="current-password"></label>
            <button type="submit">Sign in</button></form><p role="status"></p>
            <script>
            document.querySelector('form').onsubmit = async event => {
              event.preventDefault();
              const form = new FormData(event.target);
              const response = await fetch('/session', {method:'POST', headers:{'Content-Type':'application/json'},
                body:JSON.stringify(Object.fromEntries(form))});
              const data = await response.json();
              document.querySelector('[role=status]').textContent = data.message;
            };
            </script></html>
            """));
        server.createContext("/session", exchange -> {
            if (!exchange.getRequestMethod().equals("POST")) { sendJson(exchange, 405, Map.of("error", "Method not allowed")); return; }
            try {
                var body = json.readTree(exchange.getRequestBody());
                boolean valid = body != null && "demo".equals(body.path("username").asText())
                        && "demo-password".equals(body.path("password").asText());
                sendJson(exchange, valid ? 200 : 401, Map.of("message", valid ? "Welcome, demo" : "Invalid credentials"));
            } catch (IOException e) { sendJson(exchange, 400, Map.of("error", "Invalid JSON")); }
        });
        server.createContext("/users", exchange -> {
            if (!exchange.getRequestMethod().equals("POST")) { sendJson(exchange, 405, Map.of("error", "Method not allowed")); return; }
            try {
                var body = json.readTree(exchange.getRequestBody());
                if (body == null || body.path("name").asText().isBlank() || body.path("job").asText().isBlank()) {
                    sendJson(exchange, 400, Map.of("error", "name and job are required")); return;
                }
                sendJson(exchange, 201, Map.of("id", UUID.randomUUID().toString(),
                        "name", body.path("name").asText(), "job", body.path("job").asText()));
            } catch (IOException e) { sendJson(exchange, 400, Map.of("error", "Invalid JSON")); }
        });
        server.start();
    }

    public String baseUrl() { return "http://127.0.0.1:" + server.getAddress().getPort(); }
    private void sendJson(HttpExchange exchange, int status, Object body) throws IOException {
        send(exchange, status, "application/json", json.writeValueAsString(body));
    }
    private static void send(HttpExchange exchange, int status, String type, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", type + "; charset=utf-8");
        exchange.sendResponseHeaders(status, bytes.length);
        try (var stream = exchange.getResponseBody()) { stream.write(bytes); }
        finally { exchange.close(); }
    }
    @Override public void close() { server.stop(0); }
}
