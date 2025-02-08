import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

class HorseTest {

    @Test
    void constructor_ThrowsException_WhenNameIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 10.0, 5.0));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    void constructor_ThrowsException_WhenNameIsBlank(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 10.0, 5.0));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void constructor_ThrowsException_WhenSpeedIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Horse", -1.0, 5.0));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void constructor_ThrowsException_WhenDistanceIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Horse", 10.0, -5.0));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getName() {
        Horse horse = new Horse("Thunder", 10.0, 5.0);
        assertEquals("Thunder", horse.getName());
    }

    @Test
    void getSpeed() {
        Horse horse = new Horse("Lightning", 15.0, 7.0);
        assertEquals(15.0, horse.getSpeed());
    }

    @Test
    void getDistance() {
        Horse horse = new Horse("Storm", 12.0, 8.0);
        assertEquals(8.0, horse.getDistance());
    }

    @Test
    void getDistance_WithTwoParameters() {
        Horse horse = new Horse("Breeze", 9.0);
        assertEquals(0.0, horse.getDistance());
    }

    @Test
    void move_CallsGetRandomDouble() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("Blaze", 10.0, 5.0);
            horse.move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.5, 0.9})
    void move_UpdatesDistance(double randomValue) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);
            Horse horse = new Horse("Comet", 10.0, 5.0);
            horse.move();
            assertEquals(5.0 + 10.0 * randomValue, horse.getDistance());
        }
    }
}