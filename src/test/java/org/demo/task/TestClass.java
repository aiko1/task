package org.demo.task;

import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestClass {

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Feature("Получение списка постов")
    @Test
    public void getPosts_thenOK() {
        Steps.getPosts("/posts", 200);
        Steps.getPostsBody("/posts",
            "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
    }

    @Feature("Получение списка постов")
    @Test
    public void getPosts_thenErrorIncorrectStatusCode() {
        Steps.getPosts("/posts", 400);
    }

    @Feature("Получение списка постов")
    @Test
    public void getPosts_thenErrorTitleNotExists() {
        Steps.getPostsBody("/posts", "текст на русском");
    }

    @Feature("Добавление нового поста")
    @Test
    public void createPost_thenOK() throws IOException {
        Steps.createPostCheckStatusCode("/posts", 201);
        Steps.createPostCheckRequestBody("/posts", "foo");
    }

    @Feature("Добавление нового поста")
    @Test
    public void createPost_thenErrorIncorrectStatusCode() throws IOException {
        Steps.createPostCheckStatusCode("/posts", 400);
    }

    @Feature("Добавление нового поста")
    @Test
    public void createPost_thenErrorTitleNotExists() throws IOException {
        Steps.createPostCheckRequestBody("/posts", "exists");
    }

}
