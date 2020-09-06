package com.twu;

public class Administrator extends User{
    private String name = "admin";
    private String password = "admin123";

    @Override
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
