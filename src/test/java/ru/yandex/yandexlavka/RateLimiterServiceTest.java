package ru.yandex.yandexlavka;

import org.junit.jupiter.api.Test;
import ru.yandex.yandexlavka.service.RateLimiterService;

public class RateLimiterServiceTest {

    RateLimiterService rateLimiterService = new RateLimiterService();

    @Test
    public void rateLimiterTest() throws InterruptedException {
        int i = 1000;

        while(i > 0) {
            System.out.println(rateLimiterService.acquire("/couriers"));
            i--;
        }
    }
}
