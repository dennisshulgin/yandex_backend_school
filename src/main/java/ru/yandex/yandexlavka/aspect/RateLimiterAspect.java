package ru.yandex.yandexlavka.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.yandex.yandexlavka.service.RateLimiterService;
import ru.yandex.yandexlavka.util.Utils;

@Component
@Aspect
public class RateLimiterAspect {

    private HttpServletRequest request;

    private RateLimiterService rateLimiterService;


    public RateLimiterAspect(HttpServletRequest request, RateLimiterService rateLimiterService) {
        this.request = request;
        this.rateLimiterService = rateLimiterService;
    }

    @Around("execution(* ru.yandex.yandexlavka.controller.*.*(..))")
    public ResponseEntity<?> beforeProcessRequest(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String uri = request.getRequestURI();
        String pattern = Utils.pathToPattern(uri);

        if(!rateLimiterService.acquire(pattern)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }

        return (ResponseEntity<?>) proceedingJoinPoint.proceed();
    }
}
