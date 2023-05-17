package ru.yandex.yandexlavka.http;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import java.util.Map;
import java.util.StringJoiner;

public class ApiTester {

    public static Response postMethod(Request request) throws Exception{
        HttpPost httpPost = new HttpPost(request.getUrl());

        for(Map.Entry<String, String> header : request.getHeaders().entrySet()) {
            httpPost.setHeader(header.getKey(), header.getValue());
        }

        StringEntity stringEntity = new StringEntity(request.getBody());
        httpPost.setEntity(stringEntity);

        Response response;

        try(CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost)) {

            int code = closeableHttpResponse.getCode();
            String jsonRes = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
            response = new Response(code, jsonRes);
        }
        return response;
    }

    public static Response getMethod(Request request) throws Exception{
        String paramString = "";

        if(!request.getParams().isEmpty()) {
            paramString += "?";
            StringJoiner sj = new StringJoiner("&");
            for(Map.Entry<String, String> param : request.getParams().entrySet()) {
                sj.add(param.getKey() + "=" + param.getValue());
            }
            paramString += sj.toString();
        }

        HttpGet httpGet = new HttpGet(request.getUrl() + paramString);

        for(Map.Entry<String, String> header : request.getHeaders().entrySet()) {
            httpGet.setHeader(header.getKey(), header.getValue());
        }

        Response response;

        try(CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet)) {

            int code = closeableHttpResponse.getCode();
            String jsonRes = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
            response = new Response(code, jsonRes);
        }

        return response;
    }
}
