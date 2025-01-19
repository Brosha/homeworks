package service;

import model.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileSaveService {

    private static String RES = "resources/users/";

    static {
        try {
            Path resourcesPath = Paths.get(RES);
            Files.createDirectories(resourcesPath);
        } catch (IOException e) {
            System.err.println("Не удалось создать директорию: " + RES);
            e.printStackTrace();
        }
    }

    public static User readUser(String login) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(RES + login + ".txt");
        ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
        User user = (User) inputStream.readObject();
        return user;
    }

    public static void saveUser(User user) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(RES + user.getLogin() + ".txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(user);
    }

    public static Set<String> getAllLogins() throws IOException {
        Set<String> logins = new HashSet<>();
        File usersDir = new File(RES);
        File[] files = usersDir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    // Извлекаем логин из имени файла (без расширения)
                    if (fileName.endsWith(".txt")) {
                        String login = fileName.substring(0, fileName.lastIndexOf('.'));
                        logins.add(login);
                    }
                }
            }
        }

        return logins;
    }

    public static void setRES(String RES) {
        FileSaveService.RES = RES;
    }
}
