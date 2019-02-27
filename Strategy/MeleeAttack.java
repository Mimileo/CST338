/**
 * Author: Mireya Leon
 * Title: MeleeAttack.java
 * Date: 2/27/19
 * Abstract: MeleeAttack implements Attack,
 * it must implement all classes from Attack
 */


package Strategy;

public class MeleeAttack implements Attack {

    Monster attacker;

    public MeleeAttack(Monster attacker) {
        this.attacker = attacker;
    }

    @Override
    public Integer attack(Monster target) {
        String message = attacker + " uses a melee attack on " + target;
        System.out.println(message);
        return null;
    }
}
