import model.User;
import model.Wallet;
import service.FileSaveService;
import service.UserService;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Test {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        User user = new User();
        user.setLogin("test1");
        user.setPassword("testpass");
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        user.setWallet(wallet);

        HashMap<String, ArrayList<Integer>> income = new HashMap<>();
        HashMap<String, ArrayList<Integer>> expenses = new HashMap<>();
        HashMap<String, ArrayList<Integer>> budget = new HashMap<>();

        income.put("income1", new ArrayList<>());
        income.put("income2", new ArrayList<>());
        income.get("income1").add(1000);
        income.get("income1").add(2000);
        income.get("income2").add(900);

        expenses.put("expenses1", new ArrayList<>());
        expenses.put("expenses2", new ArrayList<>());
        expenses.get("expenses1").add(14000);
        expenses.get("expenses1").add(300);
        expenses.get("expenses2").add(60);

        budget.put("budget1", new ArrayList<>());
        budget.put("budget2", new ArrayList<>());
        budget.get("budget1").add(114000);
        budget.get("budget1").add(3090);
        budget.get("budget2").add(680);

        wallet.setBudget(budget);
        wallet.setExpenses(expenses);
        wallet.setIncome(income);

        wallet.setUser(user);
        user.setWallet(wallet);
        /**
         ArrayList<User> test = new ArrayList<>();
         test.add(user);
         test.add(user);
         test.add(user);

         test.set(0, null);
         test.set(1, null);
         test.set(2, null);
         System.out.println("TEST SIZE: "+test.size());
         System.out.println("TEST Empty: "+test.isEmpty());

         for(int i=0; i<test.size(); i++){
         if(test.get(0)!= null && test.get(0).getWallet().getBudget().containsKey("nnm")) {
         }
         }



         FileOutputStream fileOutputStream = new FileOutputStream("testData/users"+user.getLogin()+".txt");
         ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
         objectOutputStream.writeObject(user);

         FileInputStream fileInputStream = new FileInputStream("testData/users"+user.getLogin()+".txt");
         ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
         User user1 = (User) inputStream.readObject();
         System.out.println(user1.getLogin()+" " + user1.getPassword()+" " + user.getWallet());
         **/

        FileSaveService.setRES("testData/users/");
        FileSaveService.saveUser(user);
        User loaded = FileSaveService.readUser(user.getLogin());

        System.out.println(loaded.getLogin() + " " + loaded.getPassword() + " " + loaded.getWallet());

        UserService userService = new UserService();
        System.out.println(userService.getUsers());
        userService.printUser(user);

        User registered1 = userService.createUser("newreg", "newreg");
        userService.addIncome("ZP", 100000);
        userService.addIncome("bonus", 6000);
        userService.addIncome("ZP", 100000);
        userService.addExpenses("Food", 300);
        userService.addExpenses("Food", 360);
        userService.addExpenses("Home", 1000);
        userService.addBudget("Food", 5000);
        userService.printUser(registered1);

        userService.logOut();
        User registered2 = userService.createUser("newREG2", "newreg");
        userService.addIncome("ZP", 10000);
        userService.addIncome("bonus", 60700);
        userService.addIncome("ZP", 80000);
        userService.addExpenses("Food", 3500);
        userService.addExpenses("Food", 360);
        userService.addExpenses("Home", 1000);
        userService.addBudget("Food", 4000);
        userService.addBudget("Study", 7000);

        userService.printUser(registered2);
        System.out.println("************************");
        userService.addExpenses("Study", 1000);

        userService.printUser(registered2);
        userService.logOut();
        registered1 = userService.loginUser(registered1.getLogin(), registered1.getPassword());
        userService.printUser(registered1);

        Set<String> logins = FileSaveService.getAllLogins();
        System.out.println(logins.toString());
        userService.sendMoney(registered2.getLogin(), 100);
        userService.printUser(registered1);
        userService.logOut();

        registered2 = userService.loginUser(registered2.getLogin(), registered2.getPassword());
        userService.printUser(registered2);
        userService.addExpenses("Food", 100000000);
        userService.printUser(registered2);
    }
}
