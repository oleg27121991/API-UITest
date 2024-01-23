package by.veremei.tests;

import by.veremei.api.authorization.AuthorizationAPI;
import by.veremei.api.bookstore.BookstoreAPI;
import by.veremei.api.data.BooksData;
import by.veremei.api.models.book.Book;
import by.veremei.api.models.book.BooksCollection;
import by.veremei.api.models.login.SuccessUserLogin;
import by.veremei.helpers.WithLogin;
import by.veremei.pages.ProfilePage;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static by.veremei.data.WebEndpoints.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class BasketTest extends BaseTest {
    BooksData booksData = new BooksData();
    AuthorizationAPI authAPI = new AuthorizationAPI();
    ProfilePage profilePage = new ProfilePage();
    private final static String TEXT_NO_ROWS_IN_COLLECTION = "No rows found";

    @Test
    @WithLogin
    @DisplayName("Удаление книги из коллекции пользователя")
    void deleteBookFromUserCollectionTest() {
        SuccessUserLogin successUserLogin = step("Получаем значения тело ответа авторизованного пользователя", () -> authAPI.getSuccessUserLogin());
        String token = step("Записываем token пользователя в переменную", successUserLogin::token);
        String userId = step("Записываем userId пользователя в переменную", successUserLogin::userId);
        step("Выполняем запрос на удаление книг из коллекции пользователя", () -> BookstoreAPI.deleteBooks(token, userId));

        Book book = new Book(booksData.isbn);
        BooksCollection bookData = new BooksCollection(userId, Collections.singletonList(book));

        step("Выполняем запрос на добавление книги в коллекцию пользователя", () -> BookstoreAPI.addBookToCollection(token, bookData));

        step("Открываем страницу профиль пользователя", () ->
                open(USER_PROFILE_URL)
        );
        step("Удаляем книгу из коллекции пользователя", () -> {
            profilePage.deleteBookFromUserCollection();
            Selenide.dismiss();
        });
        step("Проверяем что коллекция пуста", () ->
                profilePage.checkSuccessfulBookDelete(TEXT_NO_ROWS_IN_COLLECTION)
        );
    }
}

