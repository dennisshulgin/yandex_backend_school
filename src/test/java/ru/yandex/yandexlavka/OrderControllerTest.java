package ru.yandex.yandexlavka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.*;
import ru.yandex.yandexlavka.http.ApiTester;
import ru.yandex.yandexlavka.http.Request;
import ru.yandex.yandexlavka.http.Response;
import ru.yandex.yandexlavka.jsons.JsonStrings;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderControllerTest {

    String url = "http://localhost:8080/orders";

    @Test
    @Order(1)
    public void importOrdersTest() throws Exception{
        Request request = new Request(url);
        request.setBody(JsonStrings.importOrdersReq);
        request.addHeader("Content-Type", "application/json");

        Response response = ApiTester.postMethod(request);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode expectedTree = mapper.readTree(JsonStrings.importOrdersRes);
        JsonNode actualTree = mapper.readTree(response.getBody());

        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        Assertions.assertEquals(expectedTree, actualTree);
    }

    @Test
    @Order(2)
    public void getOrderByIdTest() throws Exception {
        Request request = new Request(url + "/1");
        Response response = ApiTester.getMethod(request);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode expectedTree = mapper.readTree(JsonStrings.getOrderByIdRes);
        JsonNode actualTree = mapper.readTree(response.getBody());

        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        Assertions.assertEquals(expectedTree, actualTree);
    }

    @Test
    @Order(3)
    public void getOrdersWithLimitAndOffset() throws Exception {
        Request request = new Request(url);
        request.addParam("limit", "2");
        request.addParam("offset", "1");

        Response response = ApiTester.getMethod(request);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode expectedTree = mapper.readTree(JsonStrings.getOrdersWithOffsetAndLimit);
        JsonNode actualTree = mapper.readTree(response.getBody());

        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        Assertions.assertEquals(expectedTree, actualTree);
    }

    @Test
    @Order(4)
    public void completeOrderTest() throws Exception {
        Request request = new Request(url + "/complete");
        request.setBody(JsonStrings.completeOrderReq);
        request.addHeader("Content-Type", "application/json");

        Response response = ApiTester.postMethod(request);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode expectedTree = mapper.readTree(JsonStrings.completeOrderRes);
        JsonNode actualTree = mapper.readTree(response.getBody());

        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        Assertions.assertEquals(expectedTree, actualTree);
    }
}
