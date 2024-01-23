package by.veremei.helpers;

import by.veremei.api.authorization.AuthorizationAPI;
import by.veremei.api.models.login.SuccessUserLogin;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class LoginExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) {
        AuthorizationAPI authAPI = new AuthorizationAPI();
        SuccessUserLogin successUserLogin = authAPI.setAuthorizationCookies();
        context.getStore(ExtensionContext.Namespace.create(getClass())).put("successUserLogin", successUserLogin);
    }
}
