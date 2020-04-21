package main;

import db.Repository;

public class Main {
    public static void main(String[] args) {
        Repository repo = new Repository();	
        System.out.println(repo.getAllObjects().get(0).getTemperature());
    }
}
