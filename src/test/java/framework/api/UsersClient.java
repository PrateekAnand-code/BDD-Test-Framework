package framework.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;
import static io.restassured.RestAssured.given;

public final class UsersClient {
    private final RequestSpecification specification;
    public UsersClient(String baseUrl) {
        specification = new RequestSpecBuilder().setBaseUri(baseUrl).setContentType(ContentType.JSON)
            .setConfig(RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", 10000).setParam("http.socket.timeout", 10000)))
            .build();
    }
    public Response create(String name, String job) {
        return given().spec(specification).body(Map.of("name", name, "job", job)).post("/users");
    }
}
