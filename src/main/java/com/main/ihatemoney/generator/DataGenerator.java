package com.main.ihatemoney.generator;

import com.main.ihatemoney.data.entity.*;
import com.main.ihatemoney.data.repository.BudgetRepository;
import com.main.ihatemoney.data.repository.CategoryRepository;
import com.main.ihatemoney.data.repository.TransactionRepository;
import com.main.ihatemoney.data.repository.UserRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@SpringComponent
public class DataGenerator {

    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    static Date generateRandomDate() {
        LocalDate minDay = LocalDate.now().minusYears(5);
        LocalDate maxDay = LocalDate.now().plusDays(60);

        long minEpochDay = minDay.toEpochDay();
        long maxEpochDay = maxDay.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minEpochDay, maxEpochDay + 1);

        return Date.valueOf(LocalDate.ofEpochDay(randomDay));
    }

    static String randomExpenseDescription() {
        String[] expenseDescriptions = {
                "Groceries", "Rent", "Utilities", "Transportation",
                "Dining Out", "Clothing", "Entertainment", "Gym Membership",
                "Insurance", "Phone Bill", "Internet Bill", "Car Maintenance",
                "Travel Expenses", "Medical Expenses", "Education Fees", "Home Repairs",
                "Pet Supplies", "Hobbies", "Charity Donations", "Subscriptions",
                "Coffee", "Fast Food", "Gifts", "Haircuts",
                "Laundry", "Office Supplies", "Parking Fees", "Public Transport",
                "Books", "Movies", "Concert Tickets", "Sports Equipment",
                "Taxi Rides", "Car Rental", "Fitness Classes", "Takeout",
                "Grocery Delivery", "Online Shopping", "Netflix Subscription", "Music Streaming",
                "Restaurant Delivery", "Home Decor", "Magazine Subscription", "Home Cleaning"
        };

        Random random = new Random();
        int randomIndex = random.nextInt(expenseDescriptions.length);
        return expenseDescriptions[randomIndex];
    }

    static String randomIncomeDescription() {
        String[] incomeDescriptions = {"Salary", "Bonus", "Dividends", "Rental Income"};

        Random random = new Random();
        int randomIndex = random.nextInt(incomeDescriptions.length);
        return incomeDescriptions[randomIndex];
    }

    @Bean
    public CommandLineRunner loadData(TransactionRepository transactionRepository,
                                      CategoryRepository categoryRepository,
                                      UserRepository userRepository,
                                      BudgetRepository budgetRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());

            if (transactionRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }

            logger.info("Generating demo data:");
            logger.info("Generating categories...");
            List<Category> categories = new ArrayList<>();

            // Default income categories
            categories.add(new Category("Paycheck", Type.INCOME)); // 0
            categories.add(new Category("Bonus", Type.INCOME));
            categories.add(new Category("Savings", Type.INCOME));
            categories.add(new Category("Investment", Type.INCOME));
            categories.add(new Category("Commission", Type.INCOME));
            categories.add(new Category("Retirement", Type.INCOME));
            categories.add(new Category("Social Security", Type.INCOME));
            categories.add(new Category("Rental Property", Type.INCOME));
            categories.add(new Category("Gift", Type.INCOME));
            categories.add(new Category("Other", Type.INCOME)); // 9

            // Default expense categories
            categories.add(new Category("Rent", Type.EXPENSE)); // 10
            categories.add(new Category("Car", Type.EXPENSE));
            categories.add(new Category("Food", Type.EXPENSE));
            categories.add(new Category("Medical", Type.EXPENSE));
            categories.add(new Category("Gift", Type.EXPENSE));
            categories.add(new Category("Utilities", Type.EXPENSE));
            categories.add(new Category("Travel", Type.EXPENSE));
            categories.add(new Category("Transportation", Type.EXPENSE));
            categories.add(new Category("Entertainment", Type.EXPENSE));
            categories.add(new Category("Other", Type.EXPENSE)); // 19
            categories.forEach(category -> category.setDefault(true));
            categories.forEach(category -> category.setUserIdsCsv("default"));

            List<Type> types = Arrays.asList(Type.values());
            logger.info("Types: ");
            types.forEach(type -> logger.info(type.toString()));

            List<User> users = new ArrayList<>();

            User user1 = new User();
            user1.setFirstName("Daniel");
            user1.setLastName("Dang");
            user1.setEmail("dnhuy.ityu@gmail.com");
            user1.setPassword(passwordEncoder().encode("huydeptrai123"));
            user1.setAllowMarketingEmails(true);
            user1.setId(1451L);
            user1.setRole(Role.USER);

            User user2 = new User();
            user2.setFirstName("Hiep");
            user2.setLastName("Ho");
            user2.setEmail("hiepHo@gmail.com");
            user2.setPassword(passwordEncoder().encode("hiepHo123"));
            user2.setAllowMarketingEmails(true);
            user2.setId(1452L);
            user2.setRole(Role.USER);

            User user3 = new User();
            user3.setFirstName("Huy");
            user3.setLastName("Dang");
            user3.setEmail("huydang135@gmail.com");
            user3.setPassword(passwordEncoder().encode("huydang135"));
            user3.setAllowMarketingEmails(true);
            user3.setId(1453L);
            user3.setRole(Role.USER);

            users.add(user1);
            users.add(user2);
            users.add(user3);

            Random r = new Random();

            logger.info("Generating transactions...");
            List<Transaction> transactions = new ArrayList<>();
            for (int i = 0; i < 400; i++) {
                Transaction transaction = new Transaction();
                transaction.setDate(generateRandomDate());
                transaction.setAmount(BigDecimal.valueOf(r.nextDouble(1200)));
                transaction.setType(types.get(r.nextInt(types.size())));
                transaction.setUserId(users.get(0).getId());
                if (transaction.getType().equals(Type.INCOME)) {
                    transaction.setDescription(randomIncomeDescription());
                    transaction.setCategory(categories.get(r.nextInt(10)));
                } else {
                    transaction.setDescription(randomExpenseDescription());
                    transaction.setCategory(categories.get(r.nextInt(categories.size() - 10) + 10));
                }

                logger.info(transaction.toString());
                transactions.add(transaction);
            }
            logger.info("Transactions Created!");

            List<Budget> budgets = new ArrayList<>();
            budgets.add(new Budget(
                    "April Budget - oldest",
                    Date.valueOf(LocalDate.now().minusDays(1)),
                    Date.valueOf(LocalDate.now().plusDays(3)),
                    BigDecimal.valueOf(500L),
                    "First budget",
                    user1.getId(),
                    LocalDateTime.now()
            ));

            budgets.add(new Budget(
                    "April Budget - mid",
                    Date.valueOf(LocalDate.now().plusMonths(1)),
                    Date.valueOf(LocalDate.now().plusMonths(1).plusDays(3)),
                    BigDecimal.valueOf(700L),
                    "Second budget",
                    user1.getId(),
                    LocalDateTime.now()
            ));

            budgets.add(new Budget(
                    "May Budget - youngest",
                    Date.valueOf(LocalDate.now().plusMonths(2)),
                    Date.valueOf(LocalDate.now().plusMonths(2).plusDays(3)),
                    BigDecimal.valueOf(900L),
                    "Third budget",
                    user1.getId(),
                    LocalDateTime.now()
            ));

            logger.info("Budgets Created!");

            categoryRepository.saveAll(categories);
            logger.info("categoryRepository saved!");

            transactionRepository.saveAll(transactions);
            logger.info("transactionRepository saved!");

            userRepository.saveAll(users);
            logger.info("userRepository saved!");

            budgetRepository.saveAll(budgets);
            logger.info("budgetRepository saved!");

            logger.info("Generated demo data!");
        };
    }
}
