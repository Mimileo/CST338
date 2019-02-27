/**
 * Author: Mireya Leon
 * Title: Attack.java
 * Date: 2/27/19
 * Abstract: Attack is child interface of Ability
 */


package Strategy;

public interface Attack extends Ability {

    public Integer attack(Monster m);
}
