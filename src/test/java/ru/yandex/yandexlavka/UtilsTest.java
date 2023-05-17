package ru.yandex.yandexlavka;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.yandexlavka.util.Utils;

public class UtilsTest {

    @Test
    public void pathToPatternTest() {
        String[][] tests = new String[][] {
                new String[] {"/couriers/1", "/couriers/#"},
                new String[] {"/couriers/complete", "/couriers/complete"},
                new String[] {"/couriers", "/couriers"},
                new String[] {"/orders/123", "/orders/#"}
        };

        for (String[] test : tests) {
            Assertions.assertEquals(test[1], Utils.pathToPattern(test[0]));
        }
    }
}
