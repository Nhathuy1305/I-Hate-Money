package com.main.ihatemoney.views;

import com.main.ihatemoney.data.binder.RegistrationFormBinder;
import com.main.ihatemoney.data.service.PfmService;
import com.main.ihatemoney.views.forms.RegistrationForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.crypto.password.PasswordEncoder;

@Route("register")
@PageTitle("Register | IHateMoney")
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {

    private PfmService pfmService;
    private PasswordEncoder passwordEncoder;

    public RegistrationView(PfmService pfmService, PasswordEncoder passwordEncoder) {
        this.pfmService = pfmService;
        this.passwordEncoder = passwordEncoder;

        setSizeFull();
        RegistrationForm registrationForm = new RegistrationForm();
        setAlignItems(Alignment.CENTER);

        setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);

        add(registrationForm);

        RegistrationFormBinder registrationFormBinder = new RegistrationFormBinder(registrationForm, pfmService, passwordEncoder);
        registrationFormBinder.addBindingAndValidation();
    }
}
