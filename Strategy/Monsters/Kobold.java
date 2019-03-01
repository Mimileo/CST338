package Monsters;
/**
 * Author: Mireya Leon
 * Title: Imp.java
 * Date: 2/27/19
 * Abstract: Kobold is child class of monster, it calls the super's constructor
 * and inherits variables and methods
 */


import Abilities.RangedAttack;

import java.util.HashMap;

public class Kobold extends Monster {
    public Kobold(Integer maxHp, Integer xp, HashMap<String, Integer> items) {
        super(maxHp, xp, items);

        // These should be stored in a HashMap
        // that way we can use an iterator
        Integer maxStr = 15;
        Integer maxDef = 6;
        Integer maxAgi = 3;

        attack = new RangedAttack(this);

        // this should be used in a data structure
        str = super.getAttribute(str, maxStr);
        def = super.getAttribute(def, maxDef);
        agi = super.getAttribute(agi, maxAgi);
    }

    @Override
    public String toString() {
        return "Monsters.Kobold has : " + super.toString();
    }
}
