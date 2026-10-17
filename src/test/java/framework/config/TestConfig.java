package framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** System property > environment variable > named environment resource. */
public final class TestConfig {
    private final Properties values = new Properties();

    public TestConfig() {
        String env = System.getProperty("env", "local");
        if (!env.matches("[a-zA-Z0-9_-]+")) throw new IllegalArgumentException("Invalid environment: " + env);
        String resource = "config/" + env + ".properties";
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(resource)) {
            if (stream == null) throw new IllegalArgumentException("Missing configuration: " + resource);
            values.load(stream);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read " + resource, e);
        }
    }

    public String get(String key, String fallback) {
        String environmentKey = key.replace('.', '_').toUpperCase(java.util.Locale.ROOT);
        return System.getProperty(key, System.getenv().getOrDefault(environmentKey, values.getProperty(key, fallback)));
    }

    public boolean flag(String key, boolean fallback) {
        String value = get(key, Boolean.toString(fallback));
        if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false"))
            throw new IllegalArgumentException(key + " must be true or false");
        return Boolean.parseBoolean(value);
    }
}
