import model.User;
import service.UserService;

import java.util.Arrays;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static String[] options = {
            "h -help",
            "L:user:password -Login",
            "R:user:password -Register",
            //Вводим тип доходов и значение
            "income:category:amount",
            //Вводим тип расходов и значение
            "expense:category:amount",
            //Вводим тип бюджета и значение
            "budget:category:money",
            //Вводим логин получателя и значение
            "send:login:money",
            "P -Profile",
            "E -Exit, Log Out",
            "Q -Quit",
    };

    private static User loggedUser;
    private static UserService userService = new UserService();

    public static void main(String[] args) {
        printHelp();

        Scanner scanner = new Scanner((System.in));

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("q")) {
                System.out.println("Exit.....");
                break;
            }
            commandHandler(input);

        }

    }

    private static void commandHandler(String command) {
        String[] commands = command.split(":");
        switch (commands[0]) {
            case "L": {
                if (loggedUser != null) {
                    System.out.println("You are Authrozied!");
                    break;
                } else if (commands.length != 3) {
                    System.out.println("Wrong LogoPass input: " + command);
                    break;
                } else
                    loginUser(Arrays.copyOfRange(commands, 1, commands.length));
                break;
            }
            case "R": {
                if (loggedUser != null) {
                    System.out.println("You are Authrozied!");
                    return;
                }
                if (commands.length != 3) {
                    System.out.println("Wrong LogoPass input: " + command);
                    return;
                }
                registerUser(Arrays.copyOfRange(commands, 1, commands.length));
            }
            break;
            case "E": {
                if (loggedUser == null) {
                    System.out.println("You are UnAuthrozied!");
                    return;
                }
                logOut();
            }
            break;
            case "h": {
                printHelp();
            }
            break;
            case "income": {
                if (loggedUser == null) {
                    System.out.println("You are UnAuthrozied!");
                    return;
                }

                if (commands.length != 3) {
                    System.out.println("Wrong Income input: " + command);
                    return;
                }
                addIncome(Arrays.copyOfRange(commands, 1, commands.length));
            }
            break;
            case "expense": {
                if (loggedUser == null) {
                    System.out.println("You are UnAuthrozied!");
                    return;
                }
                if (commands.length != 3) {
                    System.out.println("Wrong Expense input: " + command);
                    return;
                }
                addExpenses(Arrays.copyOfRange(commands, 1, commands.length));
            }
            break;
            case "budget": {
                if (loggedUser == null) {
                    System.out.println("You are UnAuthrozied!");
                    return;
                }
                if (commands.length != 3) {
                    System.out.println("Wrong Budget input: " + command);
                    return;
                }
                addBudget(Arrays.copyOfRange(commands, 1, commands.length));
            }
            break;
            case "send": {
                if (loggedUser == null) {
                    System.out.println("You are UnAuthrozied!");
                    return;
                }
                if (commands.length != 3) {
                    System.out.println("Wrong Transfer input: " + command);
                    return;
                }
                sendTransfer(Arrays.copyOfRange(commands, 1, commands.length));
            }
            break;
            case "p": {
                if (loggedUser == null) {
                    System.out.println("You are UnAuthrozied!");
                    return;
                }
                userService.printProfile();
            }
            break;

        }

    }

    private static void printHelp() {
        for (String o : options) {
            System.out.println(o);
        }
    }

    private static User registerUser(String[] creds) {
        String login = creds[0];
        String password = creds[1];
        loggedUser = userService.createUser(login, password);

        return loggedUser;
    }

    private static User loginUser(String[] creds) {
        String login = creds[0];
        String password = creds[1];
        loggedUser = userService.loginUser(login, password);
        return loggedUser;
    }

    private static void logOut() {
        userService.logOut();
        System.out.println("BUY, " + loggedUser.getLogin() + "!");
        loggedUser = null;
    }

    private static void addIncome(String[] income) {
        String category = income[0];
        Integer amounnt;
        try {
            amounnt = Integer.valueOf(income[1]);
            if (amounnt < 0) {
                System.out.println("Invalid Amount format");
                return;
            } else {
                userService.addIncome(category, amounnt);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Amount format");
        }

    }

    private static void addExpenses(String[] income) {
        String category = income[0];
        Integer amounnt;
        try {
            amounnt = Integer.valueOf(income[1]);
            if (amounnt < 0) {
                System.out.println("Invalid Amount format");
                return;
            } else {
                userService.addExpenses(category, amounnt);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Amount format");
        }

    }

    private static void addBudget(String[] income) {
        String category = income[0];
        Integer amounnt;
        try {
            amounnt = Integer.valueOf(income[1]);
            if (amounnt < 0) {
                System.out.println("Invalid Amount format");
                return;
            } else {
                userService.addBudget(category, amounnt);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Amount format");
        }
    }

    private static void sendTransfer(String[] income) {
        String login = income[0];
        if (login.equals(loggedUser.getLogin())) {
            System.out.println("It is yours login!");
        }
        Integer amounnt;
        try {
            amounnt = Integer.valueOf(income[1]);
            if (amounnt < 0) {
                System.out.println("Invalid Amount format");
                return;
            } else {
                userService.sendMoney(login, amounnt);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Amount format");
        }
    }


    private void getIncomes() {

    }


}