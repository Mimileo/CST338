/**
 * Customer.java
 * Name: Mireya Leon
 * Date: 3/8/19
 * Abstract: Class to represent customer objects
 */


package HW03ATM;

import java.util.Objects;

public class Customer {
    private String name;
    private int pin;
    private double balance;
    private String bank;

    public Customer(String name, int pin, double balance, String bank){
        this.name = name;
        this.pin = pin;
        this.balance = balance;
        this.bank = bank;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    //TODO: create toString()

    @Override
    public String toString() {
        return name + ": " +
                "Balance $" + String.format("%.2f", balance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return pin == customer.pin &&
                Double.compare(customer.balance, balance) == 0 &&
                name.equals(customer.name) &&
                bank.equals(customer.bank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, pin, balance, bank);
    }
}
