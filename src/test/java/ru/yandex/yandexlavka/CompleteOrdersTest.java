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
public class CompleteOrdersTest {

    String url = "http://localhost:8080";

    @Test
    @Order(1)
    public void ratingAndEarningTest() throws Exception {

        Request request1 = new Request(url + "/orders");
        request1.setBody(JsonStrings.importAdditionalOrders);
        request1.addHeader("Content-Type", "application/json");
        Response response1 = ApiTester.postMethod(request1);

        Request request2 = new Request(url + "/orders/complete");
        request2.setBody(JsonStrings.completeAdditionalOrdersReq);
        request2.addHeader("Content-Type", "application/json");
        Response response2 = ApiTester.postMethod(request2);

        Request request3 = new Request(url + "/couriers/meta-info/1");
        request3.addParam("startDate", "2023-04-23");
        request3.addParam("endDate", "2023-04-24");
        Response response3 = ApiTester.getMethod(request3);


        ObjectMapper mapper = new ObjectMapper();
        JsonNode expectedTree = mapper.readTree(JsonStrings.ratingAndEarningRes);
        JsonNode actualTree = mapper.readTree(response3.getBody());

        Assertions.assertEquals(HttpStatus.SC_OK, response3.getStatusCode());
        Assertions.assertEquals(expectedTree, actualTree);

    }
}
