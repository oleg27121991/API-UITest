package by.veremei.api.tests;

import by.veremei.api.authorization.AuthorizationAPI;
import by.veremei.api.bookstore.BookstoreAPI;
import by.veremei.api.data.AuthData;
import by.veremei.api.data.BooksData;
import by.veremei.api.models.book.Book;
import by.veremei.api.models.book.BooksCollection;
import by.veremei.api.models.login.Login;
import by.veremei.api.models.login.SuccessUserLogin;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("API tests")
public class ProfileTest {
    AuthData authData = new AuthData();
    BooksData booksData = new BooksData();

    @Test
    @Tag("API")
    @DisplayName("Добавление книги в коллекцию пользователя")
    void addBookToCollectionTest() {
        Login user = new Login(authData.userName, authData.userPass);

        SuccessUserLogin successUserLogin = step("Выполняем запрос на успешную аутентификацию пользователя", () ->
                AuthorizationAPI.loginUser(user));

        String token = step("Записываем в переменную token пользователя", successUserLogin::token);
        String userId = step("Записываем в переменную id пользователя", successUserLogin::userId);

        step("Выполняем запрос на удаление книг из коллекции пользователя", () -> BookstoreAPI.deleteBooks(token, userId));

        Book book = new Book(booksData.isbn);
        BooksCollection bookData = new BooksCollection(userId, Collections.singletonList(book));

        Response addResponse = step("Выполняем запрос на добавление книги в коллекцию пользователя", () -> BookstoreAPI.addBookToCollection(token, bookData));
        step("Проверяем, что тело ответа не равно null", () ->
            assertNotNull(addResponse));
        step("Сравниваем полученный isbn книги с ожидаемым", () ->
            assertThat(addResponse.body().jsonPath().getString("books[0].isbn")).isEqualTo(booksData.isbn));
    }
}
