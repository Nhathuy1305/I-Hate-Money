package com.main.ihatemoney.data.binder;

import com.main.ihatemoney.data.entity.User;
import com.main.ihatemoney.data.service.PfmService;
import com.main.ihatemoney.views.forms.RegistrationForm;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RegistrationFormBinder {

    private RegistrationForm registrationForm;
    private PfmService pfmService;
    private PasswordEncoder passwordEncoder;

    private boolean enablePasswordValidation;

//    private ValidationResult emailValidator(String email, ValueContext valueContext) {
////        if (pfmService.)
//    }

    // Constructor
    public RegistrationFormBinder(RegistrationForm registrationForm, PfmService pfmService, PasswordEncoder passwordEncoder) {
        this.registrationForm = registrationForm;
        this.pfmService = pfmService;
        this.passwordEncoder = passwordEncoder;
    }

    public void addBindingAndValidation() {
        BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);
        binder.bindInstanceFields(registrationForm);

//        binder.forField(registrationForm.getPassword())
//                .withValidator(this::).buin
    }

//    private ValidationResult
}
