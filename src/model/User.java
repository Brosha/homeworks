package model;

import java.io.Serializable;

public class User implements Serializable {

    private String login;
    private String password;
    private Wallet wallet;

    public User() {

    }

    public User(String login, String password, Wallet wallet) {
        this.login = login;
        this.password = password;
        this.wallet = wallet;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
