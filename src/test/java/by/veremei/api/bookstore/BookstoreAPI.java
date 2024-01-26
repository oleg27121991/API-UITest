package by.veremei.api.bookstore;

import by.veremei.api.models.book.BooksCollection;
import by.veremei.api.spec.Specifications;
import io.restassured.response.Response;

import static by.veremei.api.data.ApiEndpoint.*;
import static io.restassured.RestAssured.given;
import static by.veremei.tests.BaseTest.BASE_URI;

public class BookstoreAPI {
    public static Response deleteBooks(String token, String userId) {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URI), Specifications.responseSpecDelete204());
        return given()
                .header("Authorization", "Bearer " + token)
                .queryParams("UserId", userId)
                .when()
                .delete(DELETE_BOOKS_URL)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response addBookToCollection(String token, BooksCollection book) {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URI), Specifications.responseSpecCreated201());
        return given()
                .header("Authorization", "Bearer " + token)
                .body(book)
                .when()
                .post(POST_ADD_BOOK_TO_COLLECTION)
                .then()
                .log().all()
                .extract().response();
    }
}
