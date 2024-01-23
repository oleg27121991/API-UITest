package by.veremei.api.tests;

import by.veremei.api.authorization.AuthorizationAPI;
import by.veremei.api.data.AuthData;
import by.veremei.api.models.login.Login;
import by.veremei.api.models.login.SuccessUserLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("API tests")
public class AuthorizationTest {
    AuthData authData = new AuthData();
    @Test
    @Tag("API")
    @DisplayName("Успешная авторизация пользователя")
    void successUserLoginTest() {
        Login user = new Login(authData.userName, authData.userPass);

        SuccessUserLogin successUserLogin = step("Выполняем запрос на успешную аутентификацию пользователя", () ->
                AuthorizationAPI.loginUser(user));
        step("Проверяем, что токен не равен null", () ->
                assertNotNull(successUserLogin.token())
        );
    }
}
