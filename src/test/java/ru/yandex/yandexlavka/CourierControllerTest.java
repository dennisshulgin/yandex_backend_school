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
public class CourierControllerTest {

    String url = "http://localhost:8080/couriers";

    @Test
    @Order(1)
    public void importCouriersTest() throws Exception{
        Request request = new Request(url);
        request.setBody(JsonStrings.importCouriersReq);
        request.addHeader("Content-Type", "application/json");

        Response response = ApiTester.postMethod(request);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode expectedTree = mapper.readTree(JsonStrings.importCouriersRes);
        JsonNode actualTree = mapper.readTree(response.getBody());

        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        Assertions.assertEquals(expectedTree, actualTree);
    }
    @Test
    @Order(2)
    public void getCourierByIdTest() throws Exception {
        Request request = new Request(url + "/1");
        Response response = ApiTester.getMethod(request);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode expectedTree = mapper.readTree(JsonStrings.getCourierByIdRes);
        JsonNode actualTree = mapper.readTree(response.getBody());

        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        Assertions.assertEquals(expectedTree, actualTree);
    }

    @Test
    @Order(3)
    public void getCouriersWithLimitAndOffset() throws Exception {
        Request request = new Request(url);
        request.addParam("limit", "2");
        request.addParam("offset", "1");

        Response response = ApiTester.getMethod(request);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode expectedTree = mapper.readTree(JsonStrings.getCouriersWithOffsetAndLimit);
        JsonNode actualTree = mapper.readTree(response.getBody());

        Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        Assertions.assertEquals(expectedTree, actualTree);
    }

    @Test
    @Order(4)
    public void incorrectImport() throws Exception {
        Request request = new Request(url);
        request.setBody(JsonStrings.incorrectCourier);
        request.addHeader("Content-Type", "application/json");

        Response response = ApiTester.postMethod(request);
        Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatusCode());
    }
}
