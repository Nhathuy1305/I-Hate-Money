package com.main.ihatemoney.views;

import com.main.ihatemoney.views.forms.ResetPasswordForm;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.main.ihatemoney.data.service.UserService;

import java.util.List;

@Route("reset-password")
@PageTitle("Reset Password | IHateMoney")
@CssImport(value = "./themes/ihatemoney/views/login-form.css", themeFor = "")
@AnonymousAllowed
public class ResetPasswordView extends VerticalLayout implements BeforeEnterObserver {
    private ResetPasswordForm resetPasswordForm;
    private UserService userService;

    public ResetPasswordView(UserService userService) {
        this.userService = userService;
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        resetPasswordForm = new ResetPasswordForm(userService);
        add(resetPasswordForm);

        setHorizontalComponentAlignment(Alignment.CENTER, resetPasswordForm);
        resetPasswordForm.setSizeUndefined();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String token = event.getLocation().getQueryParameters().getParameters().getOrDefault("token", List.of("")).get(0);
        if (!userService.validateResetToken(token)) {
            Notification.show("Invalid or expired token.");
            event.rerouteTo(LoginView.class);
        } else {
            resetPasswordForm.setToken(token);
        }
    }
}