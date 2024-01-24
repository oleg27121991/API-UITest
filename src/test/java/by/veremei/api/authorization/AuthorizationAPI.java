package by.veremei.api.authorization;

import by.veremei.api.data.ApiEndpoint;
import by.veremei.api.data.AuthData;
import by.veremei.api.models.login.Login;
import by.veremei.api.models.login.SuccessUserLogin;
import by.veremei.api.spec.Specifications;
import by.veremei.tests.BaseTest;
import com.codeborne.selenide.WebDriverRunner;
import io.restassured.response.Response;
import org.openqa.selenium.Cookie;

import static by.veremei.api.data.ApiEndpoint.*;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class AuthorizationAPI extends BaseTest {
    AuthData logData = new AuthData();
    private static final String USER_ID_COOKIE_NAME = "userID";
    private static final String EXPIRES_COOKIE_NAME = "expires";
    private static final String TOKEN_COOKIE_NAME = "token";
    private static final String TOKEN_RESPONSE = "token";
    private static final String USER_ID_RESPONSE = "userId";
    private static final String EXPIRES_RESPONSE = "expires";
    public SuccessUserLogin setAuthorizationCookies() {
        Login user = new Login(logData.userName, logData.userPass);
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpecOK200());
        Response authResponse = given()
                .contentType(JSON)
                .body(user)
                .when()
                .post(POST_USER_LOGIN_URL)
                .then()
                .extract().response();

        open(BASE_URL + IMG_FOR_SET_COOKIES);

        WebDriverRunner.getWebDriver().manage().addCookie(new Cookie(USER_ID_COOKIE_NAME, authResponse.path(USER_ID_RESPONSE)));
        WebDriverRunner.getWebDriver().manage().addCookie(new Cookie(EXPIRES_COOKIE_NAME, authResponse.path(EXPIRES_RESPONSE)));
        WebDriverRunner.getWebDriver().manage().addCookie(new Cookie(TOKEN_COOKIE_NAME, authResponse.path(TOKEN_RESPONSE)));

        return authResponse.as(SuccessUserLogin.class);
    }

    public SuccessUserLogin getSuccessUserLogin() {
        return setAuthorizationCookies();
    }



    public static SuccessUserLogin loginUser(Login user) {
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL), Specifications.responseSpecOK200());
        return given()
                .body(user)
                .when()
                .post(ApiEndpoint.POST_USER_LOGIN_URL)
                .then()
                .log().all()
                .extract().as(SuccessUserLogin.class);
    }
}
