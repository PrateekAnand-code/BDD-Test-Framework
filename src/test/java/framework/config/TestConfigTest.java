package framework.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestConfigTest {
    @Test void unknownEnvironmentFailsWithActionableMessage() {
        String previous = System.getProperty("env");
        try {
            System.setProperty("env", "missing-environment");
            var error = assertThrows(IllegalArgumentException.class, TestConfig::new);
            assertTrue(error.getMessage().contains("config/missing-environment.properties"));
        } finally { restore("env", previous); }
    }
    @Test void invalidBooleanDoesNotSilentlyBecomeFalse() {
        String previous = System.getProperty("test.flag");
        try {
            System.setProperty("test.flag", "typo");
            assertThrows(IllegalArgumentException.class, () -> new TestConfig().flag("test.flag", true));
        } finally { restore("test.flag", previous); }
    }
    private static void restore(String key, String value) {
        if (value == null) System.clearProperty(key); else System.setProperty(key, value);
    }
}
