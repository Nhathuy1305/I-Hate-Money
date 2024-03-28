package com.main.ihatemoney.views;

import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Login | IHateMoney")
@AnonymousAllowed
@CssImport(value = "./themes/prospero/views/login-form.css", themeFor = "")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    LoginI18n i18n = LoginI18n.createDefault();
    LoginForm loginForm = new LoginForm();

    public LoginView() {
        // Initialize setting for login form
        LoginI18n.Form i18nForm = i18n.getForm();
        i18n.setForm(i18nForm);

        // Setting error message when input wrong credentials
        LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
        i18nErrorMessage.setTitle("Invalid credentials");
        i18nErrorMessage.setMessage("Re-check and correct your username or password again. " +
                "Don't forget that username is case-sensitive");
        i18n.setErrorMessage(i18nErrorMessage);

        loginForm.setI18n(i18n);

        // Adjust the login session
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm.setAction("login");

        Image logo = new Image("icons/IHateMoney_logo.png", "Icon");
        logo.addClassName("logo-image-login");

        // Create layout for login form
        add (
                logo,
                new H1("IHateMoney"),
                new H3("Your Path to IHateMoney"),
                loginForm,
                new RouterLink("Create an account", RegistrationView.class)
        );

        // Forgot Password
        loginForm.addForgotPasswordListener(e -> {
            ConfirmDialog confirmDialog = new ConfirmDialog();
            confirmDialog.setHeader("Forgot Password");
            confirmDialog.setText("If you are experiencing issues " +
                    "logging into your account, please contact I.Hate.Money@gmail.com");
            confirmDialog.setConfirmText("OK");
            confirmDialog.open();
        });

        // Convert the username text to lower case
        loginForm.addLoginListener(e -> {
            System.out.println("Login listener fired");
            String username = e.getUsername().toLowerCase();
            i18nForm.setUsername(username);
        });
    }

    // This method auto check incorrect credentials (username/password), expired sessions, insufficient permissions,...
    // By checking if the URL query parameters contain an "error" parameter.
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // Notify user about their authentication error
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            loginForm.setError(true);
        }
    }

    public LoginForm getLoginForm() {
        return loginForm;
    }
}
