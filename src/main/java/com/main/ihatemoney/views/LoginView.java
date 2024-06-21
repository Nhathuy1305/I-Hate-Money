package com.main.ihatemoney.views;

import com.main.ihatemoney.data.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Login | IHateMoney")
@AnonymousAllowed
@CssImport(value = "./themes/ihatemoney/views/login-form.css", themeFor = "")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    LoginI18n i18n = LoginI18n.createDefault();
    LoginForm loginForm = new LoginForm();
    private final UserService userService;

    public LoginView(UserService userService) {
        this.userService = userService;
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
                new H1("I Hate Money"),
                new H3("Your Path to Love your Money"),
                loginForm,
                new RouterLink("Create an account", RegistrationView.class)
        );

        //  Forgot Password
        loginForm.addForgotPasswordListener(e -> openForgotPasswordDialog());

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

    private void openForgotPasswordDialog() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setJustifyContentMode(JustifyContentMode.CENTER);
        layout.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        Dialog resetDialog = new Dialog();
        H3 title = new H3("Reset Your Password");
        layout.add(title);

        resetDialog.add(layout);

        EmailField emailField = new EmailField("Enter your email:");
        emailField.setRequired(true);
        emailField.setClearButtonVisible(true);
        emailField.setErrorMessage("Please enter a valid email address");

        Button sendLinkButton = new Button("Send Reset Link", event -> {
            if (!emailField.isEmpty() && emailField.getValue().contains("@")) {
                userService.sendResetLink(emailField.getValue());
                resetDialog.close();
                Notification.show("If your email is registered, you will receive a reset link.");
            } else {
                emailField.setInvalid(true);
            }
        });

        resetDialog.add(emailField, sendLinkButton);
        resetDialog.open();
    }

    public LoginForm getLoginForm() {
        return loginForm;
    }
}
