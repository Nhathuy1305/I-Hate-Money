package com.main.ihatemoney.data.binder;

import com.main.ihatemoney.data.entity.*;
import com.main.ihatemoney.data.service.PfmService;
import com.main.ihatemoney.views.LoginView;
import com.main.ihatemoney.views.forms.RegistrationForm;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RegistrationFormBinder {

    private RegistrationForm registrationForm;
    private PfmService pfmService;
    private PasswordEncoder passwordEncoder;

    private boolean enablePasswordValidation;

    // Constructor
    public RegistrationFormBinder(RegistrationForm registrationForm, PfmService pfmService, PasswordEncoder passwordEncoder) {
        this.registrationForm = registrationForm;
        this.pfmService = pfmService;
        this.passwordEncoder = passwordEncoder;
    }

    private ValidationResult emailValidator(String email, ValueContext valueContext) {
        if (pfmService.userExists(email)) {
            return ValidationResult.error("Email already exists");
        }
        return ValidationResult.ok();
    }

    private ValidationResult passwordValidator(String password, ValueContext valueContext) {

        // Check length password
        if (password == null || password.length() < 8) {
            return ValidationResult.error("Password must be at least 8 characters");
        }

        if (password == null || password.length() > 128) {
            return ValidationResult.error("Password must be at most 128 characters");
        }

        // Check complexity password (at least 1 uppercase letter, 1 lowercase letter, 1 digit)
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d).+$")) {
            return ValidationResult.error("Password should contain at least 1 uppercase letter, 1 lowercase letter, 1 digit");
        }

        if (!enablePasswordValidation) {
            enablePasswordValidation = true;
            return ValidationResult.ok();
        }

        String storedPassword = registrationForm.getPasswordConfirmation().getValue();

        if (password != null && password.equals(storedPassword)) {
            return ValidationResult.ok();
        }

        return ValidationResult.error("Password confirmation does not match");
    }

    // Called when form has been submitted successfully
    private void showSuccess(User userBean) {
        Notification notification = Notification.show("User " + userBean.getEmail() + " successfully created. Welcome "
                                                        + userBean.getFirstName());

        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        // Redirect the new user to the login page
        UI.getCurrent().navigate(LoginView.class);
    }

    private void generateSampleBudget(User newUser) {
        Budget sampleBudget = new Budget(
                        "Example Budget",
                        Date.valueOf(LocalDate.now().minusMonths(3)),
                        Date.valueOf(LocalDate.now().plusDays(3)),
                LocalDateTime.now(),
                BigDecimal.valueOf(350.00),
                "See how IHateMoney budgets work with this example. " +
                                "Customize or delete this budget to fit your own financial plans.",
                newUser.getId()
                );

        pfmService.saveBudget(sampleBudget);
    }

    private void generateSampleTransactions(User newUser) {
        Transaction income = new Transaction(
                Date.valueOf(LocalDate.now().minusMonths(2).minusDays(1)),
                BigDecimal.valueOf(1000.00),
                "Sample Income Transaction",
                pfmService.findCategoryByName("Bonus"),
                Type.INCOME,
                newUser.getId()
        );
        pfmService.saveTransaction(income);

        Transaction expense1 = new Transaction(
                Date.valueOf(LocalDate.now().minusMonths(2).minusDays(2)),
                BigDecimal.valueOf(95.00),
                "Sample Expense Transaction",
                pfmService.findCategoryByName("Food"),
                Type.EXPENSE,
                newUser.getId()
        );
        pfmService.saveTransaction(expense1);

        Transaction expense2 = new Transaction(
                Date.valueOf(LocalDate.now().minusMonths(1).minusDays(3)),
                BigDecimal.valueOf(75.00),
                "Sample Expense Transaction",
                pfmService.findCategoryByName("Utilities"),
                Type.EXPENSE,
                newUser.getId()
        );
        pfmService.saveTransaction(expense2);

        Transaction expense3 = new Transaction(
                Date.valueOf(LocalDate.now().minusDays(4)),
                BigDecimal.valueOf(30.00),
                "Sample Expense Transaction",
                pfmService.findCategoryByName("Entertainment"),
                Type.EXPENSE,
                newUser.getId()
        );
        pfmService.saveTransaction(expense3);
    }

    public void addBindingAndValidation() {
        BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);
        binder.bindInstanceFields(registrationForm);

        // Custom validation for password fields
        binder.forField(registrationForm.getPassword())
                .withValidator(this::passwordValidator).bind("password");

        // Custom validation for email fields
        binder.forField(registrationForm.getEmail())
                .withValidator(this::emailValidator).bind("email");

        registrationForm.getPasswordConfirmation().addValueChangeListener(e -> {
            // User has changed the second password field, validate and show errors
            enablePasswordValidation = true;
            binder.validate();
        });

        binder.setStatusLabel(registrationForm.getErrorMessage());

        registrationForm.getSubmit().addClickListener(e -> {
            try {
                User userBean = new User(); // new bean to store user info into
                binder.writeBean(userBean); // Run validation and write the values to the bean

                registrationForm.setEmail(userBean.getEmail().toLowerCase());
                userBean.setEmail(userBean.getEmail().toLowerCase());
                userBean.setPassword(passwordEncoder.encode(userBean.getPassword()));
                userBean.setRole(Role.USER);
                userBean.setAllowMarketingEmails(registrationForm.getAllowMarketing().getValue());

                System.out.println("Adding new user: " + userBean);

                pfmService.addNewUser(userBean);    // Add new user to the database

                generateSampleTransactions(userBean);
                generateSampleBudget(userBean);

                showSuccess(userBean);  // Display success message

            } catch (ValidationException exception) {
                System.out.println("Validation exception: " + exception.getMessage());
                exception.getBeanValidationErrors().forEach(System.out::println);
            }
        });
    }
}
