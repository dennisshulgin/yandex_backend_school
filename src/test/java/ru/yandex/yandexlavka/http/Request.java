package ru.yandex.yandexlavka.http;

import java.util.HashMap;
import java.util.Map;

public class Request {
    String url;
    String body;
    Map<String, String> params;

    Map<String, String> headers;

    public Request(String url) {
        this.url = url;
        this.params = new HashMap<>();
        this.headers = new HashMap<>();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void addParam(String key, String value) {
        params.put(key, value);
    }
}
