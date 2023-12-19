package com.internship.internshipweb.pojo;

public class User {
    private int userId;
    private String userName;
    private UserType userType;

    public User(int userId, String userName, UserType userType) {
        this.userId = userId;
        this.userName = userName;
        this.userType = userType;
    }

    public User() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userType=" + userType +
                '}';
    }
}
