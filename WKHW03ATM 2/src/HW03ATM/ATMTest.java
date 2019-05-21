/**
 * ATMTest.java
 * Name: Mireya Leon
 * Date: 3/8/19
 * Abstract: Tests with some JUNIT
 */

package HW03ATM;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ATMTest {

    ATM machine1 = new ATM("OtterUnion");
    ATM machine2 = new ATM(200, "BOA", "Library");
    ATM machine3 = new ATM("OtterUnion");
    Customer alice, tom; // create HW03ATM.Customer object named alice


    @Test
    void test1(){
        // check bank equality
        assertFalse( machine1.equals(machine2) );
        assertTrue(machine1.equals(machine1));
        assertTrue(machine1.equals(machine3));

        alice = machine1.getCustomer("Alice");
        assertNotNull(alice);
        tom = machine3.getCustomer("Tom");
        assertNotNull(tom);
        // check customer equals
        assertTrue(alice.equals(alice));
        assertFalse(alice.equals(tom));
    }

    @Test
    void test2(){
        machine1.setATM(100, "BIT");
        machine1.addFund(10.95);
        assertTrue(machine1.getLocation().equals("BIT") && machine1.getSerialNo() == 100 );
        assertTrue(machine1.getBankBalance() == 110.95);
        assertNotEquals(machine1.getBankBalance(), machine2.getBankBalance());
        assertNotEquals(machine1.getBankBalance(), machine3.getBankBalance());
    }

    @Test
    void test3(){
        machine1.addFund(400);
        assertTrue(machine1.getBankBalance() == 500);
    }

    @Test
    void test4() {

        alice = machine1.getCustomer("Alice");

        machine1.withdrawal("Alice", 7777, 10.50); // In the method, we assume
        // that the customer "Alice" wants $10.50 withdrawal with PIN 7777.

        assertTrue(alice.getBalance() == 5000);

        machine1.withdrawal("Robert", 2323, 100.01);
        machine1.withdrawal("Robert", 2323, 99.99);
        assertTrue(machine1.getBankBalance() == 100.0);
        assertEquals(machine1.getBankBalance(), machine2.getBankBalance());

        machine1.withdrawal("Alice", 1234, 10000);
        assertTrue(alice.getBalance() == 5000);
        machine1.withdrawal("Alice", 1234, 10);
        assertTrue(alice.getBalance() == 4990);
        assertTrue(machine1.getBankBalance() == 90);
        assertNotEquals(machine1.getBankBalance(), machine2.getBankBalance());
        // test to accout for double inaccuracy
        machine1.withdrawal("Alice", 1234, 89.99);
        assertEquals(alice.getBalance(), 4900.01);
        assertEquals(machine1.getBankBalance(), 0.01);
    }

    @Test
    void test5(){

        if (machine1.isCustomer("Alice")) { // check customer is not null
            alice = machine1.getCustomer("Alice"); // return customer object
            System.out.println(alice); // print customer object
            System.out.println("");
        }else{
            System.out.print("Not a customer of this bank");
        }
        assertTrue(alice != null);
    }

    @Test
    void test6(){



        alice = machine1.getCustomer("Alice"); // return customer object

        assertTrue(alice.getBalance() == 4900.01);
        machine1.deposit("Alice", 1234, 10); // In the method, we assume that
        Customer tom = machine1.getCustomer("Tom");
        assertTrue(alice.getBalance() == 4910.01);
        machine1.deposit("Alice", 7777, 20);
        //System.out.println(machine1);
        assertTrue(alice.getBalance() == 4910.01 && machine1.getBankBalance() == 110);
        machine1.deposit("Robert", 2323, 20);
        assertFalse(machine1.getBankBalance() == 130
                && tom.getBalance() == 220);
    }

    @Test
    void test7(){
        Customer tom = machine1.getCustomer("Tom");
        assertTrue(machine1.transfer("Alice", 1234, 10, "Tom", 2000));
        assertFalse(machine1.transfer("Chris", 8787, 10, "Tom", 2000));
        assertFalse(machine1.transfer("Alice", 1234, 4990.01, "Tom", 2000));


        assertFalse(machine1.transfer("Alice", 1234, 4990, "Alice", 1234));
        Customer alice = machine1.getCustomer("Alice");

        machine1.addFund(110);
        //System.out.println(machine1);

        assertEquals(tom.getBalance(), 210);
        //System.out.println(tom);
        machine1.withdrawal("Tom", 2000, 209.99);
        assertEquals(machine1.getBankBalance(), 0.01);
        System.out.println(tom);
        assertEquals(tom.getBalance(), 0.01);
        machine1.addFund(99);
        System.out.println(machine1);
        assertEquals(machine1.getBankBalance(), 99,01);
        // round to . 99 kind of valid input?
        machine1.deposit("Tom", 2000, 399.99);
        //System.out.println(tom);
        assertEquals(tom.getBalance(), 400);
        System.out.println(alice);
        assertEquals(alice.getBalance(),4900.01 );
        machine1.transfer("Tom", 2000, 399.99, "Alice", 1234);
        ///System.out.println(tom);
        //System.out.println(alice);

        //System.out.println(tom);
        assertEquals(tom.getBalance(), 0.01);
        machine1.withdrawal("Alice", 1234, 209.994);
        assertEquals(machine1.getBankBalance(), 289.01);
        System.out.println(machine1);
        machine1.deposit("Tom", 2000, 1000000000000.99);
        System.out.println(machine1);
        assertEquals(machine1.getBankBalance(), 1000000000290.00);
        assertEquals(tom.getBalance(), 1000000000001.00);
        // System.out.println(tom);

    }

    @Test
    void test8() {
        assertTrue(machine1.isCustomer("Monica"));
        assertFalse(machine2.isCustomer("Monica"));
        assertTrue(machine3.isCustomer("Monica"));

        assertFalse(machine1.isCustomer("Robert"));
        assertTrue(machine2.isCustomer("Robert"));
        assertFalse(machine3.isCustomer("Robert"));

        assertTrue(machine1.isCustomer("Alice"));
        assertFalse(machine2.isCustomer("Alice"));
        assertTrue(machine3.isCustomer("Alice"));
    }

}