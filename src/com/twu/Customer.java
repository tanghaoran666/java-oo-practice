package com.twu;

public class Customer extends User{
    int vote;
    public Customer(String name) {
        this.setName(name);
        this.vote = 10;
    }
}
