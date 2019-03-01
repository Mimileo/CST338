package Monsters;
/**
 * Author: Mireya Leon
 * Title: Monster.java
 * Date: 2/27/19
 * Abstract: abstract super class Strategy.Monster
 */


import Abilities.Attack;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public abstract class Monster {

    private Integer hp;
    private Integer xp = 10;
    Integer agi = 5;
    Integer def = 5;
    Integer str = 5;
    Attack attack;
    private Integer maxHP = null;
    private HashMap<String, Integer> items;

    public Monster(Integer maxHP, Integer xp, HashMap<String, Integer> items) {
        this.maxHP = maxHP;
        hp = this.maxHP;
        this.xp = xp;
        this.items = items;
    }

    public Integer attackTarget(Monster m) {
        return attack.attack(m);
    }


    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Integer getXp() {
        return xp;
    }

    public HashMap<String, Integer> getItems() {
        return items;
    }

    public void setItems(HashMap<String, Integer> items) {
        this.items = items;
    }

    public Integer getMaxHP() {
        return maxHP;
    }

    public Integer getAgi() {
        return agi;
    }

    public Integer getDef() {
        return def;
    }

    public Integer getStr() {
        return str;
    }

    Integer getAttribute(Integer min, Integer max){
        Random rand = new Random();

        if(min > max) {
            Integer temp = min;
            min = max;
            max = temp;
        }

        // returns a random number between min and max inclusive
        return rand.nextInt(max-min) + min;

    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monster monster = (Monster) o;
        return Objects.equals(hp, monster.hp) &&
                Objects.equals(xp, monster.xp) &&
                Objects.equals(maxHP, monster.maxHP) &&
                Objects.equals(items, monster.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hp, xp, maxHP, items);
    }

    @Override
    public String toString() {
        return "hp=" + hp + "/" + maxHP;
    }

}
