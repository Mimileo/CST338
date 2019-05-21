/**
* ATM.java
 * Name: Mireya Leon
 * Date: 3/8/19
 * Abstract: Class for ATM object. Perform operations and contains customer data.
 */


package HW03ATM;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Objects;

public class ATM {
    private int serialNo;
    private String bank;
    private String location;
    private double bankBalance;
    private int withdrawSuccess;
    private int withdrawFail;
    private int depositSuccess;
    private int depositFail;
    private int transferSuccess;
    private int transferFail;
    private static HashMap<String, Customer> customers = new HashMap<>(){{
            put("Alice", new Customer("Alice", 1234, 5000.00, "OtterUnion"));
            put("Tom", new Customer("Tom", 2000, 200.00, "OtterUnion"));
            put("Monica", new Customer("Monica", 3000, 50.00, "OtterUnion"));
            put("Michael", new Customer("Michael", 7777, 0.00, "OtterUnion"));
            put("John", new Customer("John", 8000, 500.00, "OtterUnion"));
            put("Jane", new Customer("Jane", 2222, 500.00, "OtterUnion"));
            put("Robert", new Customer("Robert", 2323, 200.00, "BOA"));
            put("Owen", new Customer("Owen", 4455, 50.00, "BOA"));
            put("Chris", new Customer("Chris", 8787, 10.00, "BOA"));
            put("Rebecca", new Customer("Rebecca", 8080, 555.55, "BOA"));
    }};


    public ATM(String bank) {
        this.serialNo = 0;
        this.bank = bank;
        this.location = "UNKNOWN";
        this.bankBalance = 100.00;
        this.withdrawSuccess = 0;
        this.withdrawFail = 0;
        this.depositSuccess = 0;
        this.depositFail = 0;
        this.transferSuccess = 0;
        this.transferFail = 0;
    }

    public ATM(int serialNo, String bank, String location) {
        this.serialNo = serialNo;
        this.bank = bank;
        this.location = location;
        this.bankBalance = 100.00;
        this.withdrawSuccess = 0;
        this.withdrawFail = 0;
        this.depositSuccess = 0;
        this.depositFail = 0;
        this.transferSuccess = 0;
        this.transferFail = 0;
    }
    //TODO: implement setATM

    public int getTotalTransactions(){
       return withdrawSuccess + withdrawFail + depositSuccess + depositFail + transferSuccess + transferFail;
    }
    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(double bankBalance) {
        this.bankBalance = bankBalance;
    }

    public int getWithdrawSuccess() {
        return withdrawSuccess;
    }

    public void setWithdrawSuccess(int withdrawSuccess) {
        this.withdrawSuccess = withdrawSuccess;
    }

    public int getWithdrawFail() {
        return withdrawFail;
    }

    public void setWithdrawFail(int withdrawFail) {
        this.withdrawFail = withdrawFail;
    }

    public int getDepositSuccess() {
        return depositSuccess;
    }

    public void setDepositSuccess(int depositSuccess) {
        this.depositSuccess = depositSuccess;
    }

    public int getDepositFail() {
        return depositFail;
    }

    public void setDepositFail(int depositFail) {
        this.depositFail = depositFail;
    }

    public int getTransferSuccess() {
        return transferSuccess;
    }

    public void setTransferSuccess(int transferSuccess) {
        this.transferSuccess = transferSuccess;
    }

    public int getTransferFail() {
        return transferFail;
    }

    public void setTransferFail(int transferFail) {
        this.transferFail = transferFail;
    }

    public void setATM(int serialNo, String location){
        this.serialNo = serialNo;
        this.location = location;
    }


    //TODO: implement menu display
    public void displayMenu() {
        System.out.print("===== ATM Transaction Menu =====\n\n" +
                "1. Withdrawal\n" +
                "2. Deposit\n" +
                "3. Transfer\n");
    }


    public void status() {
        //StringBuilder sb = new StringBuilder();

        System.out.println("Serial Number: " + getSerialNo() +
                "\nBank Name: " + getBank() + "\n" +
                "Location: " + getLocation() + "\n" +
                "Balance: " + String.format("%.2f", this.getBankBalance()) + "\n" +
                getTotalTransactions() + " Transactions so far:\n" +
                "     Withdrawal: " + (withdrawSuccess + withdrawFail) + " (" + withdrawSuccess + " success, " + withdrawFail + " fail)\n" +
                "     Deposit: " + (depositSuccess + depositFail) + " (" + depositSuccess + " success, " + depositFail + " fail)\n" +
                "     Transfer: " + (transferSuccess + transferFail) + " (" + transferSuccess + " success, " + transferFail + " fail)\n");
    }

    // to return double to 2 decimal places
    public double subtractMoney(double bank, double subtract){
        BigDecimal value = new BigDecimal(String.format("%.2f", bank)).subtract(new BigDecimal(String.format("%.2f", subtract)));
        return value.doubleValue();
    }

    //return added values to 2 decimal places
    /*
    public double addMoney(double bank, double subtract){
        BigDecimal value = new BigDecimal(String.format("%.3f",bank)).add(new BigDecimal( String.format("%.3f",subtract)));
       // value = value
        return value.doubleValue();
    }
    */

    public boolean isCustomer(String name) {
        for (String c : customers.keySet()){
            if(c.equals(name) && customers.get(c).getName().equals(name) && checkBank(customers.get(c))){
                return true;
            }
        }
        return false;
    }

    public Customer getCustomer(String name) {
        if(isCustomer(name)) {
            for (String c : customers.keySet()){
                if(c.equals(name) && customers.get(c).getName().equals(name)){
                    return customers.get(c);
                }
            }
        }
        return null;

    }

    public boolean verifyCustomer(Customer c, String name, int pin) {
        //HW03ATM.Customer current = getCustomer(name);
        if(c != null && c.getName().equals(name) && c.getPin() == pin){
            return true;
        }
        return false;
    }

    // take customer object and check if their bank matches this atm's bank
    public boolean checkBank(Customer c ) {
        if(c != null && c.getBank().equals(this.getBank())) {
            return true;
        }
        return false;
    }

    public void addFund(double fund) {
        if( fund > 0) {
            //this.setBankBalance(this.getBankBalance() + fund));
            this.bankBalance += fund;
        } else{
            System.out.println("Invalid funds entered.");
        }
    }



    public void withdrawal(String name, int pin, double amount){
        Customer customer = getCustomer(name);
        if(customer != null && verifyCustomer(customer, name, pin) && amount > 0 && checkBank(customer)){
            if(amount <= customer.getBalance() && amount <= this.getBankBalance()) {
                customer.setBalance(subtractMoney(customer.getBalance(), amount));
                //this.setBankBalance(this.getBankBalance() - amount);
                //this.setBankBalance((monetize(this.getBankBalance()).subtract( monetize(amount))));
                this.setBankBalance(subtractMoney(this.getBankBalance(),amount));
                this.withdrawSuccess++;
                System.out.println("Succeed – withdrawal");
                return;
            }
            else {
                this.withdrawFail++;
                System.out.println("Fail – withdrawal");
                return;
            }
        }

            this.withdrawFail++;
            System.out.println("Fail – withdrawal");

    }


    public void deposit(String name, int pin, double amount) {
        Customer customer = getCustomer(name);
        if(customer != null && verifyCustomer(customer, name, pin) && amount > 0 && checkBank(customer)){
           // if(amount > 0) {
                customer.setBalance(customer.getBalance() + amount);
                //bankBalance += amount;
                this.addFund(amount);
                depositSuccess++;
                System.out.println("Succeed – deposit");

        } else {
            depositFail++;
            System.out.println("Fail – deposit");
        }
    }
   // take given name and pin to verify if  name and pin matches a customer in data


    public boolean transfer(String name1, int pin1, double amount, String name2, int pin2) {
        Customer customer1 = getCustomer(name1);
        Customer customer2 = getCustomer(name2);

        if(customer1 != null && verifyCustomer(customer1, name1, pin1) && customer2 != null && !customer1.equals(customer2) && verifyCustomer(customer2, name2, pin2)
        && checkBank(customer1) && checkBank(customer2)) {
            if(amount > 0  &&  amount <= customer1.getBalance()){

                customer1.setBalance(subtractMoney(customer1.getBalance(), amount));
                customer2.setBalance(customer2.getBalance() + amount);
                transferSuccess++;
                System.out.println("Succeed – transfer");
                return true;
            }
            else{
                transferFail++;
                System.out.println("Fail – transfer");
                return false;
            }

        }
        transferFail++;
        System.out.println("Fail – transfer");
        return false;
    }

    @Override
    public String toString() {
        return "Serial Number: " + serialNo +
                "\nbank: " + bank +
                "\nLocation: " + location +
                "\nBalance: " + String.format("%.2f", bankBalance);
    }



    public boolean equals(ATM other) {
       return this.serialNo == other.getSerialNo() && this.bank.equals(other.getBank())
               && this.location.equals(other.getLocation()) && this.bankBalance == other.getBankBalance()
               && this.withdrawSuccess == other.getWithdrawSuccess() && this.withdrawFail == other.getWithdrawFail()
               && this.depositSuccess == other.getDepositSuccess() && this.depositFail == other.getDepositFail()
               && this.transferSuccess == other.getTransferSuccess() && this.transferSuccess == other.getTransferSuccess();
    }

}
