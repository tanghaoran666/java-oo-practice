package com.twu;

import java.util.Scanner;

public class User {
    String name;
    long password;
    int vote;

    public User() {
    }

    public User(String name) {
        this.name = name;
        this.password = 123;
        this.vote = 10;
    }


}
