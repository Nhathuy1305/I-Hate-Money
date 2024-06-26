package com.main.ihatemoney.views.budget;

import com.main.ihatemoney.data.entity.Budget;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.progressbar.ProgressBarVariant;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;

@CssImport(value = "./themes/ihatemoney/views/budget-card.css")
public class BudgetListViewCard extends ListItem {
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("E, MMM d, yyyy");
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    public Budget budget;

    public BudgetListViewCard(Budget budget, Double progress, String status, BigDecimal expenses) {
        this.budget = budget;
        addClassNames(LumoUtility.Background.CONTRAST_5, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.AlignItems.START, LumoUtility.Padding.MEDIUM, LumoUtility.BorderRadius.LARGE);

        Div card = new Div();
        card.addClassNames(LumoUtility.Background.CONTRAST, LumoUtility.Display.FLEX, LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.CENTER, LumoUtility.Margin.Bottom.MEDIUM, LumoUtility.Overflow.HIDDEN,
                LumoUtility.BorderRadius.MEDIUM, LumoUtility.Width.FULL);

        // Title
        Span budgetTitle = new Span();
        budgetTitle.addClassNames(LumoUtility.FontSize.XLARGE, LumoUtility.FontWeight.SEMIBOLD);
        budgetTitle.setText(budget.getName());

        // Start and End Dates
        Span startDate = new Span();
        startDate.getStyle().set("padding-bottom", "0");
        startDate.addClassNames(LumoUtility.FontSize.SMALL, LumoUtility.TextColor.SECONDARY);
        String startStr = dateFormatter.format(budget.getStartDate());
        String endStr = dateFormatter.format(budget.getEndDate());
        startDate.setText("Start: " + startStr);
        Span endDate = new Span();
        endDate.getStyle().set("padding-top", "0");
        endDate.addClassNames(LumoUtility.FontSize.SMALL, LumoUtility.TextColor.SECONDARY);
        endDate.setText("End: " + endStr);

        // Description
        Paragraph budgetDescription = new Paragraph(budget.getDescription());
        budgetDescription.addClassName(LumoUtility.Margin.Vertical.MEDIUM);

        // Expenses Total
        Div totalExpenses = new Div();
        totalExpenses.setText("Expenses: ");
        Div expenseValue = new Div();
        String expensesStr = currencyFormat.format(expenses);
        expenseValue.setText(expensesStr);
        HorizontalLayout expenseDiv = new HorizontalLayout(totalExpenses, expenseValue);
        expenseDiv.addClassName("progress-bar-label");

        // Amount Remaining Total
        Div amountRemaining = new Div();
        amountRemaining.setText("Balance: ");
        Div remainingValue = new Div();
        String remainingStr = currencyFormat.format(budget.getSpendingGoal().subtract(expenses));
        remainingValue.setText(remainingStr);
        HorizontalLayout remainingDiv = new HorizontalLayout(amountRemaining, remainingValue);
        remainingDiv.addClassName("progress-bar-label");

        ProgressBar progressBar = new ProgressBar();
        progressBar.addClassName("progress-bar");
        String progressStr = (int) (progress * 100) + "%";

        Div budgetUtilization = new Div();
        budgetUtilization.setText("Budget Utilization: ");
        Div progressValue = new Div();
        progressValue.setText(progressStr);
        HorizontalLayout budgetUtilDiv = new HorizontalLayout(budgetUtilization, progressValue);
        budgetUtilDiv.addClassName("progress-bar-label");

        if (progress <= 0.84) {
            progressBar.getStyle().set("--progress-color", "#158443"); // green
            progressBar.addThemeVariants(ProgressBarVariant.LUMO_SUCCESS);
            progressValue.addClassName("green");
            remainingValue.addClassName("green");
        } else if (progress > 0.85 && progress <= 0.94) {
            progressBar.getStyle().set("--progress-color", "#ffbd07"); // yellow
            progressValue.addClassName("yellow");
            remainingValue.addClassName("yellow");
        } else {
            progressBar.addThemeVariants(ProgressBarVariant.LUMO_ERROR);
            progressBar.getStyle().set("--progress-color", "#E21D12"); // red
            progressValue.addClassName("red");
            remainingValue.addClassName("red");
        }

        String[] goalStatusList = {"Within Budget", "At Budget Capacity", "Over Budget"};
        String goalStatus = "";
        double progressBarValue = 0.0;

        // Progress validation
        if (progress > 1.0) {
            progressBarValue = 1.0;
            goalStatus = goalStatusList[2];
        } else if (progress == 1.0) {
            progressBarValue = 1.0;
            goalStatus = goalStatusList[1];
        } else if (progress < 0.0) {
            progressBarValue = 0.0;
            goalStatus = goalStatusList[0];
        } else {
            progressBarValue = progress;
            goalStatus = goalStatusList[0];
        }
        progressBar.setValue(progressBarValue);

        // Badges
        Span statusBadge = new Span();
        statusBadge.getElement().setAttribute("theme", "badge");
        statusBadge.setText(status);

        Span goalStatusBadge = new Span();
        goalStatusBadge.getElement().setAttribute("theme", "badge");
        goalStatusBadge.setText(goalStatus);

        HorizontalLayout badges = new HorizontalLayout(statusBadge, goalStatusBadge);

        // Budget Title, Start and End Dates, Description
        VerticalLayout budgetInfo = new VerticalLayout(budgetTitle, startDate, endDate, budgetDescription);
        budgetInfo.getStyle().set("gap", "var(--lumo-space-xs");

        // Expense, Budget Utilization, Progress Bar Layout, Badges
        VerticalLayout progressLayout = new VerticalLayout(
                expenseDiv, remainingDiv, budgetUtilDiv, progressBar, badges);
        card.add(progressLayout);

        // Card
        add(
                budgetInfo,
                new Hr(), // horizontal rule (line)
                progressLayout
        );

    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }
}
