package Strategy;
/**
 * Author: Mireya Leon
 * Title: Imp.java
 * Date: 2/27/19
 * Abstract: Imp is child class of monster, it calls the super's constructor
 * and inherits variables and methods
 */


import java.util.HashMap;

public class Imp extends Monster {
    public Imp(Integer maxHp, Integer xp, HashMap<String, Integer> items) {
        super(maxHp, xp, items);
    }

    @Override
    public String toString() {
        return "Imp has : " + super.toString();
    }
}
