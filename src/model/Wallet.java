package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Wallet implements Serializable {
    private int incomeTotal;
    private int expensesTotal;
    private User user;
    private HashMap<String, ArrayList<Integer>> income;
    private HashMap<String, ArrayList<Integer>> expenses;
    private HashMap<String, ArrayList<Integer>> budget;
    //private Set<String> categories;

    public Wallet() {
        income = new HashMap<>();
        expenses = new HashMap<>();
        budget = new HashMap<>();
    }

    public void setIncomeTotal(int incomeTotal) {
        this.incomeTotal = incomeTotal;
    }

    public void setExpensesTotal(int expensesTotal) {
        this.expensesTotal = expensesTotal;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIncome(HashMap<String, ArrayList<Integer>> income) {
        this.income = income;
    }

    public void setExpenses(HashMap<String, ArrayList<Integer>> expenses) {
        this.expenses = expenses;
    }

    public void setBudget(HashMap<String, ArrayList<Integer>> budget) {
        this.budget = budget;
    }

    public int getIncomeTotal() {
        return incomeTotal;
    }

    public int getExpensesTotal() {
        return expensesTotal;
    }

    public User getUser() {
        return user;
    }

    public HashMap<String, ArrayList<Integer>> getIncome() {
        return income;
    }

    public HashMap<String, ArrayList<Integer>> getExpenses() {
        return expenses;
    }

    public HashMap<String, ArrayList<Integer>> getBudget() {
        return budget;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "incomeTotal=" + incomeTotal +
                ", expensesTotal=" + expensesTotal +
                ", user=" + user +
                ", income=" + income.toString() +
                ", expenses=" + expenses.toString() +
                ", budget=" + budget.toString() +
                '}';
    }
}
