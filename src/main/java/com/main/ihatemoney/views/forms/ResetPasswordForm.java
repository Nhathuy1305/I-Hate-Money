package com.main.ihatemoney.views.forms;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.notification.Notification;

import com.main.ihatemoney.data.service.UserService;

public class ResetPasswordForm extends FormLayout {
    private PasswordField newPassword = new PasswordField("New Password");
    private PasswordField confirmPassword = new PasswordField("Confirm Password");
    private Button resetPasswordButton = new Button("Reset Password");
    private UserService userService;
    private String token;

    public ResetPasswordForm(UserService userService) {
        this.userService = userService;
        newPassword.setRequired(true);
        confirmPassword.setRequired(true);
        add(newPassword, confirmPassword, resetPasswordButton);
        setResponsiveSteps(new ResponsiveStep("0", 1));

        setWidth("300px");
        newPassword.setWidth("100%");
        confirmPassword.setWidth("100%");
        resetPasswordButton.setWidth("100%");

        configureButton();
    }

    private void configureButton() {
        resetPasswordButton.addClickListener(event -> {
            if (!newPassword.getValue().equals(confirmPassword.getValue())) {
                Notification.show("Passwords do not match!");
            } else {
                userService.updatePassword(token, newPassword.getValue());
                Notification.show("Password has been reset successfully.");
                UI.getCurrent().navigate("login");
            }
        });
    }

    public void setToken(String token) {
        this.token = token;
    }
}
