package org.example;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

public class
Main {

    static String[] options = {
            "h -help",
            "c -cancel",
            "L -Login",
            "R -Register",
            "I-input URL",
            "P -Profile",
            "o -open",
            "ed - edit",
            "d -delete",
            "o==\"URL\"",
            "ed==\"URL\"",
            "d==\"URL\"",
            "clicks=Number",
            "E -Exit, Log Out",
            "Q -Quit",
    };
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static StringBuilder shortCut = new StringBuilder().append("https://");

    public static void main(String[] args) {
        Properties properties = new Properties();
        String path = Main.class.getClassLoader().getResource("conf.xml").getPath();
        try {
            properties.loadFromXML(new FileInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        shortCut.append(properties.getProperty("DOMAIN")).append("/");
        TokenGenerator tokenGenerator = new TokenGenerator(properties);
        Scanner scanner = new Scanner((System.in));

        for (String c : options) {
            System.out.println(c);
        }

        String userSessionID = null;
        boolean waitingLogin = false;
        boolean waitingURL = false;
        boolean waitingClicks = false;
        boolean waitingclicksInput = false;
        boolean waitingLifeTime = false;
        boolean waitingLifeTimeInput = false;
        boolean createURL = false;
        String checkedURL = null;
        boolean openingURL = false;
        boolean editingURL = false;
        boolean editingClicks = false;
        boolean waitingEditingClicksInput = false;
        boolean editingLifeTime = false;
        boolean waitingEditingLifeTimeInput = false;
        String editingURLToken = null;
        boolean createEditedURL = false;
        String delitedURL = null;
        boolean delitingURL = false;
        boolean waitingDelitingURLInput = false;
        int clicks = 0;
        long parsedTime = 0;
        int newClicks = 0;
        long newParsedTime = 0;
        while (true) {
            if (userSessionID == null && !waitingLogin) {
                System.out.println("Login Or Register");
            }

            if (!createURL && !createEditedURL) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("Q")) {
                    System.out.println("Exit.......");
                    break;
                } else if (input.equalsIgnoreCase("h")) {
                    for (String c : options) {
                        System.out.println(c);
                    }
                    continue;
                } else if (input.equalsIgnoreCase("o")) {
                    System.out.println("Input shortcut:");
                    openingURL = true;
                    continue;

                } else if (openingURL) {
                    if (input.equalsIgnoreCase("C")) {
                        System.out.println("Opening URL canceled");
                        openingURL = false;
                        System.out.println("Waiting Commands");
                    } else {
                        String[] inputs = input.split("==");
                        if (inputs[0].equalsIgnoreCase("o") && inputs.length == 2) {
                            URL url = checkURL(inputs[1]);
                            if (url != null) {
                                String host = url.getHost();
                                if (host.equals(properties.getProperty("DOMAIN"))) {
                                    String URL = tokenGenerator.getLink(url.getPath().substring(1));
                                    if (URL.equals("Not found")) {
                                        System.out.println("NOT found. Try Again");
                                        continue;
                                    }
                                    try {
                                        Desktop.getDesktop().browse(new URI(URL));
                                    } catch (IOException | URISyntaxException e) {
                                        System.out.println(URL);
                                        openingURL = false;
                                    }
                                    openingURL = false;
                                    continue;

                                } else
                                    System.out.println(url.getHost() + " is NOT OUR Domain: " + properties.getProperty("DOMAIN"));

                            } else System.out.println("Wrong Input. Try Again");
                        } else System.out.println("Wrong Input. Try Again");
                    }

                } else if (input.equalsIgnoreCase("R")) {
                    if (userSessionID == null) {
                        userSessionID = tokenGenerator.createUser();
                        System.out.println("You UUID: " + userSessionID);
                        waitingLogin = false;
                        System.out.println("Now You Can Input URL by I");
                    } else System.out.println("You are already registered");
                    continue;
                } else if (input.equalsIgnoreCase("L")) {
                    if (userSessionID == null) {
                        if (waitingLogin == false) {
                            System.out.println("Input login OR press C to cancel:");
                            waitingLogin = true;
                        }
                    } else System.out.println("You are already Logged");

                } else if (input.equalsIgnoreCase("E")) {
                    if (userSessionID != null) {
                        waitingLogin = false;
                        waitingURL = false;
                        waitingClicks = false;
                        waitingLifeTime = false;
                        System.out.flush();
                        System.out.println("Logg out success!");
                        System.out.println("Buy " + userSessionID);
                        userSessionID = null;
                    } else System.out.println("You are unauthorized");
                    continue;
                } else if (waitingLogin) {
                    if (input.equalsIgnoreCase("C")) {
                        System.out.println("Login Canceled");
                        waitingLogin = false;
                    } else if (tokenGenerator.login(input.trim())) {
                        userSessionID = input;
                        waitingLogin = false;
                        System.out.println("Login successful!");
                        System.out.println("Awaiting Commands");
                    } else System.out.println("User not found");
                    continue;
                } else if (input.equalsIgnoreCase("P") && userSessionID != null) {
                    tokenGenerator.getUserProfile(userSessionID);

                } else if (input.equalsIgnoreCase("I") && userSessionID != null && !waitingURL) {
                    waitingURL = true;
                    System.out.println("Input URL to shortcut");
                    continue;
                } else if (waitingURL) {
                    if (input.equalsIgnoreCase("C")) {
                        System.out.println("Input URL Canceled");
                        waitingURL = false;
                        System.out.println("Awaiting Commands");
                        continue;
                    }
                    if (checkURL(input) == null) {
                        System.out.println("URL is incorrect!");
                        System.out.println("Your input: " + input);
                        continue;
                    }
                    checkedURL = input;
                    waitingClicks = true;
                    waitingURL = false;
                    System.out.println("Do you want add amount of clicks? Y/N");
                    continue;
                } else if (waitingClicks) {
                    if (input.equalsIgnoreCase("Y")) {
                        waitingclicksInput = true;
                        System.out.println("Input Number of Clicks:");

                    } else if (input.equalsIgnoreCase("N")) {
                        waitingClicks = false;
                        waitingclicksInput = false;
                        System.out.println("Do you want add Life Time? Y/N");
                        waitingLifeTime = true;
                        continue;
                    } else if (waitingclicksInput) {
                        try {
                            clicks = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            System.out.println("Input is Wrong! Input Again");
                        }
                        waitingClicks = false;
                        waitingclicksInput = false;
                        System.out.println("Do you want add Life Time? Y/N");
                        waitingLifeTime = true;
                        continue;
                    }
                } else if (waitingLifeTime) {
                    if (input.equalsIgnoreCase("Y")) {
                        System.out.println("Input Life Time by the following formats:" +
                                "h=HOURS OR \n" +
                                "d=DAYS OR \n" +
                                "date=dd/MM/yyyyy");
                        waitingLifeTimeInput = true;
                    } else if (input.equalsIgnoreCase("N")) {
                        waitingLifeTime = false;
                        createURL = true;
                        waitingLifeTimeInput = false;

                    } else if (waitingLifeTimeInput) {
                        parsedTime = parseTime(input);
                        if (parsedTime == -1) {
                            System.out.println("Input is Wrong!");
                            continue;
                        }
                        waitingLifeTimeInput = false;
                        waitingLifeTime = false;
                        createURL = true;

                    }
                } else if (input.equalsIgnoreCase("ed")) {
                    System.out.println("Input shortcut:");
                    editingURL = true;
                    continue;
                } else if (editingURL) {
                    if (input.equalsIgnoreCase("C")) {
                        System.out.println("Editing URL canceled");
                        editingURL = false;
                        System.out.println("Waiting Commands");
                    } else {
                        String[] inputs = input.split("==");
                        if (inputs[0].equalsIgnoreCase("ed") && inputs.length == 2) {
                            URL url = checkURL(inputs[1]);
                            if (url != null) {
                                String host = url.getHost();
                                if (host.equals(properties.getProperty("DOMAIN"))) {
                                    String URL = tokenGenerator.findLink(userSessionID, url.getPath().substring(1));
                                    if (URL.equals("Not found")) {
                                        System.out.println("NOT found. Try Again");
                                        continue;
                                    } else if (URL.equals("Expired") || URL.equals("Number of opens exceeded")) {
                                        System.out.println(url);
                                        editingURL = false;
                                        continue;
                                    } else {
                                        editingURLToken = url.getPath().substring(1);
                                        editingClicks = true;
                                        editingURL = false;
                                        System.out.println("Do you want edit amount of clicks? Y/N");
                                    }

                                } else
                                    System.out.println(url.getHost() + " is NOT OUR Domain: " + properties.getProperty("DOMAIN"));

                            } else System.out.println("Wrong Input. Try Again");
                        } else System.out.println("Wrong Input. Try Again");
                    }

                } else if (editingClicks) {
                    if (input.equalsIgnoreCase("Y")) {
                        waitingEditingClicksInput = true;
                        System.out.println("Input Number of Clicks:");
                    } else if (input.equalsIgnoreCase("N")) {
                        editingClicks = false;
                        System.out.println("Do you want edit Life Time? Y/N");
                        editingLifeTime = true;
                        continue;
                    } else if (waitingEditingClicksInput) {
                        try {
                            newClicks = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            System.out.println("Input is Wrong! Input Again");
                        }
                        editingClicks = false;
                        waitingEditingClicksInput = false;
                        System.out.println("Do you want edit Life Time? Y/N");
                        editingLifeTime = true;
                        continue;
                    }
                } else if (editingLifeTime) {
                    if (input.equalsIgnoreCase("Y")) {
                        System.out.println("Input Life Time by the following formats:" +
                                "h=HOURS OR \n" +
                                "d=DAYS OR \n" +
                                "date=dd/MM/yyyyy");
                        waitingEditingLifeTimeInput = true;
                    } else if (input.equalsIgnoreCase("N")) {
                        editingLifeTime = false;
                        editingURL = false;
                        waitingEditingLifeTimeInput = false;
                        if (newClicks == 0) {
                            System.out.println("Editing Canceled");
                            editingURLToken = null;
                            continue;
                        } else createEditedURL = true;

                    } else if (waitingEditingLifeTimeInput) {
                        newParsedTime = parseTime(input);
                        if (newParsedTime == -1) {
                            System.out.println("Input is Wrong!");
                            continue;
                        }
                        waitingEditingLifeTimeInput = false;
                        editingLifeTime = false;
                        createEditedURL = true;
                    }
                } else if (input.equalsIgnoreCase("d")) {
                    System.out.println("Input shortcut:");
                    delitingURL = true;
                    continue;

                } else if (delitingURL) {
                    if (input.equalsIgnoreCase("C")) {
                        System.out.println("Deleting URL canceled");
                        delitingURL = false;
                        System.out.println("Waiting Commands");
                    } else {
                        String[] inputs = input.split("==");
                        if (inputs[0].equalsIgnoreCase("d") && inputs.length == 2) {
                            URL url = checkURL(inputs[1]);
                            if (url != null) {
                                String host = url.getHost();
                                if (host.equals(properties.getProperty("DOMAIN"))) {
                                    String URL = tokenGenerator.removeURL(userSessionID, url.getPath().substring(1));
                                    if (URL.equals("Not found")) {
                                        System.out.println("NOT found. Try Again");
                                        continue;
                                    } else if (URL.equals("Expired") || URL.equals("Number of opens exceeded")) {
                                        System.out.println(url);
                                        delitingURL = false;
                                        continue;
                                    } else {
                                        System.out.println("Deleted " + URL);
                                        delitingURL = false;
                                        continue;
                                    }

                                } else
                                    System.out.println(url.getHost() + " is NOT OUR Domain: " + properties.getProperty("DOMAIN"));

                            } else System.out.println("Wrong Input. Try Again");
                        } else System.out.println("Wrong Input. Try Again");
                    }

                }

            } else if (createURL) {
                if (clicks == 0 && parsedTime == 0) {
                    System.out.println(tokenGenerator.createLink(checkedURL, userSessionID));
                } else if (clicks == 0) {
                    System.out.println(tokenGenerator.createLink(checkedURL, parsedTime, userSessionID));
                } else if (parsedTime == 0) {
                    System.out.println(tokenGenerator.createLink(checkedURL, clicks, userSessionID));
                } else System.out.println(tokenGenerator.createLink(checkedURL, clicks, parsedTime, userSessionID));
                createURL = false;
                checkedURL = null;
                clicks = 0;
                parsedTime = 0;
                System.out.println("Awaiting Commands");

            } else if (createEditedURL) {
                String update;
                if (newClicks == 0) {
                    update = tokenGenerator.updateURLEntity(userSessionID, editingURLToken, newParsedTime);
                } else if (newParsedTime == 0) {
                    update = tokenGenerator.updateURLEntity(userSessionID, editingURLToken, newClicks);
                } else
                    update = tokenGenerator.updateURLEntity(userSessionID, editingURLToken, newParsedTime, newClicks);
                System.out.println(update);
                createEditedURL = false;
                newClicks = 0;
                newParsedTime = 0;
            }
        }

    }

    private static URL checkURL(String URL) {
        URL url;
        try {
            url = new URL(URL);
        } catch (MalformedURLException e) {
            return null;
        }
        return url;

    }

    private static long parseTime(String input) {
        String[] inputs = input.split("=");
        if (inputs.length != 2)
            return -1;
        switch (inputs[0]) {
            case "h": {
                int hours;
                try {
                    hours = Integer.parseInt(inputs[1]);
                } catch (NumberFormatException e) {
                    return -1;
                }
                return hours * 60 * 60 * 1000L;

            }
            case "d": {
                int days;
                try {
                    days = Integer.parseInt(inputs[1]);
                } catch (NumberFormatException e) {
                    return -1;
                }
                return days * 24 * 60 * 60 * 1000L;
            }
            case "date": {
                try {
                    Date date = dateFormat.parse(inputs[1]);
                    long current = Calendar.getInstance().getTimeInMillis();
                    return current > date.getTime() ? -1 : (date.getTime() - current);
                } catch (ParseException e) {
                    return -1;
                }
            }
        }


        return -1;
    }
}
