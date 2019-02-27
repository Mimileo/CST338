/**
 * Author: Mireya Leon
 * Title: MeleeAttack.java
 * Date: 2/27/19
 * Abstract: RangedAttack implements Attack,
 * it must implement all classes from Attack
 */


package Strategy;

public class RangedAttack implements Attack {

    Monster attacker;

    public RangedAttack(Monster attacker) {
        this.attacker = attacker;
    }

    @Override
    public Integer attack(Monster target) {
        String message = attacker + " uses a ranged attack on " + target;
        System.out.println(message);
        return null;
    }
}
