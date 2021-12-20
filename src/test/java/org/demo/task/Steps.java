package org.demo.task;

import static io.restassured.RestAssured.get;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;

public class Steps {

    private static final String RESOURCES = "src/test/resources/attachments";
    private static RequestSpecification request = RestAssured.given();

    @Step("Проверка эквивалентности статуса запроса GET {endPoint} значению {expectedStatusCode}")
    public static void getPosts(String endPoint, int expectedStatusCode) {
        int code = get(endPoint).getStatusCode();
        Assertions.assertTrue(code == expectedStatusCode,
            "Статус код запроса не соответствует ожидаемому значению");
    }

    @Step("Проверка, что тело ответа запроса GET {endPoint} содержит в себе значение {title}")
    public static void getPostsBody(String endPoint, String title) {
        String body = get(endPoint).getBody().asString();
        Assertions.assertTrue(body.contains(title), "Тело запроса не содержит ожидаемое значение");
    }

    @Attachment(value = "Вложение", type = "application/json", fileExtension = ".json")
    @Step("Считать тело запроса из файла {resourceName}")
    public static byte[] getBytesAnnotationWithArgs(String resourceName) throws IOException {
        return Files.readAllBytes(Paths.get(RESOURCES, resourceName));
    }

    @Step("Проверка эквивалентности статуса запроса POST {endPoint} значению {expectedStatusCode}")
    public static void createPostCheckStatusCode(String endPoint, int expectedStatusCode)
        throws IOException {
        request.header("Content-Type", "application/json");

        byte[] data = getBytesAnnotationWithArgs("json.json");
        request.body(new String(data, StandardCharsets.UTF_8));

        int code = request.post(endPoint).getStatusCode();

        Assertions.assertTrue(code == expectedStatusCode,
            "Статус код запроса не соответствует ожидаемому значению");
    }

    @Step("Проверка, что тело ответа запроса POST {endPoint} содержит в себе значение {title}")
    public static void createPostCheckRequestBody(String endPoint, String title)
        throws IOException {
        request.header("Content-Type", "application/json");

        byte[] data = getBytesAnnotationWithArgs("json.json");
        request.body(new String(data, StandardCharsets.UTF_8));

        String body = request.post(endPoint).getBody().asString();

        Assertions.assertTrue(body.contains(title), "Тело запроса не содержит ожидаемое значение");
    }
}
