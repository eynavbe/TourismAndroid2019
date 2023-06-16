package com.example.myprojectandroid;

public class User {
    private String email;
    private String uId;
    private String lastSeen;

    public User() {
    }

    public User(String email, String uId,String lastSeen) {
        this.email = email;
        this.uId = uId;
        this.lastSeen = lastSeen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", uId='" + uId + '\'' +
                ", lastSeen='" + lastSeen + '\'' +
                '}';
    }
}
