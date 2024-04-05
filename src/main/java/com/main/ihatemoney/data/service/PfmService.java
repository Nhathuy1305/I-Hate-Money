package com.main.ihatemoney.data.service;

import com.main.ihatemoney.data.entity.*;
import com.main.ihatemoney.data.repository.BudgetRepository;
import com.main.ihatemoney.data.repository.CategoryRepository;
import com.main.ihatemoney.data.repository.TransactionRepository;
import com.main.ihatemoney.data.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableTransactionManagement
public class PfmService {
    // Personal Finance Management Service

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Autowired
    public PfmService(TransactionRepository transactionRepository,
                      CategoryRepository categoryRepository, UserRepository userRepository,
                      BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public List<Transaction> findAllTransactions(Long userId) {
        return transactionRepository.findAllByUserId(userId);
    }

    public void deleteTransaction(Transaction transaction) {
        transactionRepository.delete(transaction);
    }

    public void saveTransaction(Transaction transaction) {
        if (transaction == null) {
            System.out.println("Transaction is null");
            return;
        }
        transactionRepository.save(transaction);
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public long countTransactionsByType(Long userId, Type type) {
        return transactionRepository.countByTransactionType(userId, type);
    }

    public List<Type> findAllTypes() {
        return Arrays.asList(Type.values());
    }

    public void addNewUser(User user) {
        userRepository.save(user);
    }

    public boolean userExists(String email) {
        return userRepository.findUserCountByEmail(email) > 0;
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User findUserById(Long userId) {
        return userRepository.findUserById(userId);
    }

    public BigDecimal sumAllTransactionsByType(Long userId, Type type) {
        return transactionRepository.sumAllTransactionsByType(userId, type);
    }

    public BigDecimal sumAllTransactionsByTypeInDateRange(Long userId, Type type, Date start, Date end) {
        return transactionRepository.sumAllTransactionsByTypeInDateRange(userId, type, start, end);
    }

    public List<Object[]> sumTransactionByCategory(Long userId, Type type) {
        return transactionRepository.sumTransactionsByCategory(userId, type);
    }

    public void addNewCategory(Category category) {
        categoryRepository.save(category);
    }

    public Category findCategoryByName(String categoryName) {
        return categoryRepository.findCategoryByName(categoryName);
    }

    @Transactional
    public void updateCustomCategoryUserIds(Long categoryId, String userId) {
        categoryRepository.updateCustomCategoryUserIds(categoryId, userId);
    }

    public List<Budget> getBudgetByUserId(Long userId) {
        return budgetRepository.getBudgetsByUserId(userId);
    }

    public void saveBudget(Budget budget) {
        if (budget == null) {
            System.out.println("Budget is null");
            return;
        }
        budgetRepository.save(budget);
    }

    public void deleteBudget(Budget budget) {
        budgetRepository.delete(budget);
    }

    public BigDecimal getSumExpensesInDateRange(Date start, Date end, Type type, Long userId) {
        return transactionRepository.getSumExpensesInDateRange(start, end, type, userId);
    }

    public List<Object[]> sumTransactionsInDateRangeByCategory(Long userId, Type type, Date start, Date end) {
        return transactionRepository.sumTransactionsInDateRangeByCategory(userId, type, start, end);
    }

    public List<Transaction> getFilteredTransactions(Long userId, String searchTerm, Date startDate, Date endDate,
                                                     String category, String type) {
        List<Transaction> transactions = transactionRepository.findAllByUserId(userId);

        // Perform filtering based on the provided criteria
        List<Transaction> filteredTransactions = transactions.stream()
                .filter(transaction -> {
                    // Filter by description (Case-insensitive)
                    if (searchTerm != null && !searchTerm.isEmpty()) {
                        String lowerSearchTerm = searchTerm.toLowerCase();
                        String lowerDescription = transaction.getDescription().toLowerCase();
                        if (!lowerDescription.contains(lowerSearchTerm)) {
                            return false;
                        }
                    }

                    // Filter by date range
                    if (startDate != null && endDate != null) {
                        Date transactionDate = transaction.getDate();
                        if (transactionDate.before(startDate) || transactionDate.after(endDate)) {
                            return false;
                        }
                    }

                    // Filter by category
                    if (category != null && !category.isEmpty()) {
                        String transactionCategory = transaction.getCategory().getName();
                        if (!transactionCategory.equals(category)) {
                            return false;
                        }
                    }

                    // Filter by type
                    if (type != null && !type.isEmpty()) {
                        String transactionType = transaction.getType().toString().toLowerCase();
                        if (!transactionType.equals(type.toLowerCase())) {
                            return false;
                        }
                    }

                    return true;
                })
                .collect(Collectors.toList());

        return filteredTransactions;
    }

    public BigDecimal getSumIncomeTransactionsAll(Long userId) {
        return transactionRepository.getSumTransactionsByType(userId, Type.INCOME);
    }

    public BigDecimal getSumExpenseTransactionsAll(Long userId) {
        return transactionRepository.getSumTransactionsByType(userId, Type.EXPENSE);
    }

    public BigDecimal getSumIncomeTransactionsUpToCurrentDate(Long userId) {
        return transactionRepository.getSumTransactionsByTypeUpToCurrentDate(userId, Type.INCOME);
    }

    public BigDecimal getSumExpenseTransactionsUpToCurrentDate(Long userId) {
        return transactionRepository.getSumTransactionsByTypeUpToCurrentDate(userId, Type.EXPENSE);
    }

    public Integer getTransactionCount(Long userId) {
        return transactionRepository.getTransactionCount(userId);
    }

    public List<Integer> findDistinctYears(Long userId) {
        return transactionRepository.findDistinctYears(userId);
    }

    public BigDecimal getSumTransactionsByMonthAndYearAndType(Long userId, Integer year, Integer month, Type type) {
        return transactionRepository.getSumTransactionsByMonthAndYearAndType(userId, year, month, type);
    }

    public List<Object[]> sumTransactionByCategoryAndYear(Long userId, Type type, Integer year) {
        return transactionRepository.sumTransactionsByCategoryAndYear(userId, type, year);
    }

    public BigDecimal getSumIncomeTransactionsByYear(Long userId, Integer year) {
        return transactionRepository.getSumTransactionsByTypeAndYear(userId, Type.INCOME, year);
    }

    public BigDecimal getSumExpenseTransactionsByYear(Long userId, Integer year) {
        return transactionRepository.getSumTransactionsByTypeAndYear(userId, Type.EXPENSE, year);
    }

    public Integer getTransactionCountByYear(Long userId, Integer year) {
        return transactionRepository.getTransactionCountByYear(userId, year);
    }

    public void updateExistingUserInfo(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void deleteAllTransactionsForUser(Long userId) {
        transactionRepository.deleteAllTransactionsByUserId(userId);
    }
}
