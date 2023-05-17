package ru.yandex.yandexlavka.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class RateLimiterService {

    private int limit;

    private final Map<String, EndPoint> endPointMap;

    public RateLimiterService() {
        this.limit = 10;
        this.endPointMap = new HashMap<>();
        this.endPointMap.put("/couriers/#", new EndPoint(limit));
        this.endPointMap.put("/couriers", new EndPoint(limit));
        this.endPointMap.put("/orders", new EndPoint(limit));
        this.endPointMap.put("/orders/complete", new EndPoint(limit));
        this.endPointMap.put("/couriers/meta-info/#", new EndPoint(limit));
        this.endPointMap.put("/orders/#", new EndPoint(limit));

        Thread releaser = new Thread(new Releaser(endPointMap));
        releaser.start();
    }

    public boolean acquire(String path) {
        if(!endPointMap.containsKey(path)) {
            return true;
        }
        EndPoint endPoint = endPointMap.get(path);
        synchronized (endPoint) {
            if(endPoint.requestCount < endPoint.limit) {
                endPoint.requestCount++;
                return true;
            } else {
                return false;
            }
        }
    }

    public static class Releaser implements Runnable {

        private volatile boolean isInterrupted = false;
        private final Map<String, EndPoint> endPointMap;

        public Releaser(Map<String, EndPoint> endPointMap) {
            this.endPointMap = endPointMap;
        }

        @Override
        public void run() {
            while(!isInterrupted) {
                for (EndPoint endPoint : endPointMap.values()) {
                    synchronized (endPoint) {
                        if (endPoint.requestCount > 0) {
                            endPoint.requestCount--;
                        }
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void interrupt() {
            this.isInterrupted = true;
        }
    }

    private static class EndPoint {
        int requestCount;
        int limit;
        public EndPoint(int limit) {
            this.limit = limit;
        }
    }
}
