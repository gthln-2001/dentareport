package de.dentareport;

public class Dentareport {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new Dentareport().getGreeting());
    }
}
