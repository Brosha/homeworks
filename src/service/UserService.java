package service;

import model.User;
import model.Wallet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class UserService {

    private User currentUser;
    private Wallet wallet;

    private static Set<String> users;

    static {
        try {
            users = FileSaveService.getAllLogins();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public User loginUser(String login, String password) {
        if (!users.contains(login)) {
            System.out.println("Login " + login + " not found!");
            return null;
        }
        try {
            User user = FileSaveService.readUser(login);
            if (password.equals(user.getPassword())) {
                currentUser = user;
                wallet = user.getWallet();
                System.out.println("Hello, " + currentUser.getLogin() + "!");
                printProfile();
                return user;
            } else {
                System.out.println("Password is incorrect!");
                return null;
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("User was not red");

        }
        return null;

    }

    public User createUser(String login, String password) {
        if (users.contains(login)) {
            System.out.println("Login is not Free!. Try again");
            return null;
        } else {
            wallet = new Wallet();
            User user = new User(login, password, wallet);
            try {
                FileSaveService.saveUser(user);
                currentUser = user;
                wallet = user.getWallet();
                users.add(currentUser.getLogin());
                System.out.println("You are registered!");
                printProfile();
                return user;
            } catch (IOException e) {
                System.out.println("Something went wrong...Try again later");
                ;
            }

        }
        return null;

    }

    public void logOut() {
        try {
            FileSaveService.saveUser(currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        currentUser = null;
        wallet = null;
    }

    public void addIncome(String income, Integer amount) {
        Wallet wallet = getCurrentUser().getWallet();
        if (!wallet.getIncome().containsKey(income)) {
            System.out.println("New income type was added: " + income);
            ArrayList<Integer> amounts = new ArrayList<>();
            wallet.getIncome().put(income, amounts);
        }

        wallet.getIncome().get(income).add(amount);
        wallet.setIncomeTotal(wallet.getIncomeTotal() + amount);
        System.out.println("Income: " + income + " Amount ");
        System.out.println("Total Income: " + wallet.getIncomeTotal());
        try {
            FileSaveService.saveUser(currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTranfserMoney(String income, Integer amount) {
        Wallet wallet = getCurrentUser().getWallet();
        if (!wallet.getIncome().containsKey(income)) {
            // System.out.println("New income type was added: "+ income);
            ArrayList<Integer> amounts = new ArrayList<>();
            wallet.getIncome().put(income, amounts);
        }

        wallet.getIncome().get(income).add(amount);
        wallet.setIncomeTotal(wallet.getIncomeTotal() + amount);
        // System.out.println("Income: "+ income + " Amount ");
        // System.out.println("Total Income: "+wallet.getIncomeTotal());
        try {
            FileSaveService.saveUser(currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addExpenses(String expense, Integer amount) {

        if (!wallet.getExpenses().containsKey(expense)) {
            System.out.println("New expenses type was added: " + expense);
            ArrayList<Integer> amounts = new ArrayList<>();
            wallet.getExpenses().put(expense, amounts);
        }

        wallet.getExpenses().get(expense).add(amount);
        wallet.setExpensesTotal(wallet.getExpensesTotal() + amount);
        System.out.println("Expense: " + expense + " Amount: " + amount);
        System.out.println("Total expenses: " + wallet.getExpensesTotal());
        if (wallet.getIncomeTotal() < wallet.getExpensesTotal()) {
            System.out.println("TOTAL INCOME < Expense!!!");
        }

        if (wallet.getBudget().containsKey(expense)) {
            List<Integer> exp = wallet.getBudget().get(expense);
            Integer balance = exp.get(1);
            exp.set(1, balance - amount);
            System.out.println("Budgtet of " + expense + ": " + exp.get(0) + ", balance: " + exp.get(1));
            if (exp.get(1) < 0) {
                System.out.println("Balance of Budget <0!");
            }
        }
        try {
            FileSaveService.saveUser(currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void addBudget(String expense, Integer budget) {
        ArrayList<Integer> exp = wallet.getBudget().get(expense);
        Integer balance = budget;
        if (exp == null) {
            exp = new ArrayList<>(2);
            exp.add(0);
            exp.add(0);
            List<Integer> expenses = wallet.getExpenses().get(expense);
            if (expenses != null) {
                for (Integer e : expenses) {
                    balance = balance - e;
                }
            }
            System.out.println("Budget " + expense + "was created with " + budget + " amount.");
            if (balance != budget) {
                System.out.println("Balance of budget was recalculated! Balance: " + balance);
            }
        }
        exp.set(0, budget);
        exp.set(1, balance);
        wallet.getBudget().put(expense, exp);

        try {
            FileSaveService.saveUser(currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendMoney(String login, Integer money) {
        if (!users.contains(login)) {
            System.out.printf("Reciver %s was not found!\n");
            return;
        }
        if (wallet.getIncomeTotal() < wallet.getExpensesTotal() + money) {
            System.out.println("You have not enough money!");
            return;
        }
        try {
            User reciver = FileSaveService.readUser(login);
            UserService reciverService = new UserService();
            reciverService.setCurrentUser(reciver);
            reciverService.setUsers(users);
            reciverService.setWallet(reciver.getWallet());
            reciverService.addIncome("Transfer money from " + currentUser.getLogin(), money);
            addExpenses("Transfer money for " + reciver.getLogin(), money);
            FileSaveService.saveUser(reciver);
            FileSaveService.saveUser(currentUser);
            System.out.println("Transfered for " + reciver.getLogin());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public void printProfile() {
        printUser(currentUser);
    }

    public void getIncomes() {

    }


    /**
     * public void updateBudget(String expense, Integer budget){
     * ArrayList<Integer> exp = wallet.getBudget().get(expense);
     * if (exp == null){
     * System.out.printf("Budget for %s was not found! \n", expense);
     * return;
     * }
     * exp.set(0, (exp.get(0)+budget))
     * <p>
     * }
     **/

    public User getCurrentUser() {
        return currentUser;
    }

    public Set<String> getUsers() {
        return users;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public static void printUser(User user) {
        System.out.println("User:");
        System.out.println("  Login: " + user.getLogin());
        System.out.println("  Wallet:");
        printWallet(user.getWallet());
    }

    public static void printWallet(Wallet wallet) {
        System.out.println("\tIncome Total: " + wallet.getIncomeTotal());
        System.out.println("\tExpenses Total: " + wallet.getExpensesTotal());
        if (wallet.getIncomeTotal() < wallet.getExpensesTotal()) {
            System.out.println("\t\t*****Income < Exepnes!!!!*****");
        }
        System.out.println("\tIncome:");
        printHashMap(wallet.getIncome());
        System.out.println("\tExpenses:");
        printHashMap(wallet.getExpenses());
        System.out.println("\tBudget:");
        printHashMapBudget(wallet.getBudget());
    }

    public static void printHashMap(HashMap<String, ArrayList<Integer>> map) {
        if (map != null) {
            for (var entry : map.entrySet()) {
                ArrayList<Integer> amounts = entry.getValue();
                int total = 0;
                for (Integer a : amounts) {
                    total += a;
                }
                System.out.println("      " + entry.getKey() + ": " + amounts + " (Total: " + total + ")");
            }
        } else {
            System.out.println("      null");
        }
    }

    public static void printHashMapBudget(HashMap<String, ArrayList<Integer>> map) {
        if (map != null) {
            for (var entry : map.entrySet()) {
                System.out.println("      " + entry.getKey() + ": " + entry.getValue());
            }
        } else {
            System.out.println("      null");
        }
    }


}
