package com.example.teammanagement.Utils;

import java.io.Serializable;
import java.util.Arrays;

public class User implements Serializable {
    private int idUser;
    private String userName;
    private String email;
    private String password;
    private int state;
    private byte[] profilePicture;
    private int role;

    public User(int idUser, String userName, String email, String password, int state, byte[] profilePicture, int role) {
        this.idUser = idUser;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.state = state;
        this.profilePicture = profilePicture;
        this.role = role;
    }

    public User() {
        this.idUser = -1;
        this.userName = "";
        this.email = "";
        this.password = "";
        this.state = -1;
        this.profilePicture = null;
        this.role = -1;
    }

    public User(int idUser, String userName, String email, String password, int state, int role) {
        this.idUser = idUser;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.state = state;
        this.role = role;
    }

    public User(String userName, String email, String password, int state, byte[] profilePicture, int role) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.state = state;
        this.profilePicture = profilePicture;
        this.role = role;
    }

    public User(String userName, String email, String password, int state, int role) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.state = state;
        this.role = role;
    }

    public User(int idUser, String userName) {
        this.idUser = idUser;
        this.userName = userName;
    }

    public User(int idUser, String userName, byte[] profilePicture) {
        this.idUser = idUser;
        this.userName = userName;
        this.profilePicture = profilePicture;
    }

    public User(String userName, byte[] profilePicture) {
        this.userName = userName;
        this.profilePicture = profilePicture;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", state=" + state +
                ", profilePicture=" + Arrays.toString(profilePicture) +
                ", role=" + role +
                '}';
    }
}
